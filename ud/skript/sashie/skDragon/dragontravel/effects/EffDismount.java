package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Dismount")
@Description({"Dismounts player from their current travel"})
@Syntaxes({"make %player% (stop|dismount from) dragon travel"})
@Examples({""})
public class EffDismount extends Effect {
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "make %player% (stop|dismount from) dragon travel";
   }

   protected void execute(Event e) {
      Player p = (Player)this.player.getSingle(e);

      try {
         DragonTravel.getInstance().getDragonManager().dismount(p, false);
      } catch (Exception var4) {
      }

   }
}
