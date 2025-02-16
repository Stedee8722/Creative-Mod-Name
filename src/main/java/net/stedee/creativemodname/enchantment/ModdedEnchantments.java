package net.stedee.creativemodname.enchantment;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.enchantment.custom.SerratedEnchantmentEffect;
import net.stedee.creativemodname.util.ModdedTags;

public class ModdedEnchantments {

    public static final RegistryKey<Enchantment> SERRATED =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(CreativeModName.MOD_ID, "serrated"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        register(registerable, SERRATED, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ModdedTags.Items.GODLY_CLEAVER),
                15,
                3,
                Enchantment.leveledCost(4, 6),
                Enchantment.leveledCost(10, 5),
                2,
                AttributeModifierSlot.MAINHAND, AttributeModifierSlot.OFFHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM,
                        new SerratedEnchantmentEffect()));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
}
