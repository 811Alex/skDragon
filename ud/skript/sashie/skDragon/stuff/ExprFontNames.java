package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
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
      ArrayList cl = new ArrayList();
      String[] var6;
      int var5 = (var6 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String f = var6[var4];
         cl.add(f);
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
