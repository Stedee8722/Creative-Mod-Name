package net.stedee.creativemodname.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.effect.custom.BloodlossEffect;

public class ModdedEffects {
    public static final RegistryEntry<StatusEffect> BLOODLOSS = registerStatusEffect("bloodloss",
        new BloodlossEffect(StatusEffectCategory.HARMFUL, 0x7f0000));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(CreativeModName.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        CreativeModName.LOGGER.info("Registering Effects for " + CreativeModName.MOD_ID);
    }
}
