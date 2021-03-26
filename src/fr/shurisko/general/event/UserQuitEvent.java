package fr.shurisko.general.event;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserQuitEvent implements Listener {

    @EventHandler
    public void whenUserDisconnect(PlayerQuitEvent e)
    {
        HeavenPlayer heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(e.getPlayer());

        if (heavenPlayer.getPlayerTeam() != null)
            heavenPlayer.getPlayerTeam().kickPlayer(heavenPlayer, false);
        e.setQuitMessage("§7[§c-§7] " + e.getPlayer().getDisplayName());
    }

}
