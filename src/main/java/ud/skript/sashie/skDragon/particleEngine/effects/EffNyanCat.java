package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawNyanCat ")
@Description({"Draws a Nyan Cat (band) effect that follows the player or plays at a location. New Syntax as of v0.06.0-BETA"})
@Syntaxes({"drawNyanCat center %object%, id %string%[, isSingle %-boolean%, %-player%], visibleRange %number%[, tps %-number%, second %-number%]"})
@Examples({"drawNyanCat center player, id \"%player%\", visibleRange 30"})
public class EffNyanCat extends Effect {
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression range;
   private Expression ticks;
   private Expression seconds;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.range = exprs[i++];
      this.ticks = exprs[i++];
      this.seconds = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawNyanCat center %entity/location%, id %string%[, isSingle %-boolean%, %-player%], visibleRange %number%[, tps %-number%, second %-number%]";
   }

   protected void execute(@Nullable Event e) {
      Object center = this.entLoc.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      Player p = null;
      boolean isSinglePlayer = false;
      if (this.singlePlayer != null && this.singlePlayer.getSingle(e) != null && this.player != null && this.player.getSingle(e) != null) {
         isSinglePlayer = (Boolean)this.singlePlayer.getSingle(e);
         p = (Player)this.player.getSingle(e);
      }

      double visibleRange = ((Number)this.range.getSingle(e)).doubleValue();
      Long finalDelayTicks = 0L;
      Long finalDelayBySec = 0L;
      if (this.ticks != null) {
         finalDelayTicks = (Long)this.ticks.getSingle(e);
      }

      if (this.seconds != null) {
         finalDelayBySec = (Long)this.seconds.getSingle(e);
      }

      EffectsLib.drawNyanBand(center, idName, isSinglePlayer, visibleRange, finalDelayTicks, finalDelayBySec, p);
   }
}
