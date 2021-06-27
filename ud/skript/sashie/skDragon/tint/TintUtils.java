package ud.skript.sashie.skDragon.tint;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TintUtils {
   private static Method handle;
   private static Method sendPacket;
   private static Method worldServer;
   private static Field world;
   private static Method center;
   private static Method distance;
   private static Method time;
   private static Method movement;
   private static Field player_connection;
   private static Constructor constructor;
   private static Constructor borderConstructor;
   private static Object constant;
   protected List togglelist = new ArrayList();
   protected int fadeTime;
   protected int intensity;
   protected int minHealth;
   protected List fadetimelist;
   protected List intensitylist;

   static {
      try {
         worldServer = getClass("org.bukkit.craftbukkit", "CraftWorld").getMethod("getHandle");
         handle = getClass("org.bukkit.craftbukkit", "entity.CraftPlayer").getMethod("getHandle");
         player_connection = getClass("net.minecraft.server", "EntityPlayer").getField("playerConnection");
         Method[] var3;
         int var2 = (var3 = getClass("net.minecraft.server", "PlayerConnection").getMethods()).length;

         for(int var1 = 0; var1 < var2; ++var1) {
            Method m = var3[var1];
            if (m.getName().equals("sendPacket")) {
               sendPacket = m;
               break;
            }
         }

         Class enumClass;
         try {
            enumClass = getClass("net.minecraft.server", "EnumWorldBorderAction");
         } catch (ClassNotFoundException var10) {
            enumClass = getClass("net.minecraft.server", "PacketPlayOutWorldBorder$EnumWorldBorderAction");
         }

         constructor = getClass("net.minecraft.server", "PacketPlayOutWorldBorder").getConstructor(getClass("net.minecraft.server", "WorldBorder"), enumClass);
         borderConstructor = getClass("net.minecraft.server", "WorldBorder").getConstructor();
         Method[] methods = getClass("net.minecraft.server", "WorldBorder").getMethods();
         String setCenter = "setCenter";
         String setWarningDistance = "setWarningDistance";
         String setWarningTime = "setWarningTime";
         String transitionSizeBetween = "transitionSizeBetween";
         if (!inClass(methods, setCenter)) {
            setCenter = "c";
         }

         if (!inClass(methods, setWarningDistance)) {
            setWarningDistance = "c";
         }

         if (!inClass(methods, setWarningTime)) {
            setWarningTime = "b";
         }

         if (!inClass(methods, transitionSizeBetween)) {
            transitionSizeBetween = "a";
         }

         world = getClass("net.minecraft.server", "WorldBorder").getField("world");
         center = getClass("net.minecraft.server", "WorldBorder").getMethod(setCenter, Double.TYPE, Double.TYPE);
         distance = getClass("net.minecraft.server", "WorldBorder").getMethod(setWarningDistance, Integer.TYPE);
         time = getClass("net.minecraft.server", "WorldBorder").getMethod(setWarningTime, Integer.TYPE);
         movement = getClass("net.minecraft.server", "WorldBorder").getMethod(transitionSizeBetween, Double.TYPE, Double.TYPE, Long.TYPE);
         Object[] var9;
         int var8 = (var9 = enumClass.getEnumConstants()).length;

         for(int var7 = 0; var7 < var8; ++var7) {
            Object o = var9[var7];
            if (o.toString().equals("INITIALIZE")) {
               constant = o;
               break;
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   private static boolean inClass(Method[] methods, String methodName) {
      Method[] var5 = methods;
      int var4 = methods.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Method m = var5[var3];
         if (m.getName() == methodName) {
            return true;
         }
      }

      return false;
   }

   private static Class getClass(String prefix, String name) throws Exception {
      return Class.forName(prefix + "." + Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + "." + name);
   }

   protected void sendBorder(Player p, int percentage, int fadeTime, int intensity) {
      percentage = Math.round((float)(percentage / intensity));
      this.setBorder(p, percentage);
      if (fadeTime >= 0) {
         this.fadeBorder(p, percentage, (long)fadeTime);
      }

   }

   protected void fadeBorder(Player p, int percentage, long time) {
      int dist = -10000 * percentage + 1300000;
      this.sendWorldBorderPacket(p, 0, 200000.0D, (double)dist, 1000L * time + 4000L);
   }

   protected void removeBorder(Player p) {
      this.sendWorldBorderPacket(p, 0, 200000.0D, 200000.0D, 0L);
   }

   protected void setBorder(Player p, int percentage) {
      int dist = -10000 * percentage + 1300000;
      this.sendWorldBorderPacket(p, dist, 200000.0D, 200000.0D, 0L);
   }

   protected void sendWorldBorderPacket(Player p, int dist, double oldRadius, double newRadius, long delay) {
      try {
         Object worldBorder = borderConstructor.newInstance();
         world.set(worldBorder, worldServer.invoke(p.getWorld()));
         center.invoke(worldBorder, p.getLocation().getX(), p.getLocation().getY());
         distance.invoke(worldBorder, dist);
         time.invoke(worldBorder, 15);
         movement.invoke(worldBorder, oldRadius, newRadius, delay);
         Object packet = constructor.newInstance(worldBorder, constant);
         sendPacket.invoke(player_connection.get(handle.invoke(p)), packet);
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }

   protected int getPlayerHealth(Player p) {
      return (int)p.getHealth();
   }

   protected int getMaxPlayerHealth(Player p) {
      return (int)p.getMaxHealth();
   }

   protected int getPlayerMissingHearts(Player p) {
      return (int)p.getMaxHealth();
   }

   protected int getPlayerHealthPercentage(Player p) {
      int health = this.getPlayerHealth(p);
      int maxhealth = this.getMaxPlayerHealth(p);
      return Math.round((float)(health * 100 / maxhealth));
   }

   protected void setTintFade(int fadeTime) {
      this.fadeTime = fadeTime;
   }

   protected void setIntensity(int intensity) {
      this.intensity = intensity;
   }

   protected void setMinHealth(int minHealth) {
      this.minHealth = minHealth;
   }

   protected int getTintFade() {
      return this.fadeTime;
   }

   protected int getIntensity() {
      return this.intensity;
   }

   protected int getMinHealth() {
      return this.minHealth;
   }

   protected void disablePlayerTint(Player p) {
      String pname = p.getName();
      if (this.togglelist.contains(pname)) {
         this.togglelist.remove(pname);
      }

   }

   protected void enablePlayerTint(Player p) {
      this.togglelist.add(p.getName());
   }

   protected void togglePlayerTint(Player p) {
      if (this.isTintEnabled(p)) {
         this.disablePlayerTint(p);
      } else {
         this.enablePlayerTint(p);
      }

   }

   protected boolean isTintEnabled(Player p) {
      return this.togglelist.contains(p.getName());
   }
}
