package org.fK_Util.comandos.TPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpa implements CommandExecutor {

    private Manager tpaManager;

    public Tpa(Manager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Apenas jogadores podem usar isso.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Uso correto: /tpa <jogador>");
            return true;
        }

        Player alvo = Bukkit.getPlayerExact(args[0]);

        if (alvo == null || !alvo.isOnline()) {
            player.sendMessage("Jogador não encontrado ou offline.");
            return true;
        }

        if (alvo.equals(player)) {
            player.sendMessage("Você não pode mandar TPA para si mesmo.");
            return true;
        }

        tpaManager.adicionarPedido(player, alvo);
        player.sendMessage("Pedido de teleporte enviado para " + alvo.getName());
        alvo.sendMessage(player.getName() + " quer teleportar até você. Use /tpaccept ou /tpdeny.");
        return true;
    }
}
