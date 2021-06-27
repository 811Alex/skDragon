package ud.skript.sashie.skDragon.dragontravel.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprStationName extends SimpleExpression {
   private Expression player;

   public Class getReturnType() {
      return String.class;
   }

   public boolean isSingle() {
      return true;
   }

   public boolean init(Expression[] expr, int matchedPattern, Kleenean arg2, ParseResult arg3) {
      this.player = expr[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean arg1) {
      return "dragon station of %player%";
   }

   @Nullable
   protected String[] get(Event e) {
      Player p = (Player)this.player.getSingle(e);
      String homeName = DragonTravel.getInstance().getDbHomesHandler().getHome(p.getUniqueId().toString()).toString();

      try {
         return new String[]{homeName};
      } catch (Exception var5) {
         return new String[]{"No station set"};
      }
   }
}
