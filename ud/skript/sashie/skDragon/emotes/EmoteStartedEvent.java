package ud.skript.sashie.skDragon.emotes;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EmoteStartedEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private boolean cancelled;
   private Entity entity;
   private String emotetype;

   public EmoteStartedEvent(String emotetype, Entity entity) {
      this.emotetype = emotetype;
      this.entity = entity;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public void setEntity(Entity entity) {
      this.entity = entity;
   }

   public String getEmoteType() {
      return this.emotetype;
   }

   public void setEmoteType(String emote) {
      this.emotetype = emote;
   }
}
