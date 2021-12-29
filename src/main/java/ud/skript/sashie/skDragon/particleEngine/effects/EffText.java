package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.awt.Font;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragon.particleEngine.maths.Text;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawText")
@Description({"Draws text at a location or a player, it can also auto face where the player looks Uses system fonts(most fonts/unicodes show up as squares)"})
@Syntaxes({"drawText %string%, particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, (only[ ]for|visible[ ]to) %-players%][, r[ainbow]M[ode] %-boolean%][, autoFace %-boolean%][, invert %-boolean%][, f[ont]Name %-string%, f[ont]Style %-fontstyle%, f[ont]Size %-number%], scale %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawText \"this works but �, �, � doesn't\", particle redstone, RGB 20, 100, 227, center location of player, id \"%player%\", rainbowMode false, autoFace false, invert false, fontName \"Arial\", fontStyle plain, fontSize 10, scale 7, visibleRange 32, RotationXYZ 0, 0, 0, displacementXYZ 0, 0, 0, pulseDelay 0"})
public class EffText extends Effect {
   private Expression inputText;
   private Expression particleName;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputRainbowMode;
   private Expression inputAutoFace;
   private Expression inputInvert;
   private Expression inputFontName;
   private Expression inputFontStyle;
   private Expression inputFontSize;
   private Expression scale;
   private Expression range;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseTick;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.inputText = exprs[i++];
      this.particleName = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.inputParticleSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputRainbowMode = exprs[i++];
      this.inputAutoFace = exprs[i++];
      this.inputInvert = exprs[i++];
      this.inputFontName = exprs[i++];
      this.inputFontStyle = exprs[i++];
      this.inputFontSize = exprs[i++];
      this.scale = exprs[i++];
      this.range = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.inputPulseTick = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawText %string%, particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%][, autoFace %-boolean%][, invert %-boolean%][, f[ont]Name %-string%, f[ont]Style %-fontstyle%, f[ont]Size %-number%], scale %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var22) {
         return;
      }

      ParticleEffect particle = (ParticleEffect)this.particleName.getSingle(e);
      float speed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      Vector offset = SkriptHandler.inputParticleOffset(e, this.offX, this.offY, this.offZ);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.inputRainbowMode);
      boolean autoFace = SkriptHandler.inputRainbowMode(e, this.inputAutoFace);
      boolean invert = SkriptHandler.inputRainbowMode(e, this.inputInvert);
      Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      String idName = (String)this.inputIdName.getSingle(e);
      Vector axis = SkriptHandler.inputEffectRotationOld(e, this.xRot, this.yRot, this.zRot);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      long scaleSize = 5L;
      if (this.scale != null) {
         scaleSize = ((Number)this.scale.getSingle(e)).longValue();
      }

      Font font = SkriptHandler.inputFont(e, this.inputFontName, this.inputFontStyle, this.inputFontSize);
      String text = (String)this.inputText.getSingle(e);
      int finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseTick);
      Text effect = new Text(text, font, particle, dataMat, dataID, speed, offset, idName, center, players, rainbowMode, invert, autoFace, 1.0F, 1.0F, (float)scaleSize, visibleRange, axis, displacement, 0L, finalPulseTick);
      effect.draw();
   }
}
