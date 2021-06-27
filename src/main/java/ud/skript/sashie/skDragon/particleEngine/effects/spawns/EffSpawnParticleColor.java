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
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.effects.DragonEffect;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.RandomUtils;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Spawn colored particles")
@Description({"Spawns specifically colored particles with options for them including offset like regular particles"})
@Syntaxes({"draw %number% colo[u]red (redstone|mobspell|mobspellambient) particle[s] at %objects% with RGB %number%, %number%, %number%[, offset %-number%, %-number%, %-number%][, id %-string%][, r[ainbow]M[ode] %-boolean%][, randomColor %-boolean%][, visibleTo %-players%][, visibleRange %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"draw 1 colored redstone particle at player with RGB 50, 80, 120, keepFor 5 seconds"})
public class EffSpawnParticleColor extends DragonEffect {
   private Expression partCount;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression coffX;
   private Expression coffY;
   private Expression coffZ;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression isRainbowTrue;
   private Expression inputRandomColor;
   private Expression inputPlayers;
   private Expression inputRange;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;
   private String parsedSyntax;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.partCount = exprs[0];
      this.entLoc = exprs[1];
      this.offX = exprs[2];
      this.offY = exprs[3];
      this.offZ = exprs[4];
      this.coffX = exprs[5];
      this.coffY = exprs[6];
      this.coffZ = exprs[7];
      this.inputIdName = exprs[8];
      this.isRainbowTrue = exprs[9];
      this.inputRandomColor = exprs[10];
      this.inputPlayers = exprs[11];
      this.inputRange = exprs[12];
      this.inputPulseDelay = exprs[13];
      this.inputKeepDelay = exprs[14];
      this.parsedSyntax = parseResult.expr;
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "Colored Particles";
   }

   protected void exec(@Nullable Event e) {
      final int count = SkriptHandler.inputParticleCount(e, this.partCount);
      final Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.isRainbowTrue);
      final boolean randomColor = SkriptHandler.inputBoolean(false, e, this.inputRandomColor);
      float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      final float coffsetX = SkriptHandler.inputFloat(0.0F, e, this.coffX);
      final float coffsetY = SkriptHandler.inputFloat(0.0F, e, this.coffY);
      final float coffsetZ = SkriptHandler.inputFloat(0.0F, e, this.coffZ);
      long finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
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

      final DragonParticle particle = new DragonParticle(inputParticle, range, count, offsetX, offsetY, offsetZ, 0.0F);
      if (this.inputKeepDelay != null && finalPulseTick > finalKeepDelay) {
         finalPulseTick = finalKeepDelay;
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            float finalOffsetX = particle.getOffsetX();
            float finalOffsetY = particle.getOffsetY();
            float finalOffsetZ = particle.getOffsetZ();
            float finalColorOffsetX = coffsetX;
            float finalColorOffsetY = coffsetY;
            float finalColorOffsetZ = coffsetZ;

            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = EffSpawnParticleColor.getLocation(loc);
                  if (randomColor) {
                      if (!rainbowMode) {
                          this.finalOffsetX = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                      }
                      this.finalOffsetY = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                      this.finalOffsetZ = RandomUtils.randomRangeFloat(0.0F, 255.0F);
                  }

                  if (rainbowMode) {
                     if (randomColor) {
                        this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, particle.getParticle());
                        if (offsetY == 0.0F) {
                           this.finalOffsetY = 1.0F;
                        }

                        if (offsetZ == 0.0F) {
                           this.finalOffsetZ = 1.0F;
                        }
                     } else {
                        this.finalOffsetX = ParticleEffect.simpleRainbowHelper(this.finalOffsetX, particle.getParticle());
                     }
                  }

                  particle.setOffsetXYZ(this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ);

                  for(int i = 0; i < count; ++i) {
                     if (coffsetX > 0.0F) {
                        this.finalColorOffsetX = RandomUtils.randomRangeFloat(-coffsetX, coffsetX);
                     }

                     if (coffsetY > 0.0F) {
                        this.finalColorOffsetY = RandomUtils.randomRangeFloat(-coffsetY, coffsetY);
                     }

                     if (coffsetZ > 0.0F) {
                        this.finalColorOffsetZ = RandomUtils.randomRangeFloat(-coffsetZ, coffsetZ);
                     }

                     location.add(this.finalColorOffsetX, this.finalColorOffsetY, this.finalColorOffsetZ);
                     particle.displayColor(idName, players, location, rainbowMode);
                     location.subtract(this.finalColorOffsetX, this.finalColorOffsetY, this.finalColorOffsetZ);
                  }
               }

            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, dotGo);
         if (this.inputIdName == null) {
            EffectsLib.stopEffectDelayed(finalKeepDelay, idName);
         }
      }

   }

   public static Location getLocation(Object location) {
      if (location instanceof Entity) {
         return ((Entity)location).getLocation();
      } else {
         return location instanceof Location ? (Location)location : null;
      }
   }
}
