package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

import javax.annotation.Nullable;

@Name("All supported particle names")
@Description({"Gets a list of all supported particle names on this version"})
@Syntaxes({"[all ]supported particle(s| names)"})
@Examples({"set {_var::*} to supported particles", "set {_var::*} to all supported particle names"})
public class ExprSupportedParticles extends SimpleExpression {
   @Nullable
   protected String[] get(Event e) {
      return ParticleEffect.getSupported().toArray(new String[0]);
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
