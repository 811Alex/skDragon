package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("All id names")
@Description({"Gets a list of all active id names used"})
@Syntaxes({"[all ][particle effect ]id names"})
@Examples({"set {_var::*} to id names", "set {_var::*} to all id names", "set {_var::*} to particle effect id names"})
public class ExprIDNames extends SimpleExpression {
   @Nullable
   protected String[] get(Event e) {
      ArrayList l = new ArrayList();
      Iterator var4 = EffectsLib.getAllEffectIds().iterator();

      while(var4.hasNext()) {
         String allEffects = (String)var4.next();
         l.add(allEffects);
      }

      return (String[])l.toArray(new String[0]);
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
