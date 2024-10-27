package p.lolameChest.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import p.lolameChest.Utils.ConfigItem;

import java.util.*;

public class ConfigManager {
    private final Plugin plugin;
    private final Map<String, ConfigItem> itemConfigs;
    private List<Location[]> chestAreas;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.itemConfigs = new HashMap<>();
        this.chestAreas = new ArrayList<>();
    }

    public void cargarConfiguracion() {
        FileConfiguration config = plugin.getConfig();

        // Cargar ítems
        Set<String> itemsConfig = config.getConfigurationSection("chest.item").getKeys(false);
        for (String itemKey : itemsConfig) {
            int probability = config.getInt("chest.item." + itemKey + ".probability");
            String intervalStr = config.getString("chest.item." + itemKey + ".interval");
            String name = config.getString("chest.item." + itemKey + ".name");
            List<String> lore = config.getStringList("chest.item." + itemKey + ".lore");

            ConfigItem configItem = new ConfigItem(itemKey, probability, intervalStr, name, lore);
            itemConfigs.put(itemKey, configItem);
        }

        if (config.contains("chest.areas")) {
            List<Map<?, ?>> areas = config.getMapList("chest.areas");
            for (Map<?, ?> area : areas) {
                String startStr = (String) area.get("start");
                String endStr = (String) area.get("end");

                Location startLoc = parseLocation(startStr);
                Location endLoc = parseLocation(endStr);

                chestAreas.add(new Location[]{startLoc, endLoc});
            }
        }
    }



    private Location parseLocation(String locStr) {
        String[] parts = locStr.split(",");
        if (parts.length != 3) return null;

        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);

        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    public void eliminarAreasRecargadas() {
        FileConfiguration config = plugin.getConfig();
        config.set("chest.areas", null);
        plugin.saveConfig();
    }

    public Map<String, ConfigItem> getItemConfigs() {
        return itemConfigs;
    }

    public List<Location[]> getChestAreas() {
        return chestAreas;
    }
}
