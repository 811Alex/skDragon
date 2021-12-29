package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.ColorImage2;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.particleEngine.utils.enums.PlaneEnum;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawImage2")
@Description({"Draws a image effect that follows the player or plays at a location. Image files go in the skDragon plugin folder. New Syntax as of v0.06.0-BETA"})
@Syntaxes({"drawImage2 file %string%, center %object%, id %string%[, isSingle %-boolean%, %-player%][, randomRotation %-boolean%,[ plane] %-string%], pixelStepX %number%, pixelStepY %number%, scale %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawImage2 file \"\\dancing mario.gif\", center location of player, id \"%player%\", randomRotation true, \"x\", pixelStepX 1, pixelStepY 1, scale 10, visibleRange 300, tps 0, second 2", "drawImage2 file \"batman.png\", center location of player, id \"%player%\", randomRotation true, \"y\", pixelStepX 5, pixelStepY 5, scale 20, visibleRange 300, tps 0, second 2"})
public class EffColorImage2 extends Effect {
   private Expression fileName;
   private Expression entLoc;
   private Expression inputIdName;
   private Expression inputPlayers;
   private Expression inputAutoFace;
   private Expression inputPlane;
   private Expression xSpeed;
   private Expression ySpeed;
   private Expression zSpeed;
   private Expression pixelX;
   private Expression pixelY;
   private Expression scale;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression range;
   private Expression inputPulseTick;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.fileName = exprs[i++];
      this.entLoc = exprs[i++];
      this.inputIdName = exprs[i++];
      this.inputPlayers = exprs[i++];
      this.inputAutoFace = exprs[i++];
      this.inputPlane = exprs[i++];
      this.xSpeed = exprs[i++];
      this.ySpeed = exprs[i++];
      this.zSpeed = exprs[i++];
      this.pixelX = exprs[i++];
      this.pixelY = exprs[i++];
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
      return "drawImage file %string%, center %entity/location%, id %string%[, onlyFor, %-players%][, autoFace %-boolean%][, autoRotation %-plane%, xyzVelocity %-number%, %-number%, %-number%], pixelStepXY %number%, %number%, scale %number%, visibleRange %number%[, Rot[ation]XYZ %-number%, %-number%, %-number%][, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String finalFileName = null;
      File file = null;
      if (this.fileName != null) {
         finalFileName = (String)this.fileName.getSingle(e);
         if (!finalFileName.startsWith(File.pathSeparator)) {
            file = new File(skDragonCore.skdragoncore.getDataFolder().getAbsolutePath(), finalFileName);
         } else {
            file = new File(finalFileName);
         }
      }

      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var19) {
         return;
      }

      String idName = (String)this.inputIdName.getSingle(e);
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      boolean autoFace = SkriptHandler.inputBoolean(false, e, this.inputAutoFace);
      Vector displacement = SkriptHandler.inputLocDisplacement(e, this.displaceX, this.displaceY, this.displaceZ);
      boolean enableRotation = false;
      PlaneEnum plane = null;
      Vector xyzSpeed = null;
      if (!autoFace && this.inputPlane != null && this.xSpeed != null && this.ySpeed != null && this.zSpeed != null) {
         plane = SkriptHandler.inputPlane(PlaneEnum.XYZ, e, this.inputPlane);
         xyzSpeed = SkriptHandler.inputThreeValues(e, this.xSpeed, this.ySpeed, this.zSpeed);
         System.out.println("rotate");
         enableRotation = true;
      }

      int pixelStepX = SkriptHandler.inputInt(10, e, this.pixelX);
      int pixelStepY = SkriptHandler.inputInt(10, e, this.pixelY);
      int scaleSize = SkriptHandler.inputInt(7, e, this.scale);
      Vector axis = SkriptHandler.inputEffectRotationOld(e, this.xRot, this.yRot, this.zRot);
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      int finalPulseTick = SkriptHandler.inputPulseTick(e, this.inputPulseTick);
      ColorImage2.drawEffect(file, idName, center, players, autoFace, enableRotation, plane, xyzSpeed, pixelStepX, pixelStepY, (float)scaleSize, visibleRange, axis, displacement, 0L, finalPulseTick);
   }
}
