package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import eu.phiwa.dragontravel.core.movement.travel.Station;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Set a station for a player")
@Description({"Sets a station for a player at a location", "Bypasses the op restriction DragonTravel used on the actual command"})
@Syntaxes({"set %player%[(s|'s)] [dragon ]station[ named] %string% with display[name] %string% at %location%"})
@Examples({""})
public class EffSetStation extends Effect {
   private Expression loc;
   private Expression player;
   private Expression name;
   private Expression displayName;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.loc = exprs[3];
      this.displayName = exprs[2];
      this.name = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "set %player%[(s|'s)] [dragon[ ]]station[ named] %string% with display[name] %string% at %location%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      String name = ((String)this.name.getSingle(e)).toLowerCase();
      String displayName = (String)this.displayName.getSingle(e);
      Location l = (Location)this.loc.getSingle(e);
      DragonTravel.getInstance().getDbStationsHandler().saveStation(new Station(name, displayName, l, p.getUniqueId().toString()));
   }
}
