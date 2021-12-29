package ud.skript.sashie.skDragon.particleEngine.maths;

import ch.njol.skript.Skript;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import wtfplswork.Runnable;

public class Line extends EffectsLib {
   public static void drawEffect(final ParticleEffect particle, final Material dataMat, final byte dataID, final float speed, final Vector offset, final Vector offsetTrans, final String idName, final DynamicLocation center, final DynamicLocation inputTarget, final List players, final boolean rainbowMode, final boolean solid, float inputDensity, final float length, final int zigZags, final float zigHeight, final double visibleRange, final Vector displacement, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int line = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(inputDensity) {
            boolean init = false;
            float finalOffsetX = (float)offset.getX();
            float finalOffsetY = (float)offset.getY();
            float finalOffsetZ = (float)offset.getZ();
            float finalOffsetXT = (float)offsetTrans.getX();
            float finalOffsetYT = (float)offsetTrans.getY();
            float finalOffsetZT = (float)offsetTrans.getZ();
            Location target;
            float density;
            float densityFactor;
            boolean zigs;
            float step;
            float deltaStep;

            {
               this.density = (float) vars.get(0);
               this.densityFactor = (float) vars.get(0) / 100.0F;
               this.zigs = false;
               this.step = 0.0F;
               this.deltaStep = 0.0F;
            }

            public void run() {
               try {
                  if (solid && center.isDynamic()) {
                     center.update();
                     center.add(displacement.getX(), displacement.getY(), displacement.getZ());
                  }

                  if (!this.init && !solid) {
                     center.add(displacement.getX(), displacement.getY(), displacement.getZ());
                     this.init = true;
                  }

                  if (rainbowMode) {
                     this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, particle);
                     this.finalOffsetXT = ParticleEffect.simpleRainbowHelper(this.finalOffsetXT, particle);
                     if (offset.getY() == 0.0D) {
                        this.finalOffsetY = 1.0F;
                     }
                     if (offsetTrans.getY() == 0.0D) {
                        this.finalOffsetYT = 1.0F;
                     }

                     if (offset.getZ() == 0.0D) {
                        this.finalOffsetZ = 1.0F;
                     }
                     if (offsetTrans.getZ() == 0.0D) {
                        this.finalOffsetZT = 1.0F;
                     }
                  }

                  if (inputTarget == null && length > 0.0F) {
                     this.target = center.clone().add(center.getDirection().normalize().multiply(length));
                  } else if (inputTarget != null) {
                     if (inputTarget.isDynamic()) {
                        this.target = inputTarget.update();
                     } else {
                        this.target = inputTarget;
                     }
                  } else if (inputTarget == null && length == 0.0F) {
                     Skript.warning("[skDragon] Error: No location set and length equals zero!");
                     Line.stopEffect(idName);
                  }

                  double amount = 0.0D;
                  if (zigZags > 0) {
                     amount = this.density / (float)zigZags;
                  }

                  Vector link = this.target.toVector().subtract(center.toVector());
                  float linkLength = (float)link.length();
                  link.normalize();
                  float ratio;
                  if (inputTarget != null && length > 0.0F) {
                     ratio = length / this.density;
                  } else {
                     ratio = linkLength / this.density;
                  }

                  Vector line = link.multiply(ratio);
                  Location finalLoc = center;
                  if (solid) {
                     finalLoc = center.clone().subtract(line);
                  } else {
                     finalLoc = center.clone().add(line.multiply(this.deltaStep));
                     if (length == 0.0F) {
                        if (line.length() >= (double)(linkLength / 2.0F)) {
                           Line.stopEffect(idName);
                        } else {
                           this.deltaStep += this.densityFactor;
                        }
                     } else if (line.length() < (double)ratio) {
                        this.deltaStep += this.densityFactor;
                     } else if (line.length() >= (double)ratio) {
                        Line.stopEffect(idName);
                     }

                     this.density = 1.0F;
                  }

                  for(int i = 0; (float)i < this.density; ++i) {
                     if (zigZags > 0) {
                        if (this.zigs) {
                           finalLoc.add(0.0D, zigHeight, 0.0D);
                        } else {
                           finalLoc.subtract(0.0D, zigHeight, 0.0D);
                        }
                     }

                     finalLoc.add(line);
                     particle.display(idName, dataMat, dataID, players, finalLoc, visibleRange, rainbowMode, this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ, this.finalOffsetXT, this.finalOffsetYT, this.finalOffsetZT, speed, 1);
                     if (!solid) {
                        finalLoc.subtract(line);
                     }
                  }

                  if ((double)this.step >= amount) {
                     this.zigs = !this.zigs;

                     this.step = 0.0F;
                  }

                  ++this.step;
               } catch (NullPointerException var9) {
                  Line.foundNull(center, idName, var9);
                  Line.stopEffect(idName);
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, line);
      }

   }
}
