package ud.skript.sashie.skDragon.emotes;

import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SkullEvents1_9 implements Listener {
   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void cancelMove(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if ((event.getSlot() < event.getInventory().getSize() || event.getRawSlot() < event.getInventory().getSize() || event.isShiftClick()) && event.getCursor() != null && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + player.getUniqueId().toString())) {
         event.setCancelled(true);
      }

      if (SkullEffectsLib.entityHasEmote(player)) {
         if (event.getInventory().getType() == InventoryType.PLAYER && event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + player.getUniqueId().toString())) {
            player.getInventory().remove(event.getCurrentItem());
            player.getInventory().removeItem(new ItemStack[]{event.getCurrentItem()});
            player.closeInventory();
            return;
         }

         if ((event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) {
            event.setCancelled(true);
            player.updateInventory();
            return;
         }

         if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + player.getUniqueId().toString())) {
            player.getInventory().remove(event.getCurrentItem());
            player.getInventory().removeItem(new ItemStack[]{event.getCurrentItem()});
            event.setCancelled(true);
            player.updateInventory();
            return;
         }

         if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.PLACE_ALL && event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + player.getUniqueId().toString())) {
            player.getInventory().remove(event.getCurrentItem());
            player.getInventory().removeItem(new ItemStack[]{event.getCurrentItem()});
            event.setCancelled(true);
            player.updateInventory();
            return;
         }
      }

   }

   @EventHandler
   public void cancelDrag(InventoryDragEvent event) {
      Iterator var3 = event.getNewItems().values().iterator();

      ItemStack item;
      do {
         if (!var3.hasNext()) {
            return;
         }

         item = (ItemStack)var3.next();
      } while(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().getDisplayName().equals("§b§lEMOTE-" + ((Player)event.getWhoClicked()).getUniqueId().toString()));

      event.setCancelled(true);
      ((Player)event.getWhoClicked()).updateInventory();
   }

   @EventHandler
   public void onDrop(PlayerDropItemEvent event) {
      if (event.getItemDrop().getItemStack().hasItemMeta() && event.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + event.getPlayer().getUniqueId().toString())) {
         event.getItemDrop().remove();
         event.getPlayer().updateInventory();
      }

   }

   @EventHandler
   public void onPlayerPickUpItem(PlayerPickupItemEvent event) {
      if (event.getItem().getItemStack() != null && event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().hasDisplayName() && event.getItem().getItemStack().getItemMeta().getDisplayName().equals("§b§lEMOTE-" + event.getPlayer().getUniqueId().toString())) {
         event.setCancelled(true);
         event.getItem().remove();
      }

   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      ItemStack[] var6;
      int var5 = (var6 = player.getInventory().getContents()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         ItemStack item = var6[var4];
         if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§b§lEMOTE-" + player.getUniqueId().toString())) {
            player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
            player.getInventory().removeItem(new ItemStack[]{item});
         }
      }

   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      SkullEffectsLib.stopEmote(event.getPlayer());
   }

   @EventHandler
   public void onWorldChange(PlayerChangedWorldEvent event) {
      SkullEffectsLib.stopEmote(event.getPlayer());
   }

   @EventHandler
   public void onEntityDeath(EntityDeathEvent event) {
      SkullEffectsLib.stopEmote(event.getEntity());
   }
}
