package org.fK_Util.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;
import org.fK_Util.comandos.TPA.Manager;

public class TpAccept implements CommandExecutor {

    private final Manager tpaManager;

    public TpAccept(Manager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Apenas jogadores podem usar isso.");
            return true;
        }

        String prefix = FK_Util.getPrefix();
        if (!player.hasPermission("FK_UTIL.Tpaccept")) {
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (!tpaManager.temPedido(player)) {
            player.sendMessage(prefix + " &cVocê não tem pedidos de teleporte.");
            return true;
        }

        Player solicitante = tpaManager.getSolicitante(player);

        if (solicitante == null || !solicitante.isOnline()) {
            player.sendMessage(prefix + " &cO jogador que fez o pedido não está mais online.");
            tpaManager.removerPedido(player);
            return true;
        }

        Back.setBack(solicitante.getPlayer(), solicitante.getLocation());
        solicitante.sendMessage(prefix + " &aSeu pedido foi aceito! Teleportando...");
        solicitante.teleport(player.getLocation());
        player.sendMessage(prefix + " &aVocê aceitou o pedido de " + solicitante.getName());
        tpaManager.removerPedido(player);

        return true;
    }
}
