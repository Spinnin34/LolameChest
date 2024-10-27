package p.lolameChest.Utils;

import java.util.List;
import java.util.Random;

public class ConfigItem {

    private final String itemName;
    private final int probability;
    private final int minAmount;
    private final int maxAmount;
    private final String name;
    private final List<String> lore;

    private final Random random;

    public ConfigItem(String itemName, int probability, String interval, String name, List<String> lore) {
        this.itemName = itemName;
        this.probability = probability;

        String[] intervalParts = interval.split("-");
        this.minAmount = Integer.parseInt(intervalParts[0]);
        this.maxAmount = Integer.parseInt(intervalParts[1]);

        this.name = name;
        this.lore = lore;
        this.random = new Random();

    }

    public String getItemName() {
        return itemName;
    }

    public int getProbability() {
        return probability;
    }

    public int getRandomAmount() {
        return random.nextInt((maxAmount - minAmount) + 1) + minAmount;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }
}
