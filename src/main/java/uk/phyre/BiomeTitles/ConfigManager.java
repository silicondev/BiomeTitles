package uk.phyre.biomeTitles;

import org.bukkit.Input;
import org.bukkit.plugin.java.JavaPlugin;
import uk.phyre.biomeTitles.Models.BiomeTitleInfo;
import uk.phyre.biomeTitles.Models.TitleInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputFilter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ConfigManager {
    private final JavaPlugin _plugin;

    public ConfigManager(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public BiomeTitleInfo GetBiomeInfo(String biomeName) {
        if (biomeName.toLowerCase().startsWith("minecraft:")) {
            biomeName = biomeName.substring(10);
        }

        var conf = _plugin.getConfig();

        if (!conf.contains("biomes." + biomeName.toLowerCase()))
            return null;

        var info = new BiomeTitleInfo();
        info.title = conf.getString(String.format("biomes.%s.title", biomeName)).replace("&", "§");
        info.subtitle = conf.getString(String.format("biomes.%s.subtitle", biomeName)).replace("&", "§");
        info.display = conf.getString(String.format("biomes.%s.display", biomeName)).replace("&", "§");

        return info;
    }

    public TitleInfo GetTitleInfo() {
        var conf = _plugin.getConfig();
        var info = new TitleInfo();
        info.FadeIn = conf.getInt("titleInfo.fadeIn");
        info.Stay = conf.getInt("titleInfo.stay");
        info.FadeOut = conf.getInt("titleInfo.fadeOut");
        return info;
    }
}
