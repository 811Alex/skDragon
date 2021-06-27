package ud.skript.sashie.skDragon.dragontravel.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.dragontravel.DragonTravelHook;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - All home names")
@Description({"Gets a list of all active dragontravel home names used(these are the uuid of the player)"})
@Syntaxes({"[all ][dragontravel ]home names"})
@Examples({"set {_var::*} to home names", "set {_var::*} to all home names", "set {_var::*} to dragontravel home names"})
public class ExprAllHomes extends SimpleExpression {
   DragonTravelHook hook = new DragonTravelHook();

   @Nullable
   protected String[] get(Event e) {
      this.hook.initHomesDB();
      ArrayList cl = new ArrayList();
      Iterator var4 = this.hook.getAllHomes().iterator();

      while(var4.hasNext()) {
         String names = (String)var4.next();
         cl.add(names);
      }

      return (String[])cl.toArray(new String[cl.size()]);
   }

   public boolean init(Expression[] e, int i, Kleenean k, ParseResult p) {
      return true;
   }

   public Class getReturnType() {
      return String.class;
   }

   public boolean isSingle() {
      return false;
   }

   public String toString(@Nullable Event e, boolean b) {
      return this.getClass().getName();
   }
}
