package ud.skript.sashie.skDragon.dragontravel.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import eu.phiwa.com.sk89q.minecraft.util.commands.CommandContext;
import eu.phiwa.dragontravel.api.DragonException;
import eu.phiwa.dragontravel.core.DragonManager;
import eu.phiwa.dragontravel.core.DragonTravel;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("DragonTravel - Take to player")
@Description({" Takes player to a selected player", "Requires player to have option toggled in DragonTravel to allow this"})
@Syntaxes({"take %player% to player[ named] %player%"})
@Examples({""})
public class EffPlayerTravel extends Effect {
   private Expression targetPlayer;
   private Expression player;

   public boolean init(Expression[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
      this.targetPlayer = exprs[1];
      this.player = exprs[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean debug) {
      return "take %player% to player[ named] %player%";
   }

   protected void execute(Event e) {
      Player targetPlayer = (Player)this.targetPlayer.getSingle(e);
      Player p = (Player)this.player.getSingle(e);
      if (targetPlayer == null) {
         p.sendMessage(DragonTravel.getInstance().getMessagesHandler().getMessage("Messages.Travels.Error.PlayerNotOnline").replace("{playername}", ((CommandContext)targetPlayer).getString(0)));
      } else if (targetPlayer == p) {
         p.sendMessage(DragonTravel.getInstance().getMessagesHandler().getMessage("Messages.Travels.Error.CannotTravelToYourself"));
      } else if (!(Boolean)DragonTravel.getInstance().getDragonManager().getPlayerToggles().get(targetPlayer.getUniqueId())) {
         p.sendMessage(DragonTravel.getInstance().getMessagesHandler().getMessage("Messages.Travels.Error.TargetPlayerDoesnotAllowPTravel").replace("{playername}", ((CommandContext)targetPlayer).getString(0)));
      } else {
         try {
            DragonManager.getDragonManager().getTravelEngine().toPlayer(p, targetPlayer, true);
         } catch (DragonException var5) {
            Skript.warning("[skDragon] Error: Did it bop when it should have booped?");
            var5.printStackTrace();
         }

      }
   }
}
