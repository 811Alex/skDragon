package ud.skript.sashie.skDragon.particleEngine.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import ud.skript.sashie.skDragonCore;

public class SchedulingManager {
   public static void runTask(Runnable runnable) {
      Bukkit.getScheduler().runTask(skDragonCore.skdragoncore, runnable);
   }

   /** @deprecated */
   @Deprecated
   public static BukkitTask runRepeatingTaskTimer(Runnable runnable, int delay, int period) {
      return Bukkit.getScheduler().runTaskTimer(skDragonCore.skdragoncore, runnable, delay, period);
   }

   /** @deprecated */
   @Deprecated
   public static int runSyncDelayed(Runnable runnable, int delay) {
      return Bukkit.getScheduler().scheduleSyncDelayedTask(skDragonCore.skdragoncore, runnable, delay);
   }

   public static BukkitTask runAsyncDelayed(Runnable runnable, int delay) {
      return Bukkit.getScheduler().runTaskLaterAsynchronously(skDragonCore.skdragoncore, runnable, delay);
   }

   /** @deprecated */
   @Deprecated
   public static int runSyncRepeating(Runnable runnable, long delayStart, long delayPulse) {
      return Bukkit.getScheduler().scheduleSyncRepeatingTask(skDragonCore.skdragoncore, runnable, delayStart, delayPulse);
   }

   public static BukkitTask runAsyncRepeating(Runnable runnable, int delay, int pulse) {
      return Bukkit.getScheduler().runTaskTimerAsynchronously(skDragonCore.skdragoncore, runnable, delay, pulse);
   }

   public static BukkitTask runTaskLater(Runnable runnable, int delay) {
      return Bukkit.getScheduler().runTaskLater(skDragonCore.skdragoncore, runnable, delay);
   }

   public static void cancel(int id) {
      Bukkit.getScheduler().cancelTask(id);
   }
}
