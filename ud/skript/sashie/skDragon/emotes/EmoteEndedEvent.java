package ud.skript.sashie.skDragon.emotes;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EmoteEndedEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private Entity entity;

   public EmoteEndedEvent(Entity entity) {
      this.entity = entity;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public void setEntity(Entity entity) {
      this.entity = entity;
   }
}
