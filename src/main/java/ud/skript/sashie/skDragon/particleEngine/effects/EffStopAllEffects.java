package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Stop all particle effects")
@Description({"Stops all running particle effects"})
@Syntaxes({"stop all particle effects"})
@Examples({"stop all particle effects"})
public class EffStopAllEffects extends Effect {
   public boolean init(Expression[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
      return true;
   }

   public String toString(@Nullable Event arg0, boolean arg1) {
      return "stop all particle effects";
   }

   protected void execute(Event e) {
      EffectsLib.stopAll();
   }
}
