package ud.skript.sashie.skDragon.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import ud.skript.sashie.skDragon.particleEngine.utils.ReflectionUtils;

public class SkullPacket extends ReflectionPacket {
   private static Constructor packetConstructor;
   private static Class enumItemSlot;
   private static Class itemStackClass;
   private static Method asNMSCopy;
   private ItemStack helmet;
   private LivingEntity entity;

   public SkullPacket(LivingEntity entity, ItemStack helmet) throws IllegalArgumentException {
      this.entity = entity;
      this.helmet = helmet;
   }

   void initialize() throws Exception {
      enumItemSlot = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumItemSlot");
      itemStackClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
      asNMSCopy = ReflectionUtils.getMethod("CraftItemStack", ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY, "asNMSCopy", ItemStack.class);
      packetConstructor = ReflectionUtils.getConstructor(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityEquipment"));
   }

   Constructor getPacketConstructor() {
      return packetConstructor;
   }

   void sendPacket(Object packet) throws Exception {
      Object itemObject = asNMSCopy.invoke(itemStackClass, this.helmet);
      ReflectionUtils.setValue(packet, true, "a", this.entity.getEntityId());
      ReflectionUtils.setValue(packet, true, "b", enumItemSlot.getEnumConstants()[enumItemSlot.getEnumConstants().length - 1]);
      ReflectionUtils.setValue(packet, true, "c", itemObject);
   }

   public void sendTo(double range) throws IllegalArgumentException {
      this.sendTo(this.entity.getLocation(), range);
   }
}
