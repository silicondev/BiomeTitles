package uk.phyre.biomeTitles;

import org.bukkit.block.Biome;
import org.bukkit.plugin.java.JavaPlugin;
import uk.phyre.biomeTitles.Models.BiomeTitleInfo;
import uk.phyre.biomeTitles.Models.TitleInfo;

import java.util.LinkedList;
import java.util.List;

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

        if (!conf.contains("biomes." + biomeName.toUpperCase()))
            return null;

        var info = new BiomeTitleInfo();
        info.title = conf.getString(String.format("biomes.%s.title", biomeName.toUpperCase())).replace("&", "§");
        info.subtitle = conf.getString(String.format("biomes.%s.subtitle", biomeName.toUpperCase())).replace("&", "§");
        info.display = conf.getString(String.format("biomes.%s.display", biomeName.toUpperCase())).replace("&", "§");

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

    public List<String> GetBiomeGroups(String biomeName) {
        var conf = _plugin.getConfig();
        var list = new LinkedList<String>();

        if (!conf.contains("biomeGroups"))
            return list;

        var groups = conf.getList("biomeGroups");
        if (groups != null) {
            for (Object biomes : groups) {
                if (biomes instanceof List && ((List<?>) biomes).stream().anyMatch(x -> ((String)x).equalsIgnoreCase(biomeName))) {
                    list.addAll((List<String>)biomes);
                }
            }
        }

        if (BiomeTitles.DEBUG_MODE)
            _plugin.getLogger().info(String.format("Found %s biomes that match group of %s", list.size(), biomeName));

        list.removeIf(x -> x.equalsIgnoreCase(biomeName));

        return list;
    }

    public List<String> GetDisabledRegions() {
        var conf = _plugin.getConfig();
        if (!conf.contains("disabledRegions"))
            return new LinkedList<String>();
        return conf.getStringList("disabledRegions");
    }
}
