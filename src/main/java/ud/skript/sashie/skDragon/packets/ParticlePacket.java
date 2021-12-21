package ud.skript.sashie.skDragon.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ud.skript.sashie.skDragonCore;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragon.particleEngine.utils.ReflectionUtils;

public class ParticlePacket extends ASMPacket {
   int version;
   private static Constructor packetConstructor;
   private static Class enumParticle;
   private final ParticleEffect effect;
   private float offsetX;
   private final float offsetY;
   private final float offsetZ;
   private final float speed;
   private final int amount;
   private final boolean longDistance;
   private final ParticleEffect.ParticleData data;
   private ParticleEffect.RedstoneColor colorData;
   private Location center;

   public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
      this.version = skDragonCore.serverVersion;
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
      this(effect, color.getR(), color.getG(), color.getB(), 1.0F, 0, longDistance, null);
      if (effect == ParticleEffect.redstone && color instanceof ParticleEffect.OrdinaryColor && ((ParticleEffect.OrdinaryColor)color).getRed() == 0) {
         this.offsetX = 1.17549435E-38F;
      }

      if (effect == ParticleEffect.redstone) {
         this.colorData = (ParticleEffect.RedstoneColor)color;
      }

   }

   void initialize() throws Exception {
      version = ReflectionUtils.PackageType.getServerVersionMinor();

      Class packetClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(this.version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
      Class particleParam;
      if (this.version >= 15) {
         enumParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
         particleParam = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
         packetConstructor = ReflectionUtils.getConstructor(packetClass, particleParam, Boolean.class, Double.class, Double.class, Double.class, Float.class, Float.class, Float.class, Float.class, Integer.class);
      } else if (this.version >= 13) {
         enumParticle = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftParticle");
         particleParam = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
         packetConstructor = ReflectionUtils.getConstructor(packetClass, particleParam, Boolean.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, Float.class, Integer.class);
      } else if (this.version < 13) {
         enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
         packetConstructor = ReflectionUtils.getConstructor(packetClass, enumParticle, Boolean.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE, int[].class);
      }

      packetConstructor.setAccessible(true);
   }

   void sendPacket(Object packet) throws Exception {
      if (this.version < 8) {
         String name = this.effect.getName();
         if (this.data != null) {
            name = name + this.data.getPacketDataString();
         }

         ReflectionUtils.setValue(packet, true, "a", name);
      } else if (this.version >= 13) {
         Particle particle = Particle.values()[this.effect.getID()];
         Class particleParam = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParam");
         Method toNMS = null;
         Object param = null;
         Class materialDataClass;
         Constructor materialDataConstructor;
         if (this.effect == ParticleEffect.redstone) {
            materialDataClass = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ParticleParamRedstone");
            materialDataConstructor = ReflectionUtils.getConstructor(materialDataClass, Float.class, Float.class, Float.class, Float.class);
            param = materialDataConstructor.newInstance(this.colorData.getR(), this.colorData.getG(), this.colorData.getB(), this.colorData.getSize());
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

         if (this.version >= 15) {
            packet = packetConstructor.newInstance(param, this.longDistance, this.center.getX(), this.center.getY(), this.center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
         } else {
            packet = packetConstructor.newInstance(param, this.longDistance, (float)this.center.getX(), (float)this.center.getY(), (float)this.center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount);
         }
      } else if (this.data != null) {
         int[] packetData = this.data.getPacketData();
         packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, (float)this.center.getX(), (float)this.center.getY(), (float)this.center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, this.effect == ParticleEffect.itemcrack ? packetData : new int[]{packetData[0] | packetData[1] << 12});
      } else {
         packet = packetConstructor.newInstance(enumParticle.getEnumConstants()[this.effect.getID()], this.longDistance, (float)this.center.getX(), (float)this.center.getY(), (float)this.center.getZ(), this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, new int[1]);
      }

   }

   protected void initializePacket() {
      if (this.center != null) {
         try {
            this.sendPacket(this.packet);
         } catch (Exception var2) {
            throw new PacketInstantiationException("Packet instantiation failed", var2);
         }
      }
   }

   public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
      this.center = center;
      super.sendTo(player);
   }

   public void sendTo(Location center, List players) throws IllegalArgumentException {
      this.center = center;
      super.sendTo(players);
   }

   public void sendTo(Location center, double range) throws IllegalArgumentException {
      this.center = center;
      super.sendTo(center, range);
   }
}
