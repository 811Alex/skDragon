package ud.skript.sashie.skDragon.particleEngine.maths;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import wtfplswork.Runnable;

public class Disco extends EffectsLib {
   public static void drawEffect(final int style, final ParticleEffect particle, final Material dataMat, final byte dataID, final float speed, Vector offset, final ParticleEffect particle2, final Material dataMat2, final byte dataID2, final float speed2, final Vector offset2, final String idName, final DynamicLocation center, final List players, final boolean rainbowMode, final int maxLines, int lineLength, final float sphereRadius, final int sphereDensity, final int lineDensity, final double visibleRange, final Vector displacement, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int disco = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(lineLength, offset) {
            boolean init = false;
            int max;
            float finalOffsetX;
            float finalOffsetY;
            float finalOffsetZ;
            float finalOffsetX2;
            float finalOffsetY2;
            float finalOffsetZ2;

            {
               this.max = (int) vars.get(0);
               this.finalOffsetX = (float)((Vector) vars.get(1)).getX();
               this.finalOffsetY = (float)((Vector) vars.get(1)).getY();
               this.finalOffsetZ = (float)((Vector) vars.get(1)).getZ();
               this.finalOffsetX2 = (float)offset2.getX();
               this.finalOffsetY2 = (float)offset2.getY();
               this.finalOffsetZ2 = (float)offset2.getZ();
            }

            public void run() {
               try {
                  center.update();
                  if (!center.isDynamic() && !this.init) {
                     center.add(displacement.getX(), displacement.getY(), displacement.getZ());
                     this.init = true;
                  } else if (center.isDynamic()) {
                     center.add(displacement.getX(), displacement.getY(), displacement.getZ());
                  }

                  if (rainbowMode) {
                     this.finalOffsetX2 = ParticleEffect.simpleRainbowHelper(this.finalOffsetX2, particle2);
                     if (offset2.getY() == 0.0D) {
                        this.finalOffsetY2 = 1.0F;
                     }

                     if (offset2.getZ() == 0.0D) {
                        this.finalOffsetZ2 = 1.0F;
                     }
                  }

                  int mL = RandomUtils.random.nextInt(maxLines - 2) + 2;

                  int i;
                  for(i = 0; i < mL * 2; ++i) {
                     double x = RandomUtils.random.nextInt(this.max - this.max * -1) + this.max * -1;
                     double y = RandomUtils.random.nextInt(this.max - this.max * -1) + this.max * -1;
                     double z = RandomUtils.random.nextInt(this.max - this.max * -1) + this.max * -1;
                     if (style == 1) {
                        y = RandomUtils.random.nextInt(this.max * 2 - this.max) + this.max;
                     } else if (style == 2) {
                        y = RandomUtils.random.nextInt(this.max * -1 - this.max * -2) + this.max * -2;
                     }

                     Location target = center.clone().subtract(x, y, z);
                     if (target == null) {
                        Disco.stopEffect(idName);
                        return;
                     }

                     Vector link = target.toVector().subtract(center.toVector());
                     float length = (float)link.length();
                     link.normalize();
                     float ratio = length / (float)lineDensity;
                     Vector v = link.multiply(ratio);
                     Location loc = center.clone().subtract(v);

                     for(int ix = 0; ix < lineDensity; ++ix) {
                        loc.add(v);
                        particle2.display(idName, dataMat2, dataID2, players, loc, visibleRange, rainbowMode, this.finalOffsetX2, this.finalOffsetY2, this.finalOffsetZ2, speed2, 1);
                     }
                  }

                  for(i = 0; i < sphereDensity; ++i) {
                     Vector vector = RandomUtils.getRandomVector().multiply(sphereRadius);
                     center.add(vector);
                     particle.display(idName, dataMat, dataID, players, center, visibleRange, false, this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ, speed, 1);
                     center.subtract(vector);
                  }
               } catch (NullPointerException var16) {
                  Disco.foundNull(center, idName, var16);
                  Disco.stopEffect(idName);
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, disco);
      }

   }
}
