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

public class EffToggleTint extends Effect {
   private Expression inputPlayers;
   private Expression inputFadeTime;
   private Expression inputIntensity;
   private Expression inputMinHealth;
   private Expression inputDamageMode;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.inputPlayers = exprs[0];
      this.inputFadeTime = exprs[1];
      this.inputIntensity = exprs[2];
      this.inputMinHealth = exprs[3];
      this.inputDamageMode = exprs[4];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "toggle[ red] tint for %players%[ with fadeTime %-number%, intensity %-number% and damageMode %-boolean%]";
   }

   protected void execute(@Nullable Event e) {
      List players = SkriptHandler.inputPlayers(e, this.inputPlayers);
      SkriptHandler.inputInt(5, e, this.inputFadeTime);
      SkriptHandler.inputInt(2, e, this.inputIntensity);
      SkriptHandler.inputInt(2, e, this.inputMinHealth);
      SkriptHandler.inputBoolean(true, e, this.inputDamageMode);
      Iterator var8 = players.iterator();

      while(var8.hasNext()) {
         Player p = (Player)var8.next();
         TintAPI.tintUtils.togglePlayerTint(p);
      }

   }
}
