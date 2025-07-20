package org.fK_Util.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.Config;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class Ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        FileConfiguration config = FK_Util.getConfig("config");
        if(!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(config.getString("options.prefix")+" Apenas jogadores podem executar o comando!");
        }else {
        PlayerCustom p = new PlayerCustom((Player) sender);
        p.sendColouredMessage(config.getString("options.prefix")+" &bO seu ping é: &a"+p.getPing()+"ms.");

    }
        return true;
    }
}
