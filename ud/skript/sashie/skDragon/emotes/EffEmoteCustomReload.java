package ud.skript.sashie.skDragon.emotes;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Emotes - Custom reload")
@Description({"Reloads the CustomEmotes.yml file"})
@Syntaxes({"reload custom emotes"})
@Examples({"reload custom emotes"})
public class EffEmoteCustomReload extends Effect {
   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "Custom Emotes Reload";
   }

   protected void execute(@Nullable Event e) {
      SkullConfig config = new SkullConfig();
      config.reload();
   }
}
