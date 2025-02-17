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

        if (!from.getBlock().getBiome().equals(to.getBlock().getBiome())) {
            // Player has entered new biome
            var biomeName = to.getBlock().getBiome().toString();

            if (BiomeTitles.DEBUG_MODE)
                _plugin.getLogger().info(String.format("[BiomeTitles] Player '%s' entering biome '%s'", player.getDisplayName(), biomeName));

            var info = _config.GetBiomeInfo(biomeName);

            if (info == null)
                return;

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
                    _plugin.getLogger().warning(String.format("[BiomeTitles] CONFIG ERROR - '%s' is not a valid display.", info.display));
            }
        }
    }
}
