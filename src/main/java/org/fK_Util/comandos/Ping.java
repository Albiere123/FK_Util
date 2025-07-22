package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class Ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getConsoleSender().sendMessage(FK_Util.getPrefix() + " Apenas jogadores podem executar o comando!");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);
        if (!(player.hasPermission("FK_UTIL.Ping"))) {
            p.sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão!");
            return true;
        }

        p.sendColouredMessage(FK_Util.getPrefix() + " &bO seu ping é: &a" + p.getPing() + "ms.");


        return true;
    }
}
