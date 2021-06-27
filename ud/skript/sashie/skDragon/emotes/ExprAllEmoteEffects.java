package ud.skript.sashie.skDragon.emotes;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Emotes - All effects")
@Description({"Lists all currently running effects"})
@Syntaxes({"all emote effects"})
@Examples({"set {list::*} to all emote effects"})
public class ExprAllEmoteEffects extends SimpleExpression {
   public Class getReturnType() {
      return String.class;
   }

   public boolean isSingle() {
      return false;
   }

   public boolean init(Expression[] args, int arg1, Kleenean arg2, ParseResult arg3) {
      return true;
   }

   public String toString(@Nullable Event arg0, boolean arg1) {
      return "EmoteEffect";
   }

   @Nullable
   protected String[] get(Event arg0) {
      return (String[])SkullEffectsLib.getActiveEmoteKeys().toArray(new String[SkullEffectsLib.getActiveEmoteKeys().size()]);
   }
}
