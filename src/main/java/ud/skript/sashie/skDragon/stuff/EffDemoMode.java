package ud.skript.sashie.skDragon.stuff;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
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
      String p = Bukkit.getServer().getClass().getPackage().getName();
      String ver = p.substring(p.lastIndexOf(".") + 1, p.length());

      try {
         Class cPlayer = Class.forName("org.bukkit.craftbukkit." + ver + ".entity.CraftPlayer");
         Class PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + ver + ".PacketPlayOutGameStateChange");
         Constructor playOutConstructor = PacketPlayOutGameStateChange.getConstructor(Integer.TYPE, Float.TYPE);
         Method getHandleMethod = cPlayer.getMethod("getHandle");
         Object handle = getHandleMethod.invoke(cPlayer.cast(this.u.getSingle(e)));
          Field plyConnField = Arrays.stream(handle.getClass().getFields()).filter(f -> f.getType().getSimpleName().equals("PlayerConnection")).findFirst().orElseThrow();
         Object pc = handle.getClass().getField(plyConnField.getName()).get(handle);
         Method sPM = pc.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server." + ver + ".Packet"));
         sPM.invoke(pc, playOutConstructor.newInstance(5, 0));
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
