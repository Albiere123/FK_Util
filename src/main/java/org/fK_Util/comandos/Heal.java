package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class Heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = FK_Util.getPrefix();
        if (!(sender instanceof Player player)) {
            System.out.println(prefix + " Apenas jogadores podem usar este comando.");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if (!player.hasPermission("FK_UTIL.Heal")) {
            p.sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (args.length > 0 && !Bukkit.getPlayer(args[0]).getDisplayName().equalsIgnoreCase(player.getDisplayName())) {

            Player player2 = Bukkit.getPlayer(args[0]);
            if (player2 == null || player2.isOnline()) {
                p.sendColouredMessage(prefix + " &cNão encontrei o jogador. Verifique se o jogador está online.");
                return true;
            }

            player2.setHealth(20);
            p.sendColouredMessage(prefix + " &aA vida do jogador &f" + player2.getDisplayName() + " &afoi restaurada com sucesso!");
            (new PlayerCustom(player2)).sendColouredMessage(prefix + " &aSua vida foi restaurada pelo staff &f" + player);
        }

        player.setHealth(20);
        p.sendColouredMessage(prefix + " &aSua vida foi restauraa com sucesso!");
        return true;
    }
}
