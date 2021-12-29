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
import ud.skript.sashie.skDragon.particleEngine.maths.Disco;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawDisco")
@Description({"Draws a disco ball type thing that follows the player or plays at a location. New as of v0.13.0-Beta"})
@Syntaxes({"drawDisco style %number%, particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %particlename%[, material2 %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %object%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%], maxLines %number%, lineLength %number%, sphereRadius %number%, sphereDensity %number%, lineDensity %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawDisco style 1, particle redstone, RGB .608, 1, 1, particle2 redstone, RGB2 0, 0, 0, center location of player, id \"%player%\", rainbowMode true, maxLines 7, lineLength 5, sphereRadius .6, sphereDensity 70, lineDensity 40, visibleRange 32, displacementXYZ 0, 5, 0, pulseDelay 2", "drawDisco style 2, particle redstone, RGB 0, 0, 0, particle2 redstone, RGB2 0, 0, 0, center player, id \"%player%\", rainbowMode true, maxLines 7, lineLength 5, sphereRadius .6, sphereDensity 30, lineDensity 30, visibleRange 32, displacementXYZ 0, 4, 0, pulseDelay 0"})
public class EffDisco extends Effect {
   private Expression inputStyle;
   private Expression particleName;
   private Expression inputParticleData;
   private Expression inputParticleSpeed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression particleName2;
   private Expression inputParticleData2;
   private Expression inputParticleSpeed2;
   private Expression offX2;
   private Expression offY2;
   private Expression offZ2;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputRainbowMode;
   private Expression inputMaxLines;
   private Expression inputLineLength;
   private Expression inputSphereRadius;
   private Expression inputSphereDensity;
   private Expression inputLineDensity;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression inputPulseTick;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.inputStyle = exprs[i++];
      this.particleName = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.inputParticleSpeed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.particleName2 = exprs[i++];
      this.inputParticleData2 = exprs[i++];
      this.inputParticleSpeed2 = exprs[i++];
      this.offX2 = exprs[i++];
      this.offY2 = exprs[i++];
      this.offZ2 = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputRainbowMode = exprs[i++];
      this.inputMaxLines = exprs[i++];
      this.inputLineLength = exprs[i++];
      this.inputSphereRadius = exprs[i++];
      this.inputSphereDensity = exprs[i++];
      this.inputLineDensity = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.inputPulseTick = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawDisco style %number%, particle %particlename%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %particlename%[, material2 %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, onlyFor %-players%][, r[ainbow]M[ode] %-boolean%], maxLines %number%, lineLength %number%, sphereRadius %number%, sphereDensity %number%, lineDensity %number%, visibleRange %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var26) {
         return;
      }

      int finalStyle = ((Number)this.inputStyle.getSingle(e)).intValue();
      ParticleEffect particle = (ParticleEffect)this.particleName.getSingle(e);
      float speed = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed);
      Vector offset = SkriptHandler.inputParticleOffset(e, this.offX, this.offY, this.offZ);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      ParticleEffect particle2 = (ParticleEffect)this.particleName2.getSingle(e);
      float speed2 = SkriptHandler.inputParticleSpeed(e, this.inputParticleSpeed2);
      Vector offset2 = SkriptHandler.inputParticleOffset(e, this.offX2, this.offY2, this.offZ2);
      Material dataMat2 = SkriptHandler.inputParticleDataMat(e, this.inputParticleData2);
      byte dataID2 = SkriptHandler.inputParticleDataID(e, this.inputParticleData2);
      String idName = (String)this.inputIdName.getSingle(e);
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      boolean rainbowMode = SkriptHandler.inputRainbowMode(e, this.inputRainbowMode);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      int finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseTick);
      int finalMaxLines = ((Number)this.inputMaxLines.getSingle(e)).intValue();
      int finalLineLength = ((Number)this.inputLineLength.getSingle(e)).intValue();
      float finalSphereRadius = ((Number)this.inputSphereRadius.getSingle(e)).floatValue();
      int finalSphereDensity = ((Number)this.inputSphereDensity.getSingle(e)).intValue();
      int finalLineDensity = ((Number)this.inputLineDensity.getSingle(e)).intValue();
      Disco.drawEffect(finalStyle, particle, dataMat, dataID, speed, offset, particle2, dataMat2, dataID2, speed2, offset2, idName, center, players, rainbowMode, finalMaxLines, finalLineLength, finalSphereRadius, finalSphereDensity, finalLineDensity, visibleRange, displacement, 0L, finalPulseTick);
   }
}
