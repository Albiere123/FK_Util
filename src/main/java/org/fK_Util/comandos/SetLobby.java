package org.fK_Util.comandos;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;

public class SetLobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = FK_Util.getConfig("config");
        if (!(sender instanceof Player player)) {
            System.out.println(config.get("options.prefix") + " Apenas para jogadores!");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if (!(player.hasPermission("FK_UTIL.Setlobby"))) {
            p.sendColouredMessage(FK_Util.getPrefix() + " &cVocê não possue permissão!");
            return true;
        }

        if (config.contains("essentialLocation.lobby")) {
            p.sendColouredMessage(FK_Util.getPrefix() + " &cJá existe um lobby, por favor, delete o anterior e salve este.");
            return true;
        }

        Location loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
        config.set("essentialLocation.lobby", loc);
        Lobby.lobby = loc;
        p.sendColouredMessage(config.get("options.prefix") + " &aLobby salvo com sucesso!");
        return true;
    }
}
