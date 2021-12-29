package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawBreath ")
@Description({"Draws a 'fire breath' type effect at the players mouth or at a location. New as of v0.07.0-Beta "})
@Syntaxes({"draw[Dragon]Breath particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], arcPitch %number%, arcCount %number%, arcDensity %number%, arcSteps %number%, arcLength %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawDragonBreath particle \"redstone\", RGB 102, 204, 255, center player, id \"%player%\", arcPitch .1, arcCount 40, arcDensity 50, arcSteps 3, arcLength 6, visibleRange 30", "drawDragonBreath particle \"flame\", center player, id \"%player%-1\", arcPitch .05, arcCount 10, arcDensity 50, arcSteps 2, arcLength 6, visibleRange 30"})
public class EffBreath extends Effect {
   private Expression particleString;
   private Expression data;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression offXT;
   private Expression offYT;
   private Expression offZT;
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression arcPitch;
   private Expression arcCount;
   private Expression pDensity;
   private Expression stepsPerIt;
   private Expression arcLength;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.particleString = exprs[i++];
      this.data = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.offXT = exprs[i++];
      this.offYT = exprs[i++];
      this.offZT = exprs[i++];
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.arcPitch = exprs[i++];
      this.arcCount = exprs[i++];
      this.pDensity = exprs[i++];
      this.stepsPerIt = exprs[i++];
      this.arcLength = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawDragonBreath particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], arcPitch %number%, arcCount %number%, arcDensity %number%, arcSteps %number%, arcLength %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "flame";
      float finalSpeed = 0.0F;
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float offsetXT = 0.0F;
      float offsetYT = 0.0F;
      float offsetZT = 0.0F;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = (float)((Number)this.offX.getSingle(e)).intValue();
         offsetY = (float)((Number)this.offY.getSingle(e)).intValue();
         offsetZ = (float)((Number)this.offZ.getSingle(e)).intValue();
      }

      if (this.offXT != null && this.offYT != null && this.offZT != null) {
         offsetXT = (float)((Number)this.offXT.getSingle(e)).intValue();
         offsetYT = (float)((Number)this.offYT.getSingle(e)).intValue();
         offsetZT = (float)((Number)this.offZT.getSingle(e)).intValue();
      }

      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      Player p = null;
      boolean isSinglePlayer = false;
      if (this.singlePlayer != null && this.singlePlayer.getSingle(e) != null && this.player != null && this.player.getSingle(e) != null) {
         isSinglePlayer = (Boolean)this.singlePlayer.getSingle(e);
         p = (Player)this.player.getSingle(e);
      }

      boolean rainbowMode = false;
      if (this.rainbMode != null && this.rainbMode.getSingle(e) != null) {
         rainbowMode = (Boolean)this.rainbMode.getSingle(e);
      }

      float finalArcPitch = ((Number)this.arcPitch.getSingle(e)).floatValue();
      int finalArcs = ((Number)this.arcCount.getSingle(e)).intValue();
      int finalParticleCount = ((Number)this.pDensity.getSingle(e)).intValue();
      int finalStepPerIteration = ((Number)this.stepsPerIt.getSingle(e)).intValue();
      float finalLength = ((Number)this.arcLength.getSingle(e)).floatValue();
      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
      }

      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      try {
         Material dataMat = ((ItemStack)this.data.getSingle(e)).getType();
         byte dataID = ((ItemStack)this.data.getSingle(e)).getData().getData();
         EffectsLib.drawBreath(particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, center, idName, isSinglePlayer, p, rainbowMode, finalArcPitch, finalArcs, finalParticleCount, finalStepPerIteration, finalLength, visibleRange, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      } catch (Exception var30) {
         Material dataMatNull = Material.DIRT;
         byte dataIDNull = 0;
         EffectsLib.drawBreath(particle, dataMatNull, dataIDNull, finalSpeed, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, center, idName, isSinglePlayer, p, rainbowMode, finalArcPitch, finalArcs, finalParticleCount, finalStepPerIteration, finalLength, visibleRange, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      }

   }
}
