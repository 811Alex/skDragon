package ud.skript.sashie.skDragon.particleEngine.effects.spawns;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
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

@Name("Spawn material based directional particles")
@Description({"Unlike normal directional particles this syntax allows input for the 2 compatible material type particles", "These particles require a marterial input ie. 'diamond sword' or 'redstone block' depending on the type used"})
@Syntaxes({"draw %number% directional (blockdust|itemcrack) particle[s] made of %itemstack% at %objects% with direction %vector% and speed %number%[, offset %-number%, %-number%, %-number%][, id %-string%][, visibleTo %-players%][, visibleRange %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"draw 2 directional blockdust particles made of redstone block at location of player with direction {_v2} and speed .6", "draw 2 directional itemcrack particles made of diamond sword at location of player with direction {_v2} and speed .6"})
public class EffSpawnParticleDirectionMat extends DragonEffect {
   private Expression partCount;
   private Expression inputParticleData;
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
      int i = 0;
      this.partCount = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputDirection = exprs[i++];
      this.inputSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputRange = exprs[i++];
      this.inputPulseDelay = exprs[i++];
      this.inputKeepDelay = exprs[i++];
      this.parsedSyntax = parseResult.expr;
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "Material Based Directional Particles";
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
      ParticleEffect inputParticle = ParticleEffect.happyvillager;
      Material dataMat = Material.DIRT;
      if (this.parsedSyntax.contains("blockdust")) {
         inputParticle = ParticleEffect.blockdust;
      } else if (this.parsedSyntax.contains("itemcrack")) {
         inputParticle = ParticleEffect.itemcrack;
      }

      dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      final Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      final float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      final float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      final float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      Vector direction = SkriptHandler.inputVector(e, this.inputDirection);
      float speed = SkriptHandler.inputFloat(0.0F, e, this.inputSpeed);
      long finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      long finalKeepDelay = SkriptHandler.inputKeepDelay(e, this.inputKeepDelay);
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
      particle.setMaterial(dataMat);
      particle.setMaterialId(dataID);
      if (this.inputKeepDelay != null && finalPulseTick > finalKeepDelay) {
         finalPulseTick = finalKeepDelay;
      }

      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            float finalOffsetX = offsetX;
            float finalOffsetY = offsetY;
            float finalOffsetZ = offsetZ;

            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = EffSpawnParticleDirectionMat.getLocation(loc);

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

                     location.add(this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ);
                     particle.displayDirectional(idName, players, location);
                     location.subtract(this.finalOffsetX, this.finalOffsetY, this.finalOffsetZ);
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
