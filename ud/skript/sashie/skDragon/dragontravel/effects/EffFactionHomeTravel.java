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

@Name("DragonTravel - Take to faction home")
@Description({"Takes player to their faction home", "Untested (it uses whatever version DragonTravel uses)"})
@Syntaxes({"take %player% to[ their] dragon faction home"})
@Examples({""})
public class EffFactionHomeTravel extends Effect {
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% to[ their] dragon faction home";
   }

   protected void execute(Event e) {
      Player p = (Player)this.player.getSingle(e);

      try {
         DragonManager.getDragonManager().getTravelEngine().toFactionHome(p, true);
      } catch (DragonException var4) {
         Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
         var4.printStackTrace();
      }

   }
}
