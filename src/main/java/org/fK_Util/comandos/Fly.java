package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class Fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = FK_Util.getPrefix();
        if (!(sender instanceof Player player)) {
            System.out.println(prefix + " Apenas jogadores podem usar este comando.");
            return true;
        }

        if (!player.hasPermission("FK_UTIL.Fly")) {
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (args.length > 0 && !args[0].equalsIgnoreCase(player.getDisplayName())) {
            Player player2 = Bukkit.getPlayer(args[0]);
            if (player2 == null || !player2.isOnline()) {
                (new PlayerCustom(player)).sendColouredMessage(prefix + " &cO player citado não está online.");
                return true;
            }
            player2.setAllowFlight(!player2.getAllowFlight());
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &aO modo de voo do player &f" + player2 + " &a alterado para &f" + (player.getAllowFlight() ? "Ativado." : "Desativado."));
            (new PlayerCustom(player2)).sendColouredMessage(prefix + " &aModo de voo alterado para &f" + (player.getAllowFlight() ? "Ativado" : "Desativado") + " &apelo staff &f" + player);
        } else {
            player.setAllowFlight(!player.getAllowFlight());
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &aModo de voo alterado para &f" + (player.getAllowFlight() ? "Ativado." : "Desativado."));
        }
        return true;

    }
}
