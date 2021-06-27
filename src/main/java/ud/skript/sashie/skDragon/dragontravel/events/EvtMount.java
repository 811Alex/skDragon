package ud.skript.sashie.skDragon.dragontravel.events;

import eu.phiwa.dragontravel.api.events.DragonPostPlayerMountEvent;
import eu.phiwa.dragontravel.api.events.DragonPrePlayerMountEvent;
import eu.phiwa.dragontravel.core.hooks.server.IRyeDragon;
import eu.phiwa.dragontravel.core.movement.DragonType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EvtMount implements Listener {
   @EventHandler
   public void onPreMount(DragonPrePlayerMountEvent event) {
      DragonPrePlayerMountEvent e = new DragonPrePlayerMountEvent(event.getPlayer().getPlayer(), event.getDragon(), event.getDragonType());
      Bukkit.getServer().getPluginManager().callEvent(e);
      if (e.isCancelled()) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void onPostMount(DragonPostPlayerMountEvent event) {
      DragonPostPlayerMountEvent e = new DragonPostPlayerMountEvent(event.getPlayer(), null, null);
      Bukkit.getServer().getPluginManager().callEvent(e);
   }
}
