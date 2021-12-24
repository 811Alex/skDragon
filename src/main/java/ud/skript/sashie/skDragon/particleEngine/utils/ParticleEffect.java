package ud.skript.sashie.skDragon.particleEngine.utils;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;

public enum ParticleEffect {
   explosion("explosion", "18 -> 9: 0", ParticleProperty.DIRECTIONAL),
   explosionlarge("explosionlarge", "17 -> 9: 1"),
   explosionhuge("explosionhuge", "16 -> 9: 2"),
   fireworkspark("fireworkspark", "0 -> 9: 3", ParticleProperty.DIRECTIONAL),
   waterbubble("waterbubble", "9: 4", ParticleProperty.DIRECTIONAL, ParticleProperty.USES_WATER),
   watersplash("watersplash", "14 -> 9: 5", ParticleProperty.DIRECTIONAL),
   waterwake("waterwake", "9: 6", ParticleProperty.DIRECTIONAL),
   suspended("suspended", "9: 7", ParticleProperty.USES_WATER),
   suspenddepth("suspenddepth", "9: 8", ParticleProperty.DIRECTIONAL),
   crit("crit", "1 -> 9: 9", ParticleProperty.DIRECTIONAL),
   critmagic("critmagic", "2 -> 9: 10", ParticleProperty.DIRECTIONAL),
   smoke("smoke", "20 -> 9: 11", ParticleProperty.DIRECTIONAL),
   smokelarge("smokelarge", "31 -> 9: 12", ParticleProperty.DIRECTIONAL),
   spell("spell", "5 -> 9: 13"),
   spellinstant("spellinstant", "6 -> 9: 14"),
   mobspell("mobspell", "9: 15", ParticleProperty.COLORABLE),
   mobspellambient("mobspellambient", "9: 16", ParticleProperty.COLORABLE),
   witchspell("witchspell", "7 -> 9: 17"),
   waterdrip("waterdrip", "24 -> 9: 18"),
   lavadrip("lavadrip", "25 -> 9: 19"),
   angryvillager("angryvillager", "29 -> 9: 20"),
   happyvillager("happyvillager", "30 -> 9: 21", ParticleProperty.DIRECTIONAL),
   townaura("townaura", "9: 22", ParticleProperty.DIRECTIONAL),
   note("note", "8 -> 9: 23", ParticleProperty.COLORABLE),
   portal("portal", "9 -> 9: 24", ParticleProperty.DIRECTIONAL),
   enchantmenttable("enchantmenttable", "10 -> 9: 25", ParticleProperty.DIRECTIONAL),
   flame("flame", "11 -> 9: 26", ParticleProperty.DIRECTIONAL),
   lava("lava", "12 -> 9: 27"),
   footstep("footstep", "13 -> 9: 28 -> 13: -1"),
   cloud("cloud", "21 -> 9: 29 -> 13: 28", ParticleProperty.DIRECTIONAL),
   redstone("redstone", "22 -> 9: 30 -> 13: 29", ParticleProperty.COLORABLE),
   snowball("snowball", "23 -> 9: 31 -> 13: 30"),
   snowshovel("snowshovel", "26 -> 9: 32 -> 13: 31", ParticleProperty.DIRECTIONAL),
   slime("slime", "27 -> 9: 33 -> 13: 32"),
   heart("heart", "28 -> 9: 34 -> 13: 33"),
   barrier("barrier", "9: 35 -> 13: 34"),
   itemcrack("itemcrack", "32 -> 9: 36 -> 13: 35", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   blockcrack("blockcrack", "33 -> 9: 37 -> 13: 36", ParticleProperty.REQUIRES_DATA),
   blockdust("blockdust", "34 -> 9: 38 -> 13: 37", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   waterdrop("waterdrop", "24 -> 9: 39 -> 13: 38"),
   itemtake("itemtake", "9: 40 -> 13: -1"),
   mobappearance("mobappearance", "9: 41 -> 13: 39"),
   dragonbreath("dragonbreath", "9: 42 -> 13: 40", ParticleProperty.DIRECTIONAL),
   endrod("endrod", "9: 43 -> 13: 41", ParticleProperty.DIRECTIONAL),
   damage("damage", "9: 44 -> 13: 42", ParticleProperty.DIRECTIONAL),
   sweep("sweep", "9: 45 -> 13: 43"),
   fallingdust("fallingdust", "10: 46 -> 13: 44", ParticleProperty.REQUIRES_DATA),
   totem("totem", "11: 47 -> 13: 45", ParticleProperty.DIRECTIONAL),
   spit("spit", "11: 48 -> 13: 46", ParticleProperty.DIRECTIONAL),
   squidink("squidink", "13: 47", ParticleProperty.DIRECTIONAL),
   bubblepop("bubblepop", "13: 48", ParticleProperty.DIRECTIONAL),
   bubblecurrentdown("bubblecurrentdown", "13: 49", ParticleProperty.DIRECTIONAL),
   bubblecurrentup("bubblecurrentup", "13: 50", ParticleProperty.DIRECTIONAL),
   nautilus("nautilus", "13: 51", ParticleProperty.DIRECTIONAL),
   dolphin("dolphin", "13: 52", ParticleProperty.DIRECTIONAL),
   sneeze("sneeze", "14: 53", ParticleProperty.DIRECTIONAL),
   campfirecozy("campfirecozy", "14: 54", ParticleProperty.DIRECTIONAL),
   campfiresignal("campfiresignal", "14: 55", ParticleProperty.DIRECTIONAL),
   composter("composter", "14: 56", ParticleProperty.DIRECTIONAL),
   flash("flash", "14: 57", ParticleProperty.DIRECTIONAL),
   fallinglava("fallinglava", "14: 58", ParticleProperty.DIRECTIONAL),
   landinglava("landinglava", "14: 59", ParticleProperty.DIRECTIONAL),
   fallingwater("fallingwater", "14: 60", ParticleProperty.DIRECTIONAL),
   drippinghoney("drippinghoney", "15: 61", ParticleProperty.DIRECTIONAL),
   fallinghoney("fallinghoney", "15: 62", ParticleProperty.DIRECTIONAL),
   landinghoney("landinghoney", "15: 63", ParticleProperty.DIRECTIONAL),
   fallingnectar("fallingnectar", "15: 64", ParticleProperty.DIRECTIONAL),
   soulfireflame("soulfireflame", "16: 65", ParticleProperty.DIRECTIONAL),
   ash("ash", "16: 66", ParticleProperty.DIRECTIONAL),
   crimsonspore("crimsonspore", "16: 67", ParticleProperty.DIRECTIONAL),
   warpedspore("warpedspore", "16: 68", ParticleProperty.DIRECTIONAL),
   soul("soul", "16: 69", ParticleProperty.DIRECTIONAL),
   drippingtear("drippingtear", "16: 70", ParticleProperty.DIRECTIONAL),
   fallingtear("fallingtear", "16: 71", ParticleProperty.DIRECTIONAL),
   landingtear("landingtear", "16: 72", ParticleProperty.DIRECTIONAL),
   reverseportal("reverseportal", "16: 73", ParticleProperty.DIRECTIONAL),
   whiteash("whiteash", "16: 74", ParticleProperty.DIRECTIONAL),
   light("light", "17: 75"),
   dustcolortransition("dustcolortransition", "17: 76"),
   vibration("vibration", "17: 77"),
   fallingsporeblossom("fallingsporeblossom", "17: 78"),
   sporeblossomair("sporeblossomair", "17: 79"),
   smallflame("smallflame", "17: 80"),
   snowflake("snowflake", "17: 81"),
   drippingdripstonelava("drippingdripstonelava", "17: 82"),
   fallingdripstonelava("fallingdripstonelava", "17: 83"),
   drippingdripstonewater("drippingdripstonewater", "17: 84"),
   fallingdripstonewater("fallingdripstonewater", "17: 85"),
   glowsquidink("glowsquidink", "17: 86"),
   glow("glow", "17: 87"),
   waxon("waxon", "17: 88"),
   waxoff("waxoff", "17: 89"),
   electricspark("electricspark", "17: 90"),
   scrape("scrape", "17: 91"),
   legacyblockcrack("legacyblockcrack", "13: 53 -> 14: 61 -> 15: 65 -> 16: 75 -> 17: 92", ParticleProperty.REQUIRES_DATA),
   legacyblockdust("legacyblockdust", "13: 54 -> 14: 62 -> 15: 66 -> 16: 76 -> 17: 93", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   legacyfallingdust("legacyfallingdust", "13: 55 -> 14: 63 -> 15: 67 -> 16: 77 -> 17: 94", ParticleProperty.REQUIRES_DATA),
   NULL("null", "99");

   public static final Map<String, ParticleEffect> NAME_MAP = new HashMap<>();
   private final int version = ReflectionUtils.PackageType.getServerVersionMinor();
   private final String name;
   private final Map<Integer, Integer> IDs;
   private final Optional<Integer> id;
   private final List properties;

   static {
      ParticleEffect[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         ParticleEffect effect = var3[var1];
         NAME_MAP.put(effect.name, effect);
      }
   }

   ParticleEffect(String name, String IDMap, ParticleEffect.ParticleProperty... properties) {
      this.name = name;
      this.properties = Arrays.asList(properties);
      this.IDs = Arrays.stream(ID.parse(IDMap)).collect(Collectors.toMap(ID::getVersion, ID::getId));
      this.id = getIDOptional();
   }

   public String getName() {
      return this.name;
   }

   public Optional<Integer> getIDOptional(){
      return IDs.entrySet().stream()
              .filter(entry -> entry.getKey() <= version)   // is/was supported
              .max(Entry.comparingByKey())                  // get most recent
              .map(Entry::getValue)                         // get actual ID
              .filter(n -> n >= 0);                         // not deleted
   }

   public int getID() {
      return id.orElseThrow(() -> new UnsupportedOperationException("Particle not available in this version!")) + (version < 9 ? 17 : 0); // in < 9, they're in the Effect class, together with sounds etc.
   }

   public int getMinVersion() {
      return IDs.keySet().stream().reduce(Integer::min).orElseThrow();
   }

   public int getMaxVersion() {
      Integer ver = IDs.keySet().stream().reduce(Integer::max).orElseThrow();
      return ver - (IDs.get(ver) < 0 ? -1 : 0); // if deleted, return previous version
   }

   public boolean isSupported() {
      return id.isPresent();
   }

   public static List<String> getSupported(){
      return Arrays.stream(ParticleEffect.values())
              .filter(ParticleEffect::isSupported)
              .map(ParticleEffect::getName)
              .filter(n -> !n.equals("null"))
              .collect(Collectors.toList());
   }

   public boolean hasProperty(ParticleEffect.ParticleProperty property) {
      return this.properties.contains(property);
   }

   public List getProperties() {
      return this.properties;
   }
   public static ParticleEffect fromName(String name) {
      Iterator var2 = NAME_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         if (((String)entry.getKey()).equalsIgnoreCase(name)) {
            return (ParticleEffect)entry.getValue();
         }
      }

      return null;
   }

   private static boolean isLongDistance(Location location, List players) {
      String world = location.getWorld().getName();
      Iterator var4 = players.iterator();

      Location playerLocation;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         Player player = (Player)var4.next();
         playerLocation = player.getLocation();
      } while(!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536.0D);

      return true;
   }

   private static boolean isLongDistance(Location location, Player player) {
      String world = location.getWorld().getName();
      Location playerLocation = player.getLocation();
      return world.equals(playerLocation.getWorld().getName()) && !(playerLocation.distanceSquared(location) < 65536.0D);
   }

   private static boolean isDataCorrect(ParticleEffect effect, ParticleEffect.ParticleData data) {
      return (effect == blockcrack || effect == blockdust || effect == fallingdust) && data instanceof ParticleEffect.BlockData || effect == itemcrack && data instanceof ParticleEffect.ItemData;
   }

   private static boolean isColorCorrect(ParticleEffect effect, ParticleEffect.ParticleColor color) {
      if (ParticleEffect.ParticlePacket.getVersion() < 13) {
         return (effect == mobspell || effect == mobspellambient || effect == redstone) && color instanceof ParticleEffect.OrdinaryColor || effect == note && color instanceof ParticleEffect.NoteColor;
      } else {
         return (effect == mobspell || effect == mobspellambient) && color instanceof ParticleEffect.OrdinaryColor || effect == redstone && color instanceof ParticleEffect.RedstoneColor || effect == note && color instanceof ParticleEffect.NoteColor;
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, null)).sendTo(center, range);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)).sendTo(center, players);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player player) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, player), null)).sendTo(center, player);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      this.display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
   }

   public void display(Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         throw new IllegalArgumentException("This particle effect is not directional");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, true, null)).sendTo(center, range);
      }
   }

   public void display(Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         throw new IllegalArgumentException("This particle effect is not directional");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), null)).sendTo(center, players);
      }
   }

   public void display(Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      this.display(direction, speed, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleColor color, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, color)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, color, true)).sendTo(center, range);
      }
   }

   public void display(ParticleEffect.ParticleColor color, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, color)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, color, isLongDistance(center, players))).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleColor color, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      this.display(color, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, data)).sendTo(center, range);
      }
   }

   public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      this.display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, true, data)).sendTo(center, range);
      }
   }

   public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), data)).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      this.display(data, direction, speed, center, Arrays.asList(players));
   }

   public void display(String idName, Material dataMat, byte dataID, Player player, Location center, double visibleRange, boolean isSinglePlayer, boolean rainbowMode, float hue, float offsetX, float offsetY, float offsetZ, float speed, int particleCount) {
      if (!this.isSupported()) {
         EffectsLib.stopEffect(idName);
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else {
         if (this != redstone && this != mobspell && this != mobspellambient) {
            ParticleEffect.NoteColor finalData;
            if (this == note) {
               if (rainbowMode) {
                  finalData = new ParticleEffect.NoteColor((int)hue);
                  if (isSinglePlayer) {
                     this.display(finalData, center, player);
                  } else {
                     this.display(finalData, center, visibleRange);
                  }
               } else if (isSinglePlayer) {
                  this.display(new ParticleEffect.NoteColor((int)offsetX), center, player);
               } else {
                  this.display(new ParticleEffect.NoteColor((int)offsetX), center, visibleRange);
               }
            } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
               if ((this == fallingdust || this == blockcrack || this == blockdust) && dataMat != null) {
                  finalData = null;
                  ParticleEffect.BlockData finalData2;
                  if (skDragonCore.serverVersion >= 14) {
                     finalData2 = new ParticleEffect.BlockData(dataMat);
                  } else {
                     finalData2 = new ParticleEffect.BlockData(dataMat, dataID);
                  }

                  if (isSinglePlayer) {
                     this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, player);
                  } else {
                     this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
                  }
               } else if (this == itemcrack && dataMat != null) {
                  finalData = null;
                  ParticleEffect.ItemData finalData3;
                  if (skDragonCore.serverVersion >= 14) {
                     finalData3 = new ParticleEffect.ItemData(dataMat);
                  } else {
                     finalData3 = new ParticleEffect.ItemData(dataMat, dataID);
                  }

                  if (isSinglePlayer) {
                     this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, (player));
                  } else {
                     this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
                  }
               }
            } else if (isSinglePlayer) {
               this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, player);
            } else {
               this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
            }
         } else {
            int ir;
            if (rainbowMode) {
               ir = Color.HSBtoRGB(hue, 1.0F, 1.0F);
               float r = (float)(ir >> 16 & 255) / 255.0F;
               float g = (float)(ir >> 8 & 255) / 255.0F;
               float b = (float)(ir & 255) / 255.0F;
               r = r == 0.0F ? 0.001F : r;
               if (this == redstone) {
                  ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
                  if (isSinglePlayer) {
                     this.display(color, center, player);
                  } else {
                     this.display(color, center, visibleRange);
                  }
               } else if (isSinglePlayer) {
                  this.display(r, g, b, 1.0F, 0, center, player);
               } else {
                  this.display(r, g, b, 1.0F, 0, center, visibleRange);
               }
            } else {
               ir = Math.round(offsetX);
               int g = Math.round(offsetY);
               int b = Math.round(offsetZ);
               if (this == redstone) {
                  ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(ir, g, b), 1.0F);
                  if (isSinglePlayer) {
                     this.display(color, center, (player));
                  } else {
                     this.display(color, center, visibleRange);
                  }
               } else if (isSinglePlayer) {
                  this.display(new ParticleEffect.OrdinaryColor(ir, g, b), center, (player));
               } else {
                  this.display(new ParticleEffect.OrdinaryColor(ir, g, b), center, visibleRange);
               }
            }
         }

      }
   }

   public void display(String idName, Material dataMat, byte dataID, Player player, Location center, double visibleRange, boolean isSinglePlayer, boolean rainbowMode, float hue, double offsetX, double offsetY, double offsetZ, float speed, int particleCount) {
      this.display(idName, dataMat, dataID, player, center, visibleRange, isSinglePlayer, rainbowMode, hue, (float)offsetX, (float)offsetY, (float)offsetZ, speed, particleCount);
   }

   public static float simpleRainbowHelper(float hue, String particle) {
      if (particle.equals(note.getName())) {
         if (hue >= 24.0F) {
            hue = 0.0F;
         }

         ++hue;
      } else if (particle.equals(redstone.getName()) || particle.equals(mobspell.getName()) || particle.equals(mobspellambient.getName())) {
         if (hue >= 1.0F) {
            hue = 0.0F;
         }

         hue = (float)((double)hue + 0.01D);
      }

      return hue;
   }

   public static float simpleRainbowHelper(float hue, ParticleEffect particle) {
      if (particle.equals(note)) {
         if (hue >= 24.0F) {
            hue = 0.0F;
         }

         ++hue;
      } else if (particle.equals(redstone) || particle.equals(mobspell) || particle.equals(mobspellambient)) {
         if (hue >= 1.0F) {
            hue = 0.0F;
         }

         hue = (float)((double)hue + 0.01D);
      }

      return hue;
   }

   public void display(Player player, Location center, double visibleRange, boolean isSinglePlayer, float hue) {
      if (this == redstone || this == mobspell || this == mobspellambient) {
         int argb = Color.HSBtoRGB(hue / 20.0F, 1.0F, 1.0F);
         float r = (float)(argb >> 16 & 255) / 255.0F;
         float g = (float)(argb >> 8 & 255) / 255.0F;
         float b = (float)(argb & 255) / 255.0F;
         r = r == 0.0F ? 0.001F : r;
         if (this == redstone) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            if (isSinglePlayer) {
               this.display(color, center, (player));
            } else {
               this.display(color, center, visibleRange);
            }
         } else if (isSinglePlayer) {
            this.display(r, g, b, 1.0F, 0, center, player);
         } else {
            this.display(r, g, b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, boolean isSinglePlayer, Player player, boolean rainbowMode, float hue, int r, int g, int b) {
      if (rainbowMode) {
         int argb = Color.HSBtoRGB(hue, 1.0F, 1.0F);
         float r2 = (float)(argb >> 16 & 255) / 255.0F;
         float g2 = (float)(argb >> 8 & 255) / 255.0F;
         float b2 = (float)(argb & 255) / 255.0F;
         r2 = r2 == 0.0F ? 0.001F : r2;
         ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r2, g2, b2), 1.0F);
         if (isSinglePlayer) {
            this.display(color, center, (player));
         } else {
            this.display(color, center, visibleRange);
         }
      } else {
         ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
         if (isSinglePlayer) {
            this.display(color, center, (player));
         } else {
            this.display(color, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, boolean isSinglePlayer, Player player, int r, int g, int b) {
      if (this == redstone || this == mobspell || this == mobspellambient) {
         if (this == redstone) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            if (isSinglePlayer) {
               this.display(color, center, (player));
            } else {
               this.display(color, center, visibleRange);
            }
         } else if (isSinglePlayer) {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, player);
         } else {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, List players, int r, int g, int b) {
      if (this == redstone || this == mobspell || this == mobspellambient) {
         if (this == redstone) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            if (players != null) {
               this.display(color, center, players);
            } else {
               this.display(color, center, visibleRange);
            }
         } else if (players != null) {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, players);
         } else {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(String idName, Material dataMat, byte dataID, List players, Location center, double visibleRange, boolean rainbowMode, float offsetX, float offsetY, float offsetZ, float speed, int particleCount) {
      if (!this.isSupported()) {
         EffectsLib.stopEffect(idName);
      } else {
         ParticleEffect.RedstoneColor finalData;
         if (this != redstone && this != mobspell && this != mobspellambient) {
            if (this == note) {
               ParticleEffect.NoteColor color;
               color = new NoteColor((int)offsetX);
               if (players != null) {
                  this.display(color, center, (List)players);
               } else {
                  this.display(color, center, visibleRange);
               }
            } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
               if ((this == fallingdust || this == blockcrack || this == blockdust) && dataMat != null) {
                  finalData = null;
                  ParticleEffect.BlockData finalData2;
                  if (skDragonCore.serverVersion >= 14) {
                     finalData2 = new ParticleEffect.BlockData(dataMat);
                  } else {
                     finalData2 = new ParticleEffect.BlockData(dataMat, dataID);
                  }

                  if (players != null) {
                     this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, players);
                  } else {
                     this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
                  }
               } else if (this == itemcrack && dataMat != null) {
                  finalData = null;
                  ParticleEffect.ItemData finalData3;
                  if (skDragonCore.serverVersion >= 14) {
                     finalData3 = new ParticleEffect.ItemData(dataMat);
                  } else {
                     finalData3 = new ParticleEffect.ItemData(dataMat, dataID);
                  }

                  if (players != null) {
                     this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, players);
                  } else {
                     this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
                  }
               }
            } else if (players != null) {
               this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, players);
            } else {
               this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
            }
         } else {
            ParticleEffect.OrdinaryColor color;
            if (rainbowMode) {
               if (this == redstone) {
                  finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
                  if (players != null) {
                     this.display(finalData, center, players);
                  } else {
                     this.display(finalData, center, visibleRange);
                  }
               } else {
                  color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
                  if (players != null) {
                     this.display(color, center, players);
                  } else {
                     this.display(color, center, visibleRange);
                  }
               }
            } else if (this == redstone) {
               finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
               if (players != null) {
                  this.display(finalData, center, players);
               } else {
                  this.display(finalData, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
               if (players != null) {
                  this.display(color, center, players);
               } else {
                  this.display(color, center, visibleRange);
               }
            }
         }

      }
   }

   public void display(Material dataMat, byte dataID, List players, Location center, double visibleRange, boolean rainbowMode, float offsetX, float offsetY, float offsetZ, float speed, int particleCount) {
      ParticleEffect.RedstoneColor finalData;
      if (this != redstone && this != mobspell && this != mobspellambient) {
         if (this == note) {
            ParticleEffect.NoteColor color;
            color = new NoteColor((int)offsetX);
            if (players != null) {
               this.display(color, center, (List)players);
            } else {
               this.display(color, center, visibleRange);
            }
         } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            if ((this == fallingdust || this == blockcrack || this == blockdust) && dataMat != null) {
               finalData = null;
               ParticleEffect.BlockData finalData2;
               if (skDragonCore.serverVersion >= 14) {
                  finalData2 = new ParticleEffect.BlockData(dataMat);
               } else {
                  finalData2 = new ParticleEffect.BlockData(dataMat, dataID);
               }

               if (players != null) {
                  this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, players);
               } else {
                  this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
               }
            } else if (this == itemcrack && dataMat != null) {
               finalData = null;
               ParticleEffect.ItemData finalData3;
               if (skDragonCore.serverVersion >= 14) {
                  finalData3 = new ParticleEffect.ItemData(dataMat);
               } else {
                  finalData3 = new ParticleEffect.ItemData(dataMat, dataID);
               }

               if (players != null) {
                  this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, players);
               } else {
                  this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
               }
            }
         } else if (players != null) {
            this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, players);
         } else {
            this.display(offsetX, offsetY, offsetZ, speed, particleCount, center, visibleRange);
         }
      } else {
         ParticleEffect.OrdinaryColor color;
         if (rainbowMode) {
            if (this == redstone) {
               finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
               if (players != null) {
                  this.display(finalData, center, players);
               } else {
                  this.display(finalData, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
               if (players != null) {
                  this.display(color, center, players);
               } else {
                  this.display(color, center, visibleRange);
               }
            }
         } else if (this == redstone) {
            finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
            if (players != null) {
               this.display(finalData, center, players);
            } else {
               this.display(finalData, center, visibleRange);
            }
         } else {
            color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
            if (players != null) {
               this.display(color, center, players);
            } else {
               this.display(color, center, visibleRange);
            }
         }
      }

   }

   public static double simpleRainbowHelper(double finalOffsetX, ParticleEffect particleEffect) {
      if (particleEffect.equals(note)) {
         if (finalOffsetX >= 24.0D) {
            finalOffsetX = 0.0D;
         }

         ++finalOffsetX;
      } else if (particleEffect.equals(redstone) || particleEffect.equals(mobspell) || particleEffect.equals(mobspellambient)) {
         if (finalOffsetX >= 1.0D) {
            finalOffsetX = 0.0D;
         }

         finalOffsetX += 0.01D;
      }

      return finalOffsetX;
   }

   public static final class BlockData extends ParticleEffect.ParticleData {
      public BlockData(Material material, byte data) throws IllegalArgumentException {
         super(material, data);
         if (!material.isBlock()) {
            throw new IllegalArgumentException("The material is not a block");
         }
      }

      public BlockData(Material material) throws IllegalArgumentException {
         super(material);
         if (!material.isBlock()) {
            throw new IllegalArgumentException("The material is not a block");
         }
      }
   }

   public static final class ItemData extends ParticleEffect.ParticleData {
      public ItemData(Material material, byte data) {
         super(material, data);
      }

      public ItemData(Material material) {
         super(material);
      }
   }

   public static final class NoteColor extends ParticleEffect.ParticleColor {
      private final int note;

      public NoteColor(int note) throws IllegalArgumentException {
         if (note < 0) {
            throw new IllegalArgumentException("The note value is lower than 0");
         } else if (note > 24) {
            throw new IllegalArgumentException("The note value is higher than 24");
         } else {
            this.note = note;
         }
      }

      public float getR() {
         return (float)this.note / 24.0F;
      }

      public float getG() {
         return 0.0F;
      }

      public float getB() {
         return 0.0F;
      }
   }

   public static class OrdinaryColor extends ParticleEffect.ParticleColor {
      private final int red;
      private final int green;
      private final int blue;

      public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
         if (red < 0) {
            throw new IllegalArgumentException("The red value is lower than 0");
         } else if (red > 255) {
            throw new IllegalArgumentException("The red value is higher than 255");
         } else {
            this.red = red;
            if (green < 0) {
               throw new IllegalArgumentException("The green value is lower than 0");
            } else if (green > 255) {
               throw new IllegalArgumentException("The green value is higher than 255");
            } else {
               this.green = green;
               if (blue < 0) {
                  throw new IllegalArgumentException("The blue value is lower than 0");
               } else if (blue > 255) {
                  throw new IllegalArgumentException("The blue value is higher than 255");
               } else {
                  this.blue = blue;
               }
            }
         }
      }

      public OrdinaryColor(Color color) {
         this(color.getRed(), color.getGreen(), color.getBlue());
      }

      public int getRed() {
         return this.red;
      }

      public int getGreen() {
         return this.green;
      }

      public int getBlue() {
         return this.blue;
      }

      public float getR() {
         return (float)this.red / 255.0F;
      }

      public float getG() {
         return (float)this.green / 255.0F;
      }

      public float getB() {
         return (float)this.blue / 255.0F;
      }
   }

   public abstract static class ParticleColor {
      public abstract float getR();

      public abstract float getG();

      public abstract float getB();
   }

   private static final class ParticleColorException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleColorException(String message) {
         super(message);
      }
   }

   public abstract static class ParticleData {
      private final Material material;
      private byte data;
      private int[] packetData;

      public ParticleData(Material material, byte data) {
         this.material = material;
         this.data = data;
         this.packetData = new int[]{material.getId(), data};
      }

      public ParticleData(Material material) {
         this.material = material;
      }

      public Material getMaterial() {
         return this.material;
      }

      public byte getData() {
         return this.data;
      }

      public int[] getPacketData() {
         return this.packetData;
      }

      public String getPacketDataString() {
         return "_" + this.packetData[0] + "_" + this.packetData[1];
      }
   }

   private static final class ParticleDataException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleDataException(String message) {
         super(message);
      }
   }

   public static final class ParticlePacket {
      private static int version;
      private static Class enumParticle;
      private static Constructor packetConstructor;
      private static Method getHandle;
      private static FieldAccess playerConnection;
      private static int playerConnectionIndex;
      private static MethodAccess sendPacket;
      private static int sendPacketIndex;
      private static boolean initialized;
      private final ParticleEffect effect;
      private float offsetX;
      private final float offsetY;
      private final float offsetZ;
      private final float speed;
      private final int amount;
      private final boolean longDistance;
      private final ParticleEffect.ParticleData data;
      private ParticleEffect.RedstoneColor colorData;
      private Object packet;

      public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
         initialize();
         if (speed < 0.0F) {
            throw new IllegalArgumentException("The speed is lower than 0");
         } else if (amount < 0) {
            throw new IllegalArgumentException("The amount is lower than 0");
         } else {
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
         }
      }

      public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
         this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, data);
      }

      public ParticlePacket(ParticleEffect effect, ParticleEffect.ParticleColor color, boolean longDistance) {
         this(effect, color.getR(), color.getG(), color.getB(), 1.0F, 0, longDistance, null);
         if (effect == ParticleEffect.redstone && color instanceof ParticleEffect.OrdinaryColor && ((ParticleEffect.OrdinaryColor)color).getRed() == 0) {
            this.offsetX = 1.17549435E-38F;
         }

         if (effect == ParticleEffect.redstone) {
            this.colorData = (ParticleEffect.RedstoneColor)color;
         }

      }

      public static void initialize() throws ParticleEffect.ParticlePacket.VersionIncompatibleException {
         if (!initialized) {
            try {
               version = ReflectionUtils.PackageType.getServerVersionMinor();
               getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");

               Class packetClass, packet;
               if(version < 17){
                  packetClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
                  playerConnection = FieldAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer"));
                  playerConnectionIndex = playerConnection.getIndex("playerConnection");
                  sendPacket = MethodAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection"));
                  packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet");
               }else{
                  packetClass = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("PacketPlayOutWorldParticles");
                  playerConnection = FieldAccess.get(ReflectionUtils.PackageType.MINECRAFT_LEVEL.getClass("EntityPlayer"));
                  playerConnectionIndex = playerConnection.getIndex("b");
                  sendPacket = MethodAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER_NETWORK.getClass("PlayerConnection"));
                  packet = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");
               }

               sendPacketIndex = sendPacket.getIndex(version < 18 ? "sendPacket" : "a", packet);
               Class particleParam;
               if(version >= 17){
                  enumParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
                  particleParam = ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES.getClass("ParticleParam");
                  packetConstructor = ReflectionUtils.getConstructor(packetClass, particleParam, Boolean.class, Double.class, Double.class, Double.class, Float.class, Float.class, Float.class, Float.class, Integer.class);
               }else if(version >= 15) {
                  enumParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
                  particleParam = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
                  packetConstructor = ReflectionUtils.getConstructor(packetClass, particleParam, Boolean.class, Double.class, Double.class, Double.class, Float.class, Float.class, Float.class, Float.class, Integer.class);
               } else if (version >= 13) {
                  enumParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
                  particleParam = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
                  packetConstructor = ReflectionUtils.getConstructor(packetClass, particleParam, Boolean.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, Integer.class);
               } else {
                  enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                  packetConstructor = ReflectionUtils.getConstructor(packetClass, enumParticle, Boolean.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE, int[].class);
               }
            } catch (Exception var3) {
               throw new ParticleEffect.ParticlePacket.VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", var3);
            }

            initialized = true;
         }
      }

      public static int getVersion() {
         if (!initialized) {
            initialize();
         }

         return version;
      }

      public static boolean isInitialized() {
         return initialized;
      }

      private void initializePacket(Location center) throws ParticleEffect.ParticlePacket.PacketInstantiationException {
         if (this.packet == null) {
            try {
               if (version < 8) {
                  String name = this.effect.getName();
                  if (this.data != null) {
                     name = name + this.data.getPacketDataString();
                  }

                  ReflectionUtils.setValue(this.packet, true, "a", name);
               } else if (version >= 13) {
                  Particle particle = Particle.values()[this.effect.getID()];
                  Class particleParam = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES).getClass("ParticleParam");
                  Method toNMS = null;
                  Object param = null;
                  Class materialDataClass;
                  Constructor materialDataConstructor;
                  if (this.effect == ParticleEffect.redstone) {
                     if(version < 17){
                        materialDataClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParamRedstone");
                        materialDataConstructor = ReflectionUtils.getConstructor(materialDataClass, Float.class, Float.class, Float.class, Float.class);
                        param = materialDataConstructor.newInstance(this.colorData.getR(), this.colorData.getG(), this.colorData.getB(), this.colorData.getSize());
                     }else{
                        Class vector3faClass = ReflectionUtils.PackageType.MOJANG_MATH.getClass("Vector3fa");
                        materialDataClass = ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES.getClass("ParticleParamRedstone");
                        materialDataConstructor = ReflectionUtils.getConstructor(materialDataClass, vector3faClass, Float.class);
                        Constructor vector3faConstructor = ReflectionUtils.getConstructor(vector3faClass, Float.class, Float.class, Float.class);
                        Object vector3fa = vector3faConstructor.newInstance(this.colorData.getR(), this.colorData.getG(), this.colorData.getB());
                        param = materialDataConstructor.newInstance(vector3fa, this.colorData.getSize());
                     }
                  } else {
                     Object materialData;
                     if (this.effect != ParticleEffect.fallingdust && this.effect != ParticleEffect.blockcrack && this.effect != ParticleEffect.blockdust) {
                        if (this.effect != ParticleEffect.legacyfallingdust && this.effect != ParticleEffect.legacyblockcrack && this.effect != ParticleEffect.legacyblockdust) {
                           if (this.effect == ParticleEffect.itemcrack) {
                              ItemStack item = new ItemStack(this.data.getMaterial());
                              item.setDurability(this.data.getData());
                              toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "toNMS", Particle.class, ItemStack.class);
                              param = toNMS.invoke(particleParam, particle, item);
                           } else {
                              toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "toNMS", Particle.class);
                              param = toNMS.invoke(particleParam, particle);
                           }
                        } else {
                           materialDataClass = ReflectionUtils.PackageType.BUKKIT_MATERIAL.getClass("MaterialData");
                           materialDataConstructor = ReflectionUtils.getConstructor(materialDataClass, Material.class);
                           materialData = materialDataConstructor.newInstance(this.data.getMaterial());
                           toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "toNMS", Particle.class, materialDataClass);
                           param = toNMS.invoke(particleParam, particle, materialData);
                        }
                     } else {
                        materialDataClass = ReflectionUtils.PackageType.BUKKIT_BLOCK_DATA.getClass("BlockData");
                        Method getBlockData = ReflectionUtils.getMethod(Bukkit.class, "createBlockData", Material.class);
                        materialData = getBlockData.invoke(materialDataClass, this.data.getMaterial());
                        toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "toNMS", Particle.class, materialDataClass);
                        param = toNMS.invoke(particleParam, particle, materialData);
                     }
                  }

                  if (version >= 15) {
                     this.packet = packetConstructor.newInstance(param, this.longDistance, center.getX(), center.getY(), center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
                  } else {
                     this.packet = packetConstructor.newInstance(param, this.longDistance, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
                  }
               } else if (this.data != null) {
                  int[] packetData = this.data.getPacketData();
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, this.effect == ParticleEffect.itemcrack ? packetData : new int[]{packetData[0] | packetData[1] << 12});
               } else {
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, new int[0]);
               }

            } catch (Exception var9) {
               throw new ParticleEffect.ParticlePacket.PacketInstantiationException("Packet instantiation failed", var9);
            }
         }
      }

      public void sendTo(Location center, Player player) throws ParticleEffect.ParticlePacket.PacketInstantiationException, ParticleEffect.ParticlePacket.PacketSendingException {
         this.initializePacket(center);

         try {
            sendPacket.invoke(playerConnection.get(getHandle.invoke(player), playerConnectionIndex), sendPacketIndex, this.packet);
         } catch (Exception var4) {
            throw new ParticleEffect.ParticlePacket.PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", var4);
         }
      }

      public void sendTo(Location center, List players) throws IllegalArgumentException {
         if (players.isEmpty()) {
            throw new IllegalArgumentException("The player list is empty");
         } else {
            Iterator var4 = players.iterator();

            while(var4.hasNext()) {
               Player player = (Player)var4.next();
               this.sendTo(center, player);
            }

         }
      }

      public void sendTo(Location center, double range) throws IllegalArgumentException {
         if (range < 1.0D) {
            throw new IllegalArgumentException("The range is lower than 1");
         } else {
            String worldName = center.getWorld().getName();
            double squared = range * range;
            Iterator var8 = Bukkit.getOnlinePlayers().iterator();

            while(var8.hasNext()) {
               Player player = (Player)var8.next();
               if (player.getWorld().getName().equals(worldName) && !(player.getLocation().distanceSquared(center) > squared)) {
                  this.sendTo(center, player);
               }
            }

         }
      }

      private static final class PacketInstantiationException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public PacketInstantiationException(String message, Throwable cause) {
            super(message, cause);
         }
      }

      private static final class PacketSendingException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public PacketSendingException(String message, Throwable cause) {
            super(message, cause);
         }
      }

      private static final class VersionIncompatibleException extends RuntimeException {
         private static final long serialVersionUID = 3203085387160737484L;

         public VersionIncompatibleException(String message, Throwable cause) {
            super(message, cause);
         }
      }
   }

   public enum ParticleProperty {
      USES_WATER,
      REQUIRES_DATA,
      DIRECTIONAL,
      COLORABLE
   }

   private static final class ParticleVersionException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleVersionException(String message) {
         super(message);
      }
   }

   public static final class RedstoneColor extends ParticleEffect.OrdinaryColor {
      private final Color color;
      private final float size;

      public RedstoneColor(Color color, float size) {
         super(color.getRed(), color.getGreen(), color.getBlue());
         this.color = color;
         this.size = size;
      }

      public Color getColor() {
         return this.color;
      }

      public float getSize() {
         return this.size;
      }
   }

   public static final class ID {
      private final int version; // by convention, -1 = any version
      private final int id;      // by convention, -1 = deleted on this version

      public ID(int version, int id) {
         this.version = version;
         this.id = id;
      }

      public ID(int id) {
         this(-1, id);
      }

      public int getId() {
         return id;
      }

      public int getVersion() {
         return version;
      }

      public static ID[] parse(String map){
         return Arrays.stream(map.replaceAll("\\s+", "").split("->")).map(v -> { // ignore spaces, split versions (->)
            String[] params = v.split(":");                                      // split version & id (:)
            if(params.length < 2) return new ID(Integer.parseInt(params[0]));          // only id, set ver to -1 (any)
            return new ID(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
         }).toArray(ID[]::new);
      }
   }
}
