package ud.skript.sashie.skDragon.packets;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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
            version = Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)));
            if (version == 1 && Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(4))) >= 0) {
               version = Integer.parseInt(String.valueOf(Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(3)))) + Integer.parseInt(Character.toString(ReflectionUtils.PackageType.getServerVersion().charAt(4))));
            }
            getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
            Class entityPlayerClass = (version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_LEVEL).getClass("EntityPlayer");
           Field plyConnField = Arrays.stream(entityPlayerClass.getFields()).filter(f -> f.getType().getSimpleName().equals("PlayerConnection")).findFirst().orElseThrow();
           playerConnection = FieldAccess.get(entityPlayerClass);
            playerConnectionIndex = ASMPacket.playerConnection.getIndex(plyConnField.getName());
            sendPacket = MethodAccess.get((version < 17 ? ReflectionUtils.PackageType.MINECRAFT_SERVER : ReflectionUtils.PackageType.MINECRAFT_SERVER_NETWORK).getClass("PlayerConnection"));
            sendPacketIndex = sendPacket.getIndex("sendPacket", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet"));
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
