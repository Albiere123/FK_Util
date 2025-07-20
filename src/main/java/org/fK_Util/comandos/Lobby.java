package org.fK_Util.comandos;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;


public class Lobby implements CommandExecutor {


    public static Location lobby;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        FileConfiguration config = FK_Util.getConfig("config");
        if(!(sender instanceof  Player player)) {
            System.out.println(config.getString("options.prefix") + " Apenas jogadores podem usar este comando.");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if(!config.contains("essentialLocation.lobby")) {
            p.sendColouredMessage(config.getString("options.prefix") + " &cNão encontrei a localização do lobby, por favor, avise a um administrador.");
            return true;
        }

        lobby = (Location) config.get("essentialLocation.lobby");

        p.getPlayer().sendTitle("§eTeleportando...", "3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.getPlayer().sendTitle("§eTeleportando...", "2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.getPlayer().sendTitle("§eTeleportando...", "1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        player.teleport(lobby);
        p.sendColouredMessage(config.get("options.prefix") + " &aTeleportado com sucesso!");
        return true;
    }
}
