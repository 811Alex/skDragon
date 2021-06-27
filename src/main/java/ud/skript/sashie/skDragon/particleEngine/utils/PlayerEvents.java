package ud.skript.sashie.skDragon.particleEngine.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import ud.skript.sashie.skDragonCore;

public class PlayerEvents implements Listener {
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (skDragonCore.UpdateCheck && skDragonCore.UpdateMsgOps && (player.isOp() || player.hasPermission("skdragon.updates"))) {
         String newVer = "";

         try {
            InputStream is = (new URL("http://pastebin.com/raw/jCpT9A9j")).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            newVer = br.readLine();
            if (!Objects.equals(skDragonCore.version, newVer)) {
               skDragonCore.sendMsg(player, "Hey " + player.getName() + " v" + newVer + " is available");

               String line;
               while((line = br.readLine()) != null) {
                  skDragonCore.sendMsg(player, line);
               }
            }

            br.close();
            is.close();
         } catch (Exception var7) {
            skDragonCore.sendExLog(var7.getCause().getMessage(), "skDragonCore", 0);
         }
      }

   }

   @EventHandler
   public void onPlayerMove(PlayerMoveEvent event) {
   }
}
