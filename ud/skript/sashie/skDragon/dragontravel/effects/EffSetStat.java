package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import eu.phiwa.dragontravel.core.movement.stationary.StationaryDragon;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Set stationary dragon")
@Description({"Sets a stationary dragon at a location for a player"})
@Syntaxes({"set %player%[(s|'s)] stat[ionary] dragon[ named] %string% with display[name] %string% at %location%"})
@Examples({""})
public class EffSetStat extends Effect {
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
      return "set %player%[(s|'s)] stat[ionary] dragon[ named] %string% with display[name] %string% at %location%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      String name = ((String)this.name.getSingle(e)).toLowerCase();
      String displayName = (String)this.displayName.getSingle(e);
      Location l = (Location)this.loc.getSingle(e);
      if (!DragonTravel.getInstance().getDragonManager().getStationaryDragons().containsKey(name)) {
         StationaryDragon sDragon = new StationaryDragon(p, name, displayName, l, true);
         DragonTravel.getInstance().getDragonManager().getStationaryDragons().put(name.toLowerCase(), sDragon);
      }
   }
}
