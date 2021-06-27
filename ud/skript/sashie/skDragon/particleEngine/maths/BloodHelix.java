package ud.skript.sashie.skDragon.particleEngine.maths;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;

public class BloodHelix extends EffectsLib {
   public static void drawEffect(int style, final ParticleEffect particle, final float speed, final Material dataMat, final byte dataID, final String idName, final DynamicLocation center, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, float radius, float grow, int circles, int helixes, int particleDensity, double xRotation, double yRotation, double zRotation, final float offsetX, final float offsetY, final float offsetZ, final double disX, final double disY, final double disZ, final double visibleRange, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int cone = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(radius, particleDensity, grow, helixes, circles) {
            boolean init = false;
            float hue;
            float radius1;
            double angularVelocity;
            float step;
            int particles1;
            protected int i;
            int speed1;
            double height;
            int particles2;
            float radius2;
            protected int i2;
            int speed2;
            double height2;
            public float lengthGrow;
            public int particles;
            public float radiusGrow;
            public int particlesCone;
            public double rotation;
            public boolean randomize;

            {
               this.radius1 = var1;
               this.angularVelocity = (double)(3.1415927F / (float)var2);
               this.step = 0.0F;
               this.particles1 = 40;
               this.speed1 = 25;
               this.height = 0.0D;
               this.particles2 = 40;
               this.radius2 = 2.0F;
               this.speed2 = 25;
               this.height2 = 0.0D;
               this.lengthGrow = var3;
               this.particles = var4;
               this.radiusGrow = var1;
               this.particlesCone = var5;
               this.rotation = 0.0D;
               this.randomize = false;
            }

            public void run() {
               try {
                  center.update();
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  if (!this.init) {
                     center.add(disX, disY, disZ);
                     this.init = true;
                  }

                  Location location2 = center;
                  double angle = (double)(6.2831855F * (float)this.i / (float)(this.particles1 * this.speed1));
                  double x = Math.cos(angle) * (double)this.radius1;
                  double z = Math.sin(angle) * (double)this.radius1;
                  center.add(x, this.height, z);
                  particle.display(idName, dataMat, dataID, player, center, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  center.subtract(x, 0.0D, z);
                  this.i += this.speed1;
                  if ((double)this.radius1 > 0.02D) {
                     this.radius1 = (float)((double)this.radius1 - 0.05D);
                     this.height += 0.1D;
                  } else {
                     this.radius1 = 2.0F;
                     this.height = 0.0D;
                  }

                  double angle2 = (double)(6.2831855F * (float)this.i2 / (float)(this.particles2 * this.speed2));
                  double x2 = Math.cos(angle2) * (double)(-this.radius2);
                  double z2 = Math.sin(angle2) * (double)(-this.radius2);
                  location2.add(x2, this.height2, z2);
                  particle.display(idName, dataMat, dataID, player, center, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                  location2.subtract(x, 0.0D, z);
                  this.i2 += this.speed2;
                  if ((double)this.radius2 > 0.02D) {
                     this.radius2 = (float)((double)this.radius2 - 0.05D);
                     this.height2 += 0.1D;
                  } else {
                     this.radius2 = 2.0F;
                     this.height2 = 0.0D;
                  }
               } catch (NullPointerException var14) {
                  BloodHelix.foundNull(center, idName, var14);
                  BloodHelix.stopEffect(idName);
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, cone);
      }

   }
}
