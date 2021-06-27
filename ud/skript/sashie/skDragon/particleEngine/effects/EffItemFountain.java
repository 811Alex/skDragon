package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawItemFountain ")
@Description({"Tosses items around for a preset time that follow the player or play at a location. (don't make the time too long or bad things might happen xD ) Added in 0.09.0-BETA", "style - choose between different effects ", "yVelocity - effects how high the item gets tossed, a value of 1 for example will toss the item fairly high ", "radius - effects how far the item will get tossed, a value of 1 will toss it farther then you would think xD keep this in mind Together yVelocity and radius act almost like a slingshot, the farther away they are set from the center, the faster the item will fly out in that direction. "})
@Syntaxes({"drawItemFountain %itemstack%, style %number%, center %object%, id %string%, itemTime %timespan%, yVelocity %number%, radius %number%, density %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]"})
@Examples({"drawItemFountain arg-1, style arg-4, center location of player, id \"%player%\", itemTime 1 tick, yVelocity arg-2, radius arg-3, density 40, displacementXYZ 0, 0, 0, pulseDelay 3"})
public class EffItemFountain extends Effect {
   private Expression data;
   private Expression inputStyle;
   private Expression entLoc;
   private Expression idName;
   private Expression itemTimer;
   private Expression heightMod;
   private Expression radius;
   private Expression pDensity;
   private Expression displaceX;
   private Expression displaceY;
   private Expression displaceZ;
   private Expression ticks;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.data = exprs[0];
      this.inputStyle = exprs[1];
      this.entLoc = exprs[2];
      this.idName = exprs[3];
      this.itemTimer = exprs[4];
      this.heightMod = exprs[5];
      this.radius = exprs[6];
      this.pDensity = exprs[7];
      this.displaceX = exprs[8];
      this.displaceY = exprs[9];
      this.displaceZ = exprs[10];
      this.ticks = exprs[11];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawItemFountain %itemstack%, style %number%, center %object%, id %string%, itemTime %timespan%, yVelocity %number%, radius %number%, density %number%[, dis[placement]XYZ %-number%, %-number%, %-number%][, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      double disX = 0.0D;
      double disY = 0.0D;
      double disZ = 0.0D;
      Long finalDelayTicks = 0L;
      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      long finalItemTimer = Math.round((double)((Timespan)this.itemTimer.getSingle(e)).getMilliSeconds() / 50.0D);
      float finalHeightMod = ((Number)this.heightMod.getSingle(e)).floatValue();
      int finalStyle = ((Number)this.inputStyle.getSingle(e)).intValue();
      float finalRadius = ((Number)this.radius.getSingle(e)).floatValue();
      int finalParticleCount = ((Number)this.pDensity.getSingle(e)).intValue();
      if (this.displaceX != null && this.displaceY != null && this.displaceZ != null) {
         disX = ((Number)this.displaceX.getSingle(e)).doubleValue();
         disY = ((Number)this.displaceY.getSingle(e)).doubleValue();
         disZ = ((Number)this.displaceZ.getSingle(e)).doubleValue();
      }

      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      ItemStack finalData = null;

      try {
         if (!((ItemStack)this.data.getSingle(e)).getData().getItemType().equals(Material.AIR)) {
            finalData = new ItemStack((ItemStack)this.data.getSingle(e));
         } else {
            finalData = new ItemStack(Material.DIRT, 1);
         }
      } catch (Exception var19) {
         finalData = new ItemStack(Material.DIRT, 1);
      }

      EffectsLib.drawItemFountain(finalData, finalStyle, center, idName, finalItemTimer, finalHeightMod, finalRadius, finalParticleCount, disX, disY, disZ, finalDelayTicks);
   }
}
