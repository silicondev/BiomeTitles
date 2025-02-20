package uk.phyre.biomeTitles;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class BiomeTitles extends JavaPlugin {

    private static String VERSION = "1.2";
    public static boolean DEBUG_MODE = false;
    public static boolean WORLD_GUARD_ENABLED = false;

    @Override
    public void onEnable() {
        getLogger().info("Starting initialisation.");

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        var configManager = new ConfigManager(this);
        getLogger().info("Config initialised.");

        Bukkit.getPluginManager().registerEvents(new BiomeListener(this, configManager), this);
        getLogger().info("Events initialised.");

        var parentCommand = new ParentCommand(this);
        getCommand("biometitles").setExecutor((CommandExecutor) parentCommand);
        getCommand("biometitles").setTabCompleter((TabCompleter) parentCommand);
        getLogger().info("Commands initialised.");

        try {
            new WGRegionManager();
            WORLD_GUARD_ENABLED = true;
            getLogger().info("WorldGuard hook established.");
        }
        catch (NoClassDefFoundError e) {
            WORLD_GUARD_ENABLED = false;
            getLogger().info("WorldGuard hook failed.");
        }

        getLogger().info(String.format("BiomeTitles v%s initialised.", VERSION));
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("BiomeTitles v%s shutting down.", VERSION));
    }
}
