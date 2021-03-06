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
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawWarpRings ")
@Description({"An assortment of different effects with a few shapes that play certain effects, some of which may or may not actually be considered 'warp' effects. I went a little bit nuts when I created this one, there are over 35 styles in it. Added in 0.09.0-BETA", "scan - Set to true, this effect will 'bounce' back and forth between its radius, set to false it will 'pulse' back to 0 every time it reaches its radius.", "height - Sets how high this effect will travel with certain styles", "ringCount - Sets how many 'steps' the effect will take before willing the radius, having a higher value will make it pulse slower", "ringDensity - Sets how many particles per ring"})
@Syntaxes({"drawWarpRings style %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], scan %boolean%, height %number%, radius %number%, ringCount %number%, ringDensity %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%[, repeat %number%]]"})
@Examples({"drawWarpRings style 28, particle \"redstone\", RGB 80, 255, 255, center location of block at player, id \"%player%\", rainbowMode true, scan true, height 2, radius 2, ringCount 40, ringDensity 20, visibleRange 32"})
public class EffWarpRings extends Effect {
   private Expression style;
   private Expression inputParticleString;
   private Expression inputParticleData;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression offXT;
   private Expression offYT;
   private Expression offZT;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression scan;
   private Expression inputHeight;
   private Expression inputRingCount;
   private Expression inputRingDensity;
   private Expression radius;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;
   private Expression inputKeepDelay;
   private Expression inputRepeats;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.style = exprs[i++];
      this.inputParticleString = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.offXT = exprs[i++];
      this.offYT = exprs[i++];
      this.offZT = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.scan = exprs[i++];
      this.inputHeight = exprs[i++];
      this.radius = exprs[i++];
      this.inputRingCount = exprs[i++];
      this.inputRingDensity = exprs[i++];
      this.range = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      this.inputKeepDelay = exprs[i++];
      this.inputRepeats = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawWarpRings style %number%, particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], scan %boolean%, height %number%, radius %number%, ringCount %number%, ringDensity %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%][, keepFor %-timespan%]";
   }

   protected void execute(@Nullable Event e) {
      int finalStyle = SkriptHandler.inputInt(1, e, this.style);
      String particle = SkriptHandler.inputParticleString(e, this.inputParticleString);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      float finalSpeed = SkriptHandler.inputFloat(0.0F, e, this.speed);
      int offsetX = SkriptHandler.inputInt(0, e, this.offX);
      int offsetY = SkriptHandler.inputInt(0, e, this.offY);
      int offsetZ = SkriptHandler.inputInt(0, e, this.offZ);
      int offsetXT = SkriptHandler.inputInt(0, e, this.offXT);
      int offsetYT = SkriptHandler.inputInt(0, e, this.offYT);
      int offsetZT = SkriptHandler.inputInt(0, e, this.offZT);
      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.inputIdName.getSingle(e);
      boolean isSinglePlayer = SkriptHandler.inputBoolean(false, e, this.singlePlayer);
      Player p = SkriptHandler.inputPlayer(e, this.player);
      boolean rainbowMode = SkriptHandler.inputBoolean(false, e, this.rainbMode);
      boolean scanMode = SkriptHandler.inputBoolean(false, e, this.scan);
      float finalRadius = ((Number)this.radius.getSingle(e)).floatValue();
      int ringCount = SkriptHandler.inputInt(5, e, this.inputRingCount);
      int ringDensity = SkriptHandler.inputInt(10, e, this.inputRingDensity);
      float finalHeight = ((Number)this.inputHeight.getSingle(e)).floatValue();
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      double xRotation = SkriptHandler.inputDouble(0.0D, e, this.xRot);
      double yRotation = SkriptHandler.inputDouble(0.0D, e, this.yRot);
      double zRotation = SkriptHandler.inputDouble(0.0D, e, this.zRot);
      double disX = SkriptHandler.inputDouble(0.0D, e, this.displaceX);
      double disY = SkriptHandler.inputDouble(0.0D, e, this.displaceY);
      double disZ = SkriptHandler.inputDouble(0.0D, e, this.displaceZ);
      long finalDelayTicks = SkriptHandler.inputLong(0, e, this.ticks);
      SkriptHandler.inputTimespan(0, e, this.inputKeepDelay);
      SkriptHandler.inputInt(1, e, this.inputRepeats);
      EffectsLib.drawWarpRings(finalStyle, particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, (float)offsetXT, (float)offsetYT, (float)offsetZT, center, idName, isSinglePlayer, p, rainbowMode, scanMode, finalRadius, ringCount, ringDensity, finalHeight, visibleRange, xRotation, yRotation, zRotation, disX, disY, disZ, finalDelayTicks);
   }
}
