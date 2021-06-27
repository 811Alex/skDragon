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
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;

public class EffBetaTest1 extends Effect {
   private Expression part;
   private Expression player;
   private Expression idName;

   static {
      Skript.registerEffect(EffBetaTest1.class, "drawBetaTest1 particle %particlename%, player %player%, id %string%");
   }

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.part = exprs[0];
      this.player = exprs[1];
      this.idName = exprs[2];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawBetaTest1 particle %particlename%, player %player%, id %string%";
   }

   protected void execute(@Nullable Event e) {
      Player p = (Player)this.player.getSingle(e);
      String idName = (String)this.idName.getSingle(e);
      ParticleEffect particleName = (ParticleEffect)this.part.getSingle(e);
      EffectsLib.drawBetaTest6(particleName, p, idName);
   }
}
