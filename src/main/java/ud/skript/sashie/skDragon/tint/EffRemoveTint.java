package ud.skript.sashie.skDragon.tint;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Remove Red Tint")
@Description({"Removes the red tint from a player"})
@Syntaxes({"remove[ red] tint from %players%"})
@Examples({"remove red tint from player"})
public class EffRemoveTint extends Effect {
   private Expression inputPlayers;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.inputPlayers = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "remove[ red] tint from %players%";
   }

   protected void execute(@Nullable Event e) {
      throw new NotImplementedException("Tint is not supported on this hacky skDragon version!");
   }
}
