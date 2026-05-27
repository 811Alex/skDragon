package ud.skript.sashie.skDragon.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ud.skript.sashie.skDragon.particleEngine.utils.ReflectionUtils;

public abstract class ReflectionPacket {
   private static boolean initialized;
   private static Method getHandle;
   private static Field playerConnection;
   private static Method sendPacket;
   private Object packet;

   public ReflectionPacket() throws IllegalArgumentException {
      this.preInitialize();
   }

   abstract void initialize() throws Exception;

   private void preInitialize() {
      if (!initialized) {
         try {
            this.initialize();
            getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");

            int version = ReflectionUtils.PackageType.getServerVersionMinor();
            Class packet;
            if(version < 17){
               playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
               packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet");
            }else{
               playerConnection = ReflectionUtils.getField("ServerPlayer", ReflectionUtils.PackageType.MINECRAFT_LEVEL, false, "b");
               packet = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");
            }

            try {
               sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "send", packet);
            } catch (Exception ignored) {
               sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "b", packet);
            }
         } catch (Exception var2) {
            throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", var2);
         }

         initialized = true;
      }
   }

   abstract Constructor getPacketConstructor();

   abstract void sendPacket(Object var1) throws Exception;

   private void initializePacket() {
      try {
         this.packet = this.getPacketConstructor().newInstance();
         this.sendPacket(this.packet);
      } catch (Exception var2) {
         throw new PacketInstantiationException("Packet instantiation failed", var2);
      }
   }

   public void sendTo(Player player) throws PacketInstantiationException, PacketSendingException {
      this.initializePacket();

      try {
         sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), this.packet);
      } catch (Exception var3) {
         throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", var3);
      }
   }

   public void sendTo(List players) throws IllegalArgumentException {
      if (players.isEmpty()) {
         throw new IllegalArgumentException("The player list is empty");
      } else {
         Iterator var3 = players.iterator();

         while(var3.hasNext()) {
            Player player = (Player)var3.next();
            this.sendTo(player);
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
               this.sendTo(player);
            }
         }

      }
   }
}
