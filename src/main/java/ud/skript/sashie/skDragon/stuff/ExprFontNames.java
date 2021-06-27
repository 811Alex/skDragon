package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("System fonts")
@Description({"Gets a list of all system fonts for use in drawText"})
@Syntaxes({"[all ][system ]font names"})
@Examples({"all system font names"})
public class ExprFontNames extends SimpleExpression {
   @Nullable
   protected String[] get(Event e) {
      String[] var6;
      int var5 = (var6 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).length;

      ArrayList cl = new ArrayList(Arrays.asList(var6).subList(0, var5));

      return (String[])cl.toArray(new String[0]);
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
