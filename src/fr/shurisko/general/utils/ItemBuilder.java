package fr.shurisko.general.utils;

import fr.shurisko.general.utils.api.HeavenEnchantment;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    public static ItemStack makeItem (String name, List<String> lore, List<HeavenEnchantment> enchantmentList, int number,
                                      Material material, Boolean hideEnchant, int id, Boolean unbreakable)
    {
        ItemStack item;
        if (id == -1)
            item = new ItemStack(material, number);
        else
            item =  new ItemStack(material, number, (short)id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if (enchantmentList != null)
            for (HeavenEnchantment enchantment : enchantmentList)
                meta.addEnchant(enchantment.getEnchantment(), enchantment.getLevel(), true);
        if (hideEnchant)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (unbreakable)
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }


}
