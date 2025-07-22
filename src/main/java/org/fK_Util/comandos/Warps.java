package org.fK_Util.comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import java.util.Set;
import java.util.stream.Collectors;

public class Warps implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!sender.hasPermission("FK_UTIL.Warps")) {
            sender.sendMessage(FK_Util.getPrefix() + ChatColor.RED + " Você não possue permissão!");
            return true;
        }

        FileConfiguration config = FK_Util.getConfig("config");
        FileConfiguration warps = FK_Util.getConfig("warps");

        if (!(sender instanceof Player)) {
            sender.sendMessage(FK_Util.getPrefix() + " Este comando só pode ser executado por jogadores.");
            return true;
        }
        PlayerCustom p = new PlayerCustom((Player) sender);
        Set<String> warpSet = warps.getKeys(false);

        if (warpSet.isEmpty()) {
            p.sendColouredMessage(FK_Util.getPrefix() + " &cNenhuma warp foi configurada ainda.");
            return true;
        }

        String finalWarps = warpSet.stream().collect(Collectors.joining(" "));


        p.sendColouredMessage(FK_Util.getPrefix() + " Warps disponíveis: &e" + finalWarps);

        return true;
    }
}
