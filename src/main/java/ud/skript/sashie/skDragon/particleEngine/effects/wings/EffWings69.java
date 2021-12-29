package ud.skript.sashie.skDragon.particleEngine.effects.wings;

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

@Name("drawWings")
@Description({"4 colored version", "Draws a wing trail that follows the player or plays them at a location. New Syntax as of v0.06.0-BETA", "Added flapping animation in v0.10.0-BETA"})
@Syntaxes({"drawWings69 particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], particle3 %string%[[, material] %-itemstack%][, speed3 %-number%][, ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%], particle4 %string%[[, material] %-itemstack%][, speed4 %-number%][, ([offset]XYZ4|RGB4) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ4|RGB4) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, flapSpeed %-number%, flapRange %-number%], angle %number%, height %number%, space %number%, visibleRange %number%[, tps %-number%, second %-number%]"})
@Examples({"drawWings style 12, particle1 \"flame\", speed 0, RGB 0, 0, 0, particle2 \"redstone\", speed2 0, RGB2 0, 0, 0, particle3 \"redstone\", speed3 0, RGB3 245, 122, 0, center player, id \"%player%\", isSingle false, player, rainbowMode false, angle 110, height 0, space 0.2, visibleRange 30, tps 1, second 2", "drawWings style 17, particle1 \"flame\", speed 0, XYZ 0, 0, 0, particle2 \"redstone\", particle3 \"redstone\", center player, id \"%player%\", rainbowMode true, flapSpeed 2, flapRange 30, angle 120, height 0, space 0.2, visibleRange 30, tps 0, second 3"})
public class EffWings69 extends Effect {
   private Expression particleString;
   private Expression data;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression offXT;
   private Expression offYT;
   private Expression offZT;
   private Expression particleString2;
   private Expression data2;
   private Expression speed2;
   private Expression offX2;
   private Expression offY2;
   private Expression offZ2;
   private Expression offX2T;
   private Expression offY2T;
   private Expression offZ2T;
   private Expression particleString3;
   private Expression data3;
   private Expression speed3;
   private Expression offX3;
   private Expression offY3;
   private Expression offZ3;
   private Expression offX3T;
   private Expression offY3T;
   private Expression offZ3T;
   private Expression particleString4;
   private Expression data4;
   private Expression speed4;
   private Expression offX4;
   private Expression offY4;
   private Expression offZ4;
   private Expression offX4T;
   private Expression offY4T;
   private Expression offZ4T;
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression inputFlapSpeed;
   private Expression inputFlapRange;
   private Expression angle;
   private Expression height;
   private Expression space;
   private Expression range;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.particleString = exprs[i++];
      this.data = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.offXT = exprs[i++];
      this.offYT = exprs[i++];
      this.offZT = exprs[i++];
      this.particleString2 = exprs[i++];
      this.data2 = exprs[i++];
      this.speed2 = exprs[i++];
      this.offX2 = exprs[i++];
      this.offY2 = exprs[i++];
      this.offZ2 = exprs[i++];
      this.offX2T = exprs[i++];
      this.offY2T = exprs[i++];
      this.offZ2T = exprs[i++];
      this.particleString3 = exprs[i++];
      this.data3 = exprs[i++];
      this.speed3 = exprs[i++];
      this.offX3 = exprs[i++];
      this.offY3 = exprs[i++];
      this.offZ3 = exprs[i++];
      this.offX3T = exprs[i++];
      this.offY3T = exprs[i++];
      this.offZ3T = exprs[i++];
      this.particleString4 = exprs[i++];
      this.data4 = exprs[i++];
      this.speed4 = exprs[i++];
      this.offX4 = exprs[i++];
      this.offY4 = exprs[i++];
      this.offZ4 = exprs[i++];
      this.offX4T = exprs[i++];
      this.offY4T = exprs[i++];
      this.offZ4T = exprs[i++];
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.inputFlapSpeed = exprs[i++];
      this.inputFlapRange = exprs[i++];
      this.angle = exprs[i++];
      this.height = exprs[i++];
      this.space = exprs[i++];
      this.range = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawWings69 particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], particle3 %string%[[, material] %-itemstack%][, speed3 %-number%][, ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%], particle4 %string%[[, material] %-itemstack%][, speed4 %-number%][, ([offset]XYZ4|RGB4) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ4|RGB4) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, flapSpeed %-number%, flapRange %-number%], angle %number%, height %number%, space %number%, visibleRange %number%[, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "limeglassparticle";
      int offsetX = 0;
      int offsetY = 0;
      int offsetZ = 0;
      int offsetXT = 0;
      int offsetYT = 0;
      int offsetZT = 0;
      String particle2 = "limeglassparticle";
      int offsetX2 = 0;
      int offsetY2 = 0;
      int offsetZ2 = 0;
      int offsetX2T = 0;
      int offsetY2T = 0;
      int offsetZ2T = 0;
      String particle3 = "limeglassparticle";
      int offsetX3 = 0;
      int offsetY3 = 0;
      int offsetZ3 = 0;
      int offsetX3T = 0;
      int offsetY3T = 0;
      int offsetZ3T = 0;
      float finalSpeed = 0.0F;
      float finalSpeed2 = 0.0F;
      float finalSpeed3 = 0.0F;
      String particle4 = "limeglassparticle";
      int offsetX4 = 0;
      int offsetY4 = 0;
      int offsetZ4 = 0;
      int offsetX4T = 0;
      int offsetY4T = 0;
      int offsetZ4T = 0;
      float finalSpeed4 = 0.0F;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      boolean x = true;
      boolean o = false;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = ((Number)this.offX.getSingle(e)).intValue();
         offsetY = ((Number)this.offY.getSingle(e)).intValue();
         offsetZ = ((Number)this.offZ.getSingle(e)).intValue();
      }

      if (this.offXT != null && this.offYT != null && this.offZT != null) {
         offsetXT = ((Number)this.offXT.getSingle(e)).intValue();
         offsetYT = ((Number)this.offYT.getSingle(e)).intValue();
         offsetZT = ((Number)this.offZT.getSingle(e)).intValue();
      }

      if (this.particleString2 != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString2.getSingle(e)).toLowerCase())) {
         particle2 = ((String)this.particleString2.getSingle(e)).toLowerCase();
      }

      if (this.speed2 != null) {
         finalSpeed2 = ((Number)this.speed2.getSingle(e)).floatValue();
      }

      if (this.offX2 != null && this.offY2 != null && this.offZ2 != null) {
         offsetX2 = ((Number)this.offX2.getSingle(e)).intValue();
         offsetY2 = ((Number)this.offY2.getSingle(e)).intValue();
         offsetZ2 = ((Number)this.offZ2.getSingle(e)).intValue();
      }

      if (this.offX2T != null && this.offY2T != null && this.offZ2T != null) {
         offsetX2T = ((Number)this.offX2T.getSingle(e)).intValue();
         offsetY2T = ((Number)this.offY2T.getSingle(e)).intValue();
         offsetZ2T = ((Number)this.offZ2T.getSingle(e)).intValue();
      }

      if (this.particleString3 != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString3.getSingle(e)).toLowerCase())) {
         particle3 = ((String)this.particleString3.getSingle(e)).toLowerCase();
      }

      if (this.speed3 != null) {
         finalSpeed3 = ((Number)this.speed3.getSingle(e)).floatValue();
      }

      if (this.offX3 != null && this.offY3 != null && this.offZ3 != null) {
         offsetX3 = ((Number)this.offX3.getSingle(e)).intValue();
         offsetY3 = ((Number)this.offY3.getSingle(e)).intValue();
         offsetZ3 = ((Number)this.offZ3.getSingle(e)).intValue();
      }

      if (this.offX3T != null && this.offY3T != null && this.offZ3T != null) {
         offsetX3T = ((Number)this.offX3T.getSingle(e)).intValue();
         offsetY3T = ((Number)this.offY3T.getSingle(e)).intValue();
         offsetZ3T = ((Number)this.offZ3T.getSingle(e)).intValue();
      }

      if (this.particleString4 != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString4.getSingle(e)).toLowerCase())) {
         particle4 = ((String)this.particleString4.getSingle(e)).toLowerCase();
      }

      if (this.speed4 != null) {
         finalSpeed4 = ((Number)this.speed4.getSingle(e)).floatValue();
      }

      if (this.offX4 != null && this.offY4 != null && this.offZ4 != null) {
         offsetX4 = ((Number)this.offX4.getSingle(e)).intValue();
         offsetY4 = ((Number)this.offY4.getSingle(e)).intValue();
         offsetZ4 = ((Number)this.offZ4.getSingle(e)).intValue();
      }

      if (this.offX4T != null && this.offY4T != null && this.offZ4T != null) {
         offsetX4T = ((Number)this.offX4T.getSingle(e)).intValue();
         offsetY4T = ((Number)this.offY4T.getSingle(e)).intValue();
         offsetZ4T = ((Number)this.offZ4T.getSingle(e)).intValue();
      }

      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
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

      boolean flapMode = true;
      float flapStep = 0.0F;
      if (this.inputFlapSpeed != null && this.inputFlapSpeed.getSingle(e) != null) {
         flapStep = (float)((Number)this.inputFlapSpeed.getSingle(e)).intValue();
      }

      if (flapStep <= -5.0F) {
         flapStep = -5.0F;
      } else if (flapStep == 0.0F) {
         flapMode = false;
      } else if (flapStep >= 5.0F) {
         flapStep = 5.0F;
      }

      float flapRange = 20.0F;
      if (this.inputFlapRange != null && this.inputFlapRange.getSingle(e) != null) {
         flapRange = ((Number)this.inputFlapRange.getSingle(e)).floatValue();
      }

      float finalAngle = ((Number)this.angle.getSingle(e)).floatValue();
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      float finalHeight = ((Number)this.height.getSingle(e)).floatValue();
      double finalSpace = ((Number)this.space.getSingle(e)).doubleValue();
      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      boolean[][] shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}};
      boolean[][] shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
      boolean[][] shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
      boolean[][] shape4 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.data);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.data);
      Material dataMat2 = SkriptHandler.inputParticleDataMat(e, this.data2);
      byte dataID2 = SkriptHandler.inputParticleDataID(e, this.data2);
      Material dataMat3 = SkriptHandler.inputParticleDataMat(e, this.data3);
      byte dataID3 = SkriptHandler.inputParticleDataID(e, this.data3);
      Material dataMat4 = SkriptHandler.inputParticleDataMat(e, this.data4);
      byte dataID4 = SkriptHandler.inputParticleDataID(e, this.data4);
      EffectsLib.drawWings4Color(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, (float)offsetXT, (float)offsetYT, (float)offsetZT, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, (float)offsetX2T, (float)offsetY2T, (float)offsetZ2T, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, (float)offsetX3T, (float)offsetY3T, (float)offsetZ3T, particle4, dataMat4, dataID4, finalSpeed4, (float)offsetX4, (float)offsetY4, (float)offsetZ4, (float)offsetX4T, (float)offsetY4T, (float)offsetZ4T, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, shape4, (float)((double)finalHeight - 0.3D), finalSpace, finalDelayTicks, finalDelayBySec, p);
   }
}
