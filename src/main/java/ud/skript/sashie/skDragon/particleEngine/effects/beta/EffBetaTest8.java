package ud.skript.sashie.skDragon.particleEngine.effects.beta;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;

public class EffBetaTest8 extends Effect {
   private Expression idName;
   private Expression location;

   static {
      Skript.registerEffect(EffBetaTest8.class, "drawBetaTest6 at %location%, id %string%");
   }

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.location = exprs[0];
      this.idName = exprs[1];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawBetaTest6 at %location%, id %string%";
   }

   protected void execute(@Nullable Event e) {
      String idName = (String)this.idName.getSingle(e);
      World world = ((Location)this.location.getSingle(e)).getWorld();
      double locX = ((Location)this.location.getSingle(e)).getX();
      double locY = ((Location)this.location.getSingle(e)).getY();
      double locZ = ((Location)this.location.getSingle(e)).getZ();
      Location test = (Location)this.location.getSingle(e);
      Bukkit.getServer().broadcastMessage("[skDragon] skriptLoc" + test);
      Location location = new Location(world, locX, locY, locZ);
      Bukkit.getServer().broadcastMessage("[skDragon actual test] " + location);
      EffectsLib.drawStatCircle(location, idName);
   }
}
