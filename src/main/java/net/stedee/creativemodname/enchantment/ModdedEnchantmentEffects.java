package net.stedee.creativemodname.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.enchantment.custom.SerratedEnchantmentEffect;

public class ModdedEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> SERRATED =
            registerEntityEffect("serrated", SerratedEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(CreativeModName.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        CreativeModName.LOGGER.info("Registering Mod Enchantment Effects for " + CreativeModName.MOD_ID);
    }
}
