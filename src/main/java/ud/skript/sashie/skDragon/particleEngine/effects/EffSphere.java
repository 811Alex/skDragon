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
@Syntaxes({"drawSphere[ style] %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, density %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawSphere style 1, particle \"redstone\", center {_loc}, id \"%player%\", rainbowMode true, radius 1, density 100, visibleRange 32, pulseDelay 1"})
public class EffSphere extends Effect {
   private Expression style;
   private Expression particleString;
   private Expression inputParticleData;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression offXT;
   private Expression offYT;
   private Expression offZT;
   private Expression entLoc;
   private Expression InputIdName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression InputRingDensity;
   private Expression radius;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.style = exprs[i++];
      this.particleString = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.offXT = exprs[i++];
      this.offYT = exprs[i++];
      this.offZT = exprs[i++];
      this.entLoc = exprs[i++];
      this.InputIdName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.radius = exprs[i++];
      this.InputRingDensity = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawSphere style %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, density %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float offsetXT = 0.0F;
      float offsetYT = 0.0F;
      float offsetZT = 0.0F;
      double xRotation = 0.0D;
      double yRotation = 0.0D;
      double zRotation = 0.0D;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      float finalSpeed = 0.0F;
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

      if (this.offXT != null && this.offYT != null && this.offZT != null) {
         offsetXT = (float)((Number)this.offXT.getSingle(e)).intValue();
         offsetYT = (float)((Number)this.offYT.getSingle(e)).intValue();
         offsetZT = (float)((Number)this.offZT.getSingle(e)).intValue();
      }

      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
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
      EffectsLib.drawSphere(finalStyle, particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, center, idName, isSinglePlayer, p, rainbowMode, finalRadius, ringDensity, visibleRange, xRotation, yRotation, zRotation, disX, disY, disZ, 0L, finalDelayTicks);
   }
}
