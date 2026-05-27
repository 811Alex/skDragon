package ud.skript.sashie.skDragon.packets;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ud.skript.sashie.skDragon.particleEngine.utils.ReflectionUtils;

public abstract class ASMPacket {
   private static boolean initialized;
   private static Method getHandle;
   private static FieldAccess playerConnection;
   private static int playerConnectionIndex;
   private static MethodAccess sendPacket;
   private static int sendPacketIndex;
   protected Object packet;
   private static int version;

   public ASMPacket() throws IllegalArgumentException {
      this.preInitialize();
   }

   abstract void initialize() throws Exception;

   private void preInitialize() {
      if (!initialized) {
         try {
            this.initialize();
            version = ReflectionUtils.PackageType.getServerVersionMinor();
            getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");

            Class packet;
            if(version < 17){
               playerConnection = FieldAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer"));
               playerConnectionIndex = ASMPacket.playerConnection.getIndex("playerConnection");
               sendPacket = MethodAccess.get((ReflectionUtils.PackageType.MINECRAFT_SERVER).getClass("PlayerConnection"));
               packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet");
            }else{
               playerConnection = FieldAccess.get(ReflectionUtils.PackageType.MINECRAFT_LEVEL.getClass("ServerPlayer"));
               try {
                  playerConnectionIndex = playerConnection.getIndex("c");
               } catch (Exception ignored) {
                  playerConnectionIndex = playerConnection.getIndex("connection");
               }
               sendPacket = MethodAccess.get(ReflectionUtils.PackageType.MINECRAFT_SERVER_NETWORK.getClass("ServerPlayerConnection"));
               packet = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");
            }

            try {
               sendPacketIndex = sendPacket.getIndex("b", packet);
            } catch (Exception ignored) {
               sendPacketIndex = sendPacket.getIndex("send", packet);
            }
         } catch (Exception var2) {
            throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", var2);
         }

         initialized = true;
      }
   }

   abstract void sendPacket(Object var1) throws Exception;

   protected void initializePacket() {
      try {
         this.sendPacket(this.packet);
      } catch (Exception var2) {
         throw new PacketInstantiationException("Packet instantiation failed", var2);
      }
   }

   public void sendTo(Player player) throws PacketInstantiationException, PacketSendingException {
      this.initializePacket();

      try {
         sendPacket.invoke(playerConnection.get(getHandle.invoke(player), playerConnectionIndex), sendPacketIndex, this.packet);
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
