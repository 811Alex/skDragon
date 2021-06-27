package ud.skript.sashie.skDragon.particleEngine.utils.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import ud.skript.sashie.skDragonCore;

public enum BlockColor {
   WHITE1(1, "SNOW_BLOCK", 0),
   WHITE2(2, "STAINED_GLASS", 0),
   WHITE3(3, "WOOL", 0),
   WHITE4(4, "QUARTZ_BLOCK", 0),
   GREY1(5, "IRON_BLOCK", 0),
   GREY2(6, "STONE_SLAB2", 0),
   GREY3(7, "CLAY", 0),
   GREY4(8, "WOOL", 8),
   GREY5(9, "STAINED_GLASS", 8),
   GREY6(10, "STONE", 0),
   GREY7(11, "STAINED_GLASS", 7),
   GREY8(12, "STAINED_CLAY", 9),
   BLACK1(13, "WOOL", 7),
   BLACK2(14, "STAINED_GLASS", 15),
   BLACK3(15, "WOOL", 15),
   BLACK4(16, "COAL_BLOCK", 0),
   BLACK5(17, "OBSIDIAN", 0),
   RED1(18, "NETHER_BRICK", 0),
   RED2(19, "RED_NETHER_BRICK", 0, 10),
   RED3(20, "NETHER_WART_BLOCK", 0, 10),
   RED4(21, "REDSTONE_BLOCK", 0),
   RED5(22, "WOOL", 14),
   RED6(23, "STAINED_CLAY", 14),
   RED7(24, "STAINED_GLASS", 14),
   ORANGE1(25, "STAINED_CLAY", 1),
   ORANGE2(26, "SANDSTONE", 2),
   ORANGE3(27, "SAND", 1),
   ORANGE4(28, "WOOD", 4),
   ORANGE5(29, "WOOL", 1),
   ORANGE6(30, "STAINED_GLASS", 1),
   YELLOW1(31, "STAINED_CLAY", 4),
   YELLOW2(32, "WOOL", 4),
   YELLOW3(33, "SPONGE", 0),
   YELLOW4(34, "GOLD_BLOCK", 0),
   YELLOW5(35, "SPONGE", 1),
   YELLOW6(36, "STAINED_GLASS", 4),
   GREEN1(37, "EMERALD_BLOCK", 0),
   GREEN2(38, "SLIME_BLOCK", 0),
   GREEN3(39, "STAINED_GLASS", 5),
   GREEN4(40, "WOOL", 5),
   GREEN5(41, "STAINED_CLAY", 5),
   GREEN6(42, "STAINED_GLASS", 13),
   GREEN7(43, "STAINED_CLAY", 13),
   GREEN8(44, "WOOL", 13),
   GREEN9(45, "PRISMARINE", 2),
   BLUE1(46, "DIAMOND_BLOCK", 0),
   BLUE2(47, "PACKED_ICE", 0),
   BLUE3(48, "ICE", 0),
   BLUE4(49, "STAINED_GLASS", 3),
   BLUE5(50, "WOOL", 3),
   BLUE6(51, "STAINED_GLASS", 9),
   BLUE7(52, "WOOL", 9),
   BLUE8(53, "STAINED_GLASS", 11),
   BLUE9(54, "WOOL", 11),
   BLUE10(55, "LAPIS_BLOCK", 0),
   VIOLET1(56, "STAINED_CLAY", 3),
   VIOLET2(57, "STAINED_CLAY", 10),
   VIOLET3(58, "STAINED_CLAY", 11),
   VIOLET4(59, "WOOL", 10),
   VIOLET5(60, "STAINED_GLASS", 10),
   VIOLET6(61, "WOOL", 2),
   VIOLET7(62, "STAINED_GLASS", 2),
   VIOLET8(63, "STAINED_CLAY", 2),
   VIOLET9(64, "PURPUR_BLOCK", 0, 9),
   VIOLET10(65, "STAINED_GLASS", 6),
   VIOLET11(66, "WOOL", 6),
   VIOLET12(67, "STAINED_CLAY", 2),
   VIOLET13(68, "STAINED_CLAY", 6),
   VIOLET14(69, "HARD_CLAY", 0);

   public static final Map ID_MAP = new HashMap();
   public static final Map SHADE_MAP = new HashMap();
   public static final Map COLOR_MAP = new HashMap();
   private final int id;
   private final String type;
   private final byte data;
   private final int version;

   static {
      BlockColor[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         BlockColor color = var3[var1];
         ID_MAP.put(color.id, color);
         if (color.id <= 17) {
            SHADE_MAP.put(color.id, color);
         }

         if (color.id >= 18 && color.id <= 69) {
            COLOR_MAP.put(color.id, color);
         }
      }

   }

   BlockColor(int id, String type, int data) {
      this.id = id;
      this.type = type;
      this.data = (byte)data;
      this.version = -1;
   }

   BlockColor(int id, String type, int data, int version) {
      this.id = id;
      this.type = type;
      this.data = (byte)data;
      this.version = version;
   }

   private Material getMaterial(String type) {
      return Material.valueOf(type);
   }

   public Integer getId() {
      return this.id;
   }

   public Material getType() {
      return this.getMaterial(this.type);
   }

   public byte getData() {
      return this.data;
   }

   public int getVersion() {
      return this.version;
   }

   public static BlockColor shade(Integer id) {
      return fromId(id, SHADE_MAP).id <= 17 ? fromId(id, SHADE_MAP) : null;
   }

   public static BlockColor red(Integer id) {
      id = id + 17;
      return fromId(id, COLOR_MAP).getId() >= 18 && fromId(id, COLOR_MAP).getId() <= 30 ? fromId(id, COLOR_MAP) : null;
   }

   public static BlockColor green(Integer id) {
      id = id + 30;
      return fromId(id, COLOR_MAP).getId() >= 31 && fromId(id, COLOR_MAP).getId() <= 45 ? fromId(id, COLOR_MAP) : null;
   }

   public static BlockColor blue(Integer id) {
      id = id + 45;
      return fromId(id, COLOR_MAP).getId() >= 46 && fromId(id, COLOR_MAP).getId() <= 69 ? fromId(id, COLOR_MAP) : null;
   }

   public static BlockColor rainbow(Integer id) {
      return fromId(id, COLOR_MAP);
   }

   public static BlockColor full(Integer id) {
      return fromId(id, COLOR_MAP).getId() >= 1 && fromId(id, COLOR_MAP).getId() <= 69 ? fromId(id, ID_MAP) : null;
   }

   public boolean isSupported() {
      if (this.version == -1) {
         return true;
      } else {
         return skDragonCore.serverVersion >= this.version;
      }
   }

   private static BlockColor fromId(Integer id, Map map) {
      Iterator var3 = map.entrySet().iterator();

      Entry entry;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         entry = (Entry)var3.next();
      } while(entry.getKey() != id || !((BlockColor)entry.getValue()).isSupported());

      return (BlockColor)entry.getValue();
   }

   public static BlockColor fromId(Integer id) {
      Iterator var2 = ID_MAP.entrySet().iterator();

      Entry entry;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         entry = (Entry)var2.next();
      } while(entry.getKey() != id || !((BlockColor)entry.getValue()).isSupported());

      return (BlockColor)entry.getValue();
   }

   public static int simpleShadeHelper(int id) {
      if (id >= 17) {
         id = 1;
      }

      ++id;
      return id;
   }

   public static int simpleRainbowHelper(int id) {
      if (id <= 18) {
         id = 18;
      }

      if (id >= 69) {
         id = 18;
      }

      ++id;
      return id;
   }
}
