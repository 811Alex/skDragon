package ud.skript.sashie.skDragon.particleEngine.maths;

import ch.njol.skript.Skript;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public class Cape2 extends EffectsLib {
   boolean initialize = false;
   BufferedImage image = null;
   float hue;
   Player p;
   double capeHeight;
   boolean isGliding = false;
   float floatDown = 0.523599F;
   int imgHeight;
   int imgWidth;
   HashMap vectorMap = new HashMap();
   File file;
   float size;
   DynamicLocation center;
   String idName;

   public Cape2(File file, DynamicLocation center, String idName, int scale) {
      this.file = file;
      this.center = center;
      this.idName = idName;
      this.size = 1.0F / (float)scale;
      this.init();
      this.imageInit();
   }

   private void init() {
      if (!this.initialize) {
         if (this.image == null && this.file != null) {
            try {
               this.image = ImageIO.read(this.file);
            } catch (Exception var2) {
               Skript.warning("[skDragon] Error: Invalid file type or make sure the image is in /plugins/skDragon/capes");
               this.image = null;
            }
         }

         if (this.image == null) {
            EffectsLib.stopEffect(this.idName);
            Skript.warning("[skDragon] Error: The image failed to load, try another? :c");
            return;
         }

         this.imgHeight = this.image.getHeight();
         this.imgWidth = this.image.getWidth();
         if (this.center.getEntity() instanceof Player) {
            this.p = (Player)this.center.getEntity();
         }

         this.initialize = true;
      }

   }

   private void imageInit() {
      for(int y = 0; y < this.imgHeight; ++y) {
         for(int x = 0; x < this.imgWidth; ++x) {
            int filter = this.image.getRGB(x, y);
            if (filter != 0 && filter >> 24 != 0 && filter != 16777215) {
               Color color = new Color(filter);
               this.vectorMap.put((new Vector((double)(this.imgWidth / 2 - x) - 0.5D, -y, 0.0D)).multiply(this.size), new Vector(color.getRed(), color.getGreen(), color.getBlue()));
            }
         }
      }

      this.image.flush();
   }

   public void drawEffect(final boolean isSinglePlayer, final Player player, final boolean rainbowMode, final boolean dynamicCape, final float backSet, final float gravity, final double visibleRange, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(this.idName)) {
         int cape = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable() {
            public void run() {
               try {
                  Cape2.this.center.update();
                  if (Cape2.this.p != null) {
                     if (skDragonCore.serverVersion >= 9) {
                        Cape2.this.isGliding = Cape2.this.p.isGliding();
                     }

                     if (rainbowMode) {
                        Cape2.this.hue = ParticleEffect.simpleRainbowHelper(Cape2.this.hue, "redstone");
                     }

                     if (Cape2.this.p.isSneaking()) {
                        Cape2.this.capeHeight = 1.2D / (double)Cape2.this.imgHeight;
                     } else if (Cape2.this.isGliding) {
                        Cape2.this.capeHeight = 0.5D / (double)Cape2.this.imgHeight;
                     } else {
                        Cape2.this.capeHeight = 1.4D / (double)Cape2.this.imgHeight;
                     }

                     Cape2.this.center.add(0.0D, (double)Cape2.this.imgHeight * Cape2.this.capeHeight, 0.0D);
                     if (dynamicCape) {
                        Cape2.this.center.movementCheck();
                        if (Cape2.this.center.isFalling()) {
                           Cape2.this.floatDown = 2.9F;
                        } else {
                           Cape2 var10000;
                           if (Cape2.this.center.isMoving() && !Cape2.this.center.isFalling()) {
                              if (!(Cape2.this.floatDown > 1.3F)) {
                                 Cape2.this.floatDown = 1.3F;
                              } else {
                                 var10000 = Cape2.this;
                                 var10000.floatDown -= 0.1F + gravity;
                              }
                           } else if (Cape2.this.floatDown > 0.523599F) {
                              var10000 = Cape2.this;
                              var10000.floatDown -= 0.1F + gravity;
                           } else {
                              Cape2.this.floatDown = 0.523599F;
                           }
                        }
                     }
                  }

                  Iterator var2 = Cape2.this.vectorMap.entrySet().iterator();

                  while(var2.hasNext()) {
                     Entry entry = (Entry)var2.next();
                     Vector v = ((Vector)entry.getKey()).clone();
                     Vector color = (Vector)entry.getValue();
                     double yaw = Math.toRadians(-1.0F * Cape2.this.center.getYaw() + 180.0F);
                     double pitch = -Cape2.this.floatDown;
                     double cosYaw = Math.cos(yaw);
                     double cosPitch = Math.cos(pitch);
                     double sinYaw = Math.sin(yaw);
                     double sinPitch = Math.sin(pitch);
                     double initialY = v.getY();
                     double initialZ = v.getZ();
                     double z = initialY * sinPitch - initialZ * cosPitch;
                     double y = initialY * cosPitch + initialZ * sinPitch;
                     initialZ = z;
                     double initialX = v.getX();
                     z = z * cosYaw - initialX * sinYaw;
                     double x = initialZ * sinYaw + initialX * cosYaw;
                     v.setX(x).setY(y).setZ(z);
                     Vector v2 = VectorUtils.getBackVector(Cape2.this.center);
                     if (Cape2.this.isGliding) {
                        v2.setY(0).multiply(1.1D + (double)backSet);
                     } else {
                        v2.setY(0).multiply(-0.3D + (double)backSet);
                     }

                     double r = color.getX();
                     double g = color.getY();
                     double b = color.getZ();
                     ParticleEffect.redstone.display(Cape2.this.center.add(v).add(v2), visibleRange, isSinglePlayer, player, rainbowMode, Cape2.this.hue, (int)r, (int)g, (int)b, (int)r, (int)g, (int)b);
                     Cape2.this.center.subtract(v2).subtract(v);
                  }
               } catch (NullPointerException var36) {
                  Cape2.foundNull(Cape2.this.center, Cape2.this.idName, var36);
                  Cape2.stopEffect(Cape2.this.idName);
               }

            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(this.idName, cape);
      }

   }

   public boolean getRange(float low, float high, float x) {
      return x - low <= high - low;
   }
}
