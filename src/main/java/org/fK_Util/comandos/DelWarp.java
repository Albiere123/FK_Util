package org.fK_Util.comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class DelWarp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("FK_UTIL.Delwarp")) {
            sender.sendMessage(ChatColor.RED + "Você não possue permissão!");
            return true;
        }

        FileConfiguration config = FK_Util.getConfig("config");
        FileConfiguration warps = FK_Util.getConfig("warps");

        if (args.length == 0) {
            (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &fInsira o nome da warp.");
            return true;
        }

        if (!(warps.contains(args[0].toLowerCase()))) {
            (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &cEssa warp não existe!");
            return true;
        }

        warps.set(args[0].toLowerCase(), null);
        FK_Util.saveWarp();

        (new PlayerCustom((Player) sender)).sendColouredMessage(FK_Util.getPrefix() + " &aWarp deletada com sucesso!");
        return true;
    }
}
