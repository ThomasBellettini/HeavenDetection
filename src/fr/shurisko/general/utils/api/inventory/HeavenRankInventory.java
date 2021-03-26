package fr.shurisko.general.utils.api.inventory;

import fr.shurisko.api.HeavenPlayer;
import fr.shurisko.general.utils.ItemBuilder;
import fr.shurisko.general.utils.PermissionEnum;
import fr.shurisko.general.utils.api.HeavenEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeavenRankInventory {

    public static Inventory refreshRankInventory(HeavenPlayer heavenPlayer, HeavenPlayer executer) {
        Inventory inventory = Bukkit.createInventory(null, 9*3, "§eConfig → §6" + heavenPlayer.getName());
        PermissionEnum permission = heavenPlayer.getRank();
        List<String> permissionDenied = new ArrayList<>(Arrays.asList(" ", "    §c§m→ Clic-Gauche pour donner ce grade !", " "));
        List<String> lore = new ArrayList<>(Arrays.asList(" ", "    §7→ Clic-Gauche pour donner ce grade !", " "));
        List<String> loreWhenPlayerOwnedRank = new ArrayList<>(Arrays.asList(" ", "    §7→ Clic-Gauche pour donner ce grade !", "    §a§o→ Grade Actuel !", " "));
        List<HeavenEnchantment> glowing = new ArrayList<>(Arrays.asList(new HeavenEnchantment(Enchantment.LUCK, 5)));
        int[] slot = {16, 24, 14, 12, 20, 10};
        int[] itemID = {1, 14, 12, 11, 5, 8};
        int i = 0;

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1,(short)3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(heavenPlayer.getName());
        skullMeta.setDisplayName("§c§oChange " + heavenPlayer.getName() + "'s rank");
        itemStack.setItemMeta(skullMeta);

        for (PermissionEnum perm : PermissionEnum.values()) {
            if (executer.getRank() !=  PermissionEnum.OWNER && perm == PermissionEnum.OWNER)
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, permissionDenied, null, 1, Material.BARRIER, true, -1, false ));
            else if (permission == perm)
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, loreWhenPlayerOwnedRank, glowing, 1, Material.INK_SACK, true, itemID[i], false ));
            else
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, lore, null, 1, Material.INK_SACK, true, itemID[i], false ));
            i++;
        }
        inventory.setItem(4, itemStack);
        return inventory;
    }

    public static void refreshRankInventory(HeavenPlayer heavenPlayer, Inventory inventory, HeavenPlayer executer) {
        PermissionEnum permission = heavenPlayer.getRank();
        List<String> lore = new ArrayList<>(Arrays.asList(" ", "    §7→ Clic-Gauche pour donner ce grade !", " "));
        List<String> permissionDenied = new ArrayList<>(Arrays.asList(" ", "    §c§m→ Clic-Gauche pour donner ce grade !", " "));
        List<String> loreWhenPlayerOwnedRank = new ArrayList<>(Arrays.asList(" ", "    §7→ Clic-Gauche pour donner ce grade !", "    §a§o→ Grade Actuel !", " "));
        List<HeavenEnchantment> glowing = new ArrayList<>(Arrays.asList(new HeavenEnchantment(Enchantment.LUCK, 5)));
        int[] slot = {16, 24, 14, 12, 20, 10};
        int[] itemID = {1, 14, 12, 11, 5, 8};
        int i = 0;

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1,(short)3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(heavenPlayer.getName());
        skullMeta.setDisplayName("§c§oChange " + heavenPlayer.getName() + "'s rank");
        itemStack.setItemMeta(skullMeta);

        for (PermissionEnum perm : PermissionEnum.values()) {
            if (executer.getRank() != PermissionEnum.OWNER && perm == PermissionEnum.OWNER)
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, permissionDenied, null, 1, Material.BARRIER, true, -1, false ));
            else if (permission == perm)
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, loreWhenPlayerOwnedRank, glowing, 1, Material.INK_SACK, true, itemID[i], false ));
            else
                inventory.setItem(slot[i], ItemBuilder.makeItem(perm.name, lore, null, 1, Material.INK_SACK, true, itemID[i], false ));
            i++;
        }
        inventory.setItem(4, itemStack);
    }

}
