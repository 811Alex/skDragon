package ud.skript.sashie.skDragon.particleEngine.effects.spawns;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.DragonParticle;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Spawn material particles")
@Description({"Specifically spawns material particles such as items or blocks(falling dust is a special case where it displays a color based on the block type used)"})
@Syntaxes({"draw %number% (blockcrack|blockdust|itemcrack|fallingdust)[ material]  particle[s] made of %itemstack% at %objects%[, speed %-number%][, offset %-number%, %-number%, %-number%][, id %-string%][, visibleTo %-players%][, visibleRange %-number%][, pulseDelay %-number%][, keepFor %-timespan%]"})
@Examples({"draw 1 itemcrack particle made of diamond sword at location of player", "draw 5 blockcrack particle made of redstone block at location of player"})
public class EffSpawnParticleMaterial extends Effect {
   private Expression partCount;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputRange;
   private Expression inputPulseDelay;
   private Expression inputKeepDelay;
   private String parsedSyntax;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.partCount = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputParticleSpeed = exprs[i++];
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
      return "Material Based Particles";
   }

   protected void execute(@Nullable Event e) {
      int count = SkriptHandler.inputParticleCount(e, this.partCount);
      final Object[] center = this.entLoc.getAll(e);
      final List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      float finalSpeed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      float offsetX = SkriptHandler.inputParticleOffset(e, this.offX);
      float offsetY = SkriptHandler.inputParticleOffset(e, this.offY);
      float offsetZ = SkriptHandler.inputParticleOffset(e, this.offZ);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      long finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseDelay);
      long finalKeepDelay = SkriptHandler.inputKeepDelay(e, this.inputKeepDelay);
      double range = SkriptHandler.inputDouble(32.0D, e, this.inputRange);
      final String idName;
      if (this.inputIdName != null) {
         idName = (String)this.inputIdName.getSingle(e);
      } else {
         idName = "&dot-" + Math.random() + "-&dot";
      }

      if (this.inputKeepDelay != null && finalPulseTick > finalKeepDelay) {
         finalPulseTick = finalKeepDelay;
      }

      ParticleEffect inputParticle = ParticleEffect.blockcrack;
      if (this.parsedSyntax.contains("blockcrack")) {
         inputParticle = ParticleEffect.blockcrack;
      } else if (this.parsedSyntax.contains("blockdust")) {
         inputParticle = ParticleEffect.blockdust;
      } else if (this.parsedSyntax.contains("itemcrack")) {
         inputParticle = ParticleEffect.itemcrack;
      } else if (this.parsedSyntax.contains("fallingdust")) {
         inputParticle = ParticleEffect.fallingdust;
      }

      final DragonParticle particle = new DragonParticle(inputParticle, range, count, dataMat, dataID, offsetX, offsetY, offsetZ, finalSpeed);
      if (!EffectsLib.arraylist.containsKey(idName)) {
         int dotGo = Bukkit.getServer().getScheduler().runTaskTimer(skDragonCore.skdragoncore, new Runnable() {
            public void run() {
               Object[] var4;
               int var3 = (var4 = center).length;

               for(int var2 = 0; var2 < var3; ++var2) {
                  Object loc = var4[var2];
                  Location location = SkriptHandler.getLocation(loc);
                  particle.displayMaterials(idName, players, location);
               }

            }
         }, 0L, finalPulseTick).getTaskId();
         EffectsLib.arraylist.put(idName, dotGo);
         if (this.inputIdName == null) {
            EffectsLib.stopEffectDelayed(finalKeepDelay, idName);
         }
      }

   }
}
