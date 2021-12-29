package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawPlanet ")
@Description({"Draws a randomized two colored planet that follows the player or plays at a location. New as of v0.07.0-Beta"})
@Syntaxes({"drawPlanet particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, rotation %-boolean%,[ plane] %-string%, rot[ation]Speed %-number%][, orbit %-boolean%, orbit[al]Radius %-number%, orbit[al]Step %-number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%]], radius %number%, density %number%, precision %number%, bumpHeight %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawPlanet particle1 \"redstone\", RGB 0, 255, 10, particle2 \"redstone\", RGB2 0, 10, 255, center location of player, id \"%player%\", rotation true, plane \"x\", rotationSpeed 1, orbit true, orbitalRadius 2, orbitalStep 360, xRotation 45, yRotation 0, zRotation 0, radius .25, density 150, precision 100, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 0, 255, 10, particle2 \"redstone\", RGB2 0, 10, 255, center location of player, id \"%player%\", rotation true, plane \"xz\", rotationSpeed 5, radius .25, density 100, precision 100, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 230, 204, 255, particle2 \"redstone\", RGB2 46, 138, 92, center location of player, id \"%player%\", rotation true, plane \"xz\", rotationSpeed 5, radius .25, density 100, precision 100, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 224, 224, 224, particle2 \"redstone\", RGB2 133, 133, 133, center location of player, id \"%player%-1\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 1.1, orbitalStep 180, xRotation 45, yRotation 0, zRotation 0, radius .05, density 15, precision 100, bumpHeight .05, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 153, 153, 255, particle2 \"redstone\", RGB2 153, 204, 255, center location of player, id \"%player%-2\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 1.4, orbitalStep 180, xRotation 20, yRotation 45, zRotation 0, radius .06, density 15, precision 50, bumpHeight .05, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 204, 230, 255, particle2 \"redstone\", RGB2 204, 255, 255, center location of player, id \"%player%-3\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 1.4, orbitalStep 180, xRotation 20, yRotation 180, zRotation 0, radius .06, density 15, precision 50, bumpHeight .05, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 0, 255, 10, particle2 \"redstone\", RGB2 0, 10, 255, center location of player, id \"%player%\", rotation true, plane \"x\", rotationSpeed 1, orbit true, orbitalRadius 2, orbitalStep 360, xRotation 45, yRotation 0, zRotation 0, radius .25, density 150, precision 100, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 255, 204, 51, particle2 \"redstone\", RGB2 255, 255, 0, center location of player, id \"%player%\", rotation true, plane \"xz\", rotationSpeed 5, radius .50, density 200, precision 50, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 224, 224, 224, particle2 \"redstone\", RGB2 133, 133, 133, center location of player, id \"%player%-1\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 1.5, orbitalStep 350, xRotation 45, yRotation 0, zRotation 0, radius .05, density 15, precision 100, bumpHeight .05, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 0, 255, 10, particle2 \"redstone\", RGB2 0, 10, 255, center location of player, id \"%player%-2\", rotation true, plane \"xz\", rotationSpeed 5, orbit true, orbitalRadius 3.1, orbitalStep 290, xRotation 45, yRotation 180, zRotation 0, radius .10, density 50, precision 50, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 230, 204, 255, particle2 \"redstone\", RGB2 46, 138, 92, center location of player, id \"%player%-3\", rotation true, plane \"xz\", rotationSpeed 5, orbit true, orbitalRadius 4.5, orbitalStep 300, xRotation 45, yRotation 0, zRotation 0, radius .10, density 50, precision 50, bumpHeight .25, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 153, 153, 255, particle2 \"redstone\", RGB2 153, 204, 255, center location of player, id \"%player%-4\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 5.4, orbitalStep 280, xRotation 20, yRotation 45, zRotation 0, radius .06, density 15, precision 50, bumpHeight .05, visibleRange 30, tps 1, second 2", "drawPlanet particle1 \"redstone\", RGB 204, 230, 255, particle2 \"redstone\", RGB2 204, 255, 255, center location of player, id \"%player%-5\", rotation true, plane \"xz\", rotationSpeed 1, orbit true, orbitalRadius 6.4, orbitalStep 280, xRotation 20, yRotation 180, zRotation 0, radius .06, density 15, precision 50, bumpHeight .05, visibleRange 30, tps 1, second 2"})
public class EffPlanet extends Effect {
   private Expression particleString;
   private Expression data;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression particleString2;
   private Expression data2;
   private Expression speed2;
   private Expression offX2;
   private Expression offY2;
   private Expression offZ2;
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression rotation;
   private Expression planeString;
   private Expression rotationSpeed;
   private Expression orbit;
   private Expression radiusOrbit;
   private Expression orbitalDensity;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression radius;
   private Expression density;
   private Expression precision;
   private Expression mountainHeight;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
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
      this.particleString2 = exprs[i++];
      this.data2 = exprs[i++];
      this.speed2 = exprs[i++];
      this.offX2 = exprs[i++];
      this.offY2 = exprs[i++];
      this.offZ2 = exprs[i++];
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.rotation = exprs[i++];
      this.planeString = exprs[i++];
      this.rotationSpeed = exprs[i++];
      this.orbit = exprs[i++];
      this.radiusOrbit = exprs[i++];
      this.orbitalDensity = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.radius = exprs[i++];
      this.density = exprs[i++];
      this.precision = exprs[i++];
      this.mountainHeight = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawPlanet particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, rotation %-boolean%,[ plane] %-string%, rot[ation]Speed %-number%][, orbit %-boolean%, orbit[al]Radius %-number%, orbit[al]Step %-number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%]], radius %number%, density %number%, precision %number%, bumpHeight %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      String particle2 = "waterdrip";
      float offsetX2 = 0.0F;
      float offsetY2 = 0.0F;
      float offsetZ2 = 0.0F;
      boolean enableRotation = false;
      EffectsLib.Plane plane = EffectsLib.Plane.XYZ;
      float finalRotationStep = 1.0F;
      boolean enableOrbit = false;
      float finalOrbitalRadius = 1.0F;
      float finalOrbitalStep = 360.0F;
      double xRotation = 0.0D;
      double yRotation = 0.0D;
      double zRotation = 0.0D;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      float finalSpeed = 0.0F;
      float finalSpeed2 = 0.0F;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = (float)((Number)this.offX.getSingle(e)).intValue();
         offsetY = (float)((Number)this.offY.getSingle(e)).intValue();
         offsetZ = (float)((Number)this.offZ.getSingle(e)).intValue();
      }

      if (this.particleString2 != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString2.getSingle(e)).toLowerCase())) {
         particle2 = ((String)this.particleString2.getSingle(e)).toLowerCase();
      }

      if (this.speed2 != null) {
         finalSpeed2 = ((Number)this.speed2.getSingle(e)).floatValue();
      }

      if (this.offX2 != null && this.offY2 != null && this.offZ2 != null) {
         offsetX2 = (float)((Number)this.offX2.getSingle(e)).intValue();
         offsetY2 = (float)((Number)this.offY2.getSingle(e)).intValue();
         offsetZ2 = (float)((Number)this.offZ2.getSingle(e)).intValue();
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

      if (this.rotation != null && this.rotation.getSingle(e) != null) {
         enableRotation = (Boolean)this.rotation.getSingle(e);
      }

      if (this.planeString != null) {
         String planes = ((String)this.planeString.getSingle(e)).toUpperCase();
         if (planes.equalsIgnoreCase("X") || planes.equalsIgnoreCase("Y") || planes.equalsIgnoreCase("Z") || planes.equalsIgnoreCase("XY") || planes.equalsIgnoreCase("XZ") || planes.equalsIgnoreCase("XYZ") || planes.equalsIgnoreCase("YZ")) {
            plane = EffectsLib.Plane.valueOf(planes);
         }
      }

      if (this.rotationSpeed != null) {
         finalRotationStep = ((Number)this.rotationSpeed.getSingle(e)).floatValue();
      }

      if (this.orbit != null && this.orbit.getSingle(e) != null) {
         enableOrbit = (Boolean)this.orbit.getSingle(e);
      }

      if (this.radiusOrbit != null) {
         finalOrbitalRadius = ((Number)this.radiusOrbit.getSingle(e)).floatValue();
      }

      if (this.orbitalDensity != null) {
         finalOrbitalStep = ((Number)this.orbitalDensity.getSingle(e)).floatValue();
      }

      if (this.xRot != null && this.yRot != null && this.zRot != null) {
         xRotation = ((Number)this.xRot.getSingle(e)).doubleValue();
         yRotation = ((Number)this.yRot.getSingle(e)).doubleValue();
         zRotation = ((Number)this.zRot.getSingle(e)).doubleValue();
      }

      float finalRadius = ((Number)this.radius.getSingle(e)).floatValue();
      int finalDensity = ((Number)this.density.getSingle(e)).intValue();
      int finalPrecision = ((Number)this.precision.getSingle(e)).intValue();
      float finalMountainHeight = ((Number)this.mountainHeight.getSingle(e)).floatValue();
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
      }

      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      try {
         Material dataMat = ((ItemStack)this.data.getSingle(e)).getType();
         byte dataID = ((ItemStack)this.data.getSingle(e)).getData().getData();
         Material dataMat2 = ((ItemStack)this.data2.getSingle(e)).getType();
         byte dataID2 = ((ItemStack)this.data2.getSingle(e)).getData().getData();
         EffectsLib.drawPlanet(particle, dataMat, dataID, finalSpeed, particle2, dataMat2, dataID2, finalSpeed2, center, idName, isSinglePlayer, p, rainbowMode, enableRotation, plane, finalRotationStep, enableOrbit, finalOrbitalRadius, finalOrbitalStep, xRotation, yRotation, zRotation, finalRadius, finalDensity, finalPrecision, finalMountainHeight, visibleRange, offsetX, offsetY, offsetZ, offsetX2, offsetY2, offsetZ2, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      } catch (Exception var47) {
         Material dataMatNull = Material.DIRT;
         byte dataIDNull = 0;
         EffectsLib.drawPlanet(particle, dataMatNull, dataIDNull, finalSpeed, particle2, dataMatNull, dataIDNull, finalSpeed2, center, idName, isSinglePlayer, p, rainbowMode, enableRotation, plane, finalRotationStep, enableOrbit, finalOrbitalRadius, finalOrbitalStep, xRotation, yRotation, zRotation, finalRadius, finalDensity, finalPrecision, finalMountainHeight, visibleRange, offsetX, offsetY, offsetZ, offsetX2, offsetY2, offsetZ2, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      }

   }
}
