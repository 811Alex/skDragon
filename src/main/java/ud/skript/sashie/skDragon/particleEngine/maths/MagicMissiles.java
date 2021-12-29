package ud.skript.sashie.skDragon.particleEngine.maths;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public class MagicMissiles extends EffectsLib {
   public static void drawEffect(final int style, final ParticleEffect particle, final Material dataMat, final byte dataID, final float speed, final float offsetX, final float offsetY, final float offsetZ, final float offsetXT, final float offsetYT, final float offsetZT, final Object center, final String idName, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final int position, final float finalArcPitch, final int finalArcs, final int finalParticleDensity, final int finalStepPerIteration, final float finalLength, final double damageValue, final double visibleRange, final double disX, final double disY, final double disZ, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int missile = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            protected int timeStep = 0;
            protected final List rndF = new ArrayList(finalArcs);
            protected final List rndAngle = new ArrayList(finalArcs);
            Location location;
            boolean initialize = false;
            Player p;
            public float hue;
            Vector v;
            Vector dir;
            double t = 0.0D;
            List ent;
            double hitboxRadius;
            Location roughCenter;

            public void handPosition() {
               if (position == 1) {
                  this.location.add(disX, disY + 1.175D, disZ);
               } else if (position == 2) {
                  this.location.add(disX, disY + 1.175D, disZ - 0.5D);
               } else if (position == 3) {
                  this.location.add(disX, disY + 1.175D, disZ + 0.5D);
               }

            }

            public void blast() {
               float locPitch = -this.location.getPitch() * 0.017453292F;
               float locYaw = -(this.location.getYaw() + 90.0F) * 0.017453292F;

               for(int j = 0; j < finalStepPerIteration; ++j) {
                  if (this.timeStep % finalParticleDensity == 0) {
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
                     float x = (float)(this.timeStep % finalParticleDensity) * finalLength / (float)finalParticleDensity;
                     float y = (float)((double)pitch * Math.pow(x, 2.0D));
                     this.v = new Vector(x, y, 0.0F);
                     VectorUtils.rotateAroundAxisX(this.v, (Double)this.rndAngle.get(i));
                     VectorUtils.rotateAroundAxisZ(this.v, locPitch);
                     VectorUtils.rotateAroundAxisY(this.v, locYaw);
                     particle.display(idName, dataMat, dataID, player, this.location.clone().add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, speed, 1);
                     this.location.subtract(this.v);
                  }

                  ++this.timeStep;
               }

            }

            private void simpleBeam() {
               this.t += 0.5D;
               double x = this.dir.getX() * this.t;
               double y = this.dir.getY() * this.t + 1.5D;
               double z = this.dir.getZ() * this.t;
               this.location.add(x, y, z);
               particle.display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, speed, 1);
               this.ent = this.location.getWorld().getLivingEntities();
               Iterator var8 = this.ent.iterator();

               while(var8.hasNext()) {
                  LivingEntity le = (LivingEntity)var8.next();
                  double test = le.getEyeHeight() / 2.0D + 0.1D;
                  Location boop = le.getLocation().add(0.0D, test, 0.0D);
                  if (boop.distance(this.location) < test) {
                     le.damage(damageValue);
                  }
               }

               this.location.subtract(x, y, z);
            }

            private void simpleBeam1() {
               this.t += 0.5D;
               double x = this.dir.getX() * this.t;
               double y = this.dir.getY() * this.t + 1.5D;
               double z = this.dir.getZ() * this.t;
               this.location.add(x, y, z);
               particle.display(idName, dataMat, dataID, player, this.location, visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, speed, 1);
               this.collisionCheck();
               this.location.subtract(x, y, z);
            }

            private void collisionCheck() {
               this.ent = this.location.getWorld().getLivingEntities();
               Iterator var2 = this.ent.iterator();

               while(var2.hasNext()) {
                  LivingEntity le = (LivingEntity)var2.next();
                  this.hitboxRadius = le.getEyeHeight() / 2.0D + 0.1D;
                  this.roughCenter = le.getLocation().add(0.0D, this.hitboxRadius, 0.0D);
                  if (this.roughCenter.distance(this.location) < this.hitboxRadius) {
                     le.damage(damageValue);
                  }
               }

            }

            private void collisionCheck2() {
               this.ent = this.location.getWorld().getLivingEntities();
               Iterator var2 = this.ent.iterator();

               while(var2.hasNext()) {
                  LivingEntity le = (LivingEntity)var2.next();
                  this.hitboxRadius = le.getEyeHeight() / 2.0D + 0.1D;
                  this.roughCenter = le.getLocation().add(0.0D, this.hitboxRadius, 0.0D);
                  if (this.roughCenter.distance(this.location) < this.hitboxRadius) {
                     le.damage(damageValue);
                  }
               }

            }

            private void init() {
               if (!this.initialize) {
                  this.location = MagicMissiles.getLocation(center);
                  Iterator var2 = this.location.getWorld().getEntities().iterator();

                  while(var2.hasNext()) {
                     Entity entity = (Entity)var2.next();
                     if (entity instanceof Player && MagicMissiles.arraylist.containsKey(idName) && entity.getLocation().equals(this.location)) {
                        this.p = (Player)entity;
                     }
                  }

                  this.dir = this.p.getLocation().getDirection().normalize();
                  this.location = this.p.getLocation();
                  this.initialize = true;
               }

            }

            public void run() {
               this.init();
               if (rainbowMode) {
                  this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
               }

               if (style <= 1) {
                  this.simpleBeam();
               } else if (style != 2) {
                  if (style == 3) {
                     this.blast();
                  } else if (style == 4) {
                     this.blast();
                  } else if (style == 5) {
                     this.blast();
                  } else if (style == 6) {
                     this.blast();
                  } else if (style >= 7) {
                     this.blast();
                  }
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, missile);
      }

   }
}
