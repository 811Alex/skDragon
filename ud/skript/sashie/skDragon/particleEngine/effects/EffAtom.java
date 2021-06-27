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

@Name("drawAtom")
@Description({"Draws an atom effect that follows the player or plays at a location. New Syntax as of v0.06.0-BETA"})
@Syntaxes({"drawAtom particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], innerPCount %number%, innerRadius %number%, outerPCount %number%, orbitCount %number%, start %number%, visibleRange %number%, rot[ation] %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawAtom particle1 \"redstone\", particle2 \"flame\", center location of player, id \"%player%\", rainbowMode true, innerPCount 10, innerRadius .5, outerPCount 2, orbitCount 5, start 0, visibleRange 30, rotation 15"})
public class EffAtom extends Effect {
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
   private Expression innerCount;
   private Expression innerRadius;
   private Expression outerCount;
   private Expression orbitCount;
   private Expression yRot;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression step;
   private Expression range;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.particleString = exprs[0];
      this.data = exprs[1];
      this.speed = exprs[2];
      this.offX = exprs[3];
      this.offY = exprs[4];
      this.offZ = exprs[5];
      this.particleString2 = exprs[6];
      this.data2 = exprs[7];
      this.speed2 = exprs[8];
      this.offX2 = exprs[9];
      this.offY2 = exprs[10];
      this.offZ2 = exprs[11];
      this.entLoc = exprs[12];
      this.idName = exprs[13];
      this.singlePlayer = exprs[14];
      this.player = exprs[15];
      this.rainbMode = exprs[16];
      this.innerCount = exprs[17];
      this.innerRadius = exprs[18];
      this.outerCount = exprs[19];
      this.orbitCount = exprs[20];
      this.step = exprs[21];
      this.range = exprs[22];
      this.yRot = exprs[23];
      this.displaceX = exprs[24];
      this.displaceY = exprs[25];
      this.displaceZ = exprs[26];
      this.ticks = exprs[27];
      this.seconds = exprs[28];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawAtom particle1 %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], particle2 %string%[[, material] %-itemstack%][, speed2 %-number%][, ([offset]XYZ2|RGB2) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], innerPCount %number%, innerRadius %number%, outerPCount %number%, orbitCount %number%, start %number%, visibleRange %number%, rot[ation] %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      String particle2 = "happyvillager";
      float offsetX2 = 0.0F;
      float offsetY2 = 0.0F;
      float offsetZ2 = 0.0F;
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

      Number radiusInt = (Number)this.innerRadius.getSingle(e);
      float radius = radiusInt.floatValue();
      int innerParticles = ((Number)this.innerCount.getSingle(e)).intValue();
      int orbitParticles = ((Number)this.outerCount.getSingle(e)).intValue();
      int orbitalCount = ((Number)this.orbitCount.getSingle(e)).intValue();
      float finalStep = ((Number)this.step.getSingle(e)).floatValue();
      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      double rotations = ((Number)this.yRot.getSingle(e)).doubleValue();
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
         EffectsLib.drawAtom(particle, dataMat, dataID, finalSpeed, particle2, dataMat2, dataID2, finalSpeed2, center, idName, isSinglePlayer, p, rainbowMode, radius, innerParticles, orbitParticles, orbitalCount, finalStep, visibleRange, rotations, offsetX, offsetY, offsetZ, offsetX2, offsetY2, offsetZ2, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      } catch (Exception var39) {
         Material dataMatNull = Material.DIRT;
         byte dataIDNull = 0;
         EffectsLib.drawAtom(particle, dataMatNull, dataIDNull, finalSpeed, particle2, dataMatNull, dataIDNull, finalSpeed2, center, idName, isSinglePlayer, p, rainbowMode, radius, innerParticles, orbitParticles, orbitalCount, finalStep, visibleRange, rotations, offsetX, offsetY, offsetZ, offsetX2, offsetY2, offsetZ2, disX, disY, disZ, finalDelayTicks, finalDelayBySec);
      }

   }
}
