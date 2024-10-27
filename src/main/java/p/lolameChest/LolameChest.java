package p.lolameChest;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import p.lolameChest.Commands.ReloadChest;
import p.lolameChest.Listener.ServerLoadListener;
import p.lolameChest.Manager.ChestManager;
import p.lolameChest.Manager.ConfigManager;


public final class LolameChest extends JavaPlugin {
    private ConfigManager configManager;
    private ChestManager chestManager;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        configManager = new ConfigManager(this);
        configManager.cargarConfiguracion();
        // Plugin startup logic

        chestManager = new ChestManager(configManager);

        getServer().getPluginManager().registerEvents(new ServerLoadListener(chestManager), this);

        getCommand("reloadchest").setExecutor(new ReloadChest(chestManager));
    }

    @Override
    public void onDisable() {
    }

}
