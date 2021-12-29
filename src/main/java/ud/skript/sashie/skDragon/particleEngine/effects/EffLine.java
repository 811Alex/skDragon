package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.maths.Line;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawLine")
@Description({"Draws a line from entity/location to entity/location options exist to set the length of the line as well as point with the player(it doesnt appear to follow the pitch/yaw of mobs, might require a movement update perhaps)"})
@Syntaxes({"drawLine particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%[, target %-object%], id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%][, solid %-boolean%][, density %-number%][, length %-number%][, zigZag count %-number%, height %-number%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawLine particle redstone, RGB 0, 1, 1, center player, target location of target block, id \"%player%\", rainbowMode true, solid true, density 5, length 0, zigZag count 0, height 0, visibleRange 32, displacementXYZ 0, 1.2, 0, pulseDelay 1"})
public class EffLine extends Effect {
   private Expression particleName;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression tarLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputRainbowMode;
   private Expression inputSolid;
   private Expression inputDensity;
   private Expression inputLength;
   private Expression inputZigZagCount;
   private Expression inputZigZagHeight;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseTick;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.particleName = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.inputParticleSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.entLoc = exprs[i++];
      this.tarLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputRainbowMode = exprs[i++];
      this.inputSolid = exprs[i++];
      this.inputDensity = exprs[i++];
      this.inputLength = exprs[i++];
      this.inputZigZagCount = exprs[i++];
      this.inputZigZagHeight = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.inputPulseTick = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawLine particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%[, target %entity/location%], id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%][, solid %-boolean%][, density %-number%][, length %-number%][, zigZag count %-number%, height %-number%], visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var21) {
         return;
      }

      ParticleEffect particle = (ParticleEffect)this.particleName.getSingle(e);
      float speed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      Vector offset = SkriptHandler.inputParticleOffset(e, this.offX, this.offY, this.offZ);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      String idName = (String)this.inputIdName.getSingle(e);
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.inputRainbowMode);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      int finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseTick);
      DynamicLocation target = null;
      if (this.tarLoc != null) {
         target = DynamicLocation.init(this.tarLoc.getSingle(e));
      }

      boolean solid = SkriptHandler.inputBoolean(true, e, this.inputSolid);
      float density = SkriptHandler.inputFloat(20.0F, e, this.inputDensity);
      float length = SkriptHandler.inputFloat(0.0F, e, this.inputLength);
      int zigZagCount = SkriptHandler.inputInt(0, e, this.inputZigZagCount);
      float zigZagHeight = SkriptHandler.inputFloat(0.0F, e, this.inputZigZagHeight);
      Line.drawEffect(particle, dataMat, dataID, speed, offset, idName, center, target, players, rainbowMode, solid, density, length, zigZagCount, zigZagHeight, visibleRange, displacement, 0L, finalPulseTick);
   }
}
