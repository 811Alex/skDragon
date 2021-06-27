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

@Name("Stop a particle effect")
@Description({"Stops a single particle effect or a list of them"})
@Syntaxes({"stopEffect[ id] %strings%"})
@Examples({"stopEffect id \"%player%\"", "stopEffect id {_list::*}"})
public class EffStopEffectID extends Effect {
   private Expression name;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.name = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "stopEffect";
   }

   protected void execute(@Nullable Event e) {
      String[] idName = (String[])this.name.getAll(e);
      String[] var6 = idName;
      int var5 = idName.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String id = var6[var4];
         EffectsLib.stopItemEffect(id);
         EffectsLib.stopEffect2(id);
         EffectsLib.stopEffect(id);
      }

   }
}
