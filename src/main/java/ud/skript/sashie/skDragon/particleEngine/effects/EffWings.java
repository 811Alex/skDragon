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

@Name("drawWings")
@Description({"Draws a wing trail that follows the player or plays them at a location. New Syntax as of v0.06.0-BETA Added flapping animation in v0.10.0-BETA"})
@Syntaxes({"drawWings[ style] %number%, particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], particle3 %string%[[, material] %-itemstack%][, speed3 %-number%][, ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, flapSpeed %-number%, flapRange %-number%], angle %number%, height %number%, space %number%, visibleRange %number%[, tps %-number%, second %-number%]"})
@Examples({"drawWings style 12, particle1 \"flame\", speed 0, RGB 0, 0, 0, particle2 \"redstone\", speed2 0, RGB2 0, 0, 0, particle3 \"redstone\", speed3 0, RGB3 245, 122, 0, center player, id \"%player%\", isSingle false, player, rainbowMode false, angle 110, height 0, space 0.2, visibleRange 30, tps 1, second 2", "drawWings style 17, particle1 \"flame\", speed 0, XYZ 0, 0, 0, particle2 \"redstone\", particle3 \"redstone\", center player, id \"%player%\", rainbowMode true, flapSpeed 2, flapRange 30, angle 120, height 0, space 0.2, visibleRange 30, tps 0, second 3"})
public class EffWings extends Effect {
   private Expression style;
   private Expression inputParticleString;
   private Expression inputParticleData;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression inputParticleString2;
   private Expression inputParticleData2;
   private Expression speed2;
   private Expression offX2;
   private Expression offY2;
   private Expression offZ2;
   private Expression inputParticleString3;
   private Expression inputParticleData3;
   private Expression speed3;
   private Expression offX3;
   private Expression offY3;
   private Expression offZ3;
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
      this.style = exprs[i++];
      this.inputParticleString = exprs[i++];
      this.inputParticleData = exprs[i++];
      this.speed = exprs[i++];
      this.offX = exprs[i++];
      this.offY = exprs[i++];
      this.offZ = exprs[i++];
      this.inputParticleString2 = exprs[i++];
      this.inputParticleData2 = exprs[i++];
      this.speed2 = exprs[i++];
      this.offX2 = exprs[i++];
      this.offY2 = exprs[i++];
      this.offZ2 = exprs[i++];
      this.inputParticleString3 = exprs[i++];
      this.inputParticleData3 = exprs[i++];
      this.speed3 = exprs[i++];
      this.offX3 = exprs[i++];
      this.offY3 = exprs[i++];
      this.offZ3 = exprs[i++];
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
      return "drawWings[ style] %number%, particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], particle3 %string%[[, material] %-itemstack%][, speed3 %-number%][, ([offset]XYZ3|RGB3) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, flapSpeed %-number%, flapRange %-number%], angle %number%, height %number%, space %number%, visibleRange %number%[, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      boolean x = true;
      boolean o = false;
      String particle = SkriptHandler.inputParticleString(e, this.inputParticleString);
      float finalSpeed = SkriptHandler.inputFloat(0.0F, e, this.speed);
      int offsetX = SkriptHandler.inputInt(0, e, this.offX);
      int offsetY = SkriptHandler.inputInt(0, e, this.offY);
      int offsetZ = SkriptHandler.inputInt(0, e, this.offZ);
      String particle2 = SkriptHandler.inputParticleString(e, this.inputParticleString2);
      float finalSpeed2 = SkriptHandler.inputFloat(0.0F, e, this.speed2);
      int offsetX2 = SkriptHandler.inputInt(0, e, this.offX2);
      int offsetY2 = SkriptHandler.inputInt(0, e, this.offY2);
      int offsetZ2 = SkriptHandler.inputInt(0, e, this.offZ2);
      String particle3 = SkriptHandler.inputParticleString(e, this.inputParticleString3);
      float finalSpeed3 = SkriptHandler.inputFloat(0.0F, e, this.speed3);
      int offsetX3 = SkriptHandler.inputInt(0, e, this.offX3);
      int offsetY3 = SkriptHandler.inputInt(0, e, this.offY3);
      int offsetZ3 = SkriptHandler.inputInt(0, e, this.offZ3);
      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      Player p = SkriptHandler.inputPlayer(e, this.player);
      boolean isSinglePlayer = SkriptHandler.inputBoolean(false, e, this.singlePlayer);
      boolean rainbowMode = SkriptHandler.inputBoolean(false, e, this.rainbMode);
      boolean flapMode = true;
      float flapStep = SkriptHandler.inputFloat(0.0F, e, this.inputFlapSpeed);
      if (!(flapStep <= -5.0F)) {
         if (flapStep != 0.0F) {
            if (flapStep >= 5.0F) {
               flapStep = 5.0F;
            }
         } else {
            flapMode = false;
         }
      } else {
         flapStep = -5.0F;
      }

      float flapRange = SkriptHandler.inputFloat(20.0F, e, this.inputFlapRange);
      float finalAngle = SkriptHandler.inputFloat(110.0F, e, this.angle);
      double visibleRange = SkriptHandler.inputDouble(32.0D, e, this.range);
      float finalHeight = SkriptHandler.inputFloat(0.0F, e, this.height);
      double finalSpace = SkriptHandler.inputDouble(0.0D, e, this.space);
      long finalDelayTicks = SkriptHandler.inputLong(0, e, this.ticks);
      long finalDelayBySec = SkriptHandler.inputLong(0, e, this.seconds);
      int finalStyle = SkriptHandler.inputInt(1, e, this.style);
      Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
      byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
      Material dataMat2 = SkriptHandler.inputParticleDataMat(e, this.inputParticleData2);
      byte dataID2 = SkriptHandler.inputParticleDataID(e, this.inputParticleData2);
      Material dataMat3 = SkriptHandler.inputParticleDataMat(e, this.inputParticleData3);
      byte dataID3 = SkriptHandler.inputParticleDataID(e, this.inputParticleData3);
      boolean[][] shape1;
      boolean[][] shape2;
      boolean[][] shape3;
      if (finalStyle > 1) {
         if (finalStyle != 2) {
            if (finalStyle != 3) {
               if (finalStyle != 4) {
                  if (finalStyle != 5) {
                     if (finalStyle != 6) {
                        if (finalStyle != 7) {
                           if (finalStyle != 8) {
                              if (finalStyle != 9) {
                                 if (finalStyle != 10) {
                                    if (finalStyle != 11) {
                                       if (finalStyle != 12) {
                                          if (finalStyle != 13) {
                                             if (finalStyle != 14) {
                                                if (finalStyle != 15) {
                                                   if (finalStyle >= 16) {
                                                      shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, x, o, o, o, o, o, o, o}, {x, o, x, o, x, o, x, o, o, o, o, o, o, o}, {o, x, o, x, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                      shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, x, o, o, o, o, o, o, o, o, o, o}, {x, x, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                      shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, x, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                      EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                                   }
                                                } else {
                                                   shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {x, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                   shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, x, o, o, o, o, o, o, o, o, o, o}, {x, x, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                   shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                   EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                                }
                                             } else {
                                                shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, x, o, o, o, o, o}, {x, o, o, o, o, o, x, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                                EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                             }
                                          } else {
                                             shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, x, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, x, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                             shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, x, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, x, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, x, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, x, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                             shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, x, o, o, o, o, o, o, o, o}, {o, o, x, x, x, x, x, o, o, o, o, o, o, o}, {o, x, x, o, o, o, x, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, x, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                             EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                          }
                                       } else {
                                          shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, o, x, o, o, o, o, o, o, o, o, o}, {o, x, o, x, o, x, x, o, o, o, o, o, o, o}, {x, o, o, x, x, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, x, x, o, o, x, o, o, o, o, o, o, o, o}, {x, o, x, x, x, o, x, o, o, o, o, o, o, o}, {o, x, o, o, o, x, x, o, o, o, o, o, o, o}};
                                          shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, x, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                          shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                          EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                       }
                                    } else {
                                       shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, x, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                       shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                       shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                       EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                    }
                                 } else {
                                    shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                    shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                    shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                    EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                                 }
                              } else {
                                 shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, o, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                 shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                 shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                                 EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                              }
                           } else {
                              shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, x, o, o, o, o, o, o, o}, {o, o, x, o, o, o, x, o, o, o, o, o, o, o}, {o, x, o, o, x, o, x, o, o, o, o, o, o, o}, {x, o, o, x, o, o, x, o, o, o, o, o, o, o}, {x, o, x, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, x, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                              shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                              shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, x, o, o, o, o, o, o, o, o}, {o, o, x, o, o, x, o, o, o, o, o, o, o, o}, {o, x, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, x, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                              EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                           }
                        } else {
                           shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                           shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, x, o, o, o, o, o}, {o, o, o, o, o, o, o, x, x, o, o, o, o, o}, {o, o, o, o, o, o, x, x, o, o, o, o, o, o}, {o, o, o, o, o, x, x, x, o, o, o, o, o, o}, {o, o, o, x, x, x, x, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                           shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, o, x, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                           EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                        }
                     } else {
                        shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, o, x, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                        shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                        shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, x, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                        EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                     }
                  } else {
                     shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                     shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                     shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                     EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
                  }
               } else {
                  shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, x, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, x, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}};
                  shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, x, o, o, o, o, o, o, o, o}, {o, o, x, o, x, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                  shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
                  EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
               }
            } else {
               shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, x, o, o, o, o, o, o, o}, {o, o, o, o, x, x, x, o, o, o, o, o, o, o}, {o, o, o, x, x, o, x, o, o, o, o, o, o, o}, {o, o, x, x, o, o, x, o, o, o, o, o, o, o}, {o, x, x, o, o, o, x, o, o, o, o, o, o, o}, {x, x, o, o, o, o, x, o, o, o, o, o, o, o}, {x, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, o, x, o, o, o, o, o, o, o}, {o, o, o, o, o, x, x, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}};
               shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
               shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, x, x, o, o, o, o, o, o, o, o}, {o, o, o, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, o, o, o, o, o, o, o, o, o, o, o, o}};
               EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
            }
         } else {
            shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, o, x, o, o, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, o, x, o, o, o, o, o, o, o, o, o}, {x, o, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
            shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, x, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, x, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
            shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
            EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, (float)((double)finalHeight - 0.4D), finalSpace, finalDelayTicks, finalDelayBySec, p);
         }
      } else {
         shape1 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, x, o, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {o, x, o, o, o, x, o, o, o, o, o, o, o, o}, {x, o, o, o, o, x, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
         shape2 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, o, o, o, o, o, o, o, o, o, o}, {o, o, x, x, x, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}};
         shape3 = new boolean[][]{{o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, o, o, o, o, o, o, o, o, o, o, o}, {x, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, x, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, x, o, o, o, o, o, o, o, o, o, o, o}, {o, o, o, x, o, o, o, o, o, o, o, o, o, o}};
         EffectsLib.drawWings(particle, dataMat, dataID, finalSpeed, (float)offsetX, (float)offsetY, (float)offsetZ, particle2, dataMat2, dataID2, finalSpeed2, (float)offsetX2, (float)offsetY2, (float)offsetZ2, particle3, dataMat3, dataID3, finalSpeed3, (float)offsetX3, (float)offsetY3, (float)offsetZ3, center, idName, isSinglePlayer, rainbowMode, flapMode, flapStep, flapRange, finalAngle, visibleRange, shape1, shape2, shape3, finalHeight, finalSpace, finalDelayTicks, finalDelayBySec, p);
      }

   }
}
