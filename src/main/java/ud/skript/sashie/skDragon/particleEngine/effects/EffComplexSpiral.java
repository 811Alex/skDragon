package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawComplexSpiral ")
@Description({"Draws a spiral that follows the player or plays at a location. New Syntax as of v0.06.0-BETA "})
@Syntaxes({"draw[Complex]Spiral particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], clockwise %boolean%, scan %boolean%, radius %number%, density %number%, height %number%, effectMod %number%, start %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawComplexSpiral particle \"redstone\", center player, id \"%player%\", rainbowMode true, clockwise true, scan true, radius 1.5, density 50, height 6, effectMod .05, start 0, visibleRange 30"})
public class EffComplexSpiral extends Effect {
   private Expression particleString;
   private Expression inputParticleData;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression clockwise;
   private Expression scan;
   private Expression height;
   private Expression effectMod;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression radius;
   private Expression cDensity;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression step;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.particleString = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.clockwise = exprs[i++];
      this.scan = exprs[i++];
      this.radius = exprs[i++];
      this.cDensity = exprs[i++];
      this.height = exprs[i++];
      this.effectMod = exprs[i++];
      this.step = exprs[i++];
      this.range = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      Object center = this.entLoc.getReturnType();
      if (center instanceof Entity) {
         return true;
      } else if (center instanceof Location) {
         return true;
      } else if (center instanceof Object) {
         return true;
      } else {
         Skript.error(this.entLoc.toString() + " is neither an entity nor a location.", ErrorQuality.SEMANTIC_ERROR);
         return false;
      }
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawComplexSpiral particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], clockwise %boolean%, scan %boolean%, radius %number%, density %number%, height %number%, effectMod %number%, start %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      double xRotation = 0.0D;
      double yRotation = 0.0D;
      double zRotation = 0.0D;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      float finalSpeed = 0.0F;
      float finalHeight = 6.0F;
      float finalEffectMod = 0.0F;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      Long cD = (Long)this.cDensity.getSingle(e);
      Integer finalCircleDensity = cD != null ? cD.intValue() : null;
      float finalStep = ((Number)this.step.getSingle(e)).floatValue();
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

      boolean finalClockwise = (Boolean)this.clockwise.getSingle(e);
      boolean finalScan = (Boolean)this.scan.getSingle(e);
      Number radiusInt = (Number)this.radius.getSingle(e);
      float radius = radiusInt.floatValue();
      if (this.height != null) {
         finalHeight = ((Number)this.height.getSingle(e)).floatValue();
      }

      if (this.effectMod != null) {
         finalEffectMod = ((Number)this.effectMod.getSingle(e)).floatValue();
      }

      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = (float)((Number)this.offX.getSingle(e)).intValue();
         offsetY = (float)((Number)this.offY.getSingle(e)).intValue();
         offsetZ = (float)((Number)this.offZ.getSingle(e)).intValue();
      }

      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
      }

      if (this.xRot != null && this.yRot != null && this.zRot != null) {
         xRotation = ((Number)this.xRot.getSingle(e)).doubleValue();
         yRotation = ((Number)this.yRot.getSingle(e)).doubleValue();
         zRotation = ((Number)this.zRot.getSingle(e)).doubleValue();
      }

      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      EffectsLib.drawComplexSpiral(particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, center, idName, isSinglePlayer, p, rainbowMode, finalClockwise, finalScan, visibleRange, xRotation, yRotation, zRotation, radius, finalHeight, finalEffectMod, finalCircleDensity, disX, disY, disZ, finalStep, finalDelayTicks, finalDelayBySec);
   }
}
