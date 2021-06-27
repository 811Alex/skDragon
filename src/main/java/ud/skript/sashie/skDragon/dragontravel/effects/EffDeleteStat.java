package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import eu.phiwa.dragontravel.core.movement.stationary.StationaryDragon;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Delete stationary dragon")
@Description({"Deletes a stationary dragon"})
@Syntaxes({"delete stat[ionary] dragon[ named] %string%"})
@Examples({""})
public class EffDeleteStat extends Effect {
   private Expression name;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.name = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "delete stat[ionary] dragon[ named] %string%";
   }

   protected void execute(@Nullable Event e) {
      String name = (String)this.name.getSingle(e);
      if (DragonTravel.getInstance().getDragonManager().getStationaryDragons().containsKey(name.toLowerCase())) {
         StationaryDragon sDragon = DragonTravel.getInstance().getDragonManager().getStationaryDragons().get(name.toLowerCase());
         sDragon.removeDragon(true);
         DragonTravel.getInstance().getDragonManager().getStationaryDragons().remove(name.toLowerCase());
      }
   }
}
