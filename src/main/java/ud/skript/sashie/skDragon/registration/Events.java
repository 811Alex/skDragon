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
      Skript.registerEvent("emote start", SimpleEvent.class, EmoteStartedEvent.class, new String[]{"emote start"});
      EventValues.registerEventValue(EmoteStartedEvent.class, Entity.class, new Getter() {
         public Entity get(EmoteStartedEvent e) {
            return e.getEntity();
         }
      }, 0);
      EventValues.registerEventValue(EmoteStartedEvent.class, String.class, new Getter() {
         public String get(EmoteStartedEvent e) {
            return e.getEmoteType();
         }
      }, 0);
      Skript.registerEvent("emote stop", SimpleEvent.class, EmoteEndedEvent.class, new String[]{"emote stop"});
      EventValues.registerEventValue(EmoteEndedEvent.class, Entity.class, new Getter() {
         public Entity get(EmoteEndedEvent e) {
            return e.getEntity();
         }
      }, 0);
   }

   public static void dragonTravelEvents() {
      Skript.registerEvent("DragonTravel dismount", SimpleEvent.class, DragonPlayerDismountEvent.class, new String[]{"DragonTravel dismount"});
      Skript.registerEvent("DragonTravel post mount", SimpleEvent.class, DragonPostPlayerMountEvent.class, new String[]{"DragonTravel post mount"});
      Skript.registerEvent("DragonTravel pre mount", SimpleEvent.class, DragonPrePlayerMountEvent.class, new String[]{"DragonTravel pre mount"});
   }

   public static void dragonTravelValues() {
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, Player.class, new Getter() {
         public Player get(DragonPrePlayerMountEvent e) {
            return e.getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, DragonType.class, new Getter() {
         public DragonType get(DragonPrePlayerMountEvent e) {
            return e.getDragonType();
         }
      }, 0);
      EventValues.registerEventValue(DragonPrePlayerMountEvent.class, Entity.class, new Getter() {
         public Entity get(DragonPrePlayerMountEvent e) {
            return e.getDragon().getEntity();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, Player.class, new Getter() {
         public Player get(DragonPostPlayerMountEvent e) {
            return e.getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, DragonType.class, new Getter() {
         public DragonType get(DragonPostPlayerMountEvent e) {
            return e.getDragonType();
         }
      }, 0);
      EventValues.registerEventValue(DragonPostPlayerMountEvent.class, Entity.class, new Getter() {
         public Entity get(DragonPostPlayerMountEvent e) {
            return e.getDragon().getEntity();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Player.class, new Getter() {
         public Player get(DragonPlayerDismountEvent e) {
            return e.getPlayer().getPlayer();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Location.class, new Getter() {
         public Location get(DragonPlayerDismountEvent e) {
            return e.getDismountLoc();
         }
      }, 0);
      EventValues.registerEventValue(DragonPlayerDismountEvent.class, Entity.class, new Getter() {
         public Entity get(DragonPlayerDismountEvent e) {
            return e.getDragon().getEntity();
         }
      }, 0);
   }
}
