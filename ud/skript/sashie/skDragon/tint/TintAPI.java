package ud.skript.sashie.skDragon.tint;

import org.bukkit.entity.Player;

public class TintAPI {
   public static TintUtils tintUtils = new TintUtils();

   public static void setTint(Player p, int percentage) {
      tintUtils.setBorder(p, 100 - percentage);
   }

   public static void fadeTint(Player p, int startpercentage, int timeInSeconds) {
      tintUtils.fadeBorder(p, 100 - startpercentage, (long)timeInSeconds);
   }

   public static void removeTint(Player p) {
      tintUtils.removeBorder(p);
   }

   public static void sendTint(Player p, int percentage, int fadeTime, int intensity) {
      tintUtils.sendBorder(p, 100 - percentage, fadeTime, intensity);
   }
}
