package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

import org.bukkit.event.Event;
import ud.skript.sashie.skDragon.particleEngine.utils.ReflectionUtils;
import ud.skript.sashie.skDragon.registration.annotations.Description;
import ud.skript.sashie.skDragon.registration.annotations.Examples;
import ud.skript.sashie.skDragon.registration.annotations.Name;
import ud.skript.sashie.skDragon.registration.annotations.Syntaxes;

@Name("Send trial packet")
@Description({"Sends a fake trial packet to a player(The message CAN NOT be edited)"})
@Syntaxes({"send[ fake] trial packet to %player%"})
@Examples({"send fake trial packet to player"})
public class EffDemoMode extends Effect {
   private Expression u;

   protected void execute(@Nullable Event e) {
      String ver = ReflectionUtils.PackageType.getServerVersion();
      int verMinor = ReflectionUtils.PackageType.getServerVersionMinor();

      try {
         Class cPlayer = ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY.getClass("CraftPlayer");
         Method getHandleMethod = cPlayer.getMethod("getHandle");
         Object handle = getHandleMethod.invoke(cPlayer.cast(this.u.getSingle(e)));
         Class PacketPlayOutGameStateChange, packet;
         Constructor playOutConstructor;
         Object playOutConstructorParam, pc;
         if(verMinor < 17){
            PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + ver + ".PacketPlayOutGameStateChange");
            playOutConstructor = PacketPlayOutGameStateChange.getConstructor(Integer.TYPE, Float.TYPE);
            playOutConstructorParam = 5;
            packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet");
            pc = handle.getClass().getField("playerConnection").get(handle);
         }else{
            PacketPlayOutGameStateChange = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutGameStateChange");
            Class PacketPlayOutGameStateChangeA = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutGameStateChange$a");
            Constructor playOutAConstructor = PacketPlayOutGameStateChangeA.getConstructor(Integer.TYPE);
            playOutConstructor = PacketPlayOutGameStateChange.getConstructor(PacketPlayOutGameStateChangeA, Float.TYPE);
            playOutConstructorParam = playOutAConstructor.newInstance(5);
            packet = ReflectionUtils.PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");
            pc = handle.getClass().getField("b").get(handle);
         }
         Method sPM = pc.getClass().getMethod(verMinor < 18 ? "sendPacket" : "a", packet);
         sPM.invoke(pc, playOutConstructor.newInstance(playOutConstructorParam, 0));
      } catch (Exception var11) {
         Skript.warning("[skDragon] Error: Player didn't have a compatible version of Minecraft!");
      }
   }

   public boolean init(Expression[] e, int i, Kleenean k, ParseResult p) {
      this.u = e[0];
      return true;
   }

   public String toString(@Nullable Event e, boolean b) {
      return this.getClass().getName();
   }
}
