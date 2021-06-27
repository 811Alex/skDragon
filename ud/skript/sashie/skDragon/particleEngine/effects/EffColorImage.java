package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawImage")
@Description({"Draws a image effect that follows the player or plays at a location. Image files go in the skDragon plugin folder. New Syntax as of v0.06.0-BETA"})
@Syntaxes({"drawImage file %string%, center %object%, id %string%[, isSingle %-boolean%, %-player%][, randomRotation %-boolean%,[ plane] %-string%], pixelStepX %number%, pixelStepY %number%, scale %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawImage file \"\\dancing mario.gif\", center location of player, id \"%player%\", randomRotation true, \"x\", pixelStepX 1, pixelStepY 1, scale 10, visibleRange 300, tps 0, second 2", "drawImage file \"batman.png\", center location of player, id \"%player%\", randomRotation true, \"y\", pixelStepX 5, pixelStepY 5, scale 20, visibleRange 300, tps 0, second 2"})
public class EffColorImage extends Effect {
   private Expression fileName;
   private Expression entLoc;
   private Expression idName;
   private Expression player;
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
   private Expression singlePlayer;
   private Expression rotation;
   private Expression planeString;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.fileName = exprs[0];
      this.entLoc = exprs[1];
      this.idName = exprs[2];
      this.singlePlayer = exprs[3];
      this.player = exprs[4];
      this.rotation = exprs[5];
      this.planeString = exprs[6];
      this.pixelX = exprs[7];
      this.pixelY = exprs[8];
      this.scale = exprs[9];
      this.range = exprs[10];
      this.xRot = exprs[11];
      this.yRot = exprs[12];
      this.zRot = exprs[13];
      this.displaceX = exprs[14];
      this.displaceY = exprs[15];
      this.displaceZ = exprs[16];
      this.ticks = exprs[17];
      this.seconds = exprs[18];
      skDragonCore.warn("Warning, this old effect syntax is now deprecated. A new syntax string is being generated to replace it!");
      skDragonCore.warn("        draw image file " + this.fileName.toString() + ", center " + this.entLoc.simplify() + ", id " + this.idName.toString() + (this.player != null ? ", clientside " + this.player.toString() : "") + (this.rotation != null ? ", rotation " + this.rotation.toString() : "") + (this.planeString != null ? ", plane " + this.planeString.toString().replaceAll("\"", "") : "") + ", auto face false" + ", xy pixel step " + this.pixelX.toString() + ", " + this.pixelY.toString() + (this.scale != null ? ", scale 1 / " + this.scale.toString() : "") + ", visible range " + this.range.toString() + (this.xRot != null ? ", xyz rotation " + this.xRot.toString() + ", " + this.yRot.toString() + ", " + this.zRot.toString() : "") + (this.displaceX != null ? ", xyz displacement " + this.displaceX.toString() + ", " + this.displaceY.toString() + ", " + this.displaceZ.toString() : "") + (this.seconds != null ? ", pulse rate " + this.seconds.toString() + " ticks" : ""));
      skDragonCore.warn("The scale option changed in the new update being for example a scale of 20(20:1 ratio) to being a multiple of said value so the old scale 20 would now be .2 for the same result");
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawImage file %string%, center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, randomRotation %-boolean%,[ plane] %-string%], pixelStepX %number%, pixelStepY %number%, scale %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      double xRotation = 0.0D;
      double yRotation = 0.0D;
      double zRotation = 0.0D;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      EffectsLib.Plane plane = EffectsLib.Plane.XYZ;
      Long scaleSize = 40L;
      int pixelStepX = 10;
      int pixelStepY = 10;
      boolean enableRotation = false;
      String finalFileName = null;
      File file = null;
      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      if (this.pixelX != null) {
         pixelStepX = ((Number)this.pixelX.getSingle(e)).intValue();
      }

      if (this.pixelY != null) {
         pixelStepY = ((Number)this.pixelY.getSingle(e)).intValue();
      }

      if (this.scale != null) {
         scaleSize = (Long)this.scale.getSingle(e);
      }

      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      Player p = null;
      boolean isSinglePlayer = false;
      if (this.singlePlayer != null && this.singlePlayer.getSingle(e) != null && this.player != null && this.player.getSingle(e) != null) {
         isSinglePlayer = (Boolean)this.singlePlayer.getSingle(e);
         p = (Player)this.player.getSingle(e);
      }

      if (this.rotation != null && this.rotation.getSingle(e) != null) {
         enableRotation = (Boolean)this.rotation.getSingle(e);
      }

      if (this.planeString != null) {
         String planes = ((String)this.planeString.getSingle(e)).toUpperCase();
         if (planes.equalsIgnoreCase("X") || planes.equalsIgnoreCase("Y") || planes.equalsIgnoreCase("Z") || planes.equalsIgnoreCase("XY") || planes.equalsIgnoreCase("XZ") || planes.equalsIgnoreCase("XYZ") || planes.equalsIgnoreCase("YZ")) {
            plane = EffectsLib.Plane.valueOf(planes);
         }
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

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      if (this.fileName != null) {
         finalFileName = (String)this.fileName.getSingle(e);
         if (!finalFileName.startsWith(File.pathSeparator)) {
            file = new File(skDragonCore.skdragoncore.getDataFolder().getAbsolutePath(), finalFileName);
         } else {
            file = new File(finalFileName);
         }
      }

      EffectsLib.drawImage(file, center, idName, isSinglePlayer, p, enableRotation, plane, pixelStepX, pixelStepY, scaleSize, visibleRange, xRotation, yRotation, zRotation, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
   }
}
