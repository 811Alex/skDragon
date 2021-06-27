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

@Name("drawRings ")
@Description({"Ring effect like the circles around the drawAtom that follows the player or plays at a location. Added in 0.09.0-BETA -Warning- setting animated to true will be a bit resource heavy, but produces an interesting effect. "})
@Syntaxes({"drawRings particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, animated %boolean%, radius %number%, ringCount %number%, ringDensity %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawRings particle \"redstone\", RGB 80, 255, 255, center player, id \"%player%\", rainbowMode true, randomRotation true, animated false, radius 1, ringCount 4, ringDensity 10, visibleRange 32, pulseDelay 2"})
public class EffRings extends Effect {
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
   private Expression rotation;
   private Expression inputAnimated;
   private Expression InputRingCount;
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
      this.particleString = exprs[0];
      this.inputParticleData = exprs[1];
      this.speed = exprs[2];
      this.offX = exprs[3];
      this.offY = exprs[4];
      this.offZ = exprs[5];
      this.entLoc = exprs[6];
      this.idName = exprs[7];
      this.singlePlayer = exprs[8];
      this.player = exprs[9];
      this.rainbMode = exprs[10];
      this.rotation = exprs[11];
      this.inputAnimated = exprs[12];
      this.radius = exprs[13];
      this.InputRingCount = exprs[14];
      this.InputRingDensity = exprs[15];
      this.range = exprs[16];
      this.xRot = exprs[17];
      this.yRot = exprs[18];
      this.zRot = exprs[19];
      this.displaceX = exprs[20];
      this.displaceY = exprs[21];
      this.displaceZ = exprs[22];
      this.ticks = exprs[23];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawRings particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, animated %boolean%, radius %number%, ringCount %number%, ringDensity %number%, visibleRange %number%[, rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
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
      int ringCount = true;
      Long finalDelayTicks = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
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

      boolean enableRotation = (Boolean)this.rotation.getSingle(e);
      boolean animated = false;
      if (this.inputAnimated != null && this.inputAnimated.getSingle(e) != null) {
         animated = (Boolean)this.inputAnimated.getSingle(e);
      }

      int ringCount = ((Number)this.InputRingCount.getSingle(e)).intValue();
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

      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      EffectsLib.drawRings(particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, center, idName, isSinglePlayer, p, rainbowMode, enableRotation, animated, finalRadius, ringCount, ringDensity, 0.0F, visibleRange, xRotation, yRotation, zRotation, disX, disY, disZ, 0L, finalDelayTicks);
   }
}
