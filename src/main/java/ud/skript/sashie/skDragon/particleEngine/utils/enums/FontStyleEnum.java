package ud.skript.sashie.skDragon.particleEngine.utils.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public enum FontStyleEnum {
   PLAIN("plain"),
   BOLD("bold"),
   ITALIC("italic"),
   BOLD_ITALIC("bolditalic"),
   ITALIC_BOLD("italicbold");

   public static final Map NAME_MAP = new HashMap();
   private final String name;

   static {
      FontStyleEnum[] var3;
      int var2 = (var3 = values()).length;

      for(int var1 = 0; var1 < var2; ++var1) {
         FontStyleEnum style = var3[var1];
         NAME_MAP.put(style.name, style);
      }

   }

   private FontStyleEnum(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public static FontStyleEnum fromName(String name) {
      Iterator var2 = NAME_MAP.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         if (((String)entry.getKey()).equalsIgnoreCase(name)) {
            return (FontStyleEnum)entry.getValue();
         }
      }

      return null;
   }
}
