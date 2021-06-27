package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Dismount to location")
@Description({"Dismounts a player to a certain location", "Doesn't seem to work as intended, there's an option in DragonTravel that might need to be disabled/enabled to allow this feature"})
@Syntaxes({"make %player% (stop|dismount from) dragon travel at %location%"})
@Examples({""})
public class EffDismountToLoc extends Effect {
   private Expression dismountLoc;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.dismountLoc = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "make %player% (stop|dismount from) dragon travel at %location%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      Location loc = (Location)this.dismountLoc.getSingle(e);

      try {
         DragonTravel.getInstance().getDragonManager().removeRiderAndDragon(p, loc);
      } catch (Exception var5) {
      }

   }
}
