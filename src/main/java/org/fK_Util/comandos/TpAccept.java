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

        if (!player.hasPermission("FK_UTIL.Tpaccept")) {
            (new PlayerCustom(player)).sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão!");
            return true;
        }

        if (!tpaManager.temPedido(player)) {
            player.sendMessage("Você não tem pedidos de teleporte.");
            return true;
        }

        Player solicitante = tpaManager.getSolicitante(player);

        if (solicitante == null || !solicitante.isOnline()) {
            player.sendMessage("O jogador que fez o pedido não está mais online.");
            tpaManager.removerPedido(player);
            return true;
        }

        Back.setBack(solicitante.getPlayer(), solicitante.getLocation());
        solicitante.teleport(player.getLocation());
        solicitante.sendMessage("Seu pedido foi aceito! Teleportando...");
        player.sendMessage("Você aceitou o pedido de " + solicitante.getName());
        tpaManager.removerPedido(player);

        return true;
    }
}
