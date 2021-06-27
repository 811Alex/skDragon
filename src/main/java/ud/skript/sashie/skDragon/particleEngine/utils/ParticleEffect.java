package ud.skript.sashie.skDragon.particleEngine.utils;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
   explosion("explosion", 0, 0, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   explosionlarge("explosionlarge", 1, 1, -1, new ParticleEffect.ParticleProperty[0]),
   explosionhuge("explosionhuge", 2, 2, -1, new ParticleEffect.ParticleProperty[0]),
   fireworkspark("fireworkspark", 3, 3, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   waterbubble("waterbubble", 4, 4, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.USES_WATER}),
   watersplash("watersplash", 5, 5, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   waterwake("waterwake", 6, 6, 7, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   suspended("suspended", 7, 7, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.USES_WATER}),
   suspenddepth("suspenddepth", 8, 8, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   crit("crit", 9, 9, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   critmagic("critmagic", 10, 10, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   smoke("smoke", 11, 11, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   smokelarge("smokelarge", 12, 12, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   spell("spell", 13, 13, -1, new ParticleEffect.ParticleProperty[0]),
   spellinstant("spellinstant", 14, 14, -1, new ParticleEffect.ParticleProperty[0]),
   mobspell("mobspell", 15, 15, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   mobspellambient("mobspellambient", 16, 16, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   witchspell("witchspell", 17, 17, -1, new ParticleEffect.ParticleProperty[0]),
   waterdrip("waterdrip", 18, 18, -1, new ParticleEffect.ParticleProperty[0]),
   lavadrip("lavadrip", 19, 19, -1, new ParticleEffect.ParticleProperty[0]),
   angryvillager("angryvillager", 20, 20, -1, new ParticleEffect.ParticleProperty[0]),
   happyvillager("happyvillager", 21, 21, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   townaura("townaura", 22, 22, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   note("note", 23, 23, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   portal("portal", 24, 24, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   enchantmenttable("enchantmenttable", 25, 25, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   flame("flame", 26, 26, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   lava("lava", 27, 27, -1, new ParticleEffect.ParticleProperty[0]),
   footstep("footstep", 28, -1, new ParticleEffect.ParticleProperty[0]),
   cloud("cloud", 29, 28, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   redstone("redstone", 30, 29, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.COLORABLE}),
   snowball("snowball", 31, 30, -1, new ParticleEffect.ParticleProperty[0]),
   snowshovel("snowshovel", 32, 31, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   slime("slime", 33, 32, -1, new ParticleEffect.ParticleProperty[0]),
   heart("heart", 34, 33, -1, new ParticleEffect.ParticleProperty[0]),
   barrier("barrier", 35, 34, 8, new ParticleEffect.ParticleProperty[0]),
   itemcrack("itemcrack", 36, 35, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   blockcrack("blockcrack", 37, 36, -1, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   blockdust("blockdust", 38, 37, 7, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   waterdrop("waterdrop", 39, 38, 8, new ParticleEffect.ParticleProperty[0]),
   itemtake("itemtake", 40, 8, new ParticleEffect.ParticleProperty[0]),
   mobappearance("mobappearance", 41, 39, 8, new ParticleEffect.ParticleProperty[0]),
   dragonbreath("dragonbreath", 42, 40, 9, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   endrod("endrod", 43, 41, 9, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   damage("damage", 44, 42, 9, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   sweep("sweep", 45, 43, 9, new ParticleEffect.ParticleProperty[0]),
   fallingdust("fallingdust", 46, 44, 10, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   totem("totem", 47, 45, 11, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   spit("spit", 48, 46, 11, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   squidink("squidink", 47, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   bubblepop("bubblepop", 48, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   bubblecurrentdown("bubblecurrentdown", 49, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   bubblecurrentup("bubblecurrentup", 50, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   nautilus("nautilus", 51, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   dolphin("dolphin", 52, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   sneeze("sneeze", 53, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   campfirecozy("campfirecozy", 54, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   campfiresignal("campfiresignal", 55, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   composter("composter", 56, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   flash("flash", 57, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   fallinglava("fallinglava", 58, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   landinglava("landinglava", 59, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   fallingwater("fallingwater", 60, 14, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   drippinghoney("drippinghoney", 61, 15, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   fallinghoney("fallinghoney", 62, 15, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   landinghoney("landinghoney", 63, 15, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   fallingnectar("fallingnectar", 64, 15, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   soulfireflame("soulfireflame", 65, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   ash("ash", 66, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   crimsonspore("crimsonspore", 67, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   warpedspore("warpedspore", 68, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   soul("soul", 69, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   drippingtear("drippingtear", 70, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   fallingtear("fallingtear", 71, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   landingtear("landingtear", 72, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   reverseportal("reverseportal", 73, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   whiteash("whiteash", 74, 16, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL}),
   legacyblockcrack("legacyblockcrack", 53, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   legacyblockdust("legacyblockdust", 54, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.DIRECTIONAL, ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   legacyfallingdust("legacyfallingdust", 55, 13, new ParticleEffect.ParticleProperty[]{ParticleEffect.ParticleProperty.REQUIRES_DATA}),
   NULL("null", 99, -1, new ParticleEffect.ParticleProperty[0]);

   public static final Map NAME_MAP = new HashMap();
   private final String name;
   private final int oldID;
   private final int newID;
   private final int requiredVersion;
   private final List properties;

   static {
      ParticleEffect[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         ParticleEffect effect = var3[var1];
         NAME_MAP.put(effect.name, effect);
      }

   }

   private ParticleEffect(String name, int id, int requiredVersion, ParticleEffect.ParticleProperty... properties) {
      this.name = name;
      this.oldID = id;
      this.newID = id;
      this.requiredVersion = requiredVersion;
      this.properties = Arrays.asList(properties);
   }

   private ParticleEffect(String name, int id, int newID, int requiredVersion, ParticleEffect.ParticleProperty... properties) {
      this.name = name;
      this.oldID = id;
      this.newID = newID;
      this.requiredVersion = requiredVersion;
      this.properties = Arrays.asList(properties);
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.oldID;
   }

   public int getNewID() {
      return this.newID;
   }

   public int getRequiredVersion() {
      return this.requiredVersion;
   }

   public boolean hasProperty(ParticleEffect.ParticleProperty property) {
      return this.properties.contains(property);
   }

   public List getProperties() {
      return this.properties;
   }

   public boolean isSupported() {
      if (this.requiredVersion == -1) {
         return true;
      } else {
         return ParticleEffect.ParticlePacket.getVersion() >= this.requiredVersion;
      }
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
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, true, (ParticleEffect.ParticleData)null)).sendTo(center, range);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), (ParticleEffect.ParticleData)null)).sendTo(center, players);
      }
   }

   public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player player) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
      if (!this.isSupported()) {
         throw new ParticleEffect.ParticleVersionException("This particle effect is not supported by your server version");
      } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
         throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
      } else {
         (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, player), (ParticleEffect.ParticleData)null)).sendTo(center, player);
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
         (new ParticleEffect.ParticlePacket(this, direction, speed, true, (ParticleEffect.ParticleData)null)).sendTo(center, range);
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
         (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), (ParticleEffect.ParticleData)null)).sendTo(center, players);
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
            this.display(r, g, b, 1.0F, 0, center, (Player)player);
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
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, (Player)player);
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
               this.display(color, center, (List)players);
            } else {
               this.display(color, center, visibleRange);
            }
         } else if (players != null) {
            this.display((float)r, (float)g, (float)b, 1.0F, 0, center, (List)players);
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
               if (rainbowMode) {
                  color = new ParticleEffect.NoteColor((int)offsetX);
                  if (players != null) {
                     this.display(color, center, (List)players);
                  } else {
                     this.display(color, center, visibleRange);
                  }
               } else {
                  color = new ParticleEffect.NoteColor((int)offsetX);
                  if (players != null) {
                     this.display(color, center, (List)players);
                  } else {
                     this.display(color, center, visibleRange);
                  }
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
                     this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, (List)players);
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
                     this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, (List)players);
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
                     this.display(finalData, center, (List)players);
                  } else {
                     this.display(finalData, center, visibleRange);
                  }
               } else {
                  color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
                  if (players != null) {
                     this.display(color, center, (List)players);
                  } else {
                     this.display(color, center, visibleRange);
                  }
               }
            } else if (this == redstone) {
               finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
               if (players != null) {
                  this.display(finalData, center, (List)players);
               } else {
                  this.display(finalData, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
               if (players != null) {
                  this.display(color, center, (List)players);
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
            if (rainbowMode) {
               color = new ParticleEffect.NoteColor((int)offsetX);
               if (players != null) {
                  this.display(color, center, (List)players);
               } else {
                  this.display(color, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.NoteColor((int)offsetX);
               if (players != null) {
                  this.display(color, center, (List)players);
               } else {
                  this.display(color, center, visibleRange);
               }
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
                  this.display(finalData2, offsetX, offsetY, offsetZ, speed, particleCount, center, (List)players);
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
                  this.display(finalData3, offsetX, offsetY, offsetZ, speed, particleCount, center, (List)players);
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
                  this.display(finalData, center, (List)players);
               } else {
                  this.display(finalData, center, visibleRange);
               }
            } else {
               color = new ParticleEffect.OrdinaryColor(Color.getHSBColor(offsetX, offsetY, offsetZ));
               if (players != null) {
                  this.display(color, center, (List)players);
               } else {
                  this.display(color, center, visibleRange);
               }
            }
         } else if (this == redstone) {
            finalData = new ParticleEffect.RedstoneColor(new Color((int)offsetX, (int)offsetY, (int)offsetZ), 1.0F);
            if (players != null) {
               this.display(finalData, center, (List)players);
            } else {
               this.display(finalData, center, visibleRange);
            }
         } else {
            color = new ParticleEffect.OrdinaryColor((int)offsetX, (int)offsetY, (int)offsetZ);
            if (players != null) {
               this.display(color, center, (List)players);
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
         this(effect, color.getR(), color.getG(), color.getB(), 1.0F, 0, longDistance, (ParticleEffect.ParticleData)null);
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
               version = Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)));
               if (version == 1 && Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(4))) >= 0) {
                  version = Integer.parseInt(String.valueOf(Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)))) + Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(4))));
               }

               Class packetClass = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME).getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
               getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
               Class entityPlayerClass = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_LEVEL).getClass("EntityPlayer");
              Field plyConnField = Arrays.stream(entityPlayerClass.getFields()).filter(f -> f.getType().getSimpleName().equals("PlayerConnection")).findFirst().orElseThrow();
               playerConnection = FieldAccess.get(entityPlayerClass);
               playerConnectionIndex = playerConnection.getIndex(plyConnField.getName());
               sendPacket = MethodAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER_NETWORK.getClass("PlayerConnection"));
               sendPacketIndex = sendPacket.getIndex("sendPacket", (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL).getClass("Packet"));
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
               } else if (version < 13) {
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
                  Particle particle = Particle.values()[this.effect.newID];
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
                           if (version == 14) {
                              particle = Particle.values()[this.effect.newID + 8];
                           } else if (version == 15) {
                              particle = Particle.values()[this.effect.newID + 12];
                           } else if (version == 16) {
                              particle = Particle.values()[this.effect.newID + 22];
                           }

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
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getId()], this.longDistance, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, this.effect == ParticleEffect.itemcrack ? packetData : new int[]{packetData[0] | packetData[1] << 12});
               } else {
                  this.packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getId()], this.longDistance, (float)center.getX(), (float)center.getY(), (float)center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, new int[0]);
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

   public static enum ParticleProperty {
      USES_WATER,
      REQUIRES_DATA,
      DIRECTIONAL,
      COLORABLE;
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
}
