package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawHalo")
@Description({"Draws a simple halo effect over the players head or at a location. New Syntax as of v0.06.0-BETA", "Added solid in v0.09.0-BETA Added density in v0.10.0-BETA", "Added an option to make the halo solid (see examples) "})
@Syntaxes({"draw[Simple]Halo particle %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %object%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, solid %-boolean%][, density %-number%], visibleRange %number%[, tps %-number%, second %-number%]"})
@Examples({"drawSimpleHalo particle \"redstone\", center player, id \"%player%\", rainbowMode true, visibleRange 32", "drawHalo particle \"redstone\", RGB 0, 0, 0, center player, id \"%player%\", rainbowMode true, solid true, visibleRange 32", "drawHalo particle \"flame\", RGB 0, 0, 0, center player, id \"%player%\", rainbowMode true, solid true, density 10, visibleRange 32"})
public class EffSimpleHalo extends Effect {
   private Expression inputParticleData;
   private Expression particleString;
   private Expression entLoc;
   private Expression idName;
   private Expression player;
   private Expression offX;
   private Expression offY;
   private Expression offZ;
   private Expression speed;
   private Expression range;
   private Expression singlePlayer;
   private Expression rainbMode;
   private Expression isSolid;
   private Expression density;
   private Expression ticks;
   private Expression seconds;

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
      this.isSolid = exprs[11];
      this.density = exprs[12];
      this.range = exprs[13];
      this.ticks = exprs[14];
      this.seconds = exprs[15];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "draw[Simple]Halo particle %string%[[, material] %-itemstack%][, speed %-number%][, ([offset]XYZ|RGB) %-number%, %-number%, %-number%], center %entity/location%, id %string%[, isSingle %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, solid %-boolean%][, density %-number%], visibleRange %number%[, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String particle = "happyvillager";
      float offsetX = 0.0F;
      float offsetY = 0.0F;
      float offsetZ = 0.0F;
      float finalSpeed = 0.0F;
      int finalDensity = 10;
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.particleString != null && ParticleEffect.NAME_MAP.containsKey(((String)this.particleString.getSingle(e)).toLowerCase())) {
         particle = ((String)this.particleString.getSingle(e)).toLowerCase();
      }

      Object center = null;

      try {
         center = this.entLoc.getSingle(e);
      } catch (SkriptAPIException var20) {
         skDragonCore.error("Presumably you've used an unset variable");
         return;
      }

      if (center == null) {
         skDragonCore.error("Location unset");
      } else {
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

         boolean solid = false;
         if (this.isSolid != null && this.isSolid.getSingle(e) != null) {
            solid = (Boolean)this.isSolid.getSingle(e);
         }

         if (this.density != null) {
            finalDensity = ((Number)this.density.getSingle(e)).intValue();
         }

         if (this.speed != null) {
            finalSpeed = ((Number)this.speed.getSingle(e)).floatValue();
         }

         if (this.offX != null && this.offY != null && this.offZ != null) {
            offsetX = (float)((Number)this.offX.getSingle(e)).intValue();
            offsetY = (float)((Number)this.offY.getSingle(e)).intValue();
            offsetZ = (float)((Number)this.offZ.getSingle(e)).intValue();
         }

         if (this.ticks != null) {
            finalDelayTicks = (Long)this.ticks.getSingle(e);
         }

         if (this.seconds != null) {
            finalDelayBySec = (Long)this.seconds.getSingle(e);
         }

         Material dataMat = SkriptHandler.inputParticleDataMat(e, this.inputParticleData);
         byte dataID = SkriptHandler.inputParticleDataID(e, this.inputParticleData);
         EffectsLib.drawSimpleHalo(particle, center, dataMat, dataID, idName, isSinglePlayer, p, rainbowMode, finalSpeed, finalDensity, visibleRange, offsetX, offsetY, offsetZ, finalDelayTicks, finalDelayBySec, solid);
      }
   }
}
