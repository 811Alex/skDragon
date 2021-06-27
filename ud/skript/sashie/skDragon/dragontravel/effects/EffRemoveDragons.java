package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Remove riderless dragons")
@Description({"Removes all riderless dragons from a certain world except stationary dragons"})
@Syntaxes({"remove riderless dragons in[ world] %string%"})
@Examples({""})
public class EffRemoveDragons extends Effect {
   private Expression worldName;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.worldName = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "remove riderless dragons in[ world] %string%";
   }

   protected void execute(@Nullable Event e) {
      String worldName = (String)this.worldName.getSingle(e);
      World w = Bukkit.getWorld(worldName);
      DragonTravel.getInstance().getDragonManager().removeDragons(w, false);
   }
}
