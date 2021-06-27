package ud.skript.sashie.skDragon.particleEngine.maths;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;
import wtfplswork.Runnable;

public class Heart extends EffectsLib {
   public static void drawEffect(final ParticleEffect particle, final float speed, final Material dataMat, final byte dataID, final String idName, final DynamicLocation center, final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final float spin, double height, double width, double innerSpike, double compress, final int particleDensity, final double xRotation, final double yRotation, final double zRotation, final float offsetX, final float offsetY, final float offsetZ, final double disX, final double disY, final double disZ, final double visibleRange, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int heart = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(width, height, innerSpike, compress) {
            boolean init = false;
            float hue;
            List vectorList = new ArrayList();
            Vector v = new Vector(0, 0, 0);
            double yFactor;
            double xFactor;
            double factorInnerSpike;
            double compressYFactorTotal;
            float complication;

            {
               this.yFactor = (double) vars.get(1);
               this.xFactor = (double) vars.get(0);
               this.factorInnerSpike = (double) vars.get(2);
               this.compressYFactorTotal = 1.0D / (double) vars.get(3);
               this.complication = 2.0F;
            }

            public void run() {
               try {
                  center.update();
                  if (rainbowMode) {
                     this.hue = ParticleEffect.simpleRainbowHelper(this.hue, particle);
                  }

                  int i;
                  if (!this.init) {
                     center.add(disX, disY, disZ);

                     for(i = 0; i < particleDensity; ++i) {
                        float alpha = 3.1415927F / this.complication / (float)particleDensity * (float)i;
                        double phi = Math.pow(Math.abs(Math.sin((double)(2.0F * this.complication * alpha))) + this.factorInnerSpike * Math.abs(Math.sin((double)(this.complication * alpha))), this.compressYFactorTotal);
                        Vector init = new Vector(0.0D, phi * (Math.sin((double)alpha) + Math.cos((double)alpha)) * this.yFactor, phi * (Math.cos((double)alpha) - Math.sin((double)alpha)) * this.xFactor);
                        VectorUtils.rotateVector(init, xRotation, yRotation, zRotation);
                        this.vectorList.add(init);
                     }

                     this.init = true;
                  } else {
                     for(i = 0; i < this.vectorList.size(); ++i) {
                        this.v = (Vector)this.vectorList.get(i);
                        VectorUtils.rotateVector(this.v, 0.0D, (double)spin, 0.0D);
                        particle.display(idName, dataMat, dataID, player, center.add(this.v), visibleRange, isSinglePlayer, rainbowMode, this.hue, offsetX, offsetY, offsetZ, speed, 1);
                        center.subtract(this.v);
                     }
                  }
               } catch (NullPointerException var6) {
                  Heart.foundNull(center, idName, var6);
                  Heart.stopEffect(idName);
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, heart);
      }

   }
}
