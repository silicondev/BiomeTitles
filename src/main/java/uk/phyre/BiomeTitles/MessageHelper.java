package uk.phyre.biomeTitles;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageHelper {
    public static void SendPlayerAndConsole(JavaPlugin plugin, CommandSender sender, String message) {
        if (sender instanceof Player) {
            sender.sendMessage("[BiomeTitles] " + message);
        }
        plugin.getLogger().info(message);
    }
}
