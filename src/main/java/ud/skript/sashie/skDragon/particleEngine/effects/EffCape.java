package ud.skript.sashie.skDragon.particleEngine.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.io.File;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.Cape;
import ud.skript.sashie.skDragon.particleEngine.utils.DynamicLocation;
import ud.skript.sashie.skDragon.particleEngine.utils.SkriptHandler;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("drawCape")
@Description({"Draws a cape on a players back using approx. 8x12 pixel images"})
@Syntaxes({"drawCape file %string%, center %object%, id %string%[, isClientside %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, dyn[amic]Mode %-boolean%][, scale %-number%][, backDis[tance] %-number%][, gravity %-number%], visibleRange %number%[, pulseDelay %-number%]"})
@Examples({"drawCape file \"superman.png\", center player, id \"%player%\", rainbowMode false, dynamicMode true, visibleRange 32, pulseDelay 1"})
public class EffCape extends Effect {
   private Expression fileName;
   private Expression entLoc;
   private Expression idName;
   private Expression singlePlayer;
   private Expression player;
   private Expression rainbMode;
   private Expression dynMode;
   private Expression inputScale;
   private Expression inputBack;
   private Expression inputGrav;
   private Expression range;
   private Expression ticks;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      int i = 0;
      this.fileName = exprs[i++];
      this.entLoc = exprs[i++];
      this.idName = exprs[i++];
      this.singlePlayer = exprs[i++];
      this.player = exprs[i++];
      this.rainbMode = exprs[i++];
      this.dynMode = exprs[i++];
      this.inputScale = exprs[i++];
      this.inputBack = exprs[i++];
      this.inputGrav = exprs[i++];
      this.range = exprs[i++];
      this.ticks = exprs[i++];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "drawCape file %string%, center %entity/location%, id %string%[, isClientside %-boolean%, %-player%][, r[ainbow]M[ode] %-boolean%][, dyn[amic]Mode %-boolean%][, scale %-number%][, backDis[tance] %-number%][, gravity %-number%], visibleRange %number%[, pulseDelay %-number%]";
   }

   protected void execute(@Nullable Event e) {
      String finalFileName = null;
      File file = null;
      if (this.fileName != null) {
         finalFileName = (String)this.fileName.getSingle(e);
         if (!finalFileName.startsWith(File.pathSeparator)) {
            file = new File(skDragonCore.skdragoncore.getDataFolder() + File.separator + "capes", finalFileName);
         } else {
            file = new File(finalFileName);
         }
      }

      String idName = (String)this.idName.getSingle(e);
      Player p = SkriptHandler.inputPlayer(e, this.player);
      boolean isSinglePlayer = SkriptHandler.inputBoolean(false, e, this.singlePlayer);
      boolean rainbowMode = SkriptHandler.inputBoolean(false, e, this.rainbMode);
      boolean dynamicCape = SkriptHandler.inputBoolean(false, e, this.dynMode);
      int scale = SkriptHandler.inputInt(8, e, this.inputScale);
      float back = SkriptHandler.inputFloat(0.0F, e, this.inputBack);
      float gravity = SkriptHandler.inputFloat(0.0F, e, this.inputGrav);
      double visibleRange = SkriptHandler.inputDouble(32.0D, e, this.range);
      long finalDelayPulse = SkriptHandler.inputLong(0, e, this.ticks);

      DynamicLocation center;
      try {
         center = DynamicLocation.init(this.entLoc.getSingle(e));
      } catch (IllegalArgumentException var18) {
         return;
      }

      Cape cape = new Cape(file, center, idName, scale);
      cape.drawEffect(isSinglePlayer, p, rainbowMode, dynamicCape, back, gravity, visibleRange, 1L, finalDelayPulse);
   }
}
