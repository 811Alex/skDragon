package ud.skript.sashie.skDragon.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import eu.phiwa.dragontravel.api.events.DragonPlayerDismountEvent;
import eu.phiwa.dragontravel.api.events.DragonPostPlayerMountEvent;
import eu.phiwa.dragontravel.api.events.DragonPrePlayerMountEvent;
import eu.phiwa.dragontravel.core.movement.DragonType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import ud.skript.sashie.skDragon.emotes.EmoteEndedEvent;
import ud.skript.sashie.skDragon.emotes.EmoteStartedEvent;

public class Events {
   public static void emoteEvents() {
      Skript.registerEvent("emote start", SimpleEvent.class, EmoteStartedEvent.class, "emote start");
      EventValues.registerEventValue(EmoteStartedEvent.class, Entity.class, new Getter() {
         public Entity get(Object e) {
            return ((EmoteStartedEvent)e).getEntity();
         }
      }, 0);
      EventValues.registerEventValue(EmoteStartedEvent.class, String.class, new Getter() {
         public String get(Object e) {
            return ((EmoteStartedEvent)e).getEmoteType();
         }
      }, 0);
      Skript.registerEvent("emote stop", SimpleEvent.class, EmoteEndedEvent.class, "emote stop");
      EventValues.registerEventValue(EmoteEndedEvent.class, Entity.class, new Getter() {
         public Entity get(Object e) {
            return ((EmoteEndedEvent)e).getEntity();
         }
      }, 0);
   }

   public static void dragonTravelEvents() {
      Skript.registerEvent("DragonTravel dismount", SimpleEvent.class, DragonPlayerDismountEvent.class, "DragonTravel dismount");
      Skript.registerEvent("DragonTravel post mount", SimpleEvent.class, DragonPostPlayerMountEvent.class, "DragonTravel post mount");
      Skript.registerEvent("DragonTravel pre mount", SimpleEvent.class, DragonPrePlayerMountEvent.class, "DragonTravel pre mount");
   }

   public static void dragonTravelValues() {
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, Player.class, new Getter() {
         public Player get(Object e) {
            return ((DragonPrePlayerMountEvent)e).getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, DragonType.class, new Getter() {
         public DragonType get(Object e) {
            return ((DragonPrePlayerMountEvent)e).getDragonType();
         }
      }, 0);
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, Entity.class, new Getter() {
         public Entity get(Object e) {
            return ((DragonPrePlayerMountEvent)e).getDragon().getEntity();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, Player.class, new Getter() {
         public Player get(Object e) {
            return ((DragonPostPlayerMountEvent)e).getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, DragonType.class, new Getter() {
         public DragonType get(Object e) {
            return ((DragonPostPlayerMountEvent)e).getDragonType();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, Entity.class, new Getter() {
         public Entity get(Object e) {
            return ((DragonPostPlayerMountEvent)e).getDragon().getEntity();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Player.class, new Getter() {
         public Player get(Object e) {
            return ((DragonPlayerDismountEvent)e).getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Location.class, new Getter() {
         public Location get(Object e) {
            return ((DragonPlayerDismountEvent)e).getDismountLoc();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Entity.class, new Getter() {
         public Entity get(Object e) {
            return ((DragonPlayerDismountEvent)e).getDragon().getEntity();
         }
      }, 0);
   }
}
