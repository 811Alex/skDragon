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

@Name("Emotes - Built in")
@Description({"Using the names in the CustomEmotes.yml file you can play any custom emote or animation sequence you've made"})
@Syntaxes({"emote %emotetype% on %entity% [with [frame] delay %-number%] [[and] repeat[ing] %-number% [time[s]]]"})
@Examples({"emote smile on player", "emote cheeky on target entity", "emote santa wink on player with frame delay 1 and repeating 2 times", "emote rage on player with frame delay 2", "emote tan on player repeating 1 time"})
public class EffEmoteType extends Effect {
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
      CustomEmote emote = ((EmoteType)this.name.getSingle(e)).initEmote();
      SkullEffectsLib.playEmote(emote, ent, SkriptHandler.inputInt(0, e, this.repeat), SkriptHandler.inputLong(0, e, this.pulseTicks));
   }
}
