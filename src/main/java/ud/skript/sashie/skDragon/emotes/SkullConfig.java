package ud.skript.sashie.skDragon.emotes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ud.skript.sashie.skDragonCore;

public class SkullConfig {
   private File skullConfig;
   private FileConfiguration skullData;
   private static final HashMap customEmoteList = new HashMap();
   private static final HashMap customSkullList = new HashMap();

   public SkullConfig() {
      this.initFile();
   }

   public static boolean emoteExists(String name) {
      return customEmoteList.containsKey(name);
   }

   public static CustomEmote getEmote(String name) {
      return (CustomEmote)customEmoteList.get(name);
   }

   public static String getSkull(String name) {
      return (String)customSkullList.get(name);
   }

   private void initFile() {
      this.skullConfig = new File(skDragonCore.getFolder(), "CustomEmotes.yml");
      if (!this.skullConfig.exists()) {
         this.skullConfig.getParentFile().mkdirs();
         skDragonCore.error("CustomEmotes.yml not found, generating new one");
         skDragonCore.getInstance().saveResource("CustomEmotes.yml", false);
      }

      this.skullData = new YamlConfiguration();

      try {
         this.skullData.load(this.skullConfig);
      } catch (InvalidConfigurationException | IOException var2) {
         var2.printStackTrace();
      }

   }

   public void initData() {
      Iterator var2 = this.skullData.getConfigurationSection("Skulls").getKeys(false).iterator();

      String name;
      while(var2.hasNext()) {
         name = (String)var2.next();
         String data = this.skullData.getString("Skulls." + name);
         customSkullList.put(name, data);
      }

      var2 = this.skullData.getConfigurationSection("Emotes").getKeys(false).iterator();

      while(var2.hasNext()) {
         name = (String)var2.next();
         CustomEmote emote = new CustomEmote(name);
         Map frames = this.skullData.getConfigurationSection("Emotes." + name + ".Frames").getValues(false);
         Map timings = this.skullData.getConfigurationSection("Emotes." + name + ".Timings").getValues(false);

         for(int i = 1; i < frames.size() + 1; ++i) {
            String key = String.valueOf(i);
            boolean found = false;
            Iterator var10 = customSkullList.keySet().iterator();

            while(var10.hasNext()) {
               String skull = (String)var10.next();
               if (this.skullData.getString("Emotes." + name + ".Frames." + key).equals(skull)) {
                  emote.addFrame((Integer)timings.get(key), (String)customSkullList.get(frames.get(key)));
                  found = true;
                  break;
               }
            }

            if (!found) {
               emote.addFrame((Integer)timings.get(key), (String)frames.get(key));
            }
         }

         customEmoteList.put(name, emote);
      }

   }

   public void reload() {
      customSkullList.clear();
      customEmoteList.clear();
      this.initData();
   }
}
