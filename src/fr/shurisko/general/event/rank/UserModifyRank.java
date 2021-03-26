package fr.shurisko.general.event.rank;

import fr.shurisko.Heaven;
import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.PermissionEnum;
import fr.shurisko.general.utils.api.inventory.HeavenRankInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UserModifyRank implements Listener {

    @EventHandler
    public void whenPlayerClick(InventoryClickEvent e)
    {
        Inventory inventory = e.getInventory();
        Player p = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        HeavenPlayer heavenPlayer;

        if (inventory == null || p == null || itemStack == null)
            return;
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName())
            return;

        if (inventory.getName().startsWith("§eConfig → §6")) {
            heavenPlayer = Heaven.getPlayerLoader.getPlayerFromBukkit(p);
            String playerName = inventory.getName().replace("§eConfig → §6", "");
            Player tmp = Bukkit.getPlayer(playerName);
            HeavenPlayer heavenPlayer_tmp = Heaven.getPlayerLoader.getPlayerFromName(playerName);

            e.setCancelled(true);
            if (heavenPlayer.getRank() != PermissionEnum.ADMIN && heavenPlayer.getRank() != PermissionEnum.OWNER) {
                p.closeInventory();
                return;
            }
            if (itemStack.getType() == Material.BARRIER) return;
            for (PermissionEnum perm : PermissionEnum.values()) {
                if (perm.name.equalsIgnoreCase(itemStack.getItemMeta().getDisplayName()) && heavenPlayer_tmp.getRank() != perm) {
                    heavenPlayer_tmp.setRank(perm);
                    HeavenRankInventory.refreshRankInventory(heavenPlayer_tmp, e.getInventory(), heavenPlayer);
                    if (tmp != null)
                        Heaven.getRankLoader.updatePlayerRank(heavenPlayer_tmp);
                }
            }
        }
    }

}
