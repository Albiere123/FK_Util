package org.fK_Util.comandos.TPA;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaAccept implements CommandExecutor {

    private final Manager tpaManager;

    public TpaAccept(Manager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player alvo)) {
            sender.sendMessage("Apenas jogadores podem usar isso.");
            return true;
        }

        if (!tpaManager.temPedido(alvo)) {
            alvo.sendMessage("Você não tem pedidos de teleporte.");
            return true;
        }

        Player solicitante = tpaManager.getSolicitante(alvo);

        if (solicitante == null || !solicitante.isOnline()) {
            alvo.sendMessage("O jogador que fez o pedido não está mais online.");
            tpaManager.removerPedido(alvo);
            return true;
        }

        solicitante.teleport(alvo.getLocation());
        solicitante.sendMessage("Seu pedido foi aceito! Teleportando...");
        alvo.sendMessage("Você aceitou o pedido de " + solicitante.getName());
        tpaManager.removerPedido(alvo);

        return true;
    }
}
