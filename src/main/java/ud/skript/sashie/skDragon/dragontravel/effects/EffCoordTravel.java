package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.api.DragonException;
import eu.phiwa.dragontravel.core.DragonManager;
import javax.annotation.Nullable;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Take to coordinates")
@Description({"Takes player to the selected coordinates in the selected world"})
@Syntaxes({"take %player% to[ coords] %number% %number% %number% in[ world] %string%"})
@Examples({"take player to coords 0, 0, 0 in world \"world\""})
public class EffCoordTravel extends Effect {
   private Expression world;
   private Expression z;
   private Expression y;
   private Expression x;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.world = exprs[4];
      this.z = exprs[3];
      this.y = exprs[2];
      this.x = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% to[ coords] %number% %number% %number% in[ world] %string%";
   }

   @Nullable
   protected void execute(Event e) {
      String w = (String)this.world.getSingle(e);
      Long zz = (Long)this.z.getSingle(e);
      Long yy = (Long)this.y.getSingle(e);
      Long xx = (Long)this.x.getSingle(e);
      Integer z = zz != null ? zz.intValue() : null;
      Integer y = yy != null ? yy.intValue() : null;
      Integer x = xx != null ? xx.intValue() : null;
      Player p = (Player)this.player.getSingle(e);
      if (z != null) {
         if (y != null) {
            if (x != null) {
               if (w == null) {
                  World nullWorld = p.getWorld();
                  w = nullWorld.getName();
               }

               try {
                  DragonManager.getDragonManager().getTravelEngine().toCoordinates(p, x, y, z, w, true);
               } catch (DragonException var11) {
                  Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
                  var11.printStackTrace();
               }

            }
         }
      }
   }
}
