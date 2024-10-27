package p.lolameChest.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import p.lolameChest.Manager.ChestManager;

public class ReloadChest implements CommandExecutor {
    private final ChestManager chestManager;

    public ReloadChest(ChestManager chestManager) {
        this.chestManager = chestManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("chest.reload.area")) {
            sender.sendMessage("No tienes permisos para ejecutar este comando.");
            return false;
        }

        chestManager.recargarCofresEnAreas();
        sender.sendMessage("Los cofres han sido recargados.");
        return true;
    }
}
