package ud.skript.sashie.skDragon.emotes;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Emotes - Stop")
@Description({"Stops an emote effect"})
@Syntaxes({"stop emote %entity%"})
@Examples({"stop emote player", "stop emote target entity"})
public class EffStopEmote extends Effect {
   private Expression ent;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.ent = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "stop emote";
   }

   protected void execute(@Nullable Event e) {
      SkullEffectsLib.stopEmote((LivingEntity)this.ent.getSingle(e));
   }
}
