package ud.skript.sashie.skDragon.dragontravel.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.dragontravel.DragonTravelHook;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Home location")
@Description({"Gets the location of a dragontravel home"})
@Syntaxes({"location of home named %string%"})
@Examples({"set {_loc} to location of home named \"test\""})
public class ExprHomeLocation extends SimpleExpression {
   private Expression name;

   public Class getReturnType() {
      return Location.class;
   }

   public boolean isSingle() {
      return true;
   }

   public boolean init(Expression[] expr, int matchedPattern, Kleenean arg2, ParseResult arg3) {
      this.name = expr[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean arg1) {
      return "DragonTravel Home Location";
   }

   @Nullable
   protected Location[] get(Event e) {
      String n = (String)this.name.getSingle(e);
      Location loc = DragonTravelHook.getHomeLocation(n);

      try {
         return new Location[]{loc};
      } catch (Exception var5) {
         skDragonCore.error("No homes with that name exist");
         return new Location[1];
      }
   }
}
