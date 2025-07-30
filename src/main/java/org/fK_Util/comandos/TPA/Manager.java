package org.fK_Util.comandos.TPA;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Player, Player> pedidos = new HashMap<>();

    public void adicionarPedido(Player solicitante, Player alvo) {
        pedidos.put(alvo, solicitante);
    }

    public Player getSolicitante(Player alvo) {
        return pedidos.get(alvo);
    }

    public void removerPedido(Player alvo) {
        pedidos.remove(alvo);
    }

    public boolean temPedido(Player alvo) {
        return pedidos.containsKey(alvo);
    }
}
