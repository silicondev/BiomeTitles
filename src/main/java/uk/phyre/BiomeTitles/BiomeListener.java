package uk.phyre.biomeTitles;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BiomeListener implements Listener {

    private final JavaPlugin _plugin;
    private final ConfigManager _config;

    public BiomeListener(JavaPlugin plugin, ConfigManager config) {
        _plugin = plugin;
        _config = config;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        var player = e.getPlayer();
        var from = e.getFrom();
        var to = e.getTo();

        if (to == null)
            return;

        var fromBiome = from.getBlock().getBiome();
        var toBiome = to.getBlock().getBiome();
        var biomeName = toBiome.toString();

        if (!fromBiome.equals(toBiome) && _config.GetBiomeGroups(fromBiome.toString()).stream().noneMatch(x -> x.equalsIgnoreCase(biomeName))) {
            // Player has entered new biome, and new biome is not in the same group as the last one
            if (BiomeTitles.DEBUG_MODE)
                _plugin.getLogger().info(String.format("Player '%s' entering biome '%s'.", player.getDisplayName(), biomeName));

            var info = _config.GetBiomeInfo(biomeName);
            if (info == null)
                return;

            if (BiomeTitles.WORLD_GUARD_ENABLED) {
                var disabledRegions = _config.GetDisabledRegions();

                var regionManager = new WGRegionManager();

                for (String region : disabledRegions) {
                    if (regionManager.IsInRegion(to, region)) {
                        if (BiomeTitles.DEBUG_MODE)
                            _plugin.getLogger().info(String.format("Player '%s' is in disabled region '%s'.", player.getDisplayName(), region));
                        return;
                    }
                }
            }

            if (BiomeTitles.DEBUG_MODE)
                _plugin.getLogger().info(String.format("Found details for '%s': '%s', '%s', display: %s", biomeName, info.title, info.subtitle, info.display));

            switch (info.display) {
                case "title":
                    var titleInfo = _config.GetTitleInfo();
                    player.sendTitle(info.title, info.subtitle, titleInfo.FadeIn, titleInfo.Stay, titleInfo.FadeOut);
                    break;
                case "chat":
                    player.spigot().sendMessage(ChatMessageType.CHAT, new TextComponent(String.format("%s §r§f| %s", info.title, info.subtitle)));
                    break;
                case "actionbar":
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.format("%s §r§f| %s", info.title, info.subtitle)));
                    break;
                default:
                    _plugin.getLogger().warning(String.format("CONFIG ERROR - '%s' is not a valid display.", info.display));
            }
        }
    }
}
