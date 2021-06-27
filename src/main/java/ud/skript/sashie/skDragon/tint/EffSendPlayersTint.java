package ud.skript.sashie.skDragon.tint;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Send Red Tint")
@Description({"Sends red tint to a player. (Needs graphics set to fancy or in optifine vignettes)"})
@Syntaxes({"send[ red] tint to %players%[ with fadeTime %-number%, intensity %-number% and damageMode %-boolean%]"})
@Examples({"send red tint to player", "send red tint to player with fadeTime 3, intensity 2 and damageMode true"})
public class EffSendPlayersTint extends Effect {
   private Expression inputPlayers;
   private Expression inputFadeTime;
   private Expression inputIntensity;
   private Expression inputDamageMode;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.inputPlayers = exprs[0];
      this.inputFadeTime = exprs[1];
      this.inputIntensity = exprs[2];
      this.inputDamageMode = exprs[3];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "send[ red] tint to %players%[ with fadeTime %number%, intensity %number% and damageMode %boolean%]";
   }

   protected void execute(@Nullable Event e) {
     throw new NotImplementedException("Tint is not supported on this hacky skDragon version!");
   }
}
