package ud.skript.sashie.skDragon.metrics;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Metrics {
   private static boolean logFailedRequests;
   private static String serverUUID;
   private final JavaPlugin plugin;
   private final List charts = new ArrayList();

   static {
      if (System.getProperty("bstats.relocatecheck") == null || !System.getProperty("bstats.relocatecheck").equals("false")) {
         String defaultPackage = new String(new byte[]{111, 114, 103, 46, 98, 115, 116, 97, 116, 115, 46, 98, 117, 107, 107, 105, 116});
         String examplePackage = new String(new byte[]{121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101});
         if (Metrics.class.getPackage().getName().equals(defaultPackage) || Metrics.class.getPackage().getName().equals(examplePackage)) {
            throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
         }
      }

   }

   public Metrics(JavaPlugin plugin) {
      if (plugin == null) {
         throw new IllegalArgumentException("Plugin cannot be null!");
      } else {
         this.plugin = plugin;
         File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
         File configFile = new File(bStatsFolder, "config.yml");
         YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
         if (!config.isSet("serverUuid")) {
            config.addDefault("enabled", true);
            config.addDefault("serverUuid", UUID.randomUUID().toString());
            config.addDefault("logFailedRequests", false);
            config.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)").copyDefaults(true);

            try {
               config.save(configFile);
            } catch (IOException var9) {
            }
         }

         serverUUID = config.getString("serverUuid");
         logFailedRequests = config.getBoolean("logFailedRequests", false);
         if (config.getBoolean("enabled", true)) {
            boolean found = false;
            Iterator var7 = Bukkit.getServicesManager().getKnownServices().iterator();

            while(var7.hasNext()) {
               Class service = (Class)var7.next();

               try {
                  service.getField("B_STATS_VERSION");
                  found = true;
                  break;
               } catch (NoSuchFieldException var10) {
               }
            }

            Bukkit.getServicesManager().register(Metrics.class, this, plugin, ServicePriority.Normal);
            if (!found) {
               this.startSubmitting();
            }
         }

      }
   }

   public void addCustomChart(Metrics.CustomChart chart) {
      if (chart == null) {
         throw new IllegalArgumentException("Chart cannot be null!");
      } else {
         this.charts.add(chart);
      }
   }

   private void startSubmitting() {
      final Timer timer = new Timer(true);
      timer.scheduleAtFixedRate(new TimerTask() {
         public void run() {
            if (!Metrics.this.plugin.isEnabled()) {
               timer.cancel();
            } else {
               Bukkit.getScheduler().runTask(Metrics.this.plugin, new Runnable() {
                  public void run() {
                     Metrics.this.submitData();
                  }
               });
            }
         }
      }, 300000L, 1800000L);
   }

   public JSONObject getPluginData() {
      JSONObject data = new JSONObject();
      String pluginName = this.plugin.getDescription().getName();
      String pluginVersion = this.plugin.getDescription().getVersion();
      data.put("pluginName", pluginName);
      data.put("pluginVersion", pluginVersion);
      JSONArray customCharts = new JSONArray();
      Iterator var6 = this.charts.iterator();

      while(var6.hasNext()) {
         Metrics.CustomChart customChart = (Metrics.CustomChart)var6.next();
         JSONObject chart = customChart.getRequestJsonObject();
         if (chart != null) {
            customCharts.add(chart);
         }
      }

      data.put("customCharts", customCharts);
      return data;
   }

   private JSONObject getServerData() {
      int playerAmount;
      try {
         Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers");
         playerAmount = onlinePlayersMethod.getReturnType().equals(Collection.class) ? ((Collection)onlinePlayersMethod.invoke(Bukkit.getServer())).size() : ((Player[])onlinePlayersMethod.invoke(Bukkit.getServer())).length;
      } catch (Exception var10) {
         playerAmount = Bukkit.getOnlinePlayers().size();
      }

      int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
      String bukkitVersion = Bukkit.getVersion();
      bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
      String javaVersion = System.getProperty("java.version");
      String osName = System.getProperty("os.name");
      String osArch = System.getProperty("os.arch");
      String osVersion = System.getProperty("os.version");
      int coreCount = Runtime.getRuntime().availableProcessors();
      JSONObject data = new JSONObject();
      data.put("serverUUID", serverUUID);
      data.put("playerAmount", playerAmount);
      data.put("onlineMode", onlineMode);
      data.put("bukkitVersion", bukkitVersion);
      data.put("javaVersion", javaVersion);
      data.put("osName", osName);
      data.put("osArch", osArch);
      data.put("osVersion", osVersion);
      data.put("coreCount", coreCount);
      return data;
   }

   private void submitData() {
      final JSONObject data = this.getServerData();
      JSONArray pluginData = new JSONArray();
      Iterator var4 = Bukkit.getServicesManager().getKnownServices().iterator();

      while(var4.hasNext()) {
         Class service = (Class)var4.next();

         try {
            service.getField("B_STATS_VERSION");
            Iterator var6 = Bukkit.getServicesManager().getRegistrations(service).iterator();

            while(var6.hasNext()) {
               RegisteredServiceProvider provider = (RegisteredServiceProvider)var6.next();

               try {
                  pluginData.add(provider.getService().getMethod("getPluginData").invoke(provider.getProvider()));
               } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NullPointerException var8) {
               }
            }
         } catch (NoSuchFieldException var9) {
         }
      }

      data.put("plugins", pluginData);
      (new Thread(new Runnable() {
         public void run() {
            try {
               Metrics.sendData(data);
            } catch (Exception var2) {
               if (Metrics.logFailedRequests) {
                  Metrics.this.plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + Metrics.this.plugin.getName(), var2);
               }
            }

         }
      })).start();
   }

   private static void sendData(JSONObject data) throws Exception {
      if (data == null) {
         throw new IllegalArgumentException("Data cannot be null!");
      } else if (Bukkit.isPrimaryThread()) {
         throw new IllegalAccessException("This method must not be called from the main thread!");
      } else {
         HttpsURLConnection connection = (HttpsURLConnection)(new URL("https://bStats.org/submitData/bukkit")).openConnection();
         byte[] compressedData = compress(data.toString());
         connection.setRequestMethod("POST");
         connection.addRequestProperty("Accept", "application/json");
         connection.addRequestProperty("Connection", "close");
         connection.addRequestProperty("Content-Encoding", "gzip");
         connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setRequestProperty("User-Agent", "MC-Server/1");
         connection.setDoOutput(true);
         DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
         outputStream.write(compressedData);
         outputStream.flush();
         outputStream.close();
         connection.getInputStream().close();
      }
   }

   private static byte[] compress(String str) throws IOException {
      if (str == null) {
         return null;
      } else {
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
         gzip.write(str.getBytes(StandardCharsets.UTF_8));
         gzip.close();
         return outputStream.toByteArray();
      }
   }

   public static class AdvancedBarChart extends Metrics.CustomChart {
      private final Callable callable;

      public AdvancedBarChart(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         JSONObject values = new JSONObject();
         Map map = (Map)this.callable.call();
         if (map != null && !map.isEmpty()) {
            boolean allSkipped = true;
            Iterator var6 = map.entrySet().iterator();

            while(true) {
               Entry entry;
               do {
                  if (!var6.hasNext()) {
                     if (allSkipped) {
                        return null;
                     }

                     data.put("values", values);
                     return data;
                  }

                  entry = (Entry)var6.next();
               } while(((int[])entry.getValue()).length == 0);

               allSkipped = false;
               JSONArray categoryValues = new JSONArray();
               int[] var11;
               int var10 = (var11 = (int[])entry.getValue()).length;

               for(int var9 = 0; var9 < var10; ++var9) {
                  int categoryValue = var11[var9];
                  categoryValues.add(categoryValue);
               }

               values.put(entry.getKey(), categoryValues);
            }
         } else {
            return null;
         }
      }
   }

   public static class AdvancedPie extends Metrics.CustomChart {
      private final Callable callable;

      public AdvancedPie(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         JSONObject values = new JSONObject();
         Map map = (Map)this.callable.call();
         if (map != null && !map.isEmpty()) {
            boolean allSkipped = true;
            Iterator var6 = map.entrySet().iterator();

            while(var6.hasNext()) {
               Entry entry = (Entry)var6.next();
               if ((Integer)entry.getValue() != 0) {
                  allSkipped = false;
                  values.put(entry.getKey(), entry.getValue());
               }
            }

            if (allSkipped) {
               return null;
            } else {
               data.put("values", values);
               return data;
            }
         } else {
            return null;
         }
      }
   }

   public abstract static class CustomChart {
      final String chartId;

      CustomChart(String chartId) {
         if (chartId != null && !chartId.isEmpty()) {
            this.chartId = chartId;
         } else {
            throw new IllegalArgumentException("ChartId cannot be null or empty!");
         }
      }

      private JSONObject getRequestJsonObject() {
         JSONObject chart = new JSONObject();
         chart.put("chartId", this.chartId);

         try {
            JSONObject data = this.getChartData();
            if (data == null) {
               return null;
            } else {
               chart.put("data", data);
               return chart;
            }
         } catch (Throwable var3) {
            if (Metrics.logFailedRequests) {
               Bukkit.getLogger().log(Level.WARNING, "Failed to get data for custom chart with id " + this.chartId, var3);
            }

            return null;
         }
      }

      protected abstract JSONObject getChartData() throws Exception;
   }

   public static class DrilldownPie extends Metrics.CustomChart {
      private final Callable callable;

      public DrilldownPie(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      public JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         JSONObject values = new JSONObject();
         Map map = (Map)this.callable.call();
         if (map != null && !map.isEmpty()) {
            boolean reallyAllSkipped = true;
            Iterator var6 = map.entrySet().iterator();

            while(var6.hasNext()) {
               Entry entryValues = (Entry)var6.next();
               JSONObject value = new JSONObject();
               boolean allSkipped = true;

               for(Iterator var10 = ((Map)map.get(entryValues.getKey())).entrySet().iterator(); var10.hasNext(); allSkipped = false) {
                  Entry valueEntry = (Entry)var10.next();
                  value.put(valueEntry.getKey(), valueEntry.getValue());
               }

               if (!allSkipped) {
                  reallyAllSkipped = false;
                  values.put(entryValues.getKey(), value);
               }
            }

            if (reallyAllSkipped) {
               return null;
            } else {
               data.put("values", values);
               return data;
            }
         } else {
            return null;
         }
      }
   }

   public static class MultiLineChart extends Metrics.CustomChart {
      private final Callable callable;

      public MultiLineChart(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         JSONObject values = new JSONObject();
         Map map = (Map)this.callable.call();
         if (map != null && !map.isEmpty()) {
            boolean allSkipped = true;
            Iterator var6 = map.entrySet().iterator();

            while(var6.hasNext()) {
               Entry entry = (Entry)var6.next();
               if ((Integer)entry.getValue() != 0) {
                  allSkipped = false;
                  values.put(entry.getKey(), entry.getValue());
               }
            }

            if (allSkipped) {
               return null;
            } else {
               data.put("values", values);
               return data;
            }
         } else {
            return null;
         }
      }
   }

   public static class SimpleBarChart extends Metrics.CustomChart {
      private final Callable callable;

      public SimpleBarChart(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         JSONObject values = new JSONObject();
         Map map = (Map)this.callable.call();
         if (map != null && !map.isEmpty()) {
            Iterator var5 = map.entrySet().iterator();

            while(var5.hasNext()) {
               Entry entry = (Entry)var5.next();
               JSONArray categoryValues = new JSONArray();
               categoryValues.add(entry.getValue());
               values.put(entry.getKey(), categoryValues);
            }

            data.put("values", values);
            return data;
         } else {
            return null;
         }
      }
   }

   public static class SimplePie extends Metrics.CustomChart {
      private final Callable callable;

      public SimplePie(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         String value = (String)this.callable.call();
         if (value != null && !value.isEmpty()) {
            data.put("value", value);
            return data;
         } else {
            return null;
         }
      }
   }

   public static class SingleLineChart extends Metrics.CustomChart {
      private final Callable callable;

      public SingleLineChart(String chartId, Callable callable) {
         super(chartId);
         this.callable = callable;
      }

      protected JSONObject getChartData() throws Exception {
         JSONObject data = new JSONObject();
         int value = (Integer)this.callable.call();
         if (value == 0) {
            return null;
         } else {
            data.put("value", value);
            return data;
         }
      }
   }
}
