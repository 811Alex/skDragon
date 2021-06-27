package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawSphere")
@Description({"Draws a sphere that follows the player or plays at a location. New as of v0.10.0-Beta"})
@Syntaxes({"drawSphere[ style] %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, density %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawSphere style 1, particle \"redstone\", center {_loc}, id \"%player%\", rainbowMode true, radius 1, density 100, visibleRange 32, pulseDelay 1"})
public class EffSphere extends Effect {
   private Expression style;
   private Expression particleString;
   private Expression inputParticleData;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression InputIdName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression InputRingDensity;
   private Expression radius;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.style = exprs[0];
      this.particleString = exprs[1];
      this.inputParticleData = exprs[2];
      this.speed = exprs[3];
      this.offX = exprs[4];
      this.offY = exprs[5];
      this.offZ = exprs[6];
      this.entLoc = exprs[7];
      this.InputIdName = exprs[8];
      this.singlePlayer = exprs[9];
      this.player = exprs[10];
      this.rainbMode = exprs[11];
      this.radius = exprs[12];
      this.InputRingDensity = exprs[13];
      this.range = exprs[14];
      this.displaceX = exprs[15];
      this.displaceY = exprs[16];
      this.displaceZ = exprs[17];
      this.ticks = exprs[18];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawSphere style %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, density %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
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
      int ringDensity = true;
      Long finalDelayTicks = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.InputIdName.getSingle(e);
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

      int ringDensity = ((Number)this.InputRingDensity.getSingle(e)).intValue();
      float finalRadius = ((Number)this.radius.getSingle(e)).floatValue();
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

      int finalStyle = 1;
      if (this.style != null) {
         finalStyle = ((Long)this.style.getSingle(e)).intValue();
      }

      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      EffectsLib.drawSphere(finalStyle, particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, center, idName, isSinglePlayer, p, rainbowMode, finalRadius, ringDensity, visibleRange, xRotation, yRotation, zRotation, disX, disY, disZ, 0L, finalDelayTicks);
   }
}
