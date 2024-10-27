package p.lolameChest.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import p.lolameChest.Utils.ConfigItem;

import java.util.*;

public class ChestManager {
    private final ConfigManager configManager;
    private final Random random;

    public ChestManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.random = new Random();
    }

    public void recargarCofresEnAreas() {
        List<Location[]> chestAreas = configManager.getChestAreas();

        for (Location[] area : chestAreas) {
            Location locA = area[0];
            Location locB = area[1];

            recargarCofres(locA, locB);
        }

        configManager.eliminarAreasRecargadas();
    }

    public void recargarCofres(Location locA, Location locB) {
        Map<String, ConfigItem> itemConfigs = configManager.getItemConfigs();

        for (int x = Math.min(locA.getBlockX(), locB.getBlockX()); x <= Math.max(locA.getBlockX(), locB.getBlockX()); x++) {
            for (int y = Math.min(locA.getBlockY(), locB.getBlockY()); y <= Math.max(locA.getBlockY(), locB.getBlockY()); y++) {
                for (int z = Math.min(locA.getBlockZ(), locB.getBlockZ()); z <= Math.max(locA.getBlockZ(), locB.getBlockZ()); z++) {
                    Location loc = new Location(locA.getWorld(), x, y, z);
                    Block block = loc.getBlock();

                    if (block.getState() instanceof Chest) {
                        Chest chest = (Chest) block.getState();
                        Inventory inventory = chest.getBlockInventory();

                        inventory.clear();

                        List<Integer> availableSlots = new ArrayList<>();
                        for (int i = 0; i < inventory.getSize(); i++) {
                            availableSlots.add(i);
                        }

                        Collections.shuffle(availableSlots);

                        for (ConfigItem configItem : itemConfigs.values()) {
                            if (random.nextInt(100) < configItem.getProbability()) {
                                Material material = Material.getMaterial(configItem.getItemName());
                                if (material == null) {
                                    Bukkit.getLogger().warning("Material inválido: " + configItem.getItemName());
                                    continue;
                                }

                                int amount = configItem.getRandomAmount();
                                ItemStack itemStack = new ItemStack(material, amount);

                                ItemMeta meta = itemStack.getItemMeta();
                                if (meta != null) {
                                    if (configItem.getName() != null) {
                                        meta.setDisplayName(configItem.getName());
                                    }
                                    if (configItem.getLore() != null) {
                                        meta.setLore(configItem.getLore());
                                    }
                                    itemStack.setItemMeta(meta);
                                }

                                if (!availableSlots.isEmpty()) {
                                    int randomSlot = availableSlots.remove(0);
                                    inventory.setItem(randomSlot, itemStack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
