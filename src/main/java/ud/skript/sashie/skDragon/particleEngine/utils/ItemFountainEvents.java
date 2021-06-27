package ud.skript.sashie.skDragon.particleEngine.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import ud.skript.sashie.skDragonCore;

public class ItemFountainEvents implements Listener {
   @EventHandler
   public void onItemMerge(ItemMergeEvent event) {
      if (skDragonCore.notInUseItems.contains(event.getEntity().getUniqueId())) {
         event.setCancelled(true);
      }

   }
}
