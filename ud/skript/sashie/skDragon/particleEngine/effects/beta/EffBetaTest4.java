package ud.skript.sashie.skDragon.particleEngine.effects.beta;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;

public class EffBetaTest4 extends Effect {
   private Expression player;
   private Expression idName;

   static {
      Skript.registerEffect(EffBetaTest4.class, new String[]{"drawBetaTest4 player %player%, id %string%"});
   }

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.player = exprs[0];
      this.idName = exprs[1];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawBetaTest4 player %player%, id %string%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      EffectsLib.drawBetaTest3(p, idName);
   }
}
