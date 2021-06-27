package ud.skript.sashie.skDragon.particleEngine.maths;

import ch.njol.skript.Skript;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.MathUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SchedulingManager;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public class EffectsLib {
   public static final HashMap arraylist = new HashMap();
   public static final HashMap arraylist2 = new HashMap();
   public static final float PI = 3.1415927F;
   public static final float PI2 = 6.2831855F;
   public static final float degreesToRadians = 0.017453292F;
   private static int itStep = 0;
   public static int taskID;

   public static void stopAll() {
      String[] keys = new String[arraylist.keySet().size()];
      arraylist.keySet().toArray(keys);
      String[] var4 = keys;
      int var3 = keys.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         String key = var4[var2];
         if (key != null) {
            if (!key.contains("Fountain: ")) {
               stopEffect(key);
            } else if (arraylist.containsKey(key)) {
               Iterator var6 = Bukkit.getWorlds().iterator();

               while(var6.hasNext()) {
                  World world = (World)var6.next();
                  Iterator var8 = world.getEntities().iterator();

                  while(var8.hasNext()) {
                     Entity entity = (Entity)var8.next();
                     if (skDragonCore.notInUseItems.contains(entity.getUniqueId())) {
                        skDragonCore.notInUseItems.remove(entity.getUniqueId());
                        entity.remove();
                     }
                  }
               }

               Bukkit.getScheduler().cancelTask((Integer)arraylist.get(key));
               arraylist.remove(key);
            }
         }
      }

      String[] keys2 = new String[arraylist2.keySet().size()];
      arraylist2.keySet().toArray(keys2);
      String[] var12 = keys2;
      int var11 = keys2.length;

      for(var3 = 0; var3 < var11; ++var3) {
         String key2 = var12[var3];
         stopEffect2(key2);
      }

   }

   public static void stopItemEffect(String idName) {
      if (arraylist.containsKey("Fountain: " + idName)) {
         Iterator var2 = Bukkit.getWorlds().iterator();

         while(var2.hasNext()) {
            World world = (World)var2.next();
            Iterator var4 = world.getEntities().iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               if (skDragonCore.notInUseItems.contains(entity.getUniqueId())) {
                  skDragonCore.notInUseItems.remove(entity.getUniqueId());
                  entity.remove();
               }
            }
         }

         Bukkit.getScheduler().cancelTask((Integer)arraylist.get("Fountain: " + idName));
         arraylist.remove("Fountain: " + idName);
      }

   }

   public static void stopEffect(String idName) {
      if (arraylist.containsKey(idName)) {
         Bukkit.getScheduler().cancelTask((Integer)arraylist.get(idName));
         arraylist.remove(idName);
      }

   }

   public static void stopEffect2(String idName) {
      if (arraylist2.containsKey(idName)) {
         Bukkit.getScheduler().cancelTask((Integer)arraylist2.get(idName));
         arraylist2.remove(idName);
      }

   }

   public static void stopEffect(String idName, int id) {
      if (arraylist.containsKey(idName)) {
         Bukkit.getScheduler().cancelTask(id);
         arraylist.remove(idName);
      }

   }

   public static void stopEffect2(String idName, int id) {
      if (arraylist2.containsKey(idName)) {
         Bukkit.getScheduler().cancelTask(id);
         arraylist2.remove(idName);
      }

   }

   public static boolean isEffectActive(String idName) {
      return !arraylist.containsKey(idName);
   }

   public static void stopEffectDelayed(long delay, final String idName) {
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(skDragonCore.skdragoncore, new Runnable() {
         public void run() {
            if (EffectsLib.arraylist.get(idName) != null) {
               Bukkit.getScheduler().cancelTask((Integer)EffectsLib.arraylist.get(idName));
               EffectsLib.arraylist.remove(idName);
            }

         }
      }, delay);
   }

   public static void stopEffectDelayed2(int delay, final String idName) {
      Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(skDragonCore.skdragoncore, new Runnable() {
         public void run() {
            if (EffectsLib.arraylist2.get(idName) != null) {
               Bukkit.getScheduler().cancelTask((Integer)EffectsLib.arraylist2.get(idName));
               EffectsLib.arraylist2.remove(idName);
            }

         }
      }, (long)delay);
   }

   public static void stopEffectDelayed(long delay, final int id, final String idName) {
      if (delay > 0L) {
         Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public void run() {
               Bukkit.getScheduler().cancelTask(id);
               EffectsLib.arraylist.remove(idName);
            }
         }, delay);
      }

   }

   public static void stopEffectDelayed2(int delay, final int id, final String idName) {
      if (delay > 0) {
         Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(skDragonCore.skdragoncore, new Runnable() {
            public void run() {
               Bukkit.getScheduler().cancelTask(id);
               EffectsLib.arraylist2.remove(idName);
            }
         }, (long)delay);
      }

   }

   public static void stopEffectRepeatDelay(int repeat, int delay, final String idName) {
      int finalDelay = delay * repeat;
      Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(skDragonCore.skdragoncore, new Runnable() {
         public void run() {
            EffectsLib.stopEffect(idName);
            EffectsLib.stopEffect2(idName);
         }
      }, (long)finalDelay);
   }

   public static void stopEffectRepeatDelay(final int repeat, int delay, final int id, final String idName) {
      Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(skDragonCore.skdragoncore, new Runnable() {
         public void run() {
            if (EffectsLib.itStep >= repeat) {
               Bukkit.getScheduler().cancelTask(id);
               EffectsLib.arraylist.remove(idName);
            }

         }
      }, (long)delay);
   }

   private static void itStep() {
      ++itStep;
   }

   public static Set getAllEffectIds() {
      return arraylist.keySet();
   }

   public static Location getLocation(Object location) {
      if (location instanceof Entity) {
         return ((Entity)location).getLocation();
      } else {
         return location instanceof Location ? new Location(((Location)location).getWorld(), ((Location)location).getX(), ((Location)location).getY(), ((Location)location).getZ(), ((Location)location).getYaw(), ((Location)location).getPitch()) : null;
      }
   }

   public static Location getLocation(Object location, String idName) {
      if (location instanceof Entity) {
         return ((Entity)location).getLocation();
      } else if (location instanceof Location) {
         return new Location(((Location)location).getWorld(), ((Location)location).getX(), ((Location)location).getY(), ((Location)location).getZ(), ((Location)location).getYaw(), ((Location)location).getPitch());
      } else {
         skDragonCore.sendExLog("The location used is invalid or null", idName, 3);
         stopItemEffect(idName);
         stopEffect(idName);
         return null;
      }
   }

   public static void foundNull(Location location, String idName, Exception ex) {
      if (location != null) {
         skDragonCore.sendExLog("Null found, effect stopped", idName, 3);
         ex.printStackTrace();
      }

   }

   public static void drawSimpleHalo(final String particle, final Object center, final Material dataMat, final byte dataID, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final float speed, final int particleDensity, final double visibleRange, final float offsetX, final float offsetY, final float offsetZ, long delayTicks, long delayBySecond, final boolean solid) {
      if (!arraylist.containsKey(idName)) {
         int normalHalo = Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            float step = 0.0F;
            public float hue;
            final double angle = (double)(3.1415927F / (float)particleDensity);
            Location location;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(0.0D, 2.05D, 0.0D);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (solid) {
                     Iterator var2 = SimpleShapes.getCircle(this.location, 0.3D, particleDensity).iterator();

                     while(var2.hasNext()) {
                        Location loc = (Location)var2.next();
                        ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, loc, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     }
                  } else {
                     Vector v = new Vector(Math.cos(this.angle * (double)this.step) * 0.30000001192092896D, 0.0D, Math.sin(this.angle * (double)this.step) * 0.30000001192092896D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     ++this.step;
                  }
               } catch (NullPointerException var3) {
                  EffectsLib.foundNull(this.location, idName, var3);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, normalHalo);
      }

   }

   public static void drawComplexCircle(final String particle, final Material dataMat, final byte dataID, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean enableRotation, final float radius, final float speed, final int particleDensity, float steps, final double visibleRange, final double xRotation, final double yRotation, final double zRotation, final float offsetX, final float offsetY, final float offsetZ, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int circle = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(steps) {
            public double angularVelocityX = 0.015707964077591896D;
            public double angularVelocityY = 0.018479958176612854D;
            public double angularVelocityZ = 0.020268339663743973D;
            public float step;
            public float hue;
            Vector v;
            Location location;

            {
               this.step = var1;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY + 1.0D, disZ);
                  double angle = (double)(this.step * (6.2831855F / (float)particleDensity));
                  this.v = new Vector();
                  this.v.setX(Math.cos(angle) * (double)radius);
                  this.v.setZ(Math.sin(angle) * (double)radius);
                  VectorUtils.rotateVector(this.v, xRotation, yRotation, zRotation);
                  if (enableRotation) {
                     VectorUtils.rotateVector(this.v, this.angularVelocityX * (double)this.step, this.angularVelocityY * (double)this.step, this.angularVelocityZ * (double)this.step);
                  }

                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  ++this.step;
               } catch (NullPointerException var3) {
                  EffectsLib.foundNull(this.location, idName, var3);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, circle);
      }

   }

   public static void drawAtom(final String particle, final Material dataMat, final byte dataID, final float speed, final String particle2, final Material dataMat2, final byte dataID2, final float speed2, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final float innerRadius, final int innerParticles, final int orbitParticles, final int orbitalCount, float steps, final double visibleRange, final double rotations, final float offsetX, final float offsetY, final float offsetZ, final float offsetX2, final float offsetY2, final float offsetZ2, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int atom = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(steps) {
            public double angularVelocity = 0.07853981852531433D;
            public float step;
            public float hue;
            Location location;

            {
               this.step = var1;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY + 3.0D, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  int i;
                  for(i = 0; i < innerParticles; ++i) {
                     Vector vx = RandomUtils.getRandomVector().multiply(0.5D * (double)innerRadius);
                     ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.add(vx), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                     this.location.subtract(vx);
                  }

                  for(i = 0; i < orbitParticles; ++i) {
                     double angle = (double)this.step * this.angularVelocity;

                     for(int j = 0; j < orbitalCount; ++j) {
                        double xRotation = (double)(3.1415927F / (float)orbitalCount * (float)j);
                        Vector v = new Vector();
                        v.setX(Math.sin(angle) * (0.6D + (double)innerRadius));
                        v.setY(Math.cos(angle) * (0.6D + (double)innerRadius));
                        VectorUtils.rotateAroundAxisX(v, xRotation);
                        VectorUtils.rotateAroundAxisY(v, rotations);
                        ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        this.location.subtract(v);
                     }

                     ++this.step;
                  }
               } catch (NullPointerException var8) {
                  EffectsLib.foundNull(this.location, idName, var8);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, atom);
      }

   }

   public static void drawArc(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final Object getTarget, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final int particleDensity, final float height, final float pitchMuliplier, final double visibleRange, final double disX, final double disY, final double disZ, final double disX2, final double disY2, final double disZ2, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int arc = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            int step = 0;
            public float hue;
            Location location;
            Location target;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.target = EffectsLib.getLocation(getTarget, idName);
                  if (this.location == null || this.target == null) {
                     return;
                  }

                  this.location.add(disX, disY, disZ);
                  this.target.add(disX2, disY2, disZ2);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  Vector link = this.target.toVector().subtract(this.location.toVector());
                  float length = (float)link.length();
                  float pitch = (float)((double)(pitchMuliplier * height) / Math.pow((double)length, 2.0D));

                  for(int i = 0; i < particleDensity; ++i) {
                     Vector v = link.clone().normalize().multiply(length * (float)i / (float)particleDensity);
                     float x = (float)i / (float)particleDensity * length - length / 2.0F;
                     float y = (float)((double)(-pitch) * Math.pow((double)x, 2.0D) + (double)height);
                     this.location.add(v).add(0.0D, (double)y, 0.0D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(0.0D, (double)y, 0.0D).subtract(v);
                     ++this.step;
                  }
               } catch (NullPointerException var8) {
                  EffectsLib.foundNull(this.location, idName, var8);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, arc);
      }

   }

   public static void drawDot(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean isFacing, final float faceAngle, final float radius, final double xRotation, final double yRotation, final double zRotation, final double disX, final double disY, final double disZ, final double visibleRange, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int simpleDot = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public float hue;
            Location location;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(0.0D + disX, 0.30000001192092896D + disY, 0.0D + disZ);
                  double inc = 6.2831854820251465D;
                  double angle = 1.0D * inc;
                  Vector v = new Vector();
                  v.setX(Math.cos(angle) * (double)radius);
                  v.setZ(Math.sin(angle) * (double)radius);
                  VectorUtils.rotateVector(v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (isFacing) {
                     this.location.setPitch(0.0F);
                     this.location.add(this.location.getDirection().multiply(-0.2D));
                     Location loc1R = this.location.clone();
                     loc1R.setYaw(loc1R.getYaw() + faceAngle);
                     Location loc2R = loc1R.clone().add(loc1R.getDirection().multiply(1));
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, loc2R.add(0.0D, 0.0D, 0.0D), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  } else {
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  }
               } catch (NullPointerException var8) {
                  EffectsLib.foundNull(this.location, idName, var8);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, simpleDot);
      }

   }

   public static void drawComplexSpiral(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean clockwise, final boolean scan, final double visibleRange, final double xRotation, final double yRotation, final double zRotation, final float radius, final float height, final float effectMod, final int circleDensity, final double disX, final double disY, final double disZ, float steps, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int complexSpiral = Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(steps) {
            float step;
            private float hue;
            float i;
            boolean up;
            Vector v;
            Location location;

            {
               this.step = var1;
               this.up = true;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  double angle = (double)(6.2831855F / (float)circleDensity * this.step);
                  double y = 0.3D * (double)this.i;
                  if (!clockwise) {
                     this.v = new Vector(Math.sin(angle) * (double)radius, y, Math.cos(angle) * (double)radius);
                  }

                  if (clockwise) {
                     this.v = new Vector(Math.cos(angle) * (double)radius, y, Math.sin(angle) * (double)radius);
                  }

                  VectorUtils.rotateVector(this.v, xRotation, yRotation, zRotation);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  ++this.step;
                  if (scan) {
                     if (this.i > height) {
                        this.up = false;
                     } else if (this.i < 0.0F) {
                        this.up = true;
                     }
                  } else {
                     if (this.i > height) {
                        this.i = 0.0F;
                     }

                     if (this.i < 0.0F) {
                        this.i = height;
                     }
                  }

                  if (this.up) {
                     this.i += effectMod;
                  }

                  if (!this.up) {
                     this.i -= effectMod;
                  }
               } catch (NullPointerException var5) {
                  EffectsLib.foundNull(this.location, idName, var5);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, complexSpiral);
      }

   }

   public static void drawBand(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final boolean rainbowMode, final double visibleRange, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond, final Player player) {
      if (!arraylist.containsKey(idName)) {
         int band = Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            private float hue;
            Location location;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);

                  for(int i = 0; i < 15; ++i) {
                     this.location.setY(this.location.getY() + 0.1D);
                     if (rainbowMode) {
                        this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                     }

                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  }
               } catch (NullPointerException var2) {
                  EffectsLib.foundNull(this.location, idName, var2);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, band);
      }

   }

   public static void drawNyanBand(final Object center, final String idName, final boolean isSinglePlayer, final double visibleRange, long delayTicks, long delayBySecond, final Player player) {
      if (!arraylist.containsKey(idName)) {
         int rainbowband = Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            Location location;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);

                  for(int i = 0; i < 15; ++i) {
                     ParticleEffect.valueOf("redstone").display(player, this.location, visibleRange, isSinglePlayer, (float)i);
                     this.location.setY(this.location.getY() + 0.1D);
                  }
               } catch (NullPointerException var2) {
                  EffectsLib.foundNull(this.location, idName, var2);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, rainbowband);
      }

   }

   public static void drawImage(final File file, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean enableRotation, final EffectsLib.Plane plane, int pixelStepX, int pixelStepY, long scaleSize, final double visibleRange, final double xRotation, final double yRotation, final double zRotation, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int colorImage = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(pixelStepX, pixelStepY, scaleSize) {
            int stepX;
            int stepY;
            float size;
            double angularVelocityX;
            double angularVelocityY;
            double angularVelocityZ;
            BufferedImage image;
            boolean isGif;
            File gifFile;
            int step;
            int rotationStep;
            int delay;
            Location location;
            boolean invert;
            // $FF: synthetic field
            private static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane;

            {
               this.stepX = var1;
               this.stepY = var2;
               this.size = 1.0F / (float)var3;
               this.angularVelocityX = 0.015707964077591896D;
               this.angularVelocityY = 0.018479958176612854D;
               this.angularVelocityZ = 0.020268339663743973D;
               this.image = null;
               this.isGif = false;
               this.gifFile = null;
               this.step = 0;
               this.rotationStep = 0;
               this.delay = 0;
               this.invert = false;
            }

            public void run() {
               try {
                  if (this.image == null && file != null) {
                     try {
                        this.image = ImageIO.read(file);
                        this.isGif = file.getName().endsWith(".gif");
                        this.gifFile = file;
                     } catch (Exception var12) {
                        Skript.warning("[skDragon] Error: Invalid file used, make sure the image is in /plugins/skDragon/");
                        this.image = null;
                     }
                  }

                  if (this.image == null) {
                     EffectsLib.stopEffect(idName);
                     Skript.warning("[skDragon] Error: The image failed to load, try another? :c");
                     return;
                  }

                  if (this.isGif) {
                     try {
                        this.image = this.getImg(this.step);
                     } catch (IOException var11) {
                        Skript.warning("[skDragon] Error: The .gif failed to load..");
                        var11.printStackTrace();
                     }

                     if (this.delay == 5) {
                        ++this.step;
                        this.delay = 0;
                     }

                     ++this.delay;
                  }

                  this.location = EffectsLib.getLocation(center, idName);
                  if (!(this.location instanceof Entity)) {
                     this.location.add(disX, disY, disZ);
                  }

                  for(int y = 0; y < this.image.getHeight(); y += this.stepY) {
                     for(int x = 0; x < this.image.getWidth(); x += this.stepX) {
                        int clr = this.image.getRGB(x, y);
                        if ((this.invert || clr != 0 && clr >> 24 != 0 && clr != 16777215) && (!this.invert || clr == 0 && clr >> 24 == 0 && clr != 16777215)) {
                           Vector v = (new Vector((float)this.image.getWidth() / 2.0F - (float)x, (float)this.image.getHeight() / 2.0F - (float)y, 0.0F)).multiply(this.size);
                           VectorUtils.rotateVector(v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                           if (enableRotation) {
                              double rotX = 0.0D;
                              double rotY = 0.0D;
                              double rotZ = 0.0D;
                              switch($SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane()[plane.ordinal()]) {
                              case 1:
                                 rotX = this.angularVelocityX * (double)this.rotationStep;
                                 break;
                              case 2:
                                 rotY = this.angularVelocityY * (double)this.rotationStep;
                                 break;
                              case 3:
                                 rotZ = this.angularVelocityZ * (double)this.rotationStep;
                                 break;
                              case 4:
                                 rotX = this.angularVelocityX * (double)this.rotationStep;
                                 rotY = this.angularVelocityY * (double)this.rotationStep;
                                 break;
                              case 5:
                                 rotX = this.angularVelocityX * (double)this.rotationStep;
                                 rotZ = this.angularVelocityZ * (double)this.rotationStep;
                                 break;
                              case 6:
                                 rotX = this.angularVelocityX * (double)this.rotationStep;
                                 rotY = this.angularVelocityY * (double)this.rotationStep;
                                 rotZ = this.angularVelocityZ * (double)this.rotationStep;
                                 break;
                              case 7:
                                 rotY = this.angularVelocityY * (double)this.rotationStep;
                                 rotZ = this.angularVelocityZ * (double)this.rotationStep;
                              }

                              VectorUtils.rotateVector(v, rotX, rotY, rotZ);
                           }

                           int r = (new Color(this.image.getRGB(x, y))).getRed();
                           int g = (new Color(this.image.getRGB(x, y))).getGreen();
                           int b = (new Color(this.image.getRGB(x, y))).getBlue();
                           ParticleEffect.valueOf("redstone").display(this.location.add(v), visibleRange, isSinglePlayer, player, r, g, b);
                           this.location.subtract(v);
                        }
                     }
                  }

                  ++this.rotationStep;
               } catch (NullPointerException var13) {
                  EffectsLib.foundNull(this.location, idName, var13);
               }

            }

            private BufferedImage getImg(int s) throws IOException {
               ArrayList images = new ArrayList();
               ImageReader reader = (ImageReader)ImageIO.getImageReadersBySuffix("GIF").next();
               ImageInputStream in = ImageIO.createImageInputStream(this.gifFile);
               reader.setInput(in);
               int i = 0;

               for(int count = reader.getNumImages(true); i < count; ++i) {
                  BufferedImage image = reader.read(i);
                  images.add(image);
               }

               if (this.step >= reader.getNumImages(true)) {
                  this.step = 0;
                  return (BufferedImage)images.get(s - 1);
               } else {
                  reader.dispose();
                  in.close();
                  return (BufferedImage)images.get(s);
               }
            }

            // $FF: synthetic method
            static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane() {
               int[] var10000 = $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane;
               if (var10000 != null) {
                  return var10000;
               } else {
                  int[] var0 = new int[EffectsLib.Plane.values().length];

                  try {
                     var0[EffectsLib.Plane.X.ordinal()] = 1;
                  } catch (NoSuchFieldError var7) {
                  }

                  try {
                     var0[EffectsLib.Plane.XY.ordinal()] = 4;
                  } catch (NoSuchFieldError var6) {
                  }

                  try {
                     var0[EffectsLib.Plane.XYZ.ordinal()] = 6;
                  } catch (NoSuchFieldError var5) {
                  }

                  try {
                     var0[EffectsLib.Plane.XZ.ordinal()] = 5;
                  } catch (NoSuchFieldError var4) {
                  }

                  try {
                     var0[EffectsLib.Plane.Y.ordinal()] = 2;
                  } catch (NoSuchFieldError var3) {
                  }

                  try {
                     var0[EffectsLib.Plane.YZ.ordinal()] = 7;
                  } catch (NoSuchFieldError var2) {
                  }

                  try {
                     var0[EffectsLib.Plane.Z.ordinal()] = 3;
                  } catch (NoSuchFieldError var1) {
                  }

                  $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane = var0;
                  return var0;
               }
            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, colorImage);
      }

   }

   public static void drawBreath(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final float finalArcPitch, final int finalArcs, final int finalParticleCount, final int finalStepPerIteration, final float finalLength, final double visibleRange, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int breath = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            protected int step = 0;
            protected final List rndF = new ArrayList(finalArcs);
            protected final List rndAngle = new ArrayList(finalArcs);
            Location location;
            public float hue;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, 1.475D + disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  for(int j = 0; j < finalStepPerIteration; ++j) {
                     if (this.step % finalParticleCount == 0) {
                        this.rndF.clear();
                        this.rndAngle.clear();
                     }

                     while(this.rndF.size() < finalArcs) {
                        this.rndF.add(RandomUtils.random.nextFloat());
                     }

                     while(this.rndAngle.size() < finalArcs) {
                        this.rndAngle.add(RandomUtils.getRandomAngle());
                     }

                     for(int i = 0; i < finalArcs; ++i) {
                        float pitch = (Float)this.rndF.get(i) * 2.0F * finalArcPitch - finalArcPitch;
                        float x = (float)(this.step % finalParticleCount) * finalLength / (float)finalParticleCount;
                        float y = (float)((double)pitch * Math.pow((double)x, 2.0D));
                        Vector v = new Vector(x, y, 0.0F);
                        VectorUtils.rotateAroundAxisX(v, (Double)this.rndAngle.get(i));
                        VectorUtils.rotateAroundAxisZ(v, (double)(-this.location.getPitch() * 0.017453292F));
                        VectorUtils.rotateAroundAxisY(v, -(this.location.getYaw() + 90.0F) * 0.017453292F);
                        ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        this.location.subtract(v);
                     }

                     ++this.step;
                  }
               } catch (NullPointerException var7) {
                  EffectsLib.foundNull(this.location, idName, var7);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, breath);
      }

   }

   public static void drawCylinder(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean enableRotation, final boolean solid, final float radius, final int finalParticleCount, final float height, float ratio, final double visibleRange, double xRotation, double yRotation, double zRotation, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int cylinder = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(ratio) {
            public double angularVelocityX = 0.015707964077591896D;
            public double angularVelocityY = 0.018479958176612854D;
            public double angularVelocityZ = 0.020268339663743973D;
            public double rotationX;
            public double rotationY;
            public double rotationZ;
            protected int step = 0;
            float sideRatio;
            Location location;
            public float hue;

            {
               this.sideRatio = var1;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (this.sideRatio == 0.0F) {
                     float grounds = 9.869605F * radius * 2.0F;
                     float side = 6.2831855F * radius * height;
                     this.sideRatio = side / (side + grounds);
                  }

                  Random r = RandomUtils.random;
                  double xRotation = this.rotationX;
                  double yRotation = this.rotationY;
                  double zRotation = this.rotationZ;
                  if (enableRotation) {
                     xRotation += (double)this.step * this.angularVelocityX;
                     yRotation += (double)this.step * this.angularVelocityY;
                     zRotation += (double)this.step * this.angularVelocityZ;
                  }

                  for(int i = 0; i < finalParticleCount; ++i) {
                     float multi = solid ? r.nextFloat() : 1.0F;
                     Vector v = RandomUtils.getRandomCircleVector().multiply(radius);
                     if (r.nextFloat() <= this.sideRatio) {
                        v.multiply(multi);
                        v.setY((r.nextFloat() * 2.0F - 1.0F) * (height / 2.0F));
                     } else {
                        v.multiply(r.nextFloat());
                        if ((double)r.nextFloat() < 0.5D) {
                           v.setY(multi * (height / 2.0F));
                        } else {
                           v.setY(-multi * (height / 2.0F));
                        }
                     }

                     if (enableRotation) {
                        VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
                     }

                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(v);
                  }

                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  ++this.step;
               } catch (NullPointerException var11) {
                  EffectsLib.foundNull(this.location, idName, var11);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, cylinder);
      }

   }

   public static void drawRings(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean enableRotation, final boolean animated, final float radius, final int ringCount, final int ringDensity, float height, final double visibleRange, final double xRotation, final double yRotation, final double zRotation, final double disX, final double disY, final double disZ, long delayTicks, final long periodTicks) {
      if (!arraylist.containsKey(idName)) {
         int rings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            double rotation = 90.0D;
            public double angularVelocityX = 0.015707964077591896D;
            public double angularVelocityY = 0.018479958176612854D;
            public double angularVelocityZ = 0.020268339663743973D;
            double angularVelocity = 0.039269909262657166D;
            int step = 0;
            float hue = 0.0F;
            Location location;
            int n;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY + 1.0D, disZ);
                  this.n = 0;

                  for(int i = 0; i < ringDensity; ++i) {
                     double angle = (double)this.step * this.angularVelocity;

                     for(int j = 0; j < ringCount; ++j) {
                        double n4 = (double)(3.1415927F / (float)ringCount * (float)j);
                        Vector v = (new Vector(Math.cos(angle), Math.sin(angle), 0.0D)).multiply(radius);
                        VectorUtils.rotateAroundAxisX(v, n4);
                        VectorUtils.rotateAroundAxisY(v, this.rotation);
                        VectorUtils.rotateVector(v, xRotation, yRotation, zRotation);
                        if (enableRotation) {
                           VectorUtils.rotateVector(v, this.angularVelocityX * (double)this.step, this.angularVelocityY * (double)this.step, this.angularVelocityZ * (double)this.step);
                        }

                        this.location.add(v);
                        final Location add = this.location.clone();
                        if (!animated) {
                           if (rainbowMode) {
                              this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                           }

                           ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        } else {
                           this.n = (int)((long)this.n + periodTicks);
                           int test = SchedulingManager.runAsyncRepeating(new Runnable() {
                              float finalHue;

                              public void run() {
                                 if (rainbowMode) {
                                    this.finalHue = ParticleEffect.simpleRainbowHelper(this.finalHue, particle);
                                 }

                                 ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, add, visibleRange, isSinglePlayer, rainbowMode, this.finalHue, offsetX, offsetY, offsetZ, speed, 1);
                              }
                           }, this.n, 0).getTaskId();
                           EffectsLib.arraylist2.put(idName, test);
                           EffectsLib.stopEffectDelayed2(this.n + 2, test, idName);
                        }

                        this.location.subtract(v);
                     }

                     ++this.step;
                  }
               } catch (NullPointerException var10) {
                  EffectsLib.foundNull(this.location, idName, var10);
               }

            }
         }, 0L, periodTicks).getTaskId();
         arraylist.put(idName, rings);
      }

   }

   public static void drawSphere(final int style, final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final float radius, final int density, final double visibleRange, double xRotation, double yRotation, double zRotation, final double disX, final double disY, final double disZ, long delayTicks, long periodTicks) {
      if (!arraylist.containsKey(idName)) {
         int rings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            float hue = 0.0F;
            float radiusCounter;
            float radiusCounterRev = radius;
            float stepRadius = radius / (float)density;
            float stepRadiusIn = radius / (float)density;
            Vector v;
            boolean scan = false;
            Location location;
            double t;

            public void radiusScan() {
               if (this.scan) {
                  if (this.radiusCounter > radius) {
                     this.stepRadius = -this.stepRadius;
                  } else if (this.radiusCounter < 0.0F) {
                     this.stepRadius = -this.stepRadius;
                  }
               } else {
                  if (this.radiusCounter > radius) {
                     this.radiusCounter = 0.0F;
                  }

                  if (this.radiusCounter < 0.0F) {
                     this.radiusCounter = radius;
                  }
               }

               this.radiusCounter += this.stepRadius;
            }

            public void radiusScanRev() {
               if (this.scan) {
                  if (this.radiusCounterRev > radius) {
                     this.stepRadiusIn = -this.stepRadiusIn;
                  } else if (this.radiusCounterRev < 0.0F) {
                     this.stepRadiusIn = -this.stepRadiusIn;
                  }
               } else {
                  if (this.radiusCounterRev > radius) {
                     this.radiusCounterRev = 0.0F;
                  }

                  if (this.radiusCounterRev < 0.0F) {
                     this.radiusCounterRev = radius;
                  }
               }

               this.radiusCounterRev -= this.stepRadiusIn;
            }

            public void quickSphere(float radiusx) {
               for(int i = 0; i < density; ++i) {
                  this.v = RandomUtils.getRandomVector().multiply(radiusx);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.v);
               }

            }

            public void quickSolidSphere(float radiusx) {
               for(int i = 0; i < density; ++i) {
                  this.v = RandomUtils.getRandomVector().multiply(this.radiusCounterRev);

                  for(float i2 = 0.0F; i2 < (float)density; ++i2) {
                     this.radiusScanRev();
                  }

                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.v);
               }

            }

            public void fancySphere(double radiusx) {
               this.t += 3.141592653589793D / (double)density;

               for(double theta = 0.0D; theta <= 6.283185307179586D; theta += 3.141592653589793D / (double)density) {
                  double x = radiusx * Math.cos(theta) * Math.sin(this.t);
                  double y = radiusx * Math.cos(this.t);
                  double z = radiusx * Math.sin(theta) * Math.sin(this.t);
                  this.location.add(x, y, z);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(x, y, z);
               }

            }

            public void halfSphere(float radiusx) {
               for(int i = 0; i < density; ++i) {
                  this.v = RandomUtils.getRandomVector().multiply(radiusx);
                  this.v.setY(Math.abs(this.v.getY()));
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.v);
               }

            }

            public void run() {
               this.location = EffectsLib.getLocation(center, idName);
               this.location.add(disX, disY, disZ);
               if (rainbowMode) {
                  this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
               }

               if (style <= 1) {
                  this.quickSphere(radius);
               } else if (style == 2) {
                  this.quickSphere(this.radiusCounter);
                  this.radiusScan();
               } else if (style == 3) {
                  this.quickSphere(this.radiusCounter);
                  this.scan = true;
                  this.radiusScan();
               } else if (style == 4) {
                  this.quickSphere(this.radiusCounterRev);
                  this.radiusScanRev();
               } else if (style == 5) {
                  this.quickSphere(this.radiusCounterRev);
                  this.scan = true;
                  this.radiusScanRev();
               } else if (style == 6) {
                  this.quickSolidSphere(radius);
               } else if (style == 7) {
                  this.fancySphere((double)radius);
               } else if (style >= 8) {
                  this.halfSphere(radius);
               }

            }
         }, 0L, delayTicks).getTaskId();
         arraylist.put(idName, rings);
      }

   }

   public static void drawWarpRings(final int style, final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean scan, final float radius, int ringCount, final int ringDensity, final float height, final double visibleRange, final double xRotation, final double yRotation, final double zRotation, final double disX, final double disY, final double disZ, long delayTicks) {
      if (!arraylist.containsKey(idName)) {
         int rings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(ringCount) {
            double x;
            double z;
            float hue = 0.0F;
            float midPoint = height / 2.0F;
            float heightCounter;
            float heightCounterRev = height;
            float middleCounterUp;
            float middleCounterDown;
            float radiusCounter;
            float radiusCounterRev;
            float stepHeight;
            float stepHalf;
            float stepRadius;
            float stepHeightDown;
            float stepHalfDown;
            float stepRadiusIn;
            Vector v;
            Location location;
            double t;

            {
               this.middleCounterUp = this.midPoint;
               this.middleCounterDown = this.midPoint;
               this.radiusCounterRev = radius;
               this.stepHeight = height / (float)var3;
               this.stepHalf = this.midPoint / (float)var3;
               this.stepRadius = radius / (float)var3;
               this.stepHeightDown = height / (float)var3;
               this.stepHalfDown = this.midPoint / (float)var3;
               this.stepRadiusIn = radius / (float)var3;
               this.t = 0.0D;
            }

            public void heightScan() {
               if (scan) {
                  if (this.heightCounter > height) {
                     this.stepHeight = -this.stepHeight;
                  }

                  if (this.heightCounter < 0.0F) {
                     this.stepHeight = -this.stepHeight;
                  }
               } else {
                  if (this.heightCounter > height) {
                     this.heightCounter = 0.0F;
                  }

                  if (this.heightCounter < 0.0F) {
                     this.heightCounter = height;
                  }
               }

               this.heightCounter += this.stepHeight;
            }

            public void heightScanRev() {
               if (scan) {
                  if (this.heightCounterRev > height) {
                     this.stepHeightDown = -this.stepHeightDown;
                  }

                  if (this.heightCounterRev < 0.0F) {
                     this.stepHeightDown = -this.stepHeightDown;
                  }
               } else {
                  if (this.heightCounterRev > height) {
                     this.heightCounterRev = 0.0F;
                  }

                  if (this.heightCounterRev < 0.0F) {
                     this.heightCounterRev = height;
                  }
               }

               this.heightCounterRev -= this.stepHeightDown;
            }

            public void middleScanUp() {
               if (scan) {
                  if (this.middleCounterUp > height) {
                     this.stepHalf = -this.stepHalf;
                  } else if (this.middleCounterUp < this.midPoint) {
                     this.stepHalf = -this.stepHalf;
                  }
               } else {
                  if (this.middleCounterUp > height) {
                     this.middleCounterUp = this.midPoint;
                  }

                  if (this.middleCounterUp < this.midPoint) {
                     this.middleCounterUp = height;
                  }
               }

               this.middleCounterUp += this.stepHalf;
            }

            public void middleScanDown() {
               if (scan) {
                  if (this.middleCounterDown > this.midPoint) {
                     this.stepHalfDown = -this.stepHalfDown;
                  } else if (this.middleCounterDown < 0.0F) {
                     this.stepHalfDown = -this.stepHalfDown;
                  }
               } else {
                  if (this.middleCounterDown > this.midPoint) {
                     this.middleCounterDown = 0.0F;
                  }

                  if (this.middleCounterDown < 0.0F) {
                     this.middleCounterDown = this.midPoint;
                  }
               }

               this.middleCounterDown -= this.stepHalfDown;
            }

            public void radiusScan() {
               if (scan) {
                  if (this.radiusCounter > radius) {
                     this.stepRadius = -this.stepRadius;
                  } else if (this.radiusCounter < 0.0F) {
                     this.stepRadius = -this.stepRadius;
                  }
               } else {
                  if (this.radiusCounter > radius) {
                     this.radiusCounter = 0.0F;
                  }

                  if (this.radiusCounter < 0.0F) {
                     this.radiusCounter = radius;
                  }
               }

               this.radiusCounter += this.stepRadius;
            }

            public void radiusScanRev() {
               if (scan) {
                  if (this.radiusCounterRev > radius) {
                     this.stepRadiusIn = -this.stepRadiusIn;
                  } else if (this.radiusCounterRev < 0.0F) {
                     this.stepRadiusIn = -this.stepRadiusIn;
                  }
               } else {
                  if (this.radiusCounterRev > radius) {
                     this.radiusCounterRev = 0.0F;
                  }

                  if (this.radiusCounterRev < 0.0F) {
                     this.radiusCounterRev = radius;
                  }
               }

               this.radiusCounterRev -= this.stepRadiusIn;
            }

            public void quickSolidCircle(float radiusx, double y) {
               this.location.add(0.0D, y, 0.0D);

               for(double angle = 0.0D; angle < 6.2831854820251465D; angle += (double)(3.1415927F / (float)ringDensity)) {
                  this.x = Math.cos(angle) * (double)radiusx;
                  this.z = Math.sin(angle) * (double)radiusx;
                  this.v = new Vector(this.x, 0.0D, this.z);
                  VectorUtils.rotateVector(this.v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.x, 0.0D, this.z);
               }

               this.location.subtract(0.0D, y, 0.0D);
            }

            public void quickRadialWave(float radiusx) {
               this.t += 0.3141592741012573D;

               for(double angle = 0.0D; angle < 6.2831854820251465D; angle += (double)(3.1415927F / (float)ringDensity)) {
                  this.x = this.t * Math.cos(angle);
                  double y = Math.exp((double)(-this.stepHeight) * this.t) * Math.sin(this.t) + (double)height;
                  this.z = this.t * Math.sin(angle);
                  this.v = new Vector(this.x, y, this.z);
                  VectorUtils.rotateVector(this.v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.x, y, this.z);
               }

               if (this.t >= (double)radiusx) {
                  this.t = 0.0D;
               }

            }

            public void quickRadialWave2(float radiusx) {
               this.t += 0.3141592741012573D;

               for(double angle = 0.0D; angle < 6.2831854820251465D; angle += (double)(3.1415927F / (float)ringDensity)) {
                  this.x = this.t * Math.cos(angle);
                  double y = 2.0D * Math.exp((double)(-this.stepHeight) * this.t) * Math.sin(this.t) + (double)height;
                  this.z = this.t * Math.sin(angle);
                  this.v = new Vector(this.x, y, this.z);
                  VectorUtils.rotateVector(this.v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                  ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  this.location.subtract(this.x, y, this.z);
               }

               if (this.t >= (double)radiusx) {
                  this.t = 0.0D;
               }

            }

            private void quickCubedRings(float radiusx, double yStep) {
               float a = radiusx / 2.0F;

               for(int i = 0; i < 4; ++i) {
                  double angleY = (double)i * 3.141592653589793D / 2.0D;

                  for(int p = 0; p <= ringDensity; ++p) {
                     this.v = new Vector((double)a, yStep, (double)(radiusx * (float)p / (float)ringDensity - a));
                     VectorUtils.rotateAroundAxisY(this.v, angleY);
                     VectorUtils.rotateVector(this.v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(this.v);
                  }
               }

            }

            private void quickCubeOutline(float radiusx, double yStep) {
               float a = radiusx / 2.0F;
               double b = yStep / 2.0D;
               Vector v = new Vector();

               for(int i = 0; i < 4; ++i) {
                  double angleY = (double)i * 3.141592653589793D / 2.0D;

                  int p;
                  for(p = 0; p < 2; ++p) {
                     double angleX = (double)p * 3.141592653589793D;

                     for(int px = 0; px <= ringDensity; ++px) {
                        v.setX(a).setY(a);
                        v.setZ(radiusx * (float)px / (float)ringDensity - a);
                        VectorUtils.rotateAroundAxisX(v, angleX);
                        VectorUtils.rotateAroundAxisY(v, angleY);
                        VectorUtils.rotateVector(v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                        ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        this.location.subtract(v);
                     }
                  }

                  for(p = 0; p <= ringDensity; ++p) {
                     v.setX(b).setZ(b);
                     v.setY(yStep * (double)p / (double)ringDensity - b);
                     VectorUtils.rotateAroundAxisY(v, angleY);
                     VectorUtils.rotateVector(v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(v);
                  }
               }

            }

            private void quickDerpRing(float radiusx, double yStep) {
               float a = radiusx / 2.0F;

               for(int i = 0; i < 10; ++i) {
                  double angleY = (double)((float)i * 0.62831855F);

                  for(int p = 0; p <= ringDensity; ++p) {
                     this.v = new Vector((double)a, yStep, (double)(radiusx * (float)p / (float)ringDensity - a));
                     VectorUtils.rotateAroundAxisY(this.v, angleY);
                     VectorUtils.rotateVector(this.v, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(this.v);
                  }
               }

            }

            private void quickStarRing(float radiusx, double yStep) {
               double angleForward = 2.5132741928100586D;

               for(int i = 1; i < 6; ++i) {
                  double angleY = (double)((float)i * 1.2566371F);
                  double x = Math.cos(angleY) * (double)radiusx;
                  double z = Math.sin(angleY) * (double)radiusx;
                  this.v = new Vector(x, yStep, z);
                  Vector star = this.v.clone();
                  VectorUtils.rotateAroundAxisY(star, angleForward);
                  this.location.add(this.v);
                  Vector link = star.clone().subtract(this.v.clone());
                  float length = (float)link.length();
                  link.normalize();
                  float ratio = length / (float)ringDensity;
                  Vector v3 = link.multiply(ratio);
                  Location loc = this.location.clone().subtract(v3);

                  for(int i2 = 0; i2 < ringDensity; ++i2) {
                     VectorUtils.rotateVector(v3, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, loc.add(v3), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  }

                  this.location.subtract(this.v);
               }

            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (style <= 1) {
                     this.quickSolidCircle(radius, (double)this.heightCounter);
                     this.heightScan();
                  } else if (style == 2) {
                     this.quickSolidCircle(radius, (double)this.heightCounterRev);
                     this.heightScanRev();
                  } else if (style == 3) {
                     this.quickSolidCircle(this.radiusCounter, (double)this.heightCounter);
                     this.radiusScan();
                  } else if (style == 4) {
                     this.quickSolidCircle(this.radiusCounterRev, (double)this.heightCounter);
                     this.radiusScanRev();
                  } else if (style == 5) {
                     this.quickSolidCircle(this.radiusCounter, (double)this.heightCounterRev);
                     this.heightScanRev();
                     this.radiusScan();
                  } else if (style == 6) {
                     this.quickSolidCircle(this.radiusCounterRev, (double)this.heightCounter);
                     this.heightScan();
                     this.radiusScanRev();
                  } else if (style == 7) {
                     this.quickSolidCircle(radius, (double)this.heightCounter);
                     this.quickSolidCircle(radius, (double)this.heightCounterRev);
                     this.heightScan();
                     this.heightScanRev();
                  } else if (style == 8) {
                     this.quickSolidCircle(this.radiusCounter, (double)this.middleCounterUp);
                     this.quickSolidCircle(this.radiusCounter, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScan();
                  } else if (style == 9) {
                     this.quickSolidCircle(this.radiusCounterRev, (double)this.middleCounterUp);
                     this.quickSolidCircle(this.radiusCounterRev, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScanRev();
                  } else if (style == 10) {
                     this.quickCubedRings(radius, (double)this.heightCounter);
                     this.heightScan();
                  } else if (style == 11) {
                     this.quickCubedRings(radius, (double)this.heightCounterRev);
                     this.heightScanRev();
                  } else if (style == 12) {
                     this.quickCubedRings(this.radiusCounter, (double)this.heightCounter);
                     this.radiusScan();
                  } else if (style == 13) {
                     this.quickCubedRings(this.radiusCounterRev, (double)this.heightCounter);
                     this.radiusScanRev();
                  } else if (style == 14) {
                     this.quickCubedRings(this.radiusCounter, (double)this.heightCounterRev);
                     this.heightScanRev();
                     this.radiusScan();
                  } else if (style == 15) {
                     this.quickCubedRings(this.radiusCounterRev, (double)this.heightCounterRev);
                     this.heightScanRev();
                     this.radiusScanRev();
                  } else if (style == 16) {
                     this.quickCubedRings(radius, (double)this.heightCounter);
                     this.quickCubedRings(radius, (double)this.heightCounterRev);
                     this.heightScan();
                     this.heightScanRev();
                  } else if (style == 17) {
                     this.quickCubedRings(this.radiusCounter, (double)this.middleCounterUp);
                     this.quickCubedRings(this.radiusCounter, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScan();
                  } else if (style == 18) {
                     this.quickCubedRings(this.radiusCounterRev, (double)this.middleCounterUp);
                     this.quickCubedRings(this.radiusCounterRev, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScanRev();
                  } else if (style == 19) {
                     this.quickStarRing(radius, (double)this.heightCounter);
                     this.heightScan();
                  } else if (style == 20) {
                     this.quickStarRing(radius, (double)this.heightCounterRev);
                     this.heightScanRev();
                  } else if (style == 21) {
                     this.quickStarRing(this.radiusCounter, (double)this.heightCounter);
                     this.radiusScan();
                  } else if (style == 22) {
                     this.quickStarRing(this.radiusCounterRev, (double)this.heightCounter);
                     this.radiusScanRev();
                  } else if (style == 23) {
                     this.quickStarRing(this.radiusCounter, (double)this.heightCounterRev);
                     this.heightScanRev();
                     this.radiusScan();
                  } else if (style == 24) {
                     this.quickStarRing(this.radiusCounterRev, (double)this.heightCounterRev);
                     this.heightScanRev();
                     this.radiusScanRev();
                  } else if (style == 25) {
                     this.quickStarRing(radius, (double)this.heightCounter);
                     this.quickStarRing(radius, (double)this.heightCounterRev);
                     this.heightScan();
                     this.heightScanRev();
                  } else if (style == 26) {
                     this.quickStarRing(this.radiusCounter, (double)this.middleCounterUp);
                     this.quickStarRing(this.radiusCounter, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScan();
                  } else if (style == 27) {
                     this.quickStarRing(this.radiusCounterRev, (double)this.middleCounterUp);
                     this.quickStarRing(this.radiusCounterRev, (double)this.middleCounterDown);
                     this.middleScanUp();
                     this.middleScanDown();
                     this.radiusScanRev();
                  } else if (style == 28) {
                     this.quickSolidCircle(this.heightCounter, (double)this.midPoint * Math.cos((double)this.heightCounter) + (double)(this.midPoint / 2.0F));
                     this.heightScan();
                  } else if (style == 29) {
                     this.quickSolidCircle(this.heightCounterRev, (double)this.midPoint * Math.cos((double)this.heightCounterRev) + (double)(this.midPoint / 2.0F));
                     this.heightScanRev();
                  } else if (style == 30) {
                     this.quickSolidCircle(this.radiusCounter, (double)this.midPoint * Math.cos((double)this.heightCounter) + (double)(this.midPoint / 2.0F));
                     this.heightScan();
                     this.radiusScan();
                  } else if (style == 31) {
                     this.quickSolidCircle(this.radiusCounterRev, (double)this.midPoint * Math.cos((double)this.heightCounterRev) + (double)(this.midPoint / 2.0F));
                     this.heightScanRev();
                     this.radiusScanRev();
                  } else if (style == 32) {
                     this.quickRadialWave(radius);
                  } else if (style == 33) {
                     this.quickRadialWave2(radius);
                  } else if (style == 34) {
                     this.quickDerpRing(this.radiusCounter, (double)this.heightCounter);
                     this.radiusScan();
                  } else if (style == 35) {
                     this.quickCubeOutline(radius, (double)height);
                  } else if (style >= 36) {
                     this.quickCubeOutline(this.radiusCounter, (double)this.heightCounter);
                     this.heightScan();
                     this.radiusScan();
                  }

                  EffectsLib.itStep();
               } catch (NullPointerException var2) {
                  EffectsLib.foundNull(this.location, idName, var2);
               }

            }
         }, 0L, delayTicks).getTaskId();
         arraylist.put(idName, rings);
      }

   }

   public static void drawItemBoops(final ItemStack it, final Object center, final String idName, final float heightMod, final float radius, final int finalDensity, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int items = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            Location location;
            double angle;
            int step = 0;
            Vector v = new Vector();
            Vector vLoc = new Vector();
            Vector vTest = new Vector();
            Item i;
            Location loc;

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  World world = Bukkit.getWorld(this.location.getWorld().getName());
                  this.location.add(disX, disY, disZ);
                  this.angle = (double)((float)this.step * (6.2831855F / (float)finalDensity));
                  this.v.setX(Math.cos(this.angle) * (double)radius);
                  this.v.setY(heightMod);
                  this.v.setZ(Math.sin(this.angle) * (double)radius);
                  if (this.step == 0) {
                     this.vLoc.setX(this.location.getX()).setY(this.location.getY()).setZ(this.location.getZ());
                     this.vTest = this.vLoc.add(this.v);
                     this.loc = this.vTest.toLocation(world);
                     ItemMeta itemMeta = it.getItemMeta();
                     itemMeta.setDisplayName("" + new Random(System.nanoTime()));
                     it.setItemMeta(itemMeta);
                     this.i = world.dropItem(this.loc.add(0.0D, 1.0D, 0.0D), it);
                     this.i.setPickupDelay(99999999);
                     String newID = this.i.getUniqueId().toString() + idName;
                     EffectsLib.arraylist.put(newID, 69);
                     skDragonCore.notInUseItems.add(this.i.getUniqueId());
                  }

                  this.i.setVelocity(this.v);
                  ++this.step;
               } catch (NullPointerException var4) {
                  EffectsLib.foundNull(this.location, idName, var4);
               }

            }
         }, 0L, delayBySecond).getTaskId();
         arraylist.put(idName, items);
      }

   }

   public static void drawItemFountain(final ItemStack it, final int style, final Object center, final String idName, final long itemTime, final float heightMod, final float radius, final int density, final double disX, final double disY, final double disZ, long delayTicks) {
      if (!arraylist.containsKey("Fountain: " + idName)) {
         int itemFountain = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            Location location;
            double angle;
            double angularVelocity = 0.07853981852531433D;
            float step;
            Item i;
            Vector v = new Vector();
            Random rand = new Random();

            public void run() {
               this.location = EffectsLib.getLocation(center, idName);
               if (style <= 1) {
                  this.angle = (double)((float)(this.rand.nextInt(361) + 0) * (6.2831855F / (float)density));
                  this.v.setX(Math.cos(this.angle) * (double)radius);
                  this.v.setY(heightMod);
                  this.v.setZ(Math.sin(this.angle) * (double)radius);
               } else if (style == 2) {
                  for(int i = 0; i < density; ++i) {
                     this.v = RandomUtils.getRandomVector().multiply(radius);
                     this.v.setY(heightMod);
                     this.v.setY(Math.abs(this.v.getY()));
                  }
               } else if (style >= 3) {
                  this.step = (float)(this.rand.nextInt(181) + 0);
                  this.v.setX(Math.cos((double)(6.2831855F / (float)density)) * (double)radius);
                  this.v.setY(heightMod);
                  this.v.setZ(Math.sin((double)(6.2831855F / (float)density)) * (double)radius);
                  VectorUtils.rotateVector(this.v, this.angularVelocity * (double)this.step, this.angularVelocity * (double)this.step, this.angularVelocity * (double)this.step);
               }

               ItemMeta itemMeta = it.getItemMeta();
               itemMeta.setDisplayName(idName);
               it.setItemMeta(itemMeta);

               try {
                  this.i = this.location.getWorld().dropItem(this.location.add(disX, disY, disZ), it);
               } catch (Exception var4) {
                  skDragonCore.sendExLog("The location or item used is invalid", idName, 3);
                  EffectsLib.stopEffect(idName);
                  EffectsLib.stopItemEffect(idName);
                  return;
               }

               this.i.setPickupDelay(Math.toIntExact(itemTime) * 10);
               this.i.setVelocity(this.v);
               skDragonCore.notInUseItems.add(this.i.getUniqueId());
               if (skDragonCore.serverVersion >= 10) {
                  Iterator iter = skDragonCore.notInUseItems.iterator();

                  while(iter.hasNext()) {
                     Entity entityx = skDragonCore.getInstance().getServer().getEntity((UUID)iter.next());
                     if (entityx instanceof Item && (long)entityx.getTicksLived() >= itemTime) {
                        entityx.remove();
                        iter.remove();
                     }
                  }
               } else {
                  Iterator var7 = this.location.getWorld().getEntities().iterator();

                  while(var7.hasNext()) {
                     Entity entity = (Entity)var7.next();
                     if (entity instanceof Item && (long)entity.getTicksLived() >= itemTime && skDragonCore.notInUseItems.contains(entity.getUniqueId())) {
                        skDragonCore.notInUseItems.remove(entity.getUniqueId());
                        entity.remove();
                     }
                  }
               }

            }
         }, 0L, delayTicks).getTaskId();
         arraylist.put("Fountain: " + idName, itemFountain);
      }

   }

   /** @deprecated */
   @Deprecated
   public static void drawRainbowLine(final Player p, String idName) {
      if (!arraylist.containsKey(idName)) {
         int rainbowline = Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            private float hue = 0.0F;

            public void run() {
               Location loc = p.getLocation();
               loc.add(0.0D, 0.30000001192092896D, 0.0D);
               int argb = Color.HSBtoRGB(this.hue, 1.0F, 1.0F);
               float r = (float)(argb >> 16 & 255) / 255.0F;
               float g = (float)(argb >> 8 & 255) / 255.0F;
               float b = (float)(argb & 255) / 255.0F;
               r = r == 0.0F ? 0.001F : r;
               this.hue += 0.01F;
               this.hue = this.hue >= 1.0F ? 0.0F : this.hue;
               ParticleEffect.mobspell.display(r, g, b, 1.0F, 0, loc, 50.0D);
               ParticleEffect.mobspell.display(r, g, b, 1.0F, 0, loc, 50.0D);
               ParticleEffect.mobspell.display(r, g, b, 1.0F, 0, loc, 50.0D);
               ParticleEffect.mobspell.display(r, g, b, 1.0F, 0, loc, 50.0D);
            }
         }, 1L, 1L).getTaskId();
         arraylist.put(idName, rainbowline);
      }

   }

   public static void drawWings(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final String particle2, final Material dataMat2, final byte dataID2, final float speed2, final float offsetX2, final float offsetY2, final float offsetZ2, final String particle3, final Material dataMat3, final byte dataID3, final float speed3, final float offsetX3, final float offsetY3, final float offsetZ3, final Object center, final String idName, final boolean isSinglePlayer, final boolean rainbowMode, final boolean flapMode, float flapStep, final float flapRange, final float wingAngle, final double visibleRange, final boolean[][] shape, final boolean[][] shape2, final boolean[][] shape3, final float height, final double space, long delayTicks, long delayBySecond, final Player player) {
      if (!arraylist.containsKey(idName)) {
         int wings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(flapStep) {
            private float hue;
            Location location;
            float flap = 0.0F;
            float stepFlap;

            {
               this.stepFlap = var1;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle2);
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle3);
                  }

                  double x;
                  double defX = x = this.location.getX() + space;
                  double y = this.location.clone().getY() + 2.7D + (double)height;
                  double y2 = this.location.clone().getY() + 2.7D + (double)height;
                  double y3 = this.location.clone().getY() + 2.7D + (double)height;
                  int i;
                  int j;
                  Location target;
                  Vector vR;
                  Vector vL;
                  Vector v2;
                  double rightWing;
                  double leftWing;
                  if (!particle.equals("nothing")) {
                     for(i = 0; i < shape.length; ++i) {
                        for(j = 0; j < shape[i].length; ++j) {
                           if (shape[i][j]) {
                              target = this.location.clone();
                              target.setX(x);
                              target.setY(y);
                              vR = target.toVector().subtract(this.location.toVector());
                              vL = target.toVector().subtract(this.location.toVector());
                              v2 = VectorUtils.getBackVector(this.location);
                              rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - (wingAngle - this.flap)));
                              leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + (wingAngle - this.flap)));
                              vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                              vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                              v2.setY(0).multiply(-0.2D);
                              ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                              ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                           }

                           x += space;
                        }

                        y -= space;
                        x = defX;
                     }
                  }

                  if (!particle2.equals("nothing")) {
                     for(i = 0; i < shape2.length; ++i) {
                        for(j = 0; j < shape2[i].length; ++j) {
                           if (shape2[i][j]) {
                              target = this.location.clone();
                              target.setX(x);
                              target.setY(y2);
                              vR = target.toVector().subtract(this.location.toVector());
                              vL = target.toVector().subtract(this.location.toVector());
                              v2 = VectorUtils.getBackVector(this.location);
                              rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - (wingAngle - this.flap)));
                              leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + (wingAngle - this.flap)));
                              vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                              vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                              v2.setY(0).multiply(-0.2D);
                              ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                              ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                           }

                           x += space;
                        }

                        y2 -= space;
                        x = defX;
                     }
                  }

                  if (!particle3.equals("nothing")) {
                     for(i = 0; i < shape3.length; ++i) {
                        for(j = 0; j < shape3[i].length; ++j) {
                           if (shape3[i][j]) {
                              target = this.location.clone();
                              target.setX(x);
                              target.setY(y3);
                              vR = target.toVector().subtract(this.location.toVector());
                              vL = target.toVector().subtract(this.location.toVector());
                              v2 = VectorUtils.getBackVector(this.location);
                              rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - (wingAngle - this.flap)));
                              leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + (wingAngle - this.flap)));
                              vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                              vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                              v2.setY(0).multiply(-0.2D);
                              ParticleEffect.valueOf(particle3).display(idName, dataMat3, dataID3, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX3, offsetY3, offsetZ3, speed3, 1);
                              ParticleEffect.valueOf(particle3).display(idName, dataMat3, dataID3, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX3, offsetY3, offsetZ3, speed3, 1);
                           }

                           x += space;
                        }

                        y3 -= space;
                        x = defX;
                     }
                  }

                  if (flapMode) {
                     if (this.flap > flapRange) {
                        this.stepFlap = -this.stepFlap;
                     } else if (this.flap < 0.0F) {
                        this.stepFlap = -this.stepFlap;
                     }

                     this.flap += this.stepFlap;
                  }
               } catch (NullPointerException var21) {
                  EffectsLib.foundNull(this.location, idName, var21);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, wings);
      }

   }

   public static void drawWings4Color(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final String particle2, final Material dataMat2, final byte dataID2, final float speed2, final float offsetX2, final float offsetY2, final float offsetZ2, final String particle3, final Material dataMat3, final byte dataID3, final float speed3, final float offsetX3, final float offsetY3, final float offsetZ3, final String particle4, final Material dataMat4, final byte dataID4, final float speed4, final float offsetX4, final float offsetY4, final float offsetZ4, final Object center, final String idName, final boolean isSinglePlayer, final boolean rainbowMode, final boolean flapMode, float flapStep, final float flapRange, final float wingAngle, final double visibleRange, final boolean[][] shape, final boolean[][] shape2, final boolean[][] shape3, final boolean[][] shape4, final float height, final double space, long delayTicks, long delayBySecond, final Player player) {
      if (!arraylist.containsKey(idName)) {
         int wings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(flapStep) {
            private float hue;
            Location location;
            float flap = 0.0F;
            float stepFlap;

            {
               this.stepFlap = var1;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle2);
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle3);
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle4);
                  }

                  double x;
                  double defX = x = this.location.getX() + space;
                  double y = this.location.clone().getY() + 2.7D + (double)height;
                  double y2 = this.location.clone().getY() + 2.7D + (double)height;
                  double y3 = this.location.clone().getY() + 2.7D + (double)height;
                  double y4 = this.location.clone().getY() + 2.7D + (double)height;

                  int i;
                  int j;
                  Location target;
                  Vector vR;
                  Vector vL;
                  Vector v2;
                  double rightWing;
                  double leftWing;
                  for(i = 0; i < shape.length; ++i) {
                     for(j = 0; j < shape[i].length; ++j) {
                        if (shape[i][j]) {
                           target = this.location.clone();
                           target.setX(x);
                           target.setY(y);
                           vR = target.toVector().subtract(this.location.toVector());
                           vL = target.toVector().subtract(this.location.toVector());
                           v2 = VectorUtils.getBackVector(this.location);
                           rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - wingAngle - this.flap));
                           leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + wingAngle - this.flap));
                           vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                           vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                           v2.setY(0).multiply(-0.2D);
                           ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                           ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        }

                        x += space;
                     }

                     y -= space;
                     x = defX;
                  }

                  for(i = 0; i < shape2.length; ++i) {
                     for(j = 0; j < shape2[i].length; ++j) {
                        if (shape2[i][j]) {
                           target = this.location.clone();
                           target.setX(x);
                           target.setY(y2);
                           vR = target.toVector().subtract(this.location.toVector());
                           vL = target.toVector().subtract(this.location.toVector());
                           v2 = VectorUtils.getBackVector(this.location);
                           rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - wingAngle - this.flap));
                           leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + wingAngle - this.flap));
                           vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                           vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                           v2.setY(0).multiply(-0.2D);
                           ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                           ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                        }

                        x += space;
                     }

                     y2 -= space;
                     x = defX;
                  }

                  for(i = 0; i < shape3.length; ++i) {
                     for(j = 0; j < shape3[i].length; ++j) {
                        if (shape3[i][j]) {
                           target = this.location.clone();
                           target.setX(x);
                           target.setY(y3);
                           vR = target.toVector().subtract(this.location.toVector());
                           vL = target.toVector().subtract(this.location.toVector());
                           v2 = VectorUtils.getBackVector(this.location);
                           rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - wingAngle - this.flap));
                           leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + wingAngle - this.flap));
                           vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                           vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                           v2.setY(0).multiply(-0.2D);
                           ParticleEffect.valueOf(particle3).display(idName, dataMat3, dataID3, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX3, offsetY3, offsetZ3, speed3, 1);
                           ParticleEffect.valueOf(particle3).display(idName, dataMat3, dataID3, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX3, offsetY3, offsetZ3, speed3, 1);
                        }

                        x += space;
                     }

                     y3 -= space;
                     x = defX;
                  }

                  for(i = 0; i < shape4.length; ++i) {
                     for(j = 0; j < shape4[i].length; ++j) {
                        if (shape4[i][j]) {
                           target = this.location.clone();
                           target.setX(x);
                           target.setY(y4);
                           vR = target.toVector().subtract(this.location.toVector());
                           vL = target.toVector().subtract(this.location.toVector());
                           v2 = VectorUtils.getBackVector(this.location);
                           rightWing = Math.toRadians((double)(this.location.getYaw() + 90.0F - wingAngle - this.flap));
                           leftWing = Math.toRadians((double)(this.location.getYaw() + 90.0F + wingAngle - this.flap));
                           vR = VectorUtils.rotateAroundAxisY(vR, -rightWing);
                           vL = VectorUtils.rotateAroundAxisY(vL, -leftWing);
                           v2.setY(0).multiply(-0.2D);
                           ParticleEffect.valueOf(particle4).display(idName, dataMat4, dataID4, player, this.location.clone().add(vL).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX4, offsetY4, offsetZ4, speed4, 1);
                           ParticleEffect.valueOf(particle4).display(idName, dataMat4, dataID4, player, this.location.clone().add(vR).add(v2), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX4, offsetY4, offsetZ4, speed4, 1);
                        }

                        x += space;
                     }

                     y4 -= space;
                     x = defX;
                  }

                  if (flapMode) {
                     if (this.flap > flapRange) {
                        this.stepFlap = -this.stepFlap;
                     } else if (this.flap < 0.0F) {
                        this.stepFlap = -this.stepFlap;
                     }

                     this.flap += this.stepFlap;
                  }
               } catch (NullPointerException var23) {
                  EffectsLib.foundNull(this.location, idName, var23);
               }

            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, wings);
      }

   }

   public static void drawPlanet(final String particle, final Material dataMat, final byte dataID, final float speed, final String particle2, final Material dataMat2, final byte dataID2, final float speed2, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean enableRotation, final EffectsLib.Plane plane, final float rotationStep, final boolean enableOrbit, final float orbitalRadius, final float orbitalStepDensity, final double xRotation, final double yRotation, final double zRotation, final float radius, final int density, final int precision, final float mountainHeight, final double visibleRange, final float offsetX, final float offsetY, final float offsetZ, final float offsetX2, final float offsetY2, final float offsetZ2, final double disX, final double disY, final double disZ, long delayTicks, long delayBySecond) {
      if (!arraylist.containsKey(idName)) {
         int planet = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public float hue;
            Location location;
            double angularVelocityX = 0.008726646192371845D;
            double angularVelocityY = 0.008726646192371845D;
            double angularVelocityZ = 0.008726646192371845D;
            int orbitalStep = 0;
            boolean firstStep = true;
            Set cacheGreen = new HashSet();
            Set cacheBlue = new HashSet();
            Set cache = new HashSet();
            // $FF: synthetic field
            private static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane;

            public void invalidate() {
               this.firstStep = false;
               this.cacheGreen.clear();
               this.cacheBlue.clear();
               int sqrtParticles = (int)Math.sqrt((double)density);
               float theta = 0.0F;
               float thetaStep = 3.1415927F / (float)sqrtParticles;
               float phiStep = 6.2831855F / (float)sqrtParticles;

               int ix;
               float maxSquared;
               float average;
               for(int i = 0; i < sqrtParticles; ++i) {
                  theta += thetaStep;
                  float phi = 0.0F;

                  for(ix = 0; ix < sqrtParticles; ++ix) {
                     phi += phiStep;
                     maxSquared = radius * MathUtils.sin(theta) * MathUtils.cos(phi);
                     average = radius * MathUtils.sin(theta) * MathUtils.sin(phi);
                     float z = radius * MathUtils.cos(theta);
                     this.cache.add(new Vector(maxSquared, average, z));
                  }
               }

               float increase = mountainHeight / (float)precision;

               for(ix = 0; ix < precision; ++ix) {
                  double r1 = RandomUtils.getRandomAngle();
                  double r2 = RandomUtils.getRandomAngle();
                  double r3 = RandomUtils.getRandomAngle();
                  Iterator var15 = this.cache.iterator();

                  while(var15.hasNext()) {
                     Vector vx = (Vector)var15.next();
                     if (vx.getY() > 0.0D) {
                        vx.setY(vx.getY() + (double)increase);
                     } else {
                        vx.setY(vx.getY() - (double)increase);
                     }

                     if (ix != precision - 1) {
                        VectorUtils.rotateVector(vx, r1, r2, r3);
                     }
                  }
               }

               float minSquared = Float.POSITIVE_INFINITY;
               maxSquared = Float.NEGATIVE_INFINITY;
               Iterator var21 = this.cache.iterator();

               while(var21.hasNext()) {
                  Vector current = (Vector)var21.next();
                  float lengthSquaredx = (float)current.lengthSquared();
                  if (minSquared > lengthSquaredx) {
                     minSquared = lengthSquaredx;
                  }

                  if (maxSquared < lengthSquaredx) {
                     maxSquared = lengthSquaredx;
                  }
               }

               average = (minSquared + maxSquared) / 2.0F;
               Iterator var23 = this.cache.iterator();

               while(var23.hasNext()) {
                  Vector v = (Vector)var23.next();
                  float lengthSquared = (float)v.lengthSquared();
                  if (lengthSquared >= average) {
                     this.cacheGreen.add(v);
                  } else {
                     this.cacheBlue.add(v);
                  }
               }

            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (this.firstStep) {
                     this.invalidate();
                  }

                  double rotX;
                  double rotY;
                  if (enableOrbit) {
                     rotX = (double)(6.2831855F / orbitalStepDensity);
                     rotY = (double)this.orbitalStep * rotX;
                     Vector v2 = new Vector();
                     v2.setX(Math.cos(rotY) * (double)orbitalRadius);
                     v2.setZ(Math.sin(rotY) * (double)orbitalRadius);
                     VectorUtils.rotateVector(v2, xRotation * 0.01745329238474369D, yRotation * 0.01745329238474369D, zRotation * 0.01745329238474369D);
                     this.location.add(v2);
                     ++this.orbitalStep;
                  }

                  if (enableRotation) {
                     rotX = 0.0D;
                     rotY = 0.0D;
                     double rotZ = 0.0D;
                     switch($SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane()[plane.ordinal()]) {
                     case 1:
                        rotX = this.angularVelocityX * (double)rotationStep;
                        break;
                     case 2:
                        rotY = this.angularVelocityY * (double)rotationStep;
                        break;
                     case 3:
                        rotZ = this.angularVelocityZ * (double)rotationStep;
                        break;
                     case 4:
                        rotX = this.angularVelocityX * (double)rotationStep;
                        rotY = this.angularVelocityY * (double)rotationStep;
                        break;
                     case 5:
                        rotX = this.angularVelocityX * (double)rotationStep;
                        rotZ = this.angularVelocityZ * (double)rotationStep;
                        break;
                     case 6:
                        rotX = this.angularVelocityX * (double)rotationStep;
                        rotY = this.angularVelocityY * (double)rotationStep;
                        rotZ = this.angularVelocityZ * (double)rotationStep;
                        break;
                     case 7:
                        rotY = this.angularVelocityY * (double)rotationStep;
                        rotZ = this.angularVelocityZ * (double)rotationStep;
                     }

                     Iterator var8 = this.cache.iterator();

                     while(var8.hasNext()) {
                        Vector v = (Vector)var8.next();
                        VectorUtils.rotateVector(v, rotX, rotY, rotZ);
                     }
                  }

                  Iterator var2 = this.cacheGreen.iterator();

                  Vector vx;
                  while(var2.hasNext()) {
                     vx = (Vector)var2.next();
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(vx), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(vx);
                  }

                  var2 = this.cacheBlue.iterator();

                  while(var2.hasNext()) {
                     vx = (Vector)var2.next();
                     ParticleEffect.valueOf(particle2).display(idName, dataMat2, dataID2, player, this.location.add(vx), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX2, offsetY2, offsetZ2, speed2, 1);
                     this.location.subtract(vx);
                  }
               } catch (NullPointerException var9) {
                  EffectsLib.foundNull(this.location, idName, var9);
               }

            }

            // $FF: synthetic method
            static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane() {
               int[] var10000 = $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane;
               if (var10000 != null) {
                  return var10000;
               } else {
                  int[] var0 = new int[EffectsLib.Plane.values().length];

                  try {
                     var0[EffectsLib.Plane.X.ordinal()] = 1;
                  } catch (NoSuchFieldError var7) {
                  }

                  try {
                     var0[EffectsLib.Plane.XY.ordinal()] = 4;
                  } catch (NoSuchFieldError var6) {
                  }

                  try {
                     var0[EffectsLib.Plane.XYZ.ordinal()] = 6;
                  } catch (NoSuchFieldError var5) {
                  }

                  try {
                     var0[EffectsLib.Plane.XZ.ordinal()] = 5;
                  } catch (NoSuchFieldError var4) {
                  }

                  try {
                     var0[EffectsLib.Plane.Y.ordinal()] = 2;
                  } catch (NoSuchFieldError var3) {
                  }

                  try {
                     var0[EffectsLib.Plane.YZ.ordinal()] = 7;
                  } catch (NoSuchFieldError var2) {
                  }

                  try {
                     var0[EffectsLib.Plane.Z.ordinal()] = 3;
                  } catch (NoSuchFieldError var1) {
                  }

                  $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$maths$EffectsLib$Plane = var0;
                  return var0;
               }
            }
         }, delayTicks, delayBySecond).getTaskId();
         arraylist.put(idName, planet);
      }

   }

   public static void drawBetaTest1(final Player p, String idName) {
      if (!arraylist.containsKey(idName)) {
         final ParticleEffect.OrdinaryColor color = new ParticleEffect.OrdinaryColor(255, 20, 20);
         int i = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            int particles = 40;
            float radius = 2.0F;
            protected int i;
            int speed = 25;
            double height = 0.0D;
            int particles2 = 40;
            float radius2 = 2.0F;
            protected int i2;
            int speed2 = 25;
            double height2 = 0.0D;

            public void run() {
               Location location = p.getLocation();
               Location location2 = p.getLocation();
               double angle = 6.283185307179586D * (double)this.i / (double)(this.particles * this.speed);
               double x = Math.cos(angle) * (double)this.radius;
               double z = Math.sin(angle) * (double)this.radius;
               location.add(x, this.height, z);
               ParticleEffect.redstone.display(color, location, 30.0D);
               location.subtract(x, 0.0D, z);
               this.i += this.speed;
               if ((double)this.radius > 0.02D) {
                  this.radius = (float)((double)this.radius - 0.05D);
                  this.height += 0.1D;
               } else {
                  this.radius = 2.0F;
                  this.height = 0.0D;
               }

               double angle2 = 6.283185307179586D * (double)this.i2 / (double)(this.particles2 * this.speed2);
               double x2 = Math.cos(angle2) * (double)(-this.radius2);
               double z2 = Math.sin(angle2) * (double)(-this.radius2);
               Vector v = new Vector(x2, this.height2, z2);
               location2.add(v);
               ParticleEffect.redstone.display(color, location, 30.0D);
               location2.subtract(x, 0.0D, z);
               this.i2 += this.speed2;
               if ((double)this.radius2 > 0.02D) {
                  this.radius2 = (float)((double)this.radius2 - 0.05D);
                  this.height2 += 0.1D;
               } else {
                  this.radius2 = 2.0F;
                  this.height2 = 0.0D;
               }

            }
         }, 1L, 1L).getTaskId();
         arraylist.put(idName, i);
      } else {
         stopEffect(idName);
      }

   }

   public static void drawBetaTest2(final Player p, String idName) {
      if (!arraylist.containsKey(idName)) {
         int betatest2 = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            int particles = 70;
            float radius = 1.245F;
            protected int i;
            int speed = 3;
            double height = 0.3D;
            boolean up = false;

            public void run() {
               Location location = p.getEyeLocation();
               double angle = 6.283185307179586D * (double)this.i / (double)this.particles;
               double x = Math.cos(angle) * (double)this.radius;
               double z = Math.sin(angle) * (double)this.radius;
               location.add(x, this.height, z);
               ParticleEffect.heart.display(0.0F, 0.0F, 0.0F, 0.0F, 1, location, 50.0D);
               location.subtract(x, 0.0D, z);
               this.i += this.speed;
               if (this.height < -1.49D) {
                  this.up = true;
               } else if (this.height > 0.6D) {
                  this.up = false;
               }

               if (this.up) {
                  this.height += 0.05D;
               } else {
                  this.height -= 0.05D;
               }

            }
         }, 1L, 1L).getTaskId();
         arraylist.put(idName, betatest2);
      } else {
         stopEffect(idName);
      }

   }

   public static void drawBetaTest3(final Player p, String idName) {
      if (!arraylist.containsKey(idName)) {
         int betatest3 = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public float radius = 1.0F;
            public float grow = 0.0F;
            public double radials = 0.1963495408493621D;
            public int circles = 1;
            public int helixes = 1;
            protected int step = 0;
            public float radius2 = 1.0F;
            public float grow2 = 0.0F;
            public double radials2 = 0.1963495408493621D;
            public int helixes2 = 1;
            protected int step2 = 0;
            public float radius3 = 1.0F;
            public float grow3 = 0.0F;
            public double radials3 = 0.1963495408493621D;
            public int helixes3 = 1;
            protected int step3 = 0;

            public void run() {
               Location loc = p.getLocation().add(0.0D, 1.0D, 0.0D);
               Location loc2 = p.getLocation().add(0.0D, 1.0D, 0.0D);
               Location loc3 = p.getLocation().add(0.0D, 1.0D, 0.0D);
               Vector v = null;
               Vector v2 = null;
               Vector v3 = null;

               for(int x = 0; x < this.circles; ++x) {
                  int i;
                  double angle;
                  for(i = 0; i < this.helixes; ++i) {
                     angle = (double)this.step * this.radials + 6.283185307179586D * (double)i / (double)this.helixes;
                     v = new Vector(Math.cos(angle) * (double)this.radius, (double)((float)this.step * this.grow), Math.sin(angle) * (double)this.radius);
                     VectorUtils.rotateAroundAxisX(v, 2.356194257736206D);
                     VectorUtils.rotateAroundAxisY(v, 0.01745329052209854D);
                     loc.add(v);
                     ParticleEffect.happyvillager.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc, 50.0D);
                     loc.subtract(v);
                  }

                  ++this.step;

                  for(i = 0; i < this.helixes2; ++i) {
                     angle = (double)this.step2 * this.radials2 + 6.283185307179586D * (double)i / (double)this.helixes2;
                     v2 = new Vector(Math.cos(angle) * (double)this.radius2, (double)((float)this.step2 * this.grow2), Math.sin(angle) * (double)this.radius2);
                     VectorUtils.rotateAroundAxisX(v2, 0.7853980660438538D);
                     VectorUtils.rotateAroundAxisY(v2, 0.01745329052209854D);
                     loc2.add(v2);
                     ParticleEffect.happyvillager.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc2, 50.0D);
                     loc2.subtract(v2);
                  }

                  ++this.step2;

                  for(i = 0; i < this.helixes3; ++i) {
                     angle = (double)this.step3 * this.radials3 + 6.283185307179586D * (double)i / (double)this.helixes3;
                     v3 = new Vector(Math.cos(angle) * (double)this.radius3, (double)((float)this.step3 * this.grow3), Math.sin(angle) * (double)this.radius3);
                     VectorUtils.rotateAroundAxisX(v3, (double)(-loc3.getYaw() * 0.017453292F));
                     VectorUtils.rotateAroundAxisY(v3, 0.01745329052209854D);
                     loc3.add(v3);
                     ParticleEffect.flame.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc3, 50.0D);
                     loc3.subtract(v3);
                  }

                  ++this.step3;
               }

            }
         }, 1L, 2L).getTaskId();
         arraylist.put(idName, betatest3);
      } else {
         stopEffect(idName);
      }

   }

   public static void drawBetaTest4(final Location location, Location locTest, Player playerTest, String idName) {
      if (!arraylist.containsKey(idName)) {
         int betatest4 = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public double radius = 1.5D;
            public int particles = 50;

            public void run() {
               for(int i = 0; i < this.particles; ++i) {
                  Vector point = RandomUtils.getRandomVector().multiply(this.radius);
                  location.add(point);
                  ParticleEffect.flame.display(0.0F, 0.0F, 0.0F, 0.0F, 1, location, 50.0D);
                  location.subtract(point);
               }

            }
         }, 3L, 3L).getTaskId();
         arraylist.put(idName, betatest4);
      } else {
         stopEffect(idName);
      }

   }

   public static void drawBetaTest6(final ParticleEffect particle, final Player player, String idName) {
      if (!arraylist.containsKey(idName)) {
         int betatest6 = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public float height = 2.5F;
            public float particles = 30.0F;
            public float edgeLength = 6.5F;
            public double yRotation = 0.4487989544868469D;

            public void run() {
               Location location = player.getLocation();
               Vector v = new Vector();

               for(int x = 0; (float)x <= this.particles; ++x) {
                  double y1 = Math.sin((double)(3.1415927F * (float)x / this.particles));

                  for(int z = 0; (float)z <= this.particles; ++z) {
                     double y2 = Math.sin((double)(3.1415927F * (float)z / this.particles));
                     v.setX(this.edgeLength * (float)x / this.particles).setZ(this.edgeLength * (float)z / this.particles);
                     v.setY((double)this.height * y1 * y2);
                     VectorUtils.rotateAroundAxisY(v, this.yRotation);
                     particle.display(0.0F, 0.0F, 0.0F, 0.0F, 1, location.add(v), 50.0D);
                     location.subtract(v);
                  }
               }

            }
         }, 1L, 2L).getTaskId();
         arraylist.put(idName, betatest6);
      } else {
         stopEffect(idName);
      }

   }

   public static void drawWarpBubble(final String particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean scan, final float radius, int ringCount, final int ringDensity, final float height, final double visibleRange, double xRotation, double yRotation, double zRotation, final double disX, final double disY, final double disZ, long delayTicks, long periodTicks) {
      if (!arraylist.containsKey(idName)) {
         int rings = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(ringCount) {
            double x;
            double y;
            double z;
            float hue = 0.0F;
            double heightCounter;
            double radiusCounter;
            boolean up = true;
            boolean out = true;
            double stepHeight;
            double stepRadius;
            Vector v;
            Location location;
            float edgeLength;
            double yRotation;

            {
               this.stepHeight = (double)(height / (float)var2);
               this.stepRadius = (double)(radius / (float)var2);
               this.edgeLength = 6.5F;
               this.yRotation = 0.4487989544868469D;
            }

            public void run() {
               try {
                  this.location = EffectsLib.getLocation(center, idName);
                  this.location.add(disX, disY, disZ);
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  this.y = this.heightCounter;
                  this.location.add(0.0D, this.y, 0.0D);

                  for(double angle = 0.0D; angle < 6.2831854820251465D; angle += (double)(3.1415927F / (float)ringDensity)) {
                     this.x = Math.cos(angle) * this.radiusCounter;
                     this.z = Math.sin(angle) * this.radiusCounter;
                     this.v = new Vector(this.x, 0.0D, this.z);
                     ParticleEffect.valueOf(particle).display(idName, dataMat, dataID, player, this.location.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                     this.location.subtract(this.x, 0.0D, this.z);
                  }

                  for(int i = 0; i < ringDensity; ++i) {
                     double anglex = 6.2831854820251465D * (double)i / (double)ringDensity;
                     double y2 = Math.sin((double)(3.1415927F * (float)i / (float)ringDensity));
                     this.x = Math.cos(anglex) * this.radiusCounter;
                     this.z = Math.sin(anglex) * this.radiusCounter;
                     this.v.setX(this.edgeLength * (float)i / (float)ringDensity).setZ(this.edgeLength * (float)i / (float)ringDensity);
                     this.v.setY((double)height * y2 * y2);
                     VectorUtils.rotateAroundAxisY(this.v, this.yRotation);
                     ParticleEffect.flame.display(0.0F, 0.0F, 0.0F, 0.0F, 1, this.location.add(this.v), 50.0D);
                     this.location.subtract(this.v);
                  }

                  this.location.subtract(0.0D, this.y, 0.0D);
                  if (scan) {
                     if (this.heightCounter > (double)height) {
                        this.up = false;
                     } else if (this.heightCounter < 0.0D) {
                        this.up = true;
                     }

                     if (this.radiusCounter > (double)radius) {
                        this.out = false;
                     } else if (this.heightCounter < 0.0D) {
                        this.out = true;
                     }
                  } else {
                     if (this.heightCounter > (double)height) {
                        this.heightCounter = 0.0D;
                     }

                     if (this.heightCounter < 0.0D) {
                        this.heightCounter = (double)height;
                     }

                     if (this.radiusCounter > (double)radius) {
                        this.radiusCounter = 0.0D;
                     }

                     if (this.radiusCounter < 0.0D) {
                        this.radiusCounter = (double)radius;
                     }
                  }

                  if (this.up) {
                     this.heightCounter += this.stepHeight;
                  }

                  if (!this.up) {
                     this.heightCounter -= this.stepHeight;
                  }

                  if (this.out) {
                     this.radiusCounter += this.stepRadius;
                  }

                  if (!this.out) {
                     this.radiusCounter -= this.stepRadius;
                  }
               } catch (NullPointerException var6) {
                  EffectsLib.foundNull(this.location, idName, var6);
               }

            }
         }, 0L, delayTicks).getTaskId();
         arraylist.put(idName, rings);
      }

   }

   public static void drawStatCircle(Location loc, String idName) {
      if (!arraylist.containsKey(idName)) {
         int betatest8 = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(loc) {
            Location l;

            {
               this.l = var1;
            }

            public void run() {
               Iterator var2 = SimpleShapes.getCircle(this.l, 0.6D, 15).iterator();

               Location loc;
               while(var2.hasNext()) {
                  loc = (Location)var2.next();
                  ParticleEffect.flame.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc, 100.0D);
               }

               var2 = SimpleShapes.getCircle(this.l, 1.0D, 20).iterator();

               while(var2.hasNext()) {
                  loc = (Location)var2.next();
                  ParticleEffect.flame.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc, 100.0D);
               }

            }
         }, 0L, 5L).getTaskId();
         arraylist.put(idName, betatest8);
      }

   }

   public static ArrayList getStar2(Location center, double radius, int amount) {
      World world = center.getWorld();
      ArrayList locations = new ArrayList();

      for(int i = 1; i < 6; ++i) {
         Vector v = new Vector(Math.cos((double)i * 1.2566370614359172D) * radius, 0.0D, Math.sin((double)i * 1.2566370614359172D) * radius);
         Vector star = v.clone();
         double rcos = Math.cos(2.5132741228718345D);
         double rsin = Math.sin(2.5132741228718345D);
         double rx = star.getX() * rcos + star.getZ() * rsin;
         double rz = star.getX() * -rsin + star.getZ() * rcos;
         star.setX(rx).setZ(rz);
         center.add(v);
         Vector link = star.clone().subtract(v.clone());
         float length = (float)link.length();
         link.normalize();
         float ratio = length / (float)amount;
         Vector v3 = link.multiply(ratio);
         Location loc = center.clone().subtract(v3);

         for(int i2 = 0; i2 < amount; ++i2) {
            loc.add(v3);
            locations.add(new Location(world, loc.getX(), center.getY(), loc.getZ()));
         }

         center.subtract(v);
      }

      return locations;
   }

   public static ArrayList getStar3(Location center, double radius, int amount) {
      double angleForward = 2.5132741928100586D;
      World world = center.getWorld();
      ArrayList locations = new ArrayList();

      for(int i = 1; i < 6; ++i) {
         double angleY = (double)((float)i * 1.2566371F);
         double x = Math.cos(angleY) * radius;
         double z = Math.sin(angleY) * radius;
         Vector v = new Vector(x, 0.0D, z);
         Vector star = v.clone();
         VectorUtils.rotateAroundAxisY(star, angleForward);
         center.add(v);
         Vector link = star.clone().subtract(v.clone());
         float length = (float)link.length();
         link.normalize();
         float ratio = length / (float)amount;
         Vector v3 = link.multiply(ratio);
         Location loc = center.clone().subtract(v3);

         for(int i2 = 0; i2 < amount; ++i2) {
            locations.add(new Location(world, loc.add(v3).getX(), center.getY(), loc.add(v3).getZ()));
         }

         center.subtract(v);
      }

      return locations;
   }

   public static enum Plane {
      X,
      Y,
      Z,
      XY,
      XZ,
      XYZ,
      YZ;
   }
}
