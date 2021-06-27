package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Delete station")
@Description({"Deletes a station with the selected name"})
@Syntaxes({"delete [dragon ]station[ named] %string%"})
@Examples({""})
public class EffDeleteStation extends Effect {
   private Expression station;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.station = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "delete [dragon ]station[ named] %string%";
   }

   protected void execute(@Nullable Event e) {
      String station = (String)this.station.getSingle(e);
      DragonTravel.getInstance().getDbStationsHandler().deleteStation(station);
   }
}
