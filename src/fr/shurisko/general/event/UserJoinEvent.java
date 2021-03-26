package fr.shurisko.general.event;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.ItemBuilder;
import fr.shurisko.general.utils.api.HeavenEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void whenPlayerJoinServer(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        HeavenPlayer heavenPlayer;

        heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
        if (heavenPlayer == null) {
            Heaven.getPlayerLoader.addPlayer(new HeavenPlayer(p.getName()));
            heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
        }
        heavenPlayer.setHeaven(true);
        p.teleport(p.getWorld().getSpawnLocation());
        Heaven.getRankLoader.updatePlayerRank(heavenPlayer);
        event.setJoinMessage("§7[§a+§7] §7" + p.getDisplayName());
        p.getInventory().clear();
        List<HeavenEnchantment> enchantment = new ArrayList<HeavenEnchantment>(Arrays.asList(new HeavenEnchantment(Enchantment.LUCK, 1)));
        List<String> lore = new ArrayList<>(Arrays.asList(" ", "§8§m-------------------------", " ", "   §7→ Utilise cet item pour créer une team.", " ", "§8§m-------------------------"));
        p.getInventory().setItem(4, ItemBuilder.makeItem("§6↣ Make a team", lore, enchantment, 1, Material.NAME_TAG, true, -1, true));
        lore = new ArrayList<>(Arrays.asList(" ", "§8§m-------------------------", " ", "   §7→ Cet item permet de ce téléporter au serveur Créatif.", " ", "§8§m-------------------------"));
        if (heavenPlayer.isHeaven)
            p.getInventory().setItem(0, ItemBuilder.makeItem("§a↣ Serveur Heaven", lore, null,  1, Material.INK_SACK, false, 9, true));
        p.updateInventory();
        Heaven.getPlayerLoader.refreshTab();
        p.setScoreboard(Heaven.getRankLoader.scoreboard);
    }
}
