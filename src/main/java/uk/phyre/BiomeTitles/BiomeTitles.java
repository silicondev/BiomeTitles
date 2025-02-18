package uk.phyre.biomeTitles;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class BiomeTitles extends JavaPlugin {

    private static String VERSION = "1.1";
    public static boolean DEBUG_MODE = false;

    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        var configManager = new ConfigManager(this);
        Bukkit.getPluginManager().registerEvents(new BiomeListener(this, configManager), this);
        var parentCommand = new ParentCommand(this);
        getCommand("biometitles").setExecutor((CommandExecutor) parentCommand);
        getCommand("biometitles").setTabCompleter((TabCompleter) parentCommand);

        getLogger().info(String.format("[BiomeTitles] v%s initialised.", VERSION));
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[BiomeTitles] v%s shutting down.", VERSION));
    }
}
