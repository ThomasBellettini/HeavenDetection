package fr.shurisko.general.event;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class UserSendMessage implements Listener {

    @EventHandler
    public void whenUserSendMessage(AsyncPlayerChatEvent e)
    {
        HeavenPlayer heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(e.getPlayer());

        if (heavenPlayer.getPlayerTeam() == null) {
            e.setFormat("§7[§cⅩ§7] " + e.getPlayer().getDisplayName() + " §7⇨ " + e.getMessage());
        } else {
            e.setFormat("§7[§6" + heavenPlayer.getPlayerTeam().getName() + "§7] " + e.getPlayer().getDisplayName() + " §7⇨ " + e.getMessage());
        }
    }

}
