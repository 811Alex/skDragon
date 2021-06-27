package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.registration.annotations.DontRegister;

@DontRegister
public abstract class DragonEffect extends Effect {
   private Boolean justReturn = false;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "";
   }

   protected abstract void exec(@Nullable Event var1);

   protected void execute(@Nullable Event e) {
      if (!this.justReturn) {
         this.exec(e);
      }
   }

   public void checkParticleProperty(String particle, String error, ParticleEffect.ParticleProperty property) {
      if (!ParticleEffect.valueOf(particle).hasProperty(property)) {
         Skript.warning(particle + " " + error);
         skDragonCore.error("This effect only accepts directional particle types");
         this.justReturn = true;
      }

   }

   public Expression checkParticle(Expression exprs, ParticleEffect.ParticleProperty property) {
      String error = "";
      if (property.equals(ParticleEffect.ParticleProperty.COLORABLE)) {
         error = "colorable ";
      } else if (property.equals(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         error = "directional ";
      } else if (property.equals(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         error = "material/data ";
      } else if (property.equals(ParticleEffect.ParticleProperty.USES_WATER)) {
         error = "water ";
      }

      if (!(exprs instanceof Variable) && !ParticleEffect.valueOf(exprs.toString()).hasProperty(property)) {
         Skript.warning("'" + exprs.toString() + "' is not a " + error + "particle type");
         skDragonCore.error("'" + exprs.toString() + "' is not a " + error + "particle type");
         this.justReturn = true;
      }

      return exprs;
   }

   public Expression checkParticle(Expression exprs) {
      if (!ParticleEffect.NAME_MAP.containsKey(exprs.toString().toLowerCase())) {
         Skript.warning("'" + exprs.toString() + "' is not a particle type");
         skDragonCore.error("'" + exprs.toString() + "' is not a particle type");
         this.justReturn = true;
      }

      return exprs;
   }

   public Expression checkLocation(Expression exprs) {
      if (exprs instanceof Variable) {
         exprs = exprs.getConvertedExpression(new Class[]{Location.class});
      }

      if (!Location.class.isAssignableFrom(exprs.getReturnType()) && !Entity.class.isAssignableFrom(exprs.getReturnType())) {
         Skript.warning("'" + exprs.toString() + "' is not a valid location or entity type");
         skDragonCore.error("'" + exprs.toString() + "' is not a valid location or entity type");
         this.justReturn = true;
      } else {
         this.justReturn = false;
      }

      return exprs;
   }
}
