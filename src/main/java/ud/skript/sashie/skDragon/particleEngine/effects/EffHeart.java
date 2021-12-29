package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.Heart;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawHeart")
@Description({"Draws a heart shape at a player or location"})
@Syntaxes({"drawHeart particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], ySpin %number%, width %number%, height %number%, innerSpike %number%, compress %number%, density %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"placeholder"})
public class EffHeart extends Effect {
   private Expression inputParticle;
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
   private Expression spin;
   private Expression inputWidth;
   private Expression inputHeight;
   private Expression inputInnerSpike;
   private Expression inputCompress;
   private Expression inputDensity;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.inputParticle = exprs[i++];
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
      this.spin = exprs[i++];
      this.inputWidth = exprs[i++];
      this.inputHeight = exprs[i++];
      this.inputInnerSpike = exprs[i++];
      this.inputCompress = exprs[i++];
      this.inputDensity = exprs[i++];
      this.range = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawHeart particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], ySpin %number%, width %number%, height %number%, innerSpike %number%, compress %number%, density %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      ParticleEffect particle = (ParticleEffect)this.inputParticle.getSingle(e);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      float finalSpeed = SkriptHandler.inputFloat(0.0F, e, this.speed);
      int offsetX = SkriptHandler.inputInt(0, e, this.offX);
      int offsetY = SkriptHandler.inputInt(0, e, this.offY);
      int offsetZ = SkriptHandler.inputInt(0, e, this.offZ);
      int offsetXT = SkriptHandler.inputInt(0, e, this.offXT);
      int offsetYT = SkriptHandler.inputInt(0, e, this.offYT);
      int offsetZT = SkriptHandler.inputInt(0, e, this.offZT);

      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var40) {
         return;
      }

      String idName = (String)this.InputIdName.getSingle(e);
      boolean isSinglePlayer = SkriptHandler.inputBoolean(false, e, this.singlePlayer);
      Player p = SkriptHandler.inputPlayer(e, this.player);
      boolean rainbowMode = SkriptHandler.inputBoolean(false, e, this.rainbMode);
      float finalSpin = ((Number)this.spin.getSingle(e)).floatValue();
      double finalWidth = ((Number)this.inputWidth.getSingle(e)).doubleValue();
      double finalHeight = ((Number)this.inputHeight.getSingle(e)).doubleValue();
      double finalInnerSpike = ((Number)this.inputInnerSpike.getSingle(e)).doubleValue();
      double finalCompress = ((Number)this.inputCompress.getSingle(e)).doubleValue();
      int pDensity = SkriptHandler.inputInt(10, e, this.inputDensity);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      double xRotation = SkriptHandler.inputDouble(0.0D, e, this.xRot);
      double yRotation = SkriptHandler.inputDouble(0.0D, e, this.yRot);
      double zRotation = SkriptHandler.inputDouble(0.0D, e, this.zRot);
      double disX = SkriptHandler.inputDouble(0.0D, e, this.displaceX);
      double disY = SkriptHandler.inputDouble(0.0D, e, this.displaceY);
      double disZ = SkriptHandler.inputDouble(0.0D, e, this.displaceZ);
      long finalDelayTicks = SkriptHandler.inputLong(0, e, this.ticks);
      Heart.drawEffect(particle, finalSpeed, dataMat, dataID, idName, center, isSinglePlayer, p, rainbowMode, finalSpin, finalWidth, finalHeight, finalInnerSpike, finalCompress, pDensity, xRotation, yRotation, zRotation, (float)offsetX, (float)offsetY, (float)offsetZ, (float)offsetXT, (float)offsetYT, (float)offsetZT, disX, disY, disZ, visibleRange, 0L, finalDelayTicks);
   }
}
