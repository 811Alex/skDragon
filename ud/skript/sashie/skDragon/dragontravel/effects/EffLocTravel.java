package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.dragontravel.api.DragonException;
import eu.phiwa.dragontravel.core.DragonManager;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Take to location")
@Description({"Takes player to the selected location in the selected world"})
@Syntaxes({"take %player% to %location% in[ world] %string%"})
@Examples({""})
public class EffLocTravel extends Effect {
   private Expression world;
   private Expression location;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.world = exprs[2];
      this.location = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% to %location% in[ world] %string%";
   }

   @Nullable
   protected void execute(Event e) {
      String w = (String)this.world.getSingle(e);
      double locX = ((Location)this.location.getSingle(e)).getX();
      double locY = ((Location)this.location.getSingle(e)).getY();
      double locZ = ((Location)this.location.getSingle(e)).getZ();
      int x = (int)locX;
      int y = (int)locY;
      int z = (int)locZ;
      Player p = (Player)this.player.getSingle(e);
      if (this.location.getSingle(e) != null) {
         if (w == null) {
            World nullWorld = p.getWorld();
            w = nullWorld.getName();
         }

         try {
            DragonManager.getDragonManager().getTravelEngine().toCoordinates(p, x, y, z, w, true);
         } catch (DragonException var14) {
            Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
            var14.printStackTrace();
         }

      }
   }
}
