package ud.skript.sashie.skDragon.particleEngine.utils;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;

public class DragonParticle implements Serializable {
   private static final long serialVersionUID = 3746272493580677801L;
   ParticleEffect particle;
   boolean rainbowMode;
   boolean randomColor;
   double range;
   int count;
   Material material;
   byte materialId;
   float speed;
   float offsetX;
   float offsetY;
   float offsetZ;
   float r;
   float g;
   float b;
   double dirX;
   double dirY;
   double dirZ;

   public DragonParticle() {
      this.particle = ParticleEffect.happyvillager;
      this.rainbowMode = false;
      this.randomColor = false;
      this.range = 32.0D;
      this.count = 1;
      this.material = Material.DIRT;
      this.materialId = 0;
      this.speed = 0.0F;
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.offsetZ = 0.0F;
      this.r = 0.0F;
      this.g = 0.0F;
      this.b = 0.0F;
      this.dirX = 0.0D;
      this.dirY = 0.0D;
      this.dirZ = 0.0D;
   }

   public DragonParticle(ParticleEffect particle) {
      this.particle = ParticleEffect.happyvillager;
      this.rainbowMode = false;
      this.randomColor = false;
      this.range = 32.0D;
      this.count = 1;
      this.material = Material.DIRT;
      this.materialId = 0;
      this.speed = 0.0F;
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.offsetZ = 0.0F;
      this.r = 0.0F;
      this.g = 0.0F;
      this.b = 0.0F;
      this.dirX = 0.0D;
      this.dirY = 0.0D;
      this.dirZ = 0.0D;
      this.particle = particle;
   }

   public DragonParticle(ParticleEffect particle, double range, int count, float offsetX, float offsetY, float offsetZ, float speed) {
      this.particle = ParticleEffect.happyvillager;
      this.rainbowMode = false;
      this.randomColor = false;
      this.range = 32.0D;
      this.count = 1;
      this.material = Material.DIRT;
      this.materialId = 0;
      this.speed = 0.0F;
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.offsetZ = 0.0F;
      this.r = 0.0F;
      this.g = 0.0F;
      this.b = 0.0F;
      this.dirX = 0.0D;
      this.dirY = 0.0D;
      this.dirZ = 0.0D;
      this.particle = particle;
      this.range = range;
      this.count = count;
      this.speed = speed;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.offsetZ = offsetZ;
   }

   public DragonParticle(ParticleEffect particle, double range, int count, Material material, byte materialId, float offsetX, float offsetY, float offsetZ, float speed) {
      this.particle = ParticleEffect.happyvillager;
      this.rainbowMode = false;
      this.randomColor = false;
      this.range = 32.0D;
      this.count = 1;
      this.material = Material.DIRT;
      this.materialId = 0;
      this.speed = 0.0F;
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.offsetZ = 0.0F;
      this.r = 0.0F;
      this.g = 0.0F;
      this.b = 0.0F;
      this.dirX = 0.0D;
      this.dirY = 0.0D;
      this.dirZ = 0.0D;
      this.particle = particle;
      this.range = range;
      this.count = count;
      this.material = material;
      this.materialId = materialId;
      this.speed = speed;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.offsetZ = offsetZ;
   }

   public DragonParticle(ParticleEffect particle, double range, Vector direction, float speed) {
      this.particle = ParticleEffect.happyvillager;
      this.rainbowMode = false;
      this.randomColor = false;
      this.range = 32.0D;
      this.count = 1;
      this.material = Material.DIRT;
      this.materialId = 0;
      this.speed = 0.0F;
      this.offsetX = 0.0F;
      this.offsetY = 0.0F;
      this.offsetZ = 0.0F;
      this.r = 0.0F;
      this.g = 0.0F;
      this.b = 0.0F;
      this.dirX = 0.0D;
      this.dirY = 0.0D;
      this.dirZ = 0.0D;
      this.particle = particle;
      this.range = range;
      this.speed = speed;
      this.dirX = direction.getX();
      this.dirY = direction.getY();
      this.dirZ = direction.getZ();
   }

   public void display(Location location) {
      this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, location, this.range);
   }

   public ParticleEffect getParticle() {
      return this.particle;
   }

   public void setParticle(ParticleEffect particle) {
      this.particle = particle;
   }

   public boolean getRainbowMode() {
      return this.rainbowMode;
   }

   public void setRainbowMode(boolean rainbowMode) {
      this.rainbowMode = rainbowMode;
   }

   public boolean getRandomColor() {
      return this.randomColor;
   }

   public void setRandomColor(boolean randomColor) {
      this.randomColor = randomColor;
   }

   public double getRange() {
      return this.range;
   }

   public void setRange(double range) {
      this.range = range;
   }

   public int getCount() {
      return this.count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public Material getMaterial() {
      return this.material;
   }

   public void setMaterial(Material material) {
      this.material = material;
   }

   public byte getMaterialId() {
      return this.materialId;
   }

   public void setMaterialId(byte materialId) {
      this.materialId = materialId;
   }

   public float getSpeed() {
      return this.speed;
   }

   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public void setOffsetXYZ(float offsetX, float offsetY, float offsetZ) {
      this.setOffsetX(offsetX);
      this.setOffsetY(offsetY);
      this.setOffsetZ(offsetZ);
   }

   public void setOffsetXYZ(double offsetX, double offsetY, double offsetZ) {
      this.setOffsetX((float)offsetX);
      this.setOffsetY((float)offsetY);
      this.setOffsetZ((float)offsetZ);
   }

   public float getOffsetX() {
      return this.offsetX;
   }

   public void setOffsetX(float offsetX) {
      this.offsetX = offsetX;
   }

   public float getOffsetY() {
      return this.offsetY;
   }

   public void setOffsetY(float offsetY) {
      this.offsetY = offsetY;
   }

   public float getOffsetZ() {
      return this.offsetZ;
   }

   public Vector getOffset() {
      return new Vector(this.offsetX, this.offsetY, this.offsetZ);
   }

   public void setOffsetZ(float offsetZ) {
      this.offsetZ = offsetZ;
   }

   public void setRGB(double r, double g, double b) {
      this.r = (float)r;
      this.g = (float)g;
      this.b = (float)b;
   }

   public float getR() {
      return this.r;
   }

   public void setR(float r) {
      this.r = r;
   }

   public float getG() {
      return this.g;
   }

   public void setG(float g) {
      this.g = g;
   }

   public float getB() {
      return this.b;
   }

   public void setB(float b) {
      this.b = b;
   }

   public Vector getRGB() {
      return new Vector(this.r, this.g, this.b);
   }

   public Vector getDirection() {
      return new Vector(this.dirX, this.dirY, this.dirZ);
   }

   public void setDirection(Vector direction) {
      this.dirX = direction.getX();
      this.dirY = direction.getY();
      this.dirZ = direction.getZ();
   }

   public void display(String idName, List players, Location center, boolean rainbowMode) {
      if (!this.particle.isSupported()) {
         EffectsLib.stopEffect(idName);
      } else {
         if (this.particle != ParticleEffect.redstone && this.particle != ParticleEffect.mobspell && this.particle != ParticleEffect.mobspellambient) {
            if (this.particle == ParticleEffect.note) {
               if (rainbowMode) {
                  ParticleEffect.NoteColor color = new ParticleEffect.NoteColor((int)this.r);
                  if (players != null) {
                     this.particle.display(color, center, players);
                  } else {
                     this.particle.display(color, center, this.range);
                  }
               } else if (players != null) {
                  this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, players);
               } else {
                  this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, this.range);
               }
            } else if (this.particle.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
               if ((this.particle == ParticleEffect.fallingdust || this.particle == ParticleEffect.blockcrack || this.particle == ParticleEffect.blockdust) && this.material != null) {
                  ParticleEffect.BlockData finalData = new ParticleEffect.BlockData(this.material, this.materialId);
                  if (players != null) {
                     this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
                  } else {
                     this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
                  }
               } else if (this.particle == ParticleEffect.itemcrack && this.material != null) {
                  ParticleEffect.ItemData finalData = new ParticleEffect.ItemData(this.material, this.materialId);
                  if (players != null) {
                     this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
                  } else {
                     this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
                  }
               }
            } else if (players != null) {
               this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
            } else {
               this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
            }
         } else {
            ParticleEffect.OrdinaryColor color;
            if (rainbowMode) {
               color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(this.r, this.g, this.b));
            } else {
               color = new ParticleEffect.OrdinaryColor((int)this.r, (int)this.g, (int)this.b);
            }
            if (players != null) {
               this.particle.display(color, center, (List)players);
            } else {
               this.particle.display(color, center, this.range);
            }
         }

      }
   }

   public void display(List players, Location center, boolean rainbowMode) {
      if (this.particle != ParticleEffect.redstone && this.particle != ParticleEffect.mobspell && this.particle != ParticleEffect.mobspellambient) {
         if (this.particle == ParticleEffect.note) {
            if (rainbowMode) {
               ParticleEffect.NoteColor color = new ParticleEffect.NoteColor((int)this.r);
               if (players != null) {
                  this.particle.display(color, center, players);
               } else {
                  this.particle.display(color, center, this.range);
               }
            } else if (players != null) {
               this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, players);
            } else {
               this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, this.range);
            }
         } else if (this.particle.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            if ((this.particle == ParticleEffect.fallingdust || this.particle == ParticleEffect.blockcrack || this.particle == ParticleEffect.blockdust) && this.material != null) {
               ParticleEffect.BlockData finalData = new ParticleEffect.BlockData(this.material, this.materialId);
               if (players != null) {
                  this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
               } else {
                  this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
               }
            } else if (this.particle == ParticleEffect.itemcrack && this.material != null) {
               ParticleEffect.ItemData finalData = new ParticleEffect.ItemData(this.material, this.materialId);
               if (players != null) {
                  this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
               } else {
                  this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
               }
            }
         } else if (players != null) {
            this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
         } else {
            this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
         }
      } else {
         ParticleEffect.OrdinaryColor color;
         if (rainbowMode) {
            color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(this.r, this.g, this.b));
         } else {
            color = new ParticleEffect.OrdinaryColor((int)this.r, (int)this.g, (int)this.b);
         }
         if (players != null) {
            this.particle.display(color, center, (List)players);
         } else {
            this.particle.display(color, center, this.range);
         }
      }

   }

   public void display(List players, Location center) {
      if (this.particle.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         if ((this.particle == ParticleEffect.fallingdust || this.particle == ParticleEffect.blockcrack || this.particle == ParticleEffect.blockdust) && this.material != null) {
            ParticleEffect.BlockData finalData = new ParticleEffect.BlockData(this.material, this.materialId);
            if (players != null) {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
            } else {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
            }
         } else if (this.particle == ParticleEffect.itemcrack && this.material != null) {
            ParticleEffect.ItemData finalData = new ParticleEffect.ItemData(this.material, this.materialId);
            if (players != null) {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
            } else {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
            }
         }
      } else if (players != null) {
         this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
      } else {
         this.particle.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
      }

   }

   public void displayColor(String idName, List players, Location center, boolean rainbowMode) {
      if (!this.particle.isSupported()) {
         EffectsLib.stopEffect(idName);
      } else {
         if (this.particle != ParticleEffect.redstone && this.particle != ParticleEffect.mobspell && this.particle != ParticleEffect.mobspellambient) {
            if (this.particle == ParticleEffect.note) {
               if (rainbowMode) {
                  ParticleEffect.NoteColor color = new ParticleEffect.NoteColor((int)this.r);
                  if (players != null) {
                     this.particle.display(color, center, players);
                  } else {
                     this.particle.display(color, center, this.range);
                  }
               } else if (players != null) {
                  this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, players);
               } else {
                  this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, this.range);
               }
            }
         } else {
            ParticleEffect.OrdinaryColor color;
            if (rainbowMode) {
               color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(this.r, this.g, this.b));
            } else {
               color = new ParticleEffect.OrdinaryColor((int)this.r, (int)this.g, (int)this.b);
            }
            if (players != null) {
               this.particle.display(color, center, (List)players);
            } else {
               this.particle.display(color, center, this.range);
            }
         }

      }
   }

   public void displayColor(List players, Location center, boolean rainbowMode) {
      if (this.particle != ParticleEffect.redstone && this.particle != ParticleEffect.mobspell && this.particle != ParticleEffect.mobspellambient) {
         if (this.particle == ParticleEffect.note) {
            if (rainbowMode) {
               ParticleEffect.NoteColor color = new ParticleEffect.NoteColor((int)this.r);
               if (players != null) {
                  this.particle.display(color, center, players);
               } else {
                  this.particle.display(color, center, this.range);
               }
            } else if (players != null) {
               this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, players);
            } else {
               this.particle.display(new ParticleEffect.NoteColor((int)this.r), center, this.range);
            }
         }
      } else {
         ParticleEffect.OrdinaryColor color;
         if (rainbowMode) {
            color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(this.r, this.g, this.b));
         } else {
            color = new ParticleEffect.OrdinaryColor((int)this.r, (int)this.g, (int)this.b);
         }
         if (players != null) {
            this.particle.display(color, center, (List)players);
         } else {
            this.particle.display(color, center, this.range);
         }
      }

   }

   public void displayColor(int r, int g, int b, List players, Location center, double range) {
      this.displayColor(r, g, b, players, center, false, range);
   }

   public void displayColor(int r, int g, int b, List players, Location center) {
      this.displayColor(r, g, b, players, center, false, this.range);
   }

   public void displayColor(int r, int g, int b, List players, Location center, boolean rainbowMode, double range) {
      if (this.particle != ParticleEffect.redstone && this.particle != ParticleEffect.mobspell && this.particle != ParticleEffect.mobspellambient) {
         if (this.particle == ParticleEffect.note) {
            if (rainbowMode) {
               ParticleEffect.NoteColor color = new ParticleEffect.NoteColor(r);
               if (players != null) {
                  this.particle.display(color, center, players);
               } else {
                  this.particle.display(color, center, range);
               }
            } else if (players != null) {
               this.particle.display(new ParticleEffect.NoteColor(r), center, players);
            } else {
               this.particle.display(new ParticleEffect.NoteColor(r), center, range);
            }
         }
      } else {
         ParticleEffect.OrdinaryColor color;
         if (rainbowMode) {
            color = new ParticleEffect.OrdinaryColor(Color.getHSBColor((float)r, (float)g, (float)b));
         } else {
            color = new ParticleEffect.OrdinaryColor(r, g, b);
         }
         if (players != null) {
            this.particle.display(color, center, (List)players);
         } else {
            this.particle.display(color, center, range);
         }
      }

   }

   public void displayMaterials(String idName, List players, Location center) {
      if (!this.particle.isSupported()) {
         EffectsLib.stopEffect(idName);
      } else {
         if ((this.particle == ParticleEffect.fallingdust || this.particle == ParticleEffect.blockcrack || this.particle == ParticleEffect.blockdust) && this.material != null) {
            ParticleEffect.BlockData finalData = new ParticleEffect.BlockData(this.material, this.materialId);
            if (players != null) {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
            } else {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
            }
         } else if (this.particle == ParticleEffect.itemcrack && this.material != null) {
            ParticleEffect.ItemData finalData = new ParticleEffect.ItemData(this.material, this.materialId);
            if (players != null) {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, players);
            } else {
               this.particle.display(finalData, this.offsetX, this.offsetY, this.offsetZ, this.speed, this.count, center, this.range);
            }
         }

      }
   }

   public void displayDirectional(String idName, List players, Location center) {
      this.displayDirectional(idName, players, center, this.getDirection());
   }

   public void displayDirectional(String idName, List players, Location center, Vector direction) {
      if (this.particle.isSupported() && this.particle.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         if (this.particle == ParticleEffect.blockdust && this.material != null) {
            ParticleEffect.BlockData finalData = new ParticleEffect.BlockData(this.getMaterial(), this.getMaterialId());
            if (players != null) {
               this.particle.display(finalData, direction, this.speed, center, players);
            } else {
               this.particle.display(finalData, direction, this.speed, center, this.range);
            }
         } else if (this.particle == ParticleEffect.itemcrack && this.material != null) {
            ParticleEffect.ItemData finalData = new ParticleEffect.ItemData(this.getMaterial(), this.getMaterialId());
            if (players != null) {
               this.particle.display(finalData, direction, this.speed, center, players);
            } else {
               this.particle.display(finalData, direction, this.speed, center, this.range);
            }
         } else if (players != null) {
            this.particle.display(direction, this.speed, center, players);
         } else {
            this.particle.display(direction, this.speed, center, this.range);
         }

      } else {
         EffectsLib.stopEffect(idName);
      }
   }

   public void displayDirectional(List players, Location center, Vector direction) {
      this.displayDirectional(players, center, this.speed, direction, this.range);
   }

   public void displayDirectional(List players, Location center, float speed, Vector direction, double range) {
      if (players != null) {
         this.particle.display(direction, speed, center, players);
      } else {
         this.particle.display(direction, speed, center, range);
      }

   }
}
