package ud.skript.sashie.skDragon.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import eu.phiwa.dragontravel.api.events.DragonPlayerDismountEvent;
import eu.phiwa.dragontravel.api.events.DragonPostPlayerMountEvent;
import eu.phiwa.dragontravel.api.events.DragonPrePlayerMountEvent;
import eu.phiwa.dragontravel.core.movement.DragonType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValue;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValueRegistry;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.emotes.EmoteEndedEvent;
import ud.skript.sashie.skDragon.emotes.EmoteStartedEvent;

public class Events {
   public static void emoteEvents() {
      Skript.registerEvent("emote start", SimpleEvent.class, EmoteStartedEvent.class, "emote start");
      EventValueRegistry eventValues = skDragonCore.getAddonInstance().registry(EventValueRegistry.class);
      eventValues.register(EventValue.builder(EmoteStartedEvent.class, Entity.class)
         .getter(EmoteStartedEvent::getEntity)
         .build());
      eventValues.register(EventValue.builder(EmoteStartedEvent.class, String.class)
         .getter(EmoteStartedEvent::getEmoteType)
         .build());
      Skript.registerEvent("emote stop", SimpleEvent.class, EmoteEndedEvent.class, "emote stop");
      eventValues.register(EventValue.builder(EmoteEndedEvent.class, Entity.class)
         .getter(EmoteEndedEvent::getEntity)
         .build());
   }

   public static void dragonTravelEvents() {
      Skript.registerEvent("DragonTravel dismount", SimpleEvent.class, DragonPlayerDismountEvent.class, "DragonTravel dismount");
      Skript.registerEvent("DragonTravel post mount", SimpleEvent.class, DragonPostPlayerMountEvent.class, "DragonTravel post mount");
      Skript.registerEvent("DragonTravel pre mount", SimpleEvent.class, DragonPrePlayerMountEvent.class, "DragonTravel pre mount");
   }

   public static void dragonTravelValues() {
      EventValueRegistry eventValues = skDragonCore.getAddonInstance().registry(EventValueRegistry.class);
      eventValues.register(EventValue.builder(DragonPrePlayerMountEvent.class, Player.class)
         .getter(e -> e.getPlayer().getPlayer())
         .build());
      eventValues.register(EventValue.builder(DragonPrePlayerMountEvent.class, DragonType.class)
         .getter(DragonPrePlayerMountEvent::getDragonType)
         .build());
      eventValues.register(EventValue.builder(DragonPrePlayerMountEvent.class, Entity.class)
         .getter(e -> e.getDragon().getEntity())
         .build());
      eventValues.register(EventValue.builder(DragonPostPlayerMountEvent.class, Player.class)
         .getter(e -> e.getPlayer().getPlayer())
         .build());
      eventValues.register(EventValue.builder(DragonPostPlayerMountEvent.class, DragonType.class)
         .getter(DragonPostPlayerMountEvent::getDragonType)
         .build());
      eventValues.register(EventValue.builder(DragonPostPlayerMountEvent.class, Entity.class)
         .getter(e -> e.getDragon().getEntity())
         .build());
      eventValues.register(EventValue.builder(DragonPlayerDismountEvent.class, Player.class)
         .getter(e -> e.getPlayer().getPlayer())
         .build());
      eventValues.register(EventValue.builder(DragonPlayerDismountEvent.class, Location.class)
         .getter(DragonPlayerDismountEvent::getDismountLoc)
         .build());
      eventValues.register(EventValue.builder(DragonPlayerDismountEvent.class, Entity.class)
         .getter(e -> e.getDragon().getEntity())
         .build());
   }
}
