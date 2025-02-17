package uk.phyre.biomeTitles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ParentCommand implements CommandExecutor {
    private final JavaPlugin _plugin;

    public ParentCommand(JavaPlugin plugin) {
        _plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("[BiomeTitles] Available subcommands: reload, debug");
            return true;
        }

        switch (args[0]) {
            case "reload":
                return reloadCommand(sender);
            case "debug":
                if (args.length < 2) {
                    sender.sendMessage("[BiomeTitles] The debug command requires an 'enabled' boolean (true/false)");
                    return true;
                }
                var enabled = CastHelper.parseBoolean(args[1]);
                if (enabled == null) {
                    sender.sendMessage("[BiomeTitles] Invalid format.");
                    return true;
                }
                return setDebugger(sender, enabled);
        }

        sender.sendMessage("[BiomeTitles] Command not recognised");
        return true;
    }

    private boolean reloadCommand(CommandSender sender) {
        if (sender instanceof Player) {
            var player = (Player)sender;
            if (!player.hasPermission("biometitles.reload")) {
                player.sendMessage("[BiomeTitles] You do not have permission to use this command.");
                return true;
            }
        }

        _plugin.reloadConfig();
        MessageHelper.SendPlayerAndConsole(_plugin, sender, "Reloaded.");
        return true;
    }

    private boolean setDebugger(CommandSender sender, boolean enabled) {
        if (sender instanceof Player) {
            var player = (Player)sender;
            if (!player.hasPermission("biometitles.debug")) {
                player.sendMessage("[BiomeTitles] You do not have permission to use this command.");
                return true;
            }
        }

        if (enabled != BiomeTitles.DEBUG_MODE) {
            BiomeTitles.DEBUG_MODE = enabled;
            String txt = "disabled.";
            if (enabled)
                txt = "enabled.";
            MessageHelper.SendPlayerAndConsole(_plugin, sender, "Debug mode " + txt);
        }
        return true;
    }
}
