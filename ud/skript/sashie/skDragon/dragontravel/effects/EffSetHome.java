package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import eu.phiwa.dragontravel.core.movement.travel.Home;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Set home")
@Description({"Sets players DragonTravel home to a location"})
@Syntaxes({"set %player%[(s|'s)] dragon home at %location%"})
@Examples({""})
public class EffSetHome extends Effect {
   private Expression home;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.home = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "set %player%[(s|'s)] dragon home at %location%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      Location loc = (Location)this.home.getSingle(e);
      Home homeLoc = new Home(loc);
      DragonTravel.getInstance().getDbHomesHandler().saveHome(p.getUniqueId().toString(), homeLoc);
   }
}
