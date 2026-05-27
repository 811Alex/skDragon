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
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.maths.EffectsLib;

public enum ParticleEffect {
   explosion("explosion", "0", ParticleProperty.DIRECTIONAL),
   explosionlarge("explosionlarge", "1"),
   explosionhuge("explosionhuge", "2"),
   fireworkspark("fireworkspark", "3", ParticleProperty.DIRECTIONAL),
   waterbubble("waterbubble", "4", ParticleProperty.USES_WATER),
   watersplash("watersplash", "5"),
   waterwake("waterwake", "6", ParticleProperty.DIRECTIONAL),
   suspended("suspended", "7", ParticleProperty.USES_WATER),
   crit("crit", "8", ParticleProperty.DIRECTIONAL),
   critmagic("critmagic", "9", ParticleProperty.DIRECTIONAL),
   smoke("smoke", "10", ParticleProperty.DIRECTIONAL),
   smokelarge("smokelarge", "11", ParticleProperty.DIRECTIONAL),
   spell("spell", "12", ParticleProperty.DIRECTIONAL),
   spellinstant("spellinstant", "13", ParticleProperty.DIRECTIONAL),
   mobspell("mobspell", "14", ParticleProperty.COLORABLE, ParticleProperty.DIRECTIONAL),
   mobspellambient("mobspellambient", "15", ParticleProperty.COLORABLE, ParticleProperty.DIRECTIONAL),
   witchspell("witchspell", "15", ParticleProperty.DIRECTIONAL),
   waterdrip("waterdrip", "16"),
   lavadrip("lavadrip", "17"),
   angryvillager("angryvillager", "18"),
   happyvillager("happyvillager", "19", ParticleProperty.DIRECTIONAL),
   townaura("townaura", "20", ParticleProperty.DIRECTIONAL),
   note("note", "21", ParticleProperty.COLORABLE),
   portal("portal", "22", ParticleProperty.DIRECTIONAL),
   enchantmenttable("enchantmenttable", "23", ParticleProperty.DIRECTIONAL),
   flame("flame", "24", ParticleProperty.DIRECTIONAL),
   lava("lava", "25"),
   //footstep("footstep", "13 -> 9: 28 -> 13: -1"),
   cloud("cloud", "26", ParticleProperty.DIRECTIONAL),
   redstone("redstone", "27", ParticleProperty.COLORABLE),
   snowball("snowball", "28"),
   //snowshovel("snowshovel", "29"),
   slime("slime", "29"),
   heart("heart", "30"),
   itemcrack("itemcrack", "31", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   blockcrack("blockcrack", "32", ParticleProperty.REQUIRES_DATA),
   blockdust("blockdust", "32", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   waterdrop("waterdrop", "33"),
   //itemtake("itemtake", "9: 40 -> 13: -1"),
   mobappearance("mobappearance", "34"),
   dragonbreath("dragonbreath", "35", ParticleProperty.DIRECTIONAL),
   endrod("endrod", "36", ParticleProperty.DIRECTIONAL),
   damage("damage", "37", ParticleProperty.DIRECTIONAL),
   sweep("sweep", "38"),
   fallingdust("fallingdust", "39", ParticleProperty.REQUIRES_DATA),
   totem("totem", "40", ParticleProperty.DIRECTIONAL),
   spit("spit", "41", ParticleProperty.DIRECTIONAL),
   squidink("squidink", "42", ParticleProperty.DIRECTIONAL),
   bubblepop("bubblepop", "43", ParticleProperty.DIRECTIONAL),
   bubblecurrentdown("bubblecurrentdown", "44"),
   bubblecurrentup("bubblecurrentup", "45"),
   nautilus("nautilus", "46", ParticleProperty.DIRECTIONAL),
   dolphin("dolphin", "47", ParticleProperty.DIRECTIONAL),
   sneeze("sneeze", "48", ParticleProperty.DIRECTIONAL),
   campfirecozy("campfirecozy", "49", ParticleProperty.DIRECTIONAL),
   campfiresignal("campfiresignal", "50", ParticleProperty.DIRECTIONAL),
   composter("composter", "51"),
   flash("flash", "52"),
   fallinglava("fallinglava", "53"),
   landinglava("landinglava", "54"),
   fallingwater("fallingwater", "55"),
   drippinghoney("drippinghoney", "56"),
   fallinghoney("fallinghoney", "57"),
   landinghoney("landinghoney", "58"),
   fallingnectar("fallingnectar", "59"),
   soulfireflame("soulfireflame", "60", ParticleProperty.DIRECTIONAL),
   ash("ash", "61"),
   crimsonspore("crimsonspore", "62"),
   warpedspore("warpedspore", "63"),
   soul("soul", "64", ParticleProperty.DIRECTIONAL),
   drippingtear("drippingtear", "65"),
   fallingtear("fallingtear", "66"),
   landingtear("landingtear", "67"),
   reverseportal("reverseportal", "68", ParticleProperty.DIRECTIONAL),
   whiteash("whiteash", "69"),
   //light("light", "17: 75 -> 18: -1"),
   dustcolortransition("dustcolortransition", "70", ParticleProperty.COLORABLE, ParticleProperty.TRANSITIONABLE),
   vibration("vibration", "71", ParticleProperty.DIRECTIONAL, ParticleProperty.VIBRATION),
   fallingsporeblossom("fallingsporeblossom", "72"),
   sporeblossomair("sporeblossomair", "72"),
   smallflame("smallflame", "74", ParticleProperty.DIRECTIONAL),
   snowflake("snowflake", "75", ParticleProperty.DIRECTIONAL),
   drippingdripstonelava("drippingdripstonelava", "76"),
   fallingdripstonelava("fallingdripstonelava", "77"),
   drippingdripstonewater("drippingdripstonewater", "78"),
   fallingdripstonewater("fallingdripstonewater", "79"),
   glowsquidink("glowsquidink", "80", ParticleProperty.DIRECTIONAL),
   glow("glow", "81", ParticleProperty.DIRECTIONAL),
   waxon("waxon", "82", ParticleProperty.DIRECTIONAL),
   waxoff("waxoff", "83", ParticleProperty.DIRECTIONAL),
   electricspark("electricspark", "84", ParticleProperty.DIRECTIONAL),
   scrape("scrape", "85", ParticleProperty.DIRECTIONAL),
   blockmarker("blockmarker", "108", ParticleProperty.REQUIRES_DATA),
   legacyblockcrack("legacyblockcrack", "13: 53 -> 14: 61 -> 15: 65 -> 16: 75 -> 17: 92 -> 18: 91", ParticleProperty.REQUIRES_DATA),
   legacyblockdust("legacyblockdust", "13: 54 -> 14: 62 -> 15: 66 -> 16: 76 -> 17: 93 -> 18: 92", ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
   legacyfallingdust("legacyfallingdust", "13: 55 -> 14: 63 -> 15: 67 -> 16: 77 -> 17: 94 -> 18: 93", ParticleProperty.REQUIRES_DATA),
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
      return ((effect == blockcrack || effect == blockdust || effect == fallingdust) && data instanceof ParticleEffect.BlockData) || (effect == itemcrack && data instanceof ParticleEffect.ItemData) || effect.hasProperty(ParticleEffect.ParticleProperty.VIBRATION);
   }

   private static boolean isColorCorrect(ParticleEffect effect, ParticleEffect.ParticleColor color) {
      if (ParticleEffect.ParticlePacket.getVersion() < 13) {
         return (effect == mobspell || effect == mobspellambient || effect == redstone) && color instanceof ParticleEffect.OrdinaryColor || effect == note && color instanceof ParticleEffect.NoteColor;
      } else {
         return (effect == mobspell || effect == mobspellambient) && color instanceof ParticleEffect.OrdinaryColor || (effect == redstone || effect == dustcolortransition) && color instanceof ParticleEffect.RedstoneColor || effect == note && color instanceof ParticleEffect.NoteColor;
      }
   }

   private static boolean isTransColorCorrect(ParticleEffect effect, ParticleEffect.ParticleColor transColor) {
      return effect != dustcolortransition || transColor instanceof ParticleEffect.OrdinaryColor;
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, null)).sendTo(center, range);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)).sendTo(center, players);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player player) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
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
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, true, null, null)).sendTo(center, range);
      }
   }

   public void display(Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
         throw new IllegalArgumentException("This particle effect is not directional");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), null, null)).sendTo(center, players);
      }
   }

   public void display(Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      this.display(direction, speed, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleColor color, ParticleEffect.ParticleColor colorTrans, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, color)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else if (!isTransColorCorrect(this, colorTrans)) {
         throw new ParticleEffect.ParticleColorException("The particle transition color type is incorrect");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, color, colorTrans, true)).sendTo(center, range);
      }
   }

   public void display(ParticleEffect.ParticleColor color, ParticleEffect.ParticleColor colorTrans, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
         throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
      } else if (!isColorCorrect(this, color)) {
         throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
      } else if (!isTransColorCorrect(this, colorTrans)) {
         throw new ParticleEffect.ParticleColorException("The particle transition color type is incorrect");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else {
         (new ParticleEffect.ParticlePacket(this, color, colorTrans, isLongDistance(center, players))).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleColor color, ParticleEffect.ParticleColor colorTrans, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
      this.display(color, colorTrans, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
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
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time (not supported by this drawing method)");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      this.display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
   }

   public void display(ParticleEffect.ParticleData data, ParticleEffect.ParticleDestination destination, Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA) && !this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (destination == null && this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, true, data, destination)).sendTo(center, range);
      }
   }

   public void display(ParticleEffect.ParticleData data, ParticleEffect.ParticleDestination destination, Vector direction, float speed, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA) && !this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
      } else if (destination == null && this.hasProperty(ParticleEffect.ParticleProperty.VIBRATION)) {
         throw new ParticleEffect.ParticleVibrationException("This particle effect is a vibration, so it requires a destination & arrival time");
      } else if (!isDataCorrect(this, data)) {
         throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
      } else {
         (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), data, destination)).sendTo(center, players);
      }
   }

   public void display(ParticleEffect.ParticleData data, ParticleEffect.ParticleDestination destination, Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
      this.display(data, destination, direction, speed, center, Arrays.asList(players));
   }

   public void display(String idName, Material dataMat, byte dataID, Player player, Location center, double visibleRange, boolean isSinglePlayer, boolean rainbowMode, float hue, float offsetX, float offsetY, float offsetZ, float offsetXTrans, float offsetYTrans, float offsetZTrans, float speed, int particleCount) {
      if (!this.isSupported()) {
         EffectsLib.stopEffect(idName);
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else {
         if (this != redstone && this != dustcolortransition && this != mobspell && this != mobspellambient) {
            ParticleEffect.NoteColor finalData;
            if (this == note) {
               if (rainbowMode) {
                  finalData = new ParticleEffect.NoteColor((int)hue);
                  if (isSinglePlayer) {
                     this.display(finalData, null, center, player);
                  } else {
                     this.display(finalData, null, center, visibleRange);
                  }
               } else if (isSinglePlayer) {
                  this.display(new ParticleEffect.NoteColor((int)offsetX), null, center, player);
               } else {
                  this.display(new ParticleEffect.NoteColor((int)offsetX), null, center, visibleRange);
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
               if (this == redstone || this == dustcolortransition) {
                  ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
                  ParticleEffect.OrdinaryColor colorTrans = null;
                  if (this == dustcolortransition) {
                     colorTrans = new ParticleEffect.OrdinaryColor(new Color(1 - r, 1 - g, 1 - b));
                  }
                  if (isSinglePlayer) {
                     this.display(color, colorTrans, center, player);
                  } else {
                     this.display(color, colorTrans, center, visibleRange);
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
               if (this == redstone || this == dustcolortransition) {
                  ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(ir, g, b), 1.0F);
                  ParticleEffect.OrdinaryColor colorTrans = null;
                  if (this == dustcolortransition) {
                     int irt = Math.round(offsetXTrans);
                     int gt = Math.round(offsetYTrans);
                     int bt = Math.round(offsetZTrans);
                     colorTrans = new ParticleEffect.OrdinaryColor(new Color(irt, gt, bt));
                  }
                  if (isSinglePlayer) {
                     this.display(color, colorTrans, center, (player));
                  } else {
                     this.display(color, colorTrans, center, visibleRange);
                  }
               } else if (isSinglePlayer) {
                  this.display(new ParticleEffect.OrdinaryColor(ir, g, b), null, center, (player));
               } else {
                  this.display(new ParticleEffect.OrdinaryColor(ir, g, b), null, center, visibleRange);
               }
            }
         }
      }
   }

   public void display(String idName, Material dataMat, byte dataID, Player player, Location center, double visibleRange, boolean isSinglePlayer, boolean rainbowMode, float hue, double offsetX, double offsetY, double offsetZ, double offsetXTrans, double offsetYTrans, double offsetZTrans, float speed, int particleCount) {
      this.display(idName, dataMat, dataID, player, center, visibleRange, isSinglePlayer, rainbowMode, hue, (float)offsetX, (float)offsetY, (float)offsetZ, (float)offsetXTrans, (float)offsetYTrans, (float)offsetZTrans, speed, particleCount);
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

   public void display(Player player, Location center, double visibleRange, boolean isSinglePlayer, float hue, float hueTrans) {
      if (this == redstone || this == dustcolortransition || this == mobspell || this == mobspellambient) {
         int argb = Color.HSBtoRGB(hue / 20.0F, 1.0F, 1.0F);
         float r = (float)(argb >> 16 & 255) / 255.0F;
         float g = (float)(argb >> 8 & 255) / 255.0F;
         float b = (float)(argb & 255) / 255.0F;
         r = r == 0.0F ? 0.001F : r;
         if (this == redstone || this == dustcolortransition) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            ParticleEffect.OrdinaryColor colorTrans = null;
            if (this == dustcolortransition) {
               int argbTrans = Color.HSBtoRGB(hueTrans / 20.0F, 1.0F, 1.0F);
               float rt = (float)(argbTrans >> 16 & 255) / 255.0F;
               float gt = (float)(argbTrans >> 8 & 255) / 255.0F;
               float bt = (float)(argbTrans & 255) / 255.0F;
               rt = rt == 0.0F ? 0.001F : rt;
               colorTrans = new ParticleEffect.OrdinaryColor(new Color(rt, gt, bt));
            }
            if (isSinglePlayer) {
               this.display(color, colorTrans, center, (player));
            } else {
               this.display(color, colorTrans, center, visibleRange);
            }
         } else if (isSinglePlayer) {
            this.display(r, g, b, 1.0F, 0, center, player);
         } else {
            this.display(r, g, b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, boolean isSinglePlayer, Player player, boolean rainbowMode, float hue, int r, int g, int b, int rt, int gt, int bt) {
      if (rainbowMode) {
         int argb = Color.HSBtoRGB(hue, 1.0F, 1.0F);
         float r2 = (float)(argb >> 16 & 255) / 255.0F;
         float g2 = (float)(argb >> 8 & 255) / 255.0F;
         float b2 = (float)(argb & 255) / 255.0F;
         r2 = r2 == 0.0F ? 0.001F : r2;
         ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r2, g2, b2), 1.0F);
         ParticleEffect.OrdinaryColor colorTrans = new ParticleEffect.OrdinaryColor(new Color(1 - r2, 1 - g2, 1 - b2));
         if (isSinglePlayer) {
            this.display(color, colorTrans, center, (player));
         } else {
            this.display(color, colorTrans, center, visibleRange);
         }
      } else {
         ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
         ParticleEffect.OrdinaryColor colorTrans = new ParticleEffect.OrdinaryColor(new Color(rt, gt, bt));
         if (isSinglePlayer) {
            this.display(color, colorTrans, center, (player));
         } else {
            this.display(color, colorTrans, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, boolean isSinglePlayer, Player player, int r, int g, int b, int rt, int gt, int bt) {
      if (this == redstone || this == dustcolortransition || this == mobspell || this == mobspellambient) {
         if (this == redstone || this == dustcolortransition) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            ParticleEffect.OrdinaryColor colorTrans = null;
            if(this == dustcolortransition) {
               colorTrans = new ParticleEffect.OrdinaryColor(new Color(rt, gt, bt));
            }
            if (isSinglePlayer) {
               this.display(color, colorTrans, center, (player));
            } else {
               this.display(color, colorTrans, center, visibleRange);
            }
         } else if (isSinglePlayer) {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, player);
         } else {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(Location center, double visibleRange, List players, int r, int g, int b, int rt, int gt, int bt) {
      if (this == redstone || this == dustcolortransition || this == mobspell || this == mobspellambient) {
         if (this == redstone || this == dustcolortransition) {
            ParticleEffect.RedstoneColor color = new ParticleEffect.RedstoneColor(new Color(r, g, b), 1.0F);
            ParticleEffect.OrdinaryColor colorTrans = null;
            if (this == dustcolortransition) {
               colorTrans = new ParticleEffect.OrdinaryColor(new Color(rt, gt, bt));
            }
            if (players != null) {
               this.display(color, colorTrans, center, players);
            } else {
               this.display(color, colorTrans, center, visibleRange);
            }
         } else if (players != null) {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, players);
         } else {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, visibleRange);
         }
      }

   }

   public void display(String idName, Material dataMat, byte dataID, List players, Location center, double visibleRange, boolean rainbowMode, float offsetX, float offsetY, float offsetZ, float offsetXTrans, float offsetYTrans, float offsetZTrans, float speed, int particleCount) {
      if (!this.isSupported()) {
         EffectsLib.stopEffect(idName);
      } else {
         ParticleEffect.RedstoneColor finalData;
         if (this != redstone && this != dustcolortransition && this != mobspell && this != mobspellambient) {
            if (this == note) {
               ParticleEffect.NoteColor color;
               color = new NoteColor((int)offsetX);
               if (players != null) {
                  this.display(color, null, center, (List)players);
               } else {
                  this.display(color, null, center, visibleRange);
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
               if (this == redstone || this == dustcolortransition) {
                  finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
                  ParticleEffect.OrdinaryColor finalDataTrans = null;
                  if (this == dustcolortransition) {
                     finalDataTrans = new ParticleEffect.OrdinaryColor(new Color(255 - (int)offsetX, 255 - (int)offsetY, 255 - (int)offsetZ));
                  }
                  if (players != null) {
                     this.display(finalData, finalDataTrans, center, players);
                  } else {
                     this.display(finalData, finalDataTrans, center, visibleRange);
                  }
               } else {
                  color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
                  if (players != null) {
                     this.display(color, null, center, players);
                  } else {
                     this.display(color, null, center, visibleRange);
                  }
               }
            } else if (this == redstone || this == dustcolortransition) {
               finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
               ParticleEffect.OrdinaryColor finalDataTrans = null;
               if (this == dustcolortransition) {
                  finalDataTrans = new ParticleEffect.OrdinaryColor(new Color((int)offsetXTrans, (int)offsetYTrans, (int)offsetZTrans));
               }
               if (players != null) {
                  this.display(finalData, finalDataTrans, center, players);
               } else {
                  this.display(finalData, finalDataTrans, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
               if (players != null) {
                  this.display(color, null, center, players);
               } else {
                  this.display(color, null, center, visibleRange);
               }
            }
         }

      }
   }

   public void display(Material dataMat, byte dataID, List players, Location center, double visibleRange, boolean rainbowMode, float offsetX, float offsetY, float offsetZ, float offsetXTrans, float offsetYTrans, float offsetZTrans, float speed, int particleCount) {
      ParticleEffect.RedstoneColor finalData;
      if (this != redstone && this != dustcolortransition && this != mobspell && this != mobspellambient) {
         if (this == note) {
            ParticleEffect.NoteColor color;
            color = new NoteColor((int)offsetX);
            if (players != null) {
               this.display(color, null, center, (List)players);
            } else {
               this.display(color, null, center, visibleRange);
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
            if (this == redstone || this == dustcolortransition) {
               finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
               ParticleEffect.OrdinaryColor finalDataTrans = null;
               if (this == dustcolortransition){
                  finalDataTrans = new ParticleEffect.OrdinaryColor(new Color(255 - (int)offsetX, 255 - (int)offsetX, 255 - (int)offsetX));
               }
               if (players != null) {
                  this.display(finalData, finalDataTrans, center, players);
               } else {
                  this.display(finalData, finalDataTrans, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
               if (players != null) {
                  this.display(color, null, center, players);
               } else {
                  this.display(color, null, center, visibleRange);
               }
            }
         } else if (this == redstone || this == dustcolortransition) {
            finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
            ParticleEffect.OrdinaryColor finalDataTrans = null;
            if (this == dustcolortransition){
               finalDataTrans = new ParticleEffect.OrdinaryColor(new Color((int)offsetXTrans, (int)offsetYTrans, (int)offsetZTrans));
            }
            if (players != null) {
               this.display(finalData, finalDataTrans, center, players);
            } else {
               this.display(finalData, finalDataTrans, center, visibleRange);
            }
         } else {
            color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
            if (players != null) {
               this.display(color, null, center, players);
            } else {
               this.display(color, null, center, visibleRange);
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

   public static final class ParticleColorException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleColorException(String message) {
         super(message);
      }
   }

   public static class ParticleDestination {
      private final Location location;
      private final Entity entity;
      private final int arrivalTicks;

      public static ParticleDestination from(Object obj, int arrivalTicks){
         if (obj instanceof Location location) {
            return new ParticleDestination(location, arrivalTicks);
         } else if (obj instanceof Block block) {
            return new ParticleDestination(block, arrivalTicks);
         } else if (obj instanceof Entity entity) {
            return new ParticleDestination(entity, arrivalTicks);
         }
         throw new UnsupportedOperationException("The destination object is not a Location/Block/Entity.");
      }

      public ParticleDestination(Block block, int arrivalTicks){
         this(block.getLocation(), arrivalTicks);
      }

      public ParticleDestination(Location location, int arrivalTicks) {
         this.location = location;
         this.entity = null;
         this.arrivalTicks = arrivalTicks;
      }

      public ParticleDestination(Entity entity, int arrivalTicks) {
         this.location = null;
         this.entity = entity;
         this.arrivalTicks = arrivalTicks;
      }

      public boolean isEntity() {
         return entity != null;
      }

      public Location getLocation() {
         return location;
      }

      public Entity getEntity() {
         return entity;
      }

      public int getArrivalTicks() {
         return arrivalTicks;
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

   public static final class ParticleDataException extends RuntimeException {
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
      private ParticleEffect.OrdinaryColor colorTransData;
      private ParticleEffect.ParticleDestination destination;
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

      public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleEffect.ParticleData data, ParticleEffect.ParticleDestination destination) throws IllegalArgumentException {
         this(effect, (float)direction.getX(), (float)direction.getY(), (float)direction.getZ(), speed, 0, longDistance, effect == ParticleEffect.vibration ? null : data);
         if(effect == ParticleEffect.vibration){
            this.destination = destination;
         }
      }

      public ParticlePacket(ParticleEffect effect, ParticleEffect.ParticleColor color, ParticleEffect.ParticleColor colorTrans, boolean longDistance) {
         this(effect, color.getR(), color.getG(), color.getB(), 1.0F, 0, longDistance, null);
         if (effect == ParticleEffect.redstone && color instanceof ParticleEffect.OrdinaryColor && ((ParticleEffect.OrdinaryColor)color).getRed() == 0) {
            this.offsetX = 1.17549435E-38F;
         }

         if (effect == ParticleEffect.redstone || effect == ParticleEffect.dustcolortransition) {
            this.colorData = (ParticleEffect.RedstoneColor)color;
            if(effect == ParticleEffect.dustcolortransition)
               this.colorTransData = (ParticleEffect.OrdinaryColor)colorTrans;
         }
      }

      public static void initialize() throws ParticleEffect.ParticlePacket.VersionIncompatibleException {
         if (!initialized) {
            try {
               version = ReflectionUtils.PackageType.getServerVersionMinor();
               getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");

               Class<?> packetClass, packet;
               packetClass = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundLevelParticlesPacket");
               playerConnection = FieldAccess.get(ReflectionUtils.PackageType.MINECRAFT_LEVEL.getClass("ServerPlayer"));
               try {
                  playerConnectionIndex = playerConnection.getIndex("c");
               } catch (Exception ignored) {
                  playerConnectionIndex = playerConnection.getIndex("connection");
               }
               sendPacket = MethodAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER_NETWORK.getClass("ServerPlayerConnection"));
               packet = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");

               try {
                  sendPacketIndex = sendPacket.getIndex("b", packet);
               } catch (Exception ignored) {
                  sendPacketIndex = sendPacket.getIndex("send", packet);
               }

               enumParticle = ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES.getClass("ParticleType");
               packetConstructor = ReflectionUtils.getConstructor(packetClass, enumParticle, boolean.class, boolean.class, double.class, double.class, double.class, float.class, float.class, float.class, float.class, int.class);
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
                  Object param = null;
                  Class materialDataClass;
                  Constructor materialDataConstructor;
                  if (this.effect == ParticleEffect.redstone) {
                     if(version < 17){
                        param = ReflectionUtils.getConstructor("ParticleParamRedstone", ReflectionUtils.PackageType.MINECRAFT_SERVER, Float.class, Float.class, Float.class, Float.class)
                                .newInstance(this.colorData.getR(), this.colorData.getG(), this.colorData.getB(), this.colorData.getSize());
                     }else{
                        param = ReflectionUtils.getConstructor("DustParticleOptions", ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES, int.class, float.class)
                                .newInstance(colorData.color.getRGB(), this.colorData.getSize());
                     }
                  } else if (this.effect == ParticleEffect.dustcolortransition) {
                     Class vector3faClass = ReflectionUtils.PackageType.MOJANG_MATH.getClass("Vector3f");
                     Constructor vector3faConstructor = ReflectionUtils.getConstructor(vector3faClass, Float.class, Float.class, Float.class);
                     Object vector3fa = vector3faConstructor.newInstance(this.colorData.getR(), this.colorData.getG(), this.colorData.getB());
                     Object vector3fa2 = vector3faConstructor.newInstance(this.colorTransData.getR(), this.colorTransData.getG(), this.colorTransData.getB());
                     param = ReflectionUtils.getConstructor("DustColorTransitionOptions", ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES, vector3faClass, vector3faClass, Float.class)
                             .newInstance(vector3fa, vector3fa2, this.colorData.getSize());
                  } else if (this.effect == ParticleEffect.vibration) {
                     Class blockPosClass = ReflectionUtils.PackageType.MINECRAFT_CORE.getClass("BlockPosition");
                     Constructor blockPosConstructor = ReflectionUtils.getConstructor(blockPosClass, Double.class, Double.class, Double.class);
                     Object origin = blockPosConstructor.newInstance(center.getX(), center.getY(), center.getZ());
                     Object dest;
                     if (this.destination.isEntity()) {
                        dest = ReflectionUtils.getConstructor("EntityPositionSource", ReflectionUtils.PackageType.MINECRAFT_WORLD_LEVEL_GAMEEVENT, Integer.class)
                                .newInstance(this.destination.getEntity().getEntityId());
                     } else {
                        Location destLoc = this.destination.getLocation();
                        dest = ReflectionUtils.getConstructor("BlockPositionSource", ReflectionUtils.PackageType.MINECRAFT_WORLD_LEVEL_GAMEEVENT, blockPosClass)
                                .newInstance(blockPosConstructor.newInstance(destLoc.getX(), destLoc.getY(), destLoc.getZ()));
                     }
                     Class vibrationPathClass = ReflectionUtils.PackageType.MINECRAFT_WORLD_LEVEL_GAMEEVENT_VIBRATIONS.getClass("VibrationPath");
                     param = ReflectionUtils.getConstructor("VibrationParticleOption", ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES, vibrationPathClass)
                             .newInstance(ReflectionUtils.getConstructor(vibrationPathClass, blockPosClass, ReflectionUtils.PackageType.MINECRAFT_WORLD_LEVEL_GAMEEVENT.getClass("PositionSource"), Integer.class)
                                             .newInstance(origin, dest, this.destination.getArrivalTicks()));
                  } else {
                     Method toNMS;
                     if (this.effect != ParticleEffect.fallingdust && this.effect != ParticleEffect.blockcrack && this.effect != ParticleEffect.blockdust) {
                        if (this.effect != ParticleEffect.legacyfallingdust && this.effect != ParticleEffect.legacyblockcrack && this.effect != ParticleEffect.legacyblockdust) {
                        if (this.effect == ParticleEffect.itemcrack) {
                              ItemStack item = new ItemStack(this.data.getMaterial());
                              item.setDurability(this.data.getData());
                              toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "createParticleParam", Particle.class, ItemStack.class);
                              Class particleParam = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES).getClass("ParticleParam");
                              param = toNMS.invoke(particleParam, particle, item);
                           } else {
                              toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "createParticleParam", Particle.class, Void.class);
                              param = toNMS.invoke(null, particle, null);
                           }
                        } else {
                           materialDataClass = ReflectionUtils.PackageType.BUKKIT_MATERIAL.getClass("MaterialData");
                           Object materialData = ReflectionUtils.getConstructor(materialDataClass, Material.class)
                                   .newInstance(this.data.getMaterial());
                           toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "createParticleParam", Particle.class, materialDataClass);
                           Class particleParam = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES).getClass("ParticleParam");
                           param = toNMS.invoke(particleParam, particle, materialData);
                        }
                     } else {
                        materialDataClass = ReflectionUtils.PackageType.BUKKIT_BLOCK_DATA.getClass("BlockData");
                        Method getBlockData = ReflectionUtils.getMethod(Bukkit.class, "createBlockData", Material.class);
                        Object materialData = getBlockData.invoke(materialDataClass, this.data.getMaterial());
                        toNMS = ReflectionUtils.getMethod("CraftParticle", ReflectionUtils.PackageType.CRAFTBUKKIT, "createParticleParam", Particle.class, materialDataClass);
                        Class particleParam = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_CORE_PARTICLES).getClass("ParticleParam");
                        param = toNMS.invoke(particleParam, particle, materialData);
                     }
                  }

                  if (version >= 15) {
                     this.packet = packetConstructor.newInstance(param, this.longDistance, true, center.getX(), center.getY(), center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
                  } else {
                     this.packet = packetConstructor.newInstance(param, this.longDistance, true, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
                  }
               } else if (this.data != null) {
                  int[] packetData = this.data.getPacketData();
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, true, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, this.effect == ParticleEffect.itemcrack ? packetData : new int[]{packetData[0] | packetData[1] << 12});
               } else {
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, true, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, new int[0]);
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

             for (Player player : Bukkit.getOnlinePlayers()) {
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
      COLORABLE,
      TRANSITIONABLE,
      VIBRATION
   }

   public static final class ParticleVersionException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleVersionException(String message) {
         super(message);
      }
   }

   public static final class ParticleDirectionalException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleDirectionalException(String message) {
         super(message);
      }
   }

   public static final class ParticleVibrationException extends RuntimeException {
      private static final long serialVersionUID = 3203085387160737484L;

      public ParticleVibrationException(String message) {
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
