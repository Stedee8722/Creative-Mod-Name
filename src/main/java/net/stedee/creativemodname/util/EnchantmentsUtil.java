package net.stedee.creativemodname.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class EnchantmentsUtil {
    // Code by u/Great_Ad9570 on reddit! Thank fuck!

    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return stack.getEnchantments().getEnchantments().toString().
                contains(enchantment.getValue().toString());
    }

    public static int getLevel(ItemStack stack, RegistryKey<Enchantment> enchantment){
        for (RegistryEntry<Enchantment> enchantments : stack.getEnchantments().getEnchantments()){
            if (enchantments.toString().contains(enchantment.getValue().toString())){
                return stack.getEnchantments().getLevel(enchantments);
            }
        }
        return 0;
    }

    public static boolean isWearingEnchantedArmor(PlayerEntity player, RegistryKey<Enchantment> enchantment) {
        PlayerInventory inventory = player.getInventory();
        for (ItemStack stack : inventory.armor) {
            if (stack != null && hasEnchantment(stack, enchantment)) {
                return true;
            }
        }
        return false;
    }
}
