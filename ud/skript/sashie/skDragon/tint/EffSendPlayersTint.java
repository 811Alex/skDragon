package ud.skript.sashie.skDragon.tint;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
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
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      int fadeTime = SkriptHandler.inputInt(5, e, this.inputFadeTime);
      int intensity = SkriptHandler.inputInt(2, e, this.inputIntensity);
      boolean damageMode = SkriptHandler.inputBoolean(true, e, this.inputDamageMode);
      Player p;
      int percentage;
      if (players != null) {
         for(Iterator var7 = players.iterator(); var7.hasNext(); TintAPI.tintUtils.sendBorder(p, percentage, fadeTime, intensity)) {
            p = (Player)var7.next();
            int health = TintAPI.tintUtils.getPlayerHealth(p);
            int maxhealth = TintAPI.tintUtils.getMaxPlayerHealth(p);
            if (damageMode) {
               health = (int)p.getLastDamage();
               percentage = 100 - health * 100 / maxhealth;
            } else {
               health -= (int)p.getLastDamage();
               percentage = health * 100 / maxhealth;
            }
         }
      }

   }
}
