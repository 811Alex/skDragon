package ud.skript.sashie.skDragon.particleEngine.maths;

import ch.njol.skript.Skript;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.ColoredVector;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;

public class Cape extends EffectsLib {
   boolean initialize = false;
   boolean initImage = false;
   BufferedImage image = null;
   float hue;
   Player p;
   double capeHeight;
   boolean isGliding = false;
   float floatDown = 0.523599F;
   int imgHeight;
   int imgWidth;
   List vectorList = new ArrayList();
   File file;
   float size;
   DynamicLocation center;
   String idName;

   public Cape(File file, DynamicLocation center, String idName, int scale) {
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
               this.vectorList.add(new ColoredVector((new Vector((double)(this.imgWidth / 2 - x) - 0.5D, (double)(-y), 0.0D)).multiply(this.size), color));
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
                  Cape.this.center.update();
                  if (Cape.this.p != null) {
                     if (skDragonCore.serverVersion >= 9) {
                        if (Cape.this.p.isGliding()) {
                           Cape.this.isGliding = true;
                        } else {
                           Cape.this.isGliding = false;
                        }
                     }

                     if (rainbowMode) {
                        Cape.this.hue = ParticleEffect.simpleRainbowHelper(Cape.this.hue, "redstone");
                     }

                     if (Cape.this.p.isSneaking()) {
                        Cape.this.capeHeight = 1.2D / (double)Cape.this.imgHeight;
                     } else if (Cape.this.isGliding) {
                        Cape.this.capeHeight = 0.5D / (double)Cape.this.imgHeight;
                     } else {
                        Cape.this.capeHeight = 1.4D / (double)Cape.this.imgHeight;
                     }

                     Cape.this.center.add(0.0D, (double)Cape.this.imgHeight * Cape.this.capeHeight, 0.0D);
                     if (dynamicCape) {
                        Cape.this.center.movementCheck();
                        if (Cape.this.center.isFalling()) {
                           Cape.this.floatDown = 2.9F;
                        } else {
                           Cape var10000;
                           if (Cape.this.center.isMoving() && !Cape.this.center.isFalling()) {
                              if (!(Cape.this.floatDown > 1.3F)) {
                                 Cape.this.floatDown = 1.3F;
                              } else {
                                 var10000 = Cape.this;
                                 var10000.floatDown -= 0.1F + gravity;
                              }
                           } else if (Cape.this.floatDown > 0.523599F) {
                              var10000 = Cape.this;
                              var10000.floatDown -= 0.1F + gravity;
                           } else {
                              Cape.this.floatDown = 0.523599F;
                           }
                        }
                     }
                  }

                  for(int i = Cape.this.vectorList.size(); i > 0; --i) {
                     ColoredVector entry = (ColoredVector)Cape.this.vectorList.get(i - 1);
                     Vector v = entry.getPoint();
                     Color color = entry.getColor();
                     double yaw = Math.toRadians((double)(-1.0F * Cape.this.center.getYaw() + 180.0F));
                     double pitch = (double)(-Cape.this.floatDown);
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
                     Vector v2 = VectorUtils.getBackVector(Cape.this.center);
                     if (Cape.this.isGliding) {
                        v2.setY(0).multiply(1.1D + (double)backSet);
                     } else {
                        v2.setY(0).multiply(-0.3D + (double)backSet);
                     }

                     int r = color.getRed();
                     int g = color.getGreen();
                     int b = color.getBlue();
                     ParticleEffect.redstone.display(Cape.this.center.add(v).add(v2), visibleRange, isSinglePlayer, player, rainbowMode, Cape.this.hue, r, g, b);
                     Cape.this.center.subtract(v2).subtract(v);
                  }
               } catch (NullPointerException var33) {
                  Cape.foundNull(Cape.this.center, Cape.this.idName, var33);
                  Cape.stopEffect(Cape.this.idName);
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
