package fr.shurisko.api.loader;

import fr.shurisko.api.HeavenPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoader {

    public List<HeavenPlayer> heavenPlayers = new ArrayList<>();

    public void addPlayer (HeavenPlayer player)
    {
        for (HeavenPlayer p : heavenPlayers) {
            if (p.getUuid().equalsIgnoreCase(player.getUuid()))
                return;
        }
        heavenPlayers.add(player);
    }

    public HeavenPlayer getPlayerFromBukkit(Player player)
    {
        for (HeavenPlayer p : heavenPlayers) {
            if (p.getName().equalsIgnoreCase(player.getName()))
                return p;
        }
        return null;
    }

}
