package ud.skript.sashie;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.FileUtils;
import ch.njol.util.coll.iterator.EnumerationIterable;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ud.skript.sashie.skDragon.emotes.SkullConfig;
import ud.skript.sashie.skDragon.emotes.SkullEvents1_12;
import ud.skript.sashie.skDragon.emotes.SkullEvents1_8;
import ud.skript.sashie.skDragon.emotes.SkullEvents1_9;
import ud.skript.sashie.skDragon.metrics.Metrics;
import ud.skript.sashie.skDragon.particleEngine.utils.ItemFountainEvents;
import ud.skript.sashie.skDragon.particleEngine.utils.PlayerEvents;
import ud.skript.sashie.skDragon.particleEngine.utils.SchedulingManager;
import ud.skript.sashie.skDragon.registration.AnnotationParser;
import ud.skript.sashie.skDragon.registration.Documentation;
import ud.skript.sashie.skDragon.registration.Events;
import ud.skript.sashie.skDragon.registration.Types;

public class skDragonCore extends JavaPlugin {
   public static final Logger LOGGER = Bukkit.getServer() != null ? Bukkit.getLogger() : Logger.getLogger("global");
   public static skDragonCore skdragoncore;
   public static String version;
   public static int serverVersion;
   public static List notInUseItems = new ArrayList();
   public static boolean UpdateCheck;
   public static int UpdateTimer;
   public static boolean UpdateMsgOps;
   public static boolean DocsGen;
   public static String configVersion = "1";
   private SkriptAddon addon;

   public void onEnable() {
      skdragoncore = this;
      version = this.getDescription().getVersion();
      String initServerVer = Bukkit.getServer().getClass().getPackage().getName().substring(23);
      serverVersion = Integer.parseInt(Character.toString(initServerVer.charAt(3)));
      if (serverVersion == 1 && Integer.parseInt(Character.toString(initServerVer.charAt(4))) >= 0) {
         serverVersion = Integer.parseInt(String.valueOf(Integer.parseInt(Character.toString(initServerVer.charAt(3)))) + Integer.parseInt(Character.toString(initServerVer.charAt(4))));
      }

      this.startMetrics();
      this.updateConfigFile();
      this.loadConfig();
      this.loadConfigData();
      this.initResources();
      Plugin skript = Bukkit.getServer().getPluginManager().getPlugin("Skript");
      if (skript != null) {
         this.addon = Skript.registerAddon(skdragoncore);
         sendLog("Plugin has been Enabled");
         if (isDragonTravelEnabled()) {
            sendLog("DragonTravel was found and registered into Skript!");
            Events.dragonTravelEvents();
            Events.dragonTravelValues();
         }

         Types.particleEffects();
         Types.rotationPlane();
         if (serverVersion == 8) {
            Bukkit.getServer().getPluginManager().registerEvents(new SkullEvents1_8(), skdragoncore);
         } else if (serverVersion >= 9) {
            Bukkit.getServer().getPluginManager().registerEvents(new SkullEvents1_9(), skdragoncore);
         } else if (serverVersion >= 12) {
            Bukkit.getServer().getPluginManager().registerEvents(new SkullEvents1_12(), skdragoncore);
         }

         Types.emotes();
         Events.emoteEvents();
         Types.fontStyle();
         AnnotationParser ap = new AnnotationParser();

         try {
            ap.register();
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         if (DocsGen) {
            Documentation docs = new Documentation();
            docs.setUpSyntaxes();
            docs.writeHTML();
         }

         SkullConfig emoteConfig = new SkullConfig();
         emoteConfig.initData();
         Bukkit.getPluginManager().registerEvents(new PlayerEvents(), skdragoncore);
         Bukkit.getPluginManager().registerEvents(new ItemFountainEvents(), skdragoncore);
         if (UpdateCheck) {
            SchedulingManager.runAsyncDelayed(skdragoncore::updateCheck, 1);
            if (!UpdateMsgOps) {
               SchedulingManager.runAsyncRepeating(skdragoncore::updateCheckOp, 1, UpdateTimer);
            }
         } else {
            sendLog("Update checking is disabled, you should consider enabling them again!");
         }
      } else {
         Bukkit.getPluginManager().disablePlugin(skdragoncore);
         sendLog("Plugin is now disabled. Why you no haz Skript?");
      }

   }

   public void onDisable() {
      HandlerList.unregisterAll(this);
      Iterator var2 = Bukkit.getWorlds().iterator();

      while(var2.hasNext()) {
         World world = (World)var2.next();
         Iterator var4 = world.getEntities().iterator();

         while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            if (notInUseItems.contains(entity.getUniqueId())) {
               notInUseItems.remove(entity.getUniqueId());
               entity.remove();
            }
         }
      }

   }

   public static boolean isDragonTravelEnabled() {
      Plugin dragonTravel = Bukkit.getServer().getPluginManager().getPlugin("DragonTravel");
      return dragonTravel != null;
   }

   public static skDragonCore getInstance() {
      return skdragoncore;
   }

   public static File getFolder() {
      return skdragoncore.getDataFolder();
   }

   public void loadConfig() {
      this.saveDefaultConfig();
   }

   private void startMetrics() {
      Metrics metrics = new Metrics(this);
      metrics.addCustomChart(new Metrics.SimplePie("skript_version", new Callable() {
         public String call() throws Exception {
            return Skript.getVersion().toString();
         }
      }));
   }

   private void initResources() {
      if (!this.getDataFolder().isDirectory()) {
         this.getDataFolder().mkdirs();
      }

      File capes = new File(this.getDataFolder(), "capes" + File.separator);
      if (!capes.isDirectory()) {
         ZipFile f = null;

         try {
            if (!capes.mkdirs()) {
               throw new IOException("Could not create the directory " + capes);
            }

            f = new ZipFile(this.getFile());
            Iterator var4 = (new EnumerationIterable(f.entries())).iterator();

            while(var4.hasNext()) {
               ZipEntry e = (ZipEntry)var4.next();
               if (!e.isDirectory()) {
                  File saveTo = null;
                  if (e.getName().endsWith(".png")) {
                     File cf = new File(this.getDataFolder(), e.getName());
                     if (!cf.exists()) {
                        saveTo = cf;
                     }
                  }

                  if (saveTo != null) {
                     InputStream in = f.getInputStream(e);

                     try {
                        assert in != null;

                        FileUtils.save(in, saveTo);
                     } finally {
                        in.close();
                     }
                  }
               }
            }

            sendLog("Successfully generated default cape file.");
         } catch (ZipException var25) {
         } catch (IOException var26) {
            sendLog("Error generating default cape file");
         } finally {
            if (f != null) {
               try {
                  f.close();
               } catch (IOException var23) {
               }
            }

         }
      }

   }

   public void loadConfigData() {
      UpdateCheck = this.getConfig().getBoolean("CheckForUpdates.Enabled");
      UpdateTimer = this.getConfig().getInt("CheckForUpdates.UpdateTimer");
      UpdateMsgOps = this.getConfig().getBoolean("CheckForUpdates.JoinMessageOps");
      DocsGen = this.getConfig().getBoolean("Documentation.Enabled");
   }

   public void updateConfigFile() {
      String v = this.getConfig().getString("version");
      File configFile = new File(this.getDataFolder(), "config.yml");
      if (configFile.exists()) {
         FileConfiguration config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
         v = config.getString("version");
      }

      if (v == null || !v.equals(configVersion)) {
         sendLog("New config found, updating file!");
         if (configFile.exists()) {
            configFile.delete();
         }

         this.loadConfig();
      }

   }

   public static void sendMsg(Player player, String string) {
      player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "skDragon" + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN + string);
   }

   public static void sendOpMsg(String string) {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(true) {
         Player player;
         do {
            if (!var2.hasNext()) {
               return;
            }

            player = (Player)var2.next();
         } while(!player.isOp() && !player.hasPermission("skdragon.updates"));

         player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "skDragon" + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN + string);
      }
   }

   public static void sendLog(String string) {
      Bukkit.getServer().getLogger().info(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "skDragon" + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN + string);
   }

   public static void warn(String error) {
      LOGGER.warning("[skDragon] " + error);
   }

   public static void error(String error) {
      LOGGER.severe("[skDragon] " + error);
   }

   public static void sendExLog(String string, String className, Integer type) {
      if (type == 0) {
         Bukkit.getServer().getLogger().severe("[skDragon] v" + version + ": " + string + " (" + className + ".class)");
         Bukkit.broadcast(ChatColor.RED + "[skDragon: ERROR]" + ChatColor.GRAY + " v" + version + ": " + string + " (" + className + ".class)", "skdragon.debug");
      } else if (type == 1) {
         Bukkit.getServer().getLogger().severe("[skDragon] v" + version + ": " + string + " (" + className + ".class)");
      } else if (type == 2) {
         Bukkit.getServer().getLogger().warning("[skDragon] v" + version + ": " + string + " (" + className + ".class)");
         Bukkit.broadcast(ChatColor.GOLD + "[skDragon: WARNING]" + ChatColor.GRAY + " v" + version + ": " + string + " (" + className + ".class)", "skdragon.debug");
      } else if (type == 3) {
         Bukkit.getServer().getLogger().warning("[skDragon] v" + version + ": " + string + " (idName: " + className + ")");
      } else if (type == 4) {
         Skript.error("[skDragon] v" + version + ": " + string + " (" + className + ".class)", ErrorQuality.SEMANTIC_ERROR);
      }

   }

   private void updateCheck() {
      String newVer = "";
      sendLog("Checking for updates now!");

      try {
         InputStream is = (new URL("http://pastebin.com/raw/jCpT9A9j")).openStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         newVer = br.readLine();
         if (!Objects.equals(version, newVer)) {
            sendLog("v" + newVer + " is available");

            String line;
            while((line = br.readLine()) != null) {
               sendLog(line);
            }
         } else {
            sendLog("You have the latest version!");
         }

         br.close();
         is.close();
      } catch (Exception var5) {
         sendLog("Update check failed! Most likey you aren't connected to the internet!");
      }

   }

   private void updateCheckOp() {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(true) {
         Player player;
         do {
            if (!var2.hasNext()) {
               return;
            }

            player = (Player)var2.next();
         } while(!player.isOp() && !player.hasPermission("skdragon.updates"));

         String newVer = "";
         sendOpMsg("Checking for updates now!");

         try {
            InputStream is = (new URL("http://pastebin.com/raw/jCpT9A9j")).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            newVer = br.readLine();
            if (!Objects.equals(version, newVer)) {
               sendMsg(player, "Hey " + player.getName() + " v" + newVer + " is available");

               String line;
               while((line = br.readLine()) != null) {
                  sendMsg(player, line);
               }
            } else {
               sendLog("You have the latest version!");
            }

            br.close();
            is.close();
         } catch (Exception var7) {
            sendExLog(var7.getCause().getMessage(), "skDragonCore", 0);
         }
      }
   }
}
