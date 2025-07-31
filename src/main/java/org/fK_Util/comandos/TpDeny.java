package org.fK_Util.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;
import org.fK_Util.comandos.TPA.Manager;

public class TpDeny implements CommandExecutor {

    private final Manager tpaManager;

    public TpDeny(Manager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Apenas jogadores podem usar isso.");
            return true;
        }

        String prefix = FK_Util.getPrefix();
        if (!player.hasPermission("FK_UTIL.Tpdeny")) {
            (new PlayerCustom(player)).sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão!");
            return true;
        }

        if (!tpaManager.temPedido(player)) {
            player.sendMessage(prefix + " &cVocê não tem pedidos de teleporte.");
            return true;
        }

        Player solicitante = tpaManager.getSolicitante(player);

        if (solicitante != null && solicitante.isOnline()) {
            solicitante.sendMessage(prefix + " &cSeu pedido foi negado por &f" + player.getName());
        }

        player.sendMessage(prefix + " &cVocê negou o pedido de teleporte.");
        tpaManager.removerPedido(player);

        return true;
    }
}
