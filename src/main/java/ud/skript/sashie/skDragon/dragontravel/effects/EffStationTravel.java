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

@Name("DragonTravel - Travel to station")
@Description({"Takes player to a selected station"})
@Syntaxes({"take %player% to station[ named] %string%"})
@Examples({""})
public class EffStationTravel extends Effect {
   private Expression station;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.station = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% to station[ named] %string%";
   }

   protected void execute(Event e) {
      String station = (String)this.station.getSingle(e);
      Player p = (Player)this.player.getSingle(e);

      try {
         DragonManager.getDragonManager().getTravelEngine().toStation(p, station, true, p);
      } catch (DragonException var5) {
         Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
         var5.printStackTrace();
      }

   }
}
