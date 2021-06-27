package ud.skript.sashie.skDragon.particleEngine.effects.spawns;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.effects.DragonEffect;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.maths.SimpleShapes;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.DontRegister;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@DontRegister
@Name("Spawn more complex directional particle patterns")
@Description({"Most particles react differently to this effect using their individual built in mojang nature", "Uses any bukkit vector type as input for the direction"})
@Syntaxes({"draw %number% style %number% %particlename% particle[s] at %objects% with direction %vector% and speed %number%[, offset %-number%, %-number%, %-number%][, id %-string%][, visibleTo %-players%][, visibleRange %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"draw 2 style 1 flame particles at {_loc} with direction {_v2} and speed .2"})
public class EffSpawnParticleDirectionMadness2 extends DragonEffect {
   private Expression partCount;
   private Expression inputStyle;
   private Expression particleName;
   private Expression entLoc;
   private Expression inputDirection;
   private Expression inputSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputRange;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;
   private String parsedSyntax = "";

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.partCount = exprs[0];
      this.inputStyle = exprs[1];
      this.particleName = exprs[2];
      this.entLoc = exprs[3];
      this.inputDirection = exprs[4];
      this.inputSpeed = exprs[5];
      this.offX = exprs[6];
      this.offY = exprs[7];
      this.offZ = exprs[8];
      this.inputIdName = exprs[9];
      this.inputPlayers = exprs[10];
      this.inputRange = exprs[11];
      this.inputPulseDelay = exprs[12];
      this.inputKeepDelay = exprs[13];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "";
   }

   public static Location getLocation(Object location) {
      if (location instanceof Entity) {
         return ((Entity)location).getLocation();
      } else {
         return location instanceof Location ? (Location)location : null;
      }
   }

   protected void exec(@Nullable Event e) {
      final int count = SkriptHandler.inputParticleCount(e, this.partCount);
      final int style = SkriptHandler.inputInt(1, e, this.inputStyle);
      ParticleEffect inputParticle = (ParticleEffect)this.particleName.getSingle(e);
      final Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      Vector direction = SkriptHandler.inputVector(e, this.inputDirection);
      float speed = SkriptHandler.inputFloat(0.0F, e, this.inputSpeed);
      long finalPulseTick = (long)SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      final long finalKeepDelay = SkriptHandler.inputKeepDelay(e, this.inputKeepDelay);
      double range = SkriptHandler.inputDouble(32.0D, e, this.inputRange);
      final String idName;
      if (this.inputIdName != null) {
         idName = (String)this.inputIdName.getSingle(e);
      } else {
         idName = "&dot-" + Math.random() + "-&dot";
      }

      final DragonParticle particle = new DragonParticle();
      particle.setRange(range);
      particle.setDirection(direction);
      particle.setSpeed(speed);
      particle.setParticle(inputParticle);
      if (this.inputKeepDelay != null && finalPulseTick > finalKeepDelay) {
         finalPulseTick = finalKeepDelay;
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         final HashMap S = new HashMap();
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            float finalOffsetX = offsetX;
            float finalOffsetY = offsetY;
            float finalOffsetZ = offsetZ;
            Vector dir = particle.getDirection().normalize();
            Random r = new Random();
            protected double prevPosX;
            protected double prevPosY;
            protected double prevPosZ;
            protected double posX;
            protected double posY;
            protected double posZ;
            protected double motionX;
            protected double motionY;
            protected double motionZ;
            protected int particleMaxAge;
            private double coordX;
            private double coordY;
            private double coordZ;

            void startBigSoulWell(Location loc) {
               final int num = this.r.nextInt(999999999);
               S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable(loc) {
                  Location l;
                  int i;
                  int f;

                  {
                     this.l = var2.clone();
                     this.i = 0;
                     this.f = 0;
                  }

                  public void run() {
                     ArrayList locs = SimpleShapes.getCircle(this.l, 3.5D, 75);
                     ArrayList locs2 = SimpleShapes.getCircleReverse(this.l, 3.5D, 75);
                     ParticleEffect.witchspell.display(0.0F, 0.0F, 0.0F, 0.0F, 1, (Location)locs.get(this.i), 100.0D);
                     ParticleEffect.witchspell.display(0.0F, 0.0F, 0.0F, 0.0F, 1, (Location)locs2.get(this.i), 100.0D);
                     ++this.i;
                     ++this.f;
                     this.l.add(0.0D, 0.04D, 0.0D);
                     if (this.i == 75) {
                        this.i = 0;
                     }

                     if (this.f == 105) {
                        Bukkit.getScheduler().cancelTask((Integer)S.get(num));
                        S.remove(num);
                     }

                  }
               }, 0L, 1L));
            }

            void startColor(final Location loc, final Vector loc2) {
               final int num = this.r.nextInt(999999999);
               S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
                  float t = 0.0F;
                  int f = 0;

                  public void run() {
                     Vector link = loc2.subtract(loc.toVector());
                     float linkLength = (float)link.length();
                     link.normalize();
                     float ratio = linkLength / 20.0F;
                     Vector line = link.multiply(ratio);
                     this.t += particle.getSpeed() / 100.0F;
                     Location finalLoc = loc.clone().add(line.multiply(this.t));
                     particle.displayColor(idName, players, finalLoc, true);
                     ++this.f;
                     if (line.length() >= (double)ratio) {
                        Bukkit.getScheduler().cancelTask((Integer)S.get(num));
                        S.remove(num);
                     }

                  }
               }, 0L, 10L));
            }

            void startColor2(final Location loc, Location loc2) {
               final int num = this.r.nextInt(999999999);
               S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable(loc2) {
                  Location l;
                  float t;
                  int f;

                  {
                     this.l = var2.clone();
                     this.t = 0.0F;
                     this.f = 0;
                  }

                  public void run() {
                     Vector link = loc.toVector().subtract(this.l.toVector());
                     float linkLength = (float)link.length();
                     link.normalize();
                     float ratio = linkLength / 20.0F;
                     Vector line = link.multiply(ratio);
                     this.t += particle.getSpeed() / 100.0F;
                     Location finalLoc = this.l.clone().add(line.multiply(this.t));
                     particle.displayColor(idName, players, finalLoc, true);
                     finalLoc.subtract(line.multiply(this.t));
                     ++this.f;
                     if ((long)this.f == finalKeepDelay) {
                        Bukkit.getScheduler().cancelTask((Integer)S.get(num));
                        S.remove(num);
                     }

                  }
               }, 0L, 10L));
            }

            void startPortal(final Location loc) {
               final int num = this.r.nextInt(999999999);
               S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
                  boolean init = false;
                  int particleAge;

                  public void run() {
                     if (!this.init) {
                        double d0 = loc.clone().getX();
                        double d1 = loc.clone().getY();
                        double d2 = loc.clone().getZ();
                        double i = particle.getDirection().getX();
                        double k = particle.getDirection().getY();
                        double j = particle.getDirection().getZ();

                        for(double d11 = 0.0D; d11 < 6.283185307179586D; d11 += 0.15707963267948966D) {
                           init1(d0, d1 - 0.4D, d2, i + Math.cos(d11), k, j + Math.sin(d11));
                           particleMaxAge = (int)(Math.random() * 10.0D) + 40;
                        }

                        this.init = true;
                     }

                     float f = (float)this.particleAge / (float)particleMaxAge;
                     float f1 = -f + f * f * 2.0F;
                     float f2 = 1.0F - f1;
                     posX = coordX + motionX * (double)f2;
                     posY = coordY + motionY * (double)f2 + (double)(1.0F - f);
                     posZ = coordZ + motionZ * (double)f2;
                     Location boop = new Location(loc.getWorld(), posX, posY, posZ);
                     particle.displayColor(idName, players, boop, true);
                     if (this.particleAge++ >= particleMaxAge) {
                        Bukkit.getScheduler().cancelTask((Integer)S.get(num));
                        S.remove(num);
                     }

                  }
               }, 0L, 1L));
            }

            void startEnchant(final Location pos) {
               final int num = this.r.nextInt(999999999);
               S.put(num, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, new Runnable() {
                  boolean init = false;
                  int particleAge;
                  Random rand;

                  public void run() {
                     if (!this.init) {
                        double i = particle.getDirection().getX();
                        double k = particle.getDirection().getY();
                        double j = particle.getDirection().getZ();
                        this.rand = new Random();
                        init2(pos.getX(), pos.getY() + 2.0D, pos.getZ(), (double)((float)i + this.rand.nextFloat()) - 0.5D, (double)((float)k - this.rand.nextFloat() - 1.0F), (double)((float)j + this.rand.nextFloat()) - 0.5D);
                        particleMaxAge = (int)(Math.random() * 10.0D) + 30;
                        this.init = true;
                     }

                     float f = (float)this.particleAge / (float)particleMaxAge;
                     f = 1.0F - f;
                     float f1 = 1.0F - f;
                     f1 *= f1;
                     f1 *= f1;
                     posX = coordX + motionX * (double)f;
                     posY = coordY + motionY * (double)f - (double)(f1 * 1.2F);
                     posZ = coordZ + motionZ * (double)f;
                     Location boop = new Location(pos.getWorld(), posX, posY, posZ);
                     particle.displayColor(idName, players, boop, true);
                     if (this.particleAge++ >= particleMaxAge) {
                        Bukkit.getScheduler().cancelTask((Integer)S.get(num));
                        S.remove(num);
                     }

                  }
               }, 0L, 1L));
            }

            private void init1(double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
               this.motionX = xSpeedIn;
               this.motionY = ySpeedIn;
               this.motionZ = zSpeedIn;
               this.posX = xCoordIn;
               this.posY = yCoordIn;
               this.posZ = zCoordIn;
               this.coordX = this.posX;
               this.coordY = this.posY;
               this.coordZ = this.posZ;
            }

            private void init2(double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
               this.motionX = xSpeedIn;
               this.motionY = ySpeedIn;
               this.motionZ = zSpeedIn;
               this.coordX = xCoordIn;
               this.coordY = yCoordIn;
               this.coordZ = zCoordIn;
               this.prevPosX = xCoordIn + xSpeedIn;
               this.prevPosY = yCoordIn + ySpeedIn;
               this.prevPosZ = zCoordIn + zSpeedIn;
               this.posX = this.prevPosX;
               this.posY = this.prevPosY;
               this.posZ = this.prevPosZ;
            }

            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = EffSpawnParticleDirectionMadness2.getLocation(loc);
                  Vector target = particle.getDirection().normalize();

                  for(int i = 0; i < count; ++i) {
                     if (offsetX > 0.0F) {
                        this.finalOffsetX = RandomUtils.randomRangeFloat(-offsetX, offsetX);
                     }

                     if (offsetY > 0.0F) {
                        this.finalOffsetY = RandomUtils.randomRangeFloat(-offsetY, offsetY);
                     }

                     if (offsetZ > 0.0F) {
                        this.finalOffsetZ = RandomUtils.randomRangeFloat(-offsetZ, offsetZ);
                     }

                     location.add((double)this.finalOffsetX, (double)this.finalOffsetY, (double)this.finalOffsetZ);
                     if (style <= 1) {
                        if (!particle.getParticle().hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
                           this.startPortal(location);
                        } else {
                           particle.displayDirectional(idName, players, location);
                        }
                     } else if (style == 2) {
                        particle.displayDirectional(idName, players, EffSpawnParticleDirectionMadness2.drop(location));
                     } else if (style == 3) {
                        particle.setDirection(new Vector((double)EffSpawnParticleDirectionMadness2.Vec(), 0.1D, (double)EffSpawnParticleDirectionMadness2.Vec()));
                        particle.displayDirectional(idName, players, location);
                     } else if (style == 4) {
                        this.startBigSoulWell(location);
                     } else if (style == 5) {
                        if (!particle.getParticle().hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
                           this.startEnchant(location);
                        } else {
                           particle.displayDirectional(idName, players, location);
                        }
                     } else if (style >= 6) {
                        Iterator var9 = SimpleShapes.getStar4(location, 3.0D, 8.0F, 10).iterator();

                        while(var9.hasNext()) {
                           Location l = (Location)var9.next();
                           particle.displayColor(idName, players, l, true);
                        }
                     }

                     location.subtract((double)this.finalOffsetX, (double)this.finalOffsetY, (double)this.finalOffsetZ);
                  }
               }

            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, dotGo);
         if (this.inputIdName == null) {
            EffectsLib.stopEffectDelayed(finalKeepDelay, dotGo, idName);
         }
      }

   }

   private static Location drop(Location loc) {
      Random r = new Random();
      Random rr = new Random();
      double x = (double)r.nextInt(100) / 100.0D - 0.5D;
      double z = (double)rr.nextInt(100) / 100.0D - 0.5D;
      return loc.add(x, 0.0D, z);
   }

   private static float Vec() {
      float Vec = -0.05F + (float)(Math.random() * 0.1D);
      return Vec;
   }
}
