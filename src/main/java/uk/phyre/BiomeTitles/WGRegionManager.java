package uk.phyre.biomeTitles;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.World;

public class WGRegionManager {
    private final RegionContainer _container;

    public WGRegionManager() {
        _container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    public boolean IsInRegion(Location loc, String regionName) {
        try {
            if (loc.getWorld() == null)
                return false;

            var weWorld = BukkitAdapter.adapt(loc.getWorld());
            var regions = _container.get(weWorld);

            if (regions == null)
                return false;

            var region = regions.getRegion(regionName);

            if (region == null)
                return false;

            return region.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
