package org.fK_Util.comandos;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fK_Util.FK_Util;
import org.fK_Util.PlayerCustom;
import org.fK_Util.comandos.TPA.Manager;

public class Tpa implements CommandExecutor {

    private final Manager tpaManager;

    public Tpa(Manager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = FK_Util.getPrefix();
        if (!(sender instanceof Player player)) {
            sender.sendMessage(prefix + " Apenas jogadores podem usar isso.");
            return true;
        }

        PlayerCustom p = new PlayerCustom(player);

        if (!player.hasPermission("FK_UTIL.Tpa")) {
            (new PlayerCustom(player)).sendColouredMessage(prefix + " &cVocê não possue permissão!");
            return true;
        }

        if (args.length != 1) {
            p.sendColouredMessage(prefix + " &cUso correto: /tpa <jogador>");
            return true;
        }

        Player alvo = Bukkit.getPlayerExact(args[0]);

        if (alvo == null || !alvo.isOnline()) {
            p.sendColouredMessage(prefix + " &cJogador não encontrado ou offline.");
            return true;
        }

        if (alvo.equals(player)) {
            p.sendColouredMessage(prefix + " &cVocê não pode mandar TPA para si mesmo.");
            return true;
        }

        tpaManager.adicionarPedido(player, alvo);

        p.sendColouredMessage(prefix + " &aPedido de teleporte enviado para &f" + alvo.getName());

        TextComponent mensagem = new TextComponent(prefix + " §f" + player.getName() + " §equero teleportar até você. ");

        TextComponent aceitar = new TextComponent("[ACEITAR]");
        aceitar.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        aceitar.setBold(true);
        aceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));

        TextComponent recusar = new TextComponent("[RECUSAR]");
        recusar.setColor(net.md_5.bungee.api.ChatColor.RED);
        recusar.setBold(true);
        recusar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny"));

        mensagem.addExtra(" ");
        mensagem.addExtra(aceitar);
        mensagem.addExtra(" ");
        mensagem.addExtra(recusar);

        alvo.spigot().sendMessage(mensagem);

        return true;
    }
}
