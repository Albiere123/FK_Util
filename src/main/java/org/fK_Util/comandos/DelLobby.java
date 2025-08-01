package org.fK_Util.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

import static org.fK_Util.comandos.Lobby.lobby;

public class DelLobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = FK_Util.getConfig("config");
        String prefix = FK_Util.getPrefix();
        if (!(sender instanceof Player player)) {
            System.out.println(FK_Util.getPrefix() + " Apenas para jogadores!");
            return true;
        }

        if (!player.hasPermission("FK_UTIL.Delloby")) {
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if (!config.contains("essentialLocation.lobby")) {
            p.sendColouredMessage(FK_Util.getPrefix() + " &cNão encontrei a localização do lobby, por favor, avise a um administrador.");
            return true;
        }

        lobby = null;
        config.set("essentialLocation.lobby", null);
        p.sendColouredMessage(config.get("options.prefix") + " &aLobby deletado!");
        return true;
    }
}
