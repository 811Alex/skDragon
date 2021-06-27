package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.Cone;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.DontRegister;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@DontRegister
@Name("drawCone")
@Description({""})
@Syntaxes({"drawCone particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, growth %number%, circles %number%, helixes %number%, density %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%] style %number%"})
@Examples({""})
public class EffCone extends Effect {
   private Expression inputParticle;
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
   private Expression inputRadius;
   private Expression inputGrowth;
   private Expression inputCircles;
   private Expression inputHelixes;
   private Expression inputDensity;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;
   private Expression inputStyle;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.inputParticle = exprs[0];
      this.inputParticleData = exprs[1];
      this.speed = exprs[2];
      this.offX = exprs[3];
      this.offY = exprs[4];
      this.offZ = exprs[5];
      this.entLoc = exprs[6];
      this.InputIdName = exprs[7];
      this.singlePlayer = exprs[8];
      this.player = exprs[9];
      this.rainbMode = exprs[10];
      this.inputRadius = exprs[11];
      this.inputGrowth = exprs[12];
      this.inputCircles = exprs[13];
      this.inputHelixes = exprs[14];
      this.inputDensity = exprs[15];
      this.range = exprs[16];
      this.xRot = exprs[17];
      this.yRot = exprs[18];
      this.zRot = exprs[19];
      this.displaceX = exprs[20];
      this.displaceY = exprs[21];
      this.displaceZ = exprs[22];
      this.ticks = exprs[23];
      this.inputStyle = exprs[24];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawCone particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], radius %number%, growth %number%, circles %number%, helixes %number%, density %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%] style %number%";
   }

   protected void execute(@Nullable Event e) {
      ParticleEffect particle = (ParticleEffect)this.inputParticle.getSingle(e);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      float finalSpeed = SkriptHandler.inputFloat(0.0F, e, this.speed);
      int offsetX = SkriptHandler.inputInt(0, e, this.offX);
      int offsetY = SkriptHandler.inputInt(0, e, this.offY);
      int offsetZ = SkriptHandler.inputInt(0, e, this.offZ);

      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var36) {
         return;
      }

      String idName = (String)this.InputIdName.getSingle(e);
      boolean isSinglePlayer = SkriptHandler.inputBoolean(false, e, this.singlePlayer);
      Player p = SkriptHandler.inputPlayer(e, this.player);
      boolean rainbowMode = SkriptHandler.inputBoolean(false, e, this.rainbMode);
      float radius = ((Number)this.inputRadius.getSingle(e)).floatValue();
      float grow = ((Number)this.inputGrowth.getSingle(e)).floatValue();
      int circles = ((Number)this.inputCircles.getSingle(e)).intValue();
      int helixes = ((Number)this.inputHelixes.getSingle(e)).intValue();
      int pDensity = SkriptHandler.inputInt(10, e, this.inputDensity);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      double xRotation = SkriptHandler.inputDouble(0.0D, e, this.xRot);
      double yRotation = SkriptHandler.inputDouble(0.0D, e, this.yRot);
      double zRotation = SkriptHandler.inputDouble(0.0D, e, this.zRot);
      double disX = SkriptHandler.inputDouble(0.0D, e, this.displaceX);
      double disY = SkriptHandler.inputDouble(0.0D, e, this.displaceY);
      double disZ = SkriptHandler.inputDouble(0.0D, e, this.displaceZ);
      long finalDelayTicks = SkriptHandler.inputLong(0, e, this.ticks);
      int style = ((Number)this.inputStyle.getSingle(e)).intValue();
      Cone.drawEffect(style, particle, finalSpeed, dataMat, dataID, idName, center, isSinglePlayer, p, rainbowMode, radius, grow, circles, helixes, pDensity, xRotation, yRotation, zRotation, (float)offsetX, (float)offsetY, (float)offsetZ, disX, disY, disZ, visibleRange, 0L, finalDelayTicks);
   }
}
