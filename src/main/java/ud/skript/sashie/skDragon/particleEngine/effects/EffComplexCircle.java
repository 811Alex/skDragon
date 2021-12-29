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

@Name("drawCircle ")
@Description({"Draws a circle effect that follows the player or plays at a location. The only thing complex about this circle is its syntax. New Syntax as of v0.06.0-BETA "})
@Syntaxes({"draw[Complex]Circle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, start %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawComplexCircle particle \"redstone\", center player, id \"%player%\", rainbowMode true, randomRotation true, radius 1.5, density 20, start 0, visibleRange 30"})
public class EffComplexCircle extends Effect {
   private Expression data;
   private Expression particleString;
   private Expression entLoc;
   private Expression idName;
   private Expression radius;
   private Expression player;
   private Expression xRot;
   private Expression yRot;
   private Expression zRot;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression speed;
   private Expression pDensity;
   private Expression step;
   private Expression range;
   private Expression singlePlayer;
   private Expression rotation;
   private Expression rainbMode;
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
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.rotation = exprs[i++];
      this.radius = exprs[i++];
      this.pDensity = exprs[i++];
      this.step = exprs[i++];
      this.range = exprs[i++];
      this.xRot = exprs[i++];
      this.yRot = exprs[i++];
      this.zRot = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawComplexCircle particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], randomRotation %boolean%, radius %number%, density %number%, start %number%, visibleRange %number%[, xR[otation] %-number%, yR[otation] %-number%, zR[otation] %-number%][, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float finalSpeed = 0.0F;
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      double xRotation = 0.0D;
      double yRotation = 0.0D;
      double zRotation = 0.0D;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      int finalParticleDensity = ((Number)this.pDensity.getSingle(e)).intValue();
      float finalStep = ((Number)this.step.getSingle(e)).floatValue();
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
      Number radiusInt = (Number)this.radius.getSingle(e);
      float radius = radiusInt.floatValue();
      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = ((Number)this.offX.getSingle(e)).floatValue();
         offsetY = ((Number)this.offY.getSingle(e)).floatValue();
         offsetZ = ((Number)this.offZ.getSingle(e)).floatValue();
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

      try {
         Material dataMat = ((ItemStack)this.data.getSingle(e)).getType();
         byte dataID = ((ItemStack)this.data.getSingle(e)).getData().getData();
         EffectsLib.drawComplexCircle(particle, dataMat, dataID, center, idName, isSinglePlayer, p, rainbowMode, enableRotation, radius, finalSpeed, finalParticleDensity, finalStep, visibleRange, xRotation, yRotation, zRotation, offsetX, offsetY, offsetZ, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      } catch (Exception var36) {
         Material dataMatNull = Material.DIRT;
         byte dataIDNull = 0;
         EffectsLib.drawComplexCircle(particle, dataMatNull, dataIDNull, center, idName, isSinglePlayer, p, rainbowMode, enableRotation, radius, finalSpeed, finalParticleDensity, finalStep, visibleRange, xRotation, yRotation, zRotation, offsetX, offsetY, offsetZ, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      }

   }
}
