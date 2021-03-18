package fr.shurisko.general.event;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void whenPlayerJoinServer(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        HeavenPlayer heavenPlayer;

        heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
        if (heavenPlayer == null)
            Heaven.getPlayerLoader.addPlayer(new HeavenPlayer(p.getName()));
        p.teleport(p.getWorld().getSpawnLocation());
        Heaven.getRankLoader.updatePlayerRank(p);
        event.setJoinMessage("§7[§a+§7] §7" + p.getDisplayName());

    }


}
