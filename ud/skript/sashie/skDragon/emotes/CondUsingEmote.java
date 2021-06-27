package ud.skript.sashie.skDragon.emotes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;

@Name("Is entity using emote")
@Description({"Checks if an entity is using an emote"})
@Examples({"if player is using an emote", "\t\tbroadcast \"player is using an emote\""})
public class CondUsingEmote extends Condition {
   private Expression entity;

   static {
      Skript.registerCondition(CondUsingEmote.class, new String[]{"[entity] %entity% (is using|has) [an] emote"});
   }

   public boolean check(Event event) {
      LivingEntity e = (LivingEntity)this.entity.getSingle(event);
      return SkullEffectsLib.entityHasEmote(e);
   }

   public String toString(@Nullable Event event, boolean debug) {
      return "entity " + this.entity.toString(event, debug) + " is using emote";
   }

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.entity = exprs[0];
      return true;
   }
}
