package ud.skript.sashie.skDragon.particleEngine.effects.spawns;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.effects.DragonEffect;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.maths.FancyShapes;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Spawn more complex directional particle patterns using colorable particles")
@Description({"Most particles react differently to this effect using their individual built in mojang nature", "Uses any bukkit vector type as input for the direction"})
@Syntaxes({"draw %number% style %number% (redstone|mobspell|mobspellambient) particle[s] at %objects% with direction %vector%, speed %number% and RGB %number%, %number%, %number%[, offset %-number%, %-number%, %-number%][, id %-string%][, r[ainbow]M[ode] %-boolean%][, randomColor %-boolean%][, visibleTo %-players%][, visibleRange %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"draw 2 style 2 redstone particles at location of player with direction {_v}, speed .4 and RGB 20, 50, 80, keepFor 1 second"})
public class EffSpawnParticleDirectionMadness extends DragonEffect {
   private Expression partCount;
   private Expression inputStyle;
   private Expression entLoc;
   private Expression inputDirection;
   private Expression inputSpeed;
   private Expression coffX;
   private Expression coffY;
   private Expression coffZ;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression inputIdName;
   private Expression isRainbowTrue;
   private Expression inputRandomColor;
   private Expression inputPlayers;
   private Expression inputRange;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;
   private String parsedSyntax = "";

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.partCount = exprs[0];
      this.inputStyle = exprs[1];
      this.entLoc = exprs[2];
      this.inputDirection = exprs[3];
      this.inputSpeed = exprs[4];
      this.coffX = exprs[5];
      this.coffY = exprs[6];
      this.coffZ = exprs[7];
      this.offX = exprs[8];
      this.offY = exprs[9];
      this.offZ = exprs[10];
      this.inputIdName = exprs[11];
      this.isRainbowTrue = exprs[12];
      this.inputRandomColor = exprs[13];
      this.inputPlayers = exprs[14];
      this.inputRange = exprs[15];
      this.inputPulseDelay = exprs[16];
      this.inputKeepDelay = exprs[17];
      this.parsedSyntax = parseResult.expr;
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "Direction Particle Madness";
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
      final Object[] center = this.entLoc.getAll(e);
      final boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.isRainbowTrue);
      final boolean randomColor = SkriptHandler.inputBoolean(false, e, this.inputRandomColor);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      float coffsetX = SkriptHandler.inputFloat(0.0F, e, this.coffX);
      float coffsetY = SkriptHandler.inputFloat(0.0F, e, this.coffY);
      float coffsetZ = SkriptHandler.inputFloat(0.0F, e, this.coffZ);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      Vector direction = SkriptHandler.inputVector(e, this.inputDirection);
      float speed = SkriptHandler.inputFloat(0.0F, e, this.inputSpeed);
      long finalPulseTick = (long)SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      long finalKeepDelay = SkriptHandler.inputKeepDelay(e, this.inputKeepDelay);
      double range = SkriptHandler.inputDouble(32.0D, e, this.inputRange);
      final String idName;
      if (this.inputIdName != null) {
         idName = (String)this.inputIdName.getSingle(e);
      } else {
         idName = "&dot-" + Math.random() + "-&dot";
      }

      ParticleEffect inputParticle = ParticleEffect.redstone;
      if (this.parsedSyntax.contains("redstone")) {
         inputParticle = ParticleEffect.redstone;
      } else if (this.parsedSyntax.contains("mobspell")) {
         inputParticle = ParticleEffect.mobspell;
      } else if (this.parsedSyntax.contains("mobspellambient")) {
         inputParticle = ParticleEffect.mobspellambient;
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
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable(coffsetX, coffsetY, coffsetZ) {
            float finalOffsetX = offsetX;
            float finalOffsetY = offsetY;
            float finalOffsetZ = offsetZ;
            float finalColorOffsetX;
            float finalColorOffsetY;
            float finalColorOffsetZ;

            {
               this.finalColorOffsetX = var5;
               this.finalColorOffsetY = var6;
               this.finalColorOffsetZ = var7;
            }

            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = EffSpawnParticleDirectionMadness.getLocation(loc);
                  if (randomColor) {
                     if (rainbowMode) {
                        this.finalColorOffsetY = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                        this.finalColorOffsetZ = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                     } else {
                        this.finalColorOffsetX = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                        this.finalColorOffsetY = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                        this.finalColorOffsetZ = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                     }
                  }

                  if (rainbowMode) {
                     if (!randomColor) {
                        this.finalColorOffsetX = ParticleEffect.simpleRainbowHelper(this.finalColorOffsetX, particle.getParticle());
                     } else {
                        this.finalColorOffsetX = ParticleEffect.simpleRainbowHelper(this.finalColorOffsetX, particle.getParticle());
                        if (offsetY == 0.0F) {
                           this.finalColorOffsetY = 1.0F;
                        }

                        if (offsetZ == 0.0F) {
                           this.finalColorOffsetZ = 1.0F;
                        }
                     }
                  }

                  particle.setOffsetXYZ(this.finalColorOffsetX, this.finalColorOffsetY, this.finalColorOffsetZ);

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
                     FancyShapes boop = new FancyShapes(particle, rainbowMode);
                     if (style <= 1) {
                        boop.startLine1(location, idName, players);
                     } else if (style == 2) {
                        boop.startEnchant1(location, idName, players);
                     } else if (style == 3) {
                        boop.startEnchant2(location, idName, players);
                     } else if (style >= 4) {
                        boop.startPortal(location, idName, players);
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
}
