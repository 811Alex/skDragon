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

@Name("drawArc")
@Description({"Draws an arc from one location or player to another. New as of v0.06.33-BETA"})
@Syntaxes({"drawArc particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, target %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], density %number%, height %number%, p[itch]M[ultiplier] %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, dis[placement]X2 %-number%, dis[placement]Y2 %-number%, dis[placement]Z2 %-number%][, tps %-number%, second %-number%]"})
@Examples({"drawArc particle \"redstone\", center player, target location of player, id \"%player%\", rainbowMode true, density 50, height 2, pitchMultiplier 4, visibleRange 30", "drawArc particle \"redstone\", center player, target target block, id \"%player%\", rainbowMode true, density 50, height 2, pitchMultiplier 4, visibleRange 30", "drawArc particle \"redstone\", center player, target target block, id \"%player%\", rainbowMode true, density 50, height 2, pitchMultiplier 8, visibleRange 30, displacementX 0, displacementY 4, displacementZ 0", "drawArc particle \"redstone\", center player, target target block, id \"%player%\", rainbowMode true, density 50, height -3, pitchMultiplier 4, visibleRange 30", "drawArc particle \"redstone\", center player, target target block, id \"%player%\", rainbowMode true, density 50, height o, pitchMultiplier 4, visibleRange 30"})
public class EffArc extends Effect {
   private Expression particleString;
   private Expression data;
   private Expression speed;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression offXT;
   private Expression offYT;
   private Expression offZT;
   private Expression entLoc;
   private Expression tarLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression pDensity;
   private Expression height;
   private Expression pitch;
   private Expression range;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression displaceX2;
   private Expression displaceY2;
   private Expression displaceZ2;
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
      this.entLoc = exprs[i++];
      this.tarLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.pDensity = exprs[i++];
      this.height = exprs[i++];
      this.pitch = exprs[i++];
      this.range = exprs[i++];
      this.displaceX = exprs[i++];
      this.displaceY = exprs[i++];
      this.displaceZ = exprs[i++];
      this.displaceX2 = exprs[i++];
      this.displaceY2 = exprs[i++];
      this.displaceZ2 = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawArc particle %string%[, material %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%][, trans[ition] ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, target %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%], density %number%, height %number%, p[itch]M[ultiplier] %number%, visibleRange %number%[, dis[placement]X %-number%, dis[placement]Y %-number%, dis[placement]Z %-number%][, dis[placement]X2 %-number%, dis[placement]Y2 %-number%, dis[placement]Z2 %-number%][, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float offsetXT = 0.0F;
      float offsetYT = 0.0F;
      float offsetZT = 0.0F;
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      double disX2 = 0.0D;
      double disY2 = 0.0D;
      double disZ2 = 0.0D;
      float finalSpeed = 0.0F;
      float finalHeight = 2.0F;
      float pitchMuliplier = 4.0F;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      Object center = this.entLoc.getSingle(e);
      Object target = this.tarLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      Long pD = (Long)this.pDensity.getSingle(e);
      Integer finalParticleDensity = pD != null ? pD.intValue() : null;
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

      if (this.speed != null) {
         finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
      }

      if (this.offX != null && this.offY != null && this.offZ != null) {
         offsetX = (float)((Number)this.offX.getSingle(e)).intValue();
         offsetY = (float)((Number)this.offY.getSingle(e)).intValue();
         offsetZ = (float)((Number)this.offZ.getSingle(e)).intValue();
      }

      if (this.offXT != null && this.offYT != null && this.offZT != null) {
         offsetXT = (float)((Number)this.offXT.getSingle(e)).intValue();
         offsetYT = (float)((Number)this.offYT.getSingle(e)).intValue();
         offsetZT = (float)((Number)this.offZT.getSingle(e)).intValue();
      }

      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
      }

      if (this.displaceX2 != null && this.displaceY2 != null && this.displaceZ2 != null) {
         disX2 = ((Number)this.displaceX2.getSingle(e)).doubleValue();
         disY2 = ((Number)this.displaceY2.getSingle(e)).doubleValue();
         disZ2 = ((Number)this.displaceZ2.getSingle(e)).doubleValue();
      }

      if (this.height != null) {
         finalHeight = ((Number)this.height.getSingle(e)).floatValue();
      }

      if (this.pitch != null) {
         pitchMuliplier = ((Number)this.pitch.getSingle(e)).floatValue();
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
         EffectsLib.drawArc(particle, dataMat, dataID, finalSpeed, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, center, target, idName, isSinglePlayer, p, rainbowMode, finalParticleDensity, finalHeight, pitchMuliplier, visibleRange, disX, disY, disZ, disX2, disY2, disZ2, finalDelayTicks, finalDelayBySec);
      } catch (Exception var36) {
         Material dataMatNull = Material.DIRT;
         byte dataIDNull = 0;
         EffectsLib.drawArc(particle, dataMatNull, dataIDNull, finalSpeed, offsetX, offsetY, offsetZ, offsetXT, offsetYT, offsetZT, center, target, idName, isSinglePlayer, p, rainbowMode, finalParticleDensity, finalHeight, pitchMuliplier, visibleRange, disX, disY, disZ, disX2, disY2, disZ2, finalDelayTicks, finalDelayBySec);
      }

   }
}
