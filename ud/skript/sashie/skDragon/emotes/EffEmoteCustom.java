package ud.skript.sashie.skDragon.emotes;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Emotes - Custom")
@Description({"Using the names in the CustomEmotes.yml file you can play any custom emote or animation sequence you've made"})
@Syntaxes({"custom emote %string% on %entity%[with [frame] delay %-number%][ [and] repeat[ing] %-number% [time[s]]]"})
@Examples({"custom emote \"test\" on player", "custom emote \"test\" on target entity"})
public class EffEmoteCustom extends Effect {
   private Expression name;
   private Expression entity;
   private Expression pulseTicks;
   private Expression repeat;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.name = exprs[0];
      this.entity = exprs[1];
      this.pulseTicks = exprs[2];
      this.repeat = exprs[3];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "emote: " + this.name.toString(e, debug) + " entity: " + this.entity.toString(e, debug) + " ticks: " + (this.pulseTicks != null ? this.pulseTicks.toString(e, debug) : "0") + " repeats: " + (this.repeat != null ? this.repeat.toString(e, debug) : "0");
   }

   protected void execute(@Nullable Event e) {
      LivingEntity ent = (LivingEntity)this.entity.getSingle(e);
      CustomEmote emote = SkullConfig.getEmote((String)this.name.getSingle(e));
      SkullEffectsLib.playEmote(emote, ent, SkriptHandler.inputInt(0, e, this.repeat), SkriptHandler.inputLong(0, e, this.pulseTicks));
   }
}
