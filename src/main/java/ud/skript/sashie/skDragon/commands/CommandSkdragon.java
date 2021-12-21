package ud.skript.sashie.skDragon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ud.skript.sashie.skDragon.particleEngine.utils.ParticleEffect;
import ud.skript.sashie.skDragonCore;

/**
 * @author Alex811
 */
public class CommandSkdragon implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        String ans;

        if(args.length > 0){
            ans = switch (args[0]){
                case "version" -> skDragonCore.version;
                case "particles" -> String.join(", ", ParticleEffect.getSupported()).replaceAll(",", ChatColor.GRAY + "$0" + ChatColor.GREEN);
                default -> "Usage: " + coloredUsage(cmd) +
                        helpEntry("help", "Show this message") +
                        helpEntry("version", "Show plugin version") +
                        helpEntry("particles", "Show available particle names");
            };
        }else{
            onCommand(sender, cmd, label, new String[]{"help"});
            return true;
        }

        if(sender instanceof Player player) skDragonCore.sendMsg(player, ans);
        else skDragonCore.sendLog(ans);
        return true;
    }

    private String coloredUsage(Command cmd){
        return cmd.getUsage().replaceAll("/", ChatColor.GRAY + "$0" + ChatColor.GOLD)
                .replaceFirst("\\s", "$0" + ChatColor.YELLOW)
                .replaceAll("[|<>\\[\\]()]", ChatColor.GRAY + "$0" + ChatColor.YELLOW);
    }

    private String helpEntry(String name, String desc) {
        return "\n " + ChatColor.YELLOW + name + ChatColor.GRAY + " - " + ChatColor.RESET + desc;
    }
}
