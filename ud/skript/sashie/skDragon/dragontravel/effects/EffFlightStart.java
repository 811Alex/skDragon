package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.api.DragonException;
import eu.phiwa.dragontravel.core.DragonManager;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Take on flight")
@Description({"Will take the player on a selected reconfigured flight"})
@Syntaxes({"take %player% on flight[ named] %string%"})
@Examples({""})
public class EffFlightStart extends Effect {
   private Expression flight;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.flight = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% on flight[ named] %string%";
   }

   protected void execute(Event e) {
      String flight = (String)this.flight.getSingle(e);
      Player p = (Player)this.player.getSingle(e);

      try {
         DragonManager.getDragonManager().getFlightEngine().startFlight(p, flight, true, p);
      } catch (DragonException var5) {
         Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
         var5.printStackTrace();
      }

   }
}
