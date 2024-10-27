package p.lolameChest.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import p.lolameChest.Manager.ChestManager;

public class ServerLoadListener implements Listener {

    private final ChestManager chestManager;

    public ServerLoadListener(ChestManager chestManager) {
        this.chestManager = chestManager;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        Bukkit.getLogger().info("ServerLoadEvent detectado, comenzando a recargar cofres...");
        chestManager.recargarCofresEnAreas();
    }
}

