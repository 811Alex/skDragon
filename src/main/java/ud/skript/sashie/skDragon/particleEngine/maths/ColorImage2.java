package ud.skript.sashie.skDragon.particleEngine.maths;

import ch.njol.skript.Skript;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.VectorUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.PlaneEnum;
import wtfplswork.Runnable;

public class ColorImage2 extends EffectsLib {
   public static void drawEffect(final File file, final String idName, final DynamicLocation center, final List players, final boolean autoFace, final boolean enableRotation, final PlaneEnum plane, Vector xyzSpeed, final int pixelStepX, final int pixelStepY, final float scaleSize, final double visibleRange, Vector axis, final Vector displacement, long delayStart, long delayPulse) {
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int txt = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, new Runnable(axis, xyzSpeed) {
            boolean initImage = false;
            HashMap vectorMap = new HashMap();
            HashMap gifMap = new HashMap();
            Vector v = new Vector(0, 0, 0);
            Vector color = new Vector(0, 0, 0);
            int filter;
            float angle;
            float yaw;
            float pitchX;
            float pitchZ;
            double r;
            double g;
            double b;
            double angularVelocityX;
            double angularVelocityY;
            double angularVelocityZ;
            BufferedImage image;
            boolean isGif;
            File gifFile;
            int step;
            int rotationStep;
            boolean invert;
            // $FF: synthetic field
            private static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum;

            {
               this.yaw = (float)((Vector)vars.get(0)).getY();
               this.pitchX = (float)((Vector)vars.get(0)).getX();
               this.pitchZ = (float)((Vector)vars.get(0)).getZ();
               this.angularVelocityX = 3.1415927410125732D / ((Vector)vars.get(1)).getX();
               this.angularVelocityY = 3.1415927410125732D / ((Vector)vars.get(1)).getY();
               this.angularVelocityZ = 3.1415927410125732D / ((Vector)vars.get(1)).getZ();
               this.image = null;
               this.isGif = false;
               this.gifFile = null;
               this.step = 0;
               this.rotationStep = 0;
               this.invert = false;
            }

            public void run() {
               try {
                  if (center.isDynamic()) {
                     center.update();
                     this.angle = center.getYaw();
                     center.add(displacement.getX(), displacement.getY(), displacement.getZ());
                  }

                  int y;
                  if (!this.initImage) {
                     if (this.image == null && file != null) {
                        try {
                           this.image = ImageIO.read(file);
                           this.isGif = file.getName().endsWith(".gif");
                           this.gifFile = file;
                        } catch (Exception var9) {
                           Skript.warning("[skDragon] Error: Invalid file used, make sure the image is in /plugins/skDragon/");
                           this.image = null;
                        }
                     }

                     if (this.image == null) {
                        ColorImage2.stopEffect(idName);
                        Skript.warning("[skDragon] Error: The image failed to load, try another? :c");
                        return;
                     }

                     if (!this.isGif) {
                        for(y = 0; y < this.image.getHeight(); y += pixelStepY) {
                           for(int x = 0; x < this.image.getWidth(); x += pixelStepX) {
                              this.filter = this.image.getRGB(x, y);
                              if ((this.invert || this.filter != 0 && this.filter >> 24 != 0 && this.filter != 16777215) && (!this.invert || this.filter == 0 && this.filter >> 24 == 0 && this.filter != 16777215)) {
                                 Vector v2 = (new Vector((float)this.image.getWidth() / 2.0F - (float)x, (float)this.image.getHeight() / 2.0F - (float)y, 0.0F)).multiply(1.0F / scaleSize);
                                 v2 = VectorUtils.rotateVector(v2, (double)(this.pitchX * 0.017453292F), (double)(this.yaw * 0.017453292F), (double)(this.pitchZ * 0.017453292F));
                                 Vector vColor = new Vector((new Color(this.image.getRGB(x, y))).getRed(), (new Color(this.image.getRGB(x, y))).getGreen(), (new Color(this.image.getRGB(x, y))).getBlue());
                                 this.vectorMap.put(v2, vColor);
                              }
                           }
                        }
                     } else {
                        try {
                           ImageReader reader = (ImageReader)ImageIO.getImageReadersBySuffix("GIF").next();
                           ImageInputStream in = ImageIO.createImageInputStream(this.gifFile);
                           reader.setInput(in);
                           int i = 0;
                           int count = reader.getNumImages(true);

                           while(true) {
                              if (i >= count) {
                                 reader.dispose();
                                 in.close();
                                 break;
                              }

                              this.image = reader.read(i);

                              for(int yx = 0; yx < this.image.getHeight(); yx += pixelStepY) {
                                 for(int xx = 0; xx < this.image.getWidth(); xx += pixelStepX) {
                                    this.filter = this.image.getRGB(xx, yx);
                                    if ((this.invert || this.filter != 0 && this.filter >> 24 != 0 && this.filter != 16777215) && (!this.invert || this.filter == 0 && this.filter >> 24 == 0 && this.filter != 16777215)) {
                                       Vector v2x = (new Vector((float)this.image.getWidth() / 2.0F - (float)xx, (float)this.image.getHeight() / 2.0F - (float)yx, 0.0F)).multiply(1.0F / scaleSize);
                                       Vector vColorx = new Vector((new Color(this.image.getRGB(xx, yx))).getRed(), (new Color(this.image.getRGB(xx, yx))).getGreen(), (new Color(this.image.getRGB(xx, yx))).getBlue());
                                       v2x = VectorUtils.rotateVector(v2x, (double)(this.pitchX * 0.017453292F), (double)(this.yaw * 0.017453292F), (double)(this.pitchZ * 0.017453292F));
                                       this.vectorMap.put(v2x, vColorx);
                                    }
                                 }
                              }

                              this.gifMap.put(i, this.vectorMap);
                              this.vectorMap.clear();
                              ++i;
                           }
                        } catch (IOException var10) {
                           Skript.warning("[skDragon] Error: The .gif failed to load..");
                           var10.printStackTrace();
                        }
                     }

                     this.initImage = true;
                  } else {
                     double rotX;
                     double rotY;
                     Vector v2xx;
                     double rotZ;
                     if (!this.isGif) {
                        for(y = 0; y < this.vectorMap.size(); ++y) {
                           this.v = (Vector)this.vectorMap.keySet().toArray()[y];
                           this.color = (Vector)this.vectorMap.get(this.v);
                           if (enableRotation && !autoFace) {
                              rotX = 0.0D;
                              rotY = 0.0D;
                              rotZ = 0.0D;
                              switch($SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum()[plane.ordinal()]) {
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

                              this.v = VectorUtils.rotateVector(this.v, rotX, rotY, rotZ);
                           }

                           this.r = this.color.getX();
                           this.g = this.color.getY();
                           this.b = this.color.getZ();
                           if (center.isDynamic() && autoFace) {
                              v2xx = VectorUtils.rotateVectorYX(this.v, this.angle, 0.0F);
                              ParticleEffect.redstone.display(center.add(v2xx), visibleRange, players, (int)this.r, (int)this.g, (int)this.b);
                              center.subtract(v2xx);
                           } else {
                              ParticleEffect.redstone.display(center.add(this.v), visibleRange, players, (int)this.r, (int)this.g, (int)this.b);
                              center.subtract(this.v);
                           }
                        }
                     } else {
                        for(y = 0; y < ((HashMap)this.gifMap.get(this.step)).size(); ++y) {
                           this.v = (Vector)((HashMap)this.gifMap.get(this.step)).keySet().toArray()[y];
                           this.color = (Vector)((HashMap)this.gifMap.get(this.step)).get(this.v);
                           if (enableRotation && !autoFace) {
                              rotX = 0.0D;
                              rotY = 0.0D;
                              rotZ = 0.0D;
                              switch($SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum()[plane.ordinal()]) {
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

                              this.v = VectorUtils.rotateVector(this.v, rotX, rotY, rotZ);
                           }

                           this.r = this.color.getX();
                           this.g = this.color.getY();
                           this.b = this.color.getZ();
                           if (center.isDynamic() && autoFace) {
                              v2xx = VectorUtils.rotateVectorYX(this.v, this.angle, 0.0F);
                              ParticleEffect.redstone.display(center.add(v2xx), visibleRange, players, (int)this.r, (int)this.g, (int)this.b);
                              center.subtract(v2xx);
                           } else {
                              ParticleEffect.redstone.display(center.add(this.v), visibleRange, players, (int)this.r, (int)this.g, (int)this.b);
                              center.subtract(this.v);
                           }
                        }

                        ++this.step;
                        if (this.step == this.gifMap.size() + 1) {
                           this.step = 0;
                        }
                     }

                     ++this.rotationStep;
                  }
               } catch (NullPointerException var11) {
                  ColorImage2.foundNull(center, idName, var11);
                  ColorImage2.stopEffect(idName);
               }

            }

            // $FF: synthetic method
            static int[] $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum() {
               int[] var10000 = $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum;
               if (var10000 != null) {
                  return var10000;
               } else {
                  int[] var0 = new int[PlaneEnum.values().length];

                  try {
                     var0[PlaneEnum.X.ordinal()] = 1;
                  } catch (NoSuchFieldError var7) {
                  }

                  try {
                     var0[PlaneEnum.XY.ordinal()] = 4;
                  } catch (NoSuchFieldError var6) {
                  }

                  try {
                     var0[PlaneEnum.XYZ.ordinal()] = 6;
                  } catch (NoSuchFieldError var5) {
                  }

                  try {
                     var0[PlaneEnum.XZ.ordinal()] = 5;
                  } catch (NoSuchFieldError var4) {
                  }

                  try {
                     var0[PlaneEnum.Y.ordinal()] = 2;
                  } catch (NoSuchFieldError var3) {
                  }

                  try {
                     var0[PlaneEnum.YZ.ordinal()] = 7;
                  } catch (NoSuchFieldError var2) {
                  }

                  try {
                     var0[PlaneEnum.Z.ordinal()] = 3;
                  } catch (NoSuchFieldError var1) {
                  }

                  $SWITCH_TABLE$ud$skript$sashie$skDragon$particleEngine$utils$enums$PlaneEnum = var0;
                  return var0;
               }
            }
         }, delayStart, delayPulse).getTaskId();
         EffectsLib.arraylist.put(idName, txt);
      }

   }
}
