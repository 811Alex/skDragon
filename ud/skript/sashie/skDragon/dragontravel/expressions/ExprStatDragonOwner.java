package ud.skript.sashie.skDragon.dragontravel.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.dragontravel.DragonTravelHook;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Station owner")
@Description({"Gets the owner of a stationary dragon"})
@Syntaxes({"owner of stationary dragon named %string%"})
@Examples({"set {_name} to owner of stationary dragon named \"test\""})
public class ExprStatDragonOwner extends SimpleExpression {
   private Expression name;

   public Class getReturnType() {
      return String.class;
   }

   public boolean isSingle() {
      return true;
   }

   public boolean init(Expression[] expr, int matchedPattern, Kleenean arg2, ParseResult arg3) {
      this.name = expr[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean arg1) {
      return "DragonTravel Stationary Dragon owner";
   }

   @Nullable
   protected String[] get(Event e) {
      String n = (String)this.name.getSingle(e);
      String displayName = DragonTravelHook.getStatDragonOwner(n);

      try {
         return new String[]{displayName};
      } catch (Exception var5) {
         skDragonCore.error("No stationary dragons with that name exist");
         return new String[1];
      }
   }
}
