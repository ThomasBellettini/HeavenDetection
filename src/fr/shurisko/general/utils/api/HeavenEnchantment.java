package fr.shurisko.general.utils.api;

import org.bukkit.enchantments.Enchantment;

public class HeavenEnchantment {

    public Enchantment enchantment;
    public int level;

    public HeavenEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }
}
