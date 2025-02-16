package net.stedee.creativemodname.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.stedee.creativemodname.effect.ModdedEffects;

public record SerratedEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<SerratedEnchantmentEffect> CODEC = MapCodec.unit(SerratedEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity victim, Vec3d pos) {
        if (!(victim instanceof LivingEntity)) return;
        StatusEffectInstance effect = ((LivingEntity) victim).getStatusEffect(ModdedEffects.BLOODLOSS);
        if (effect != null) {
            effect.upgrade(new StatusEffectInstance(ModdedEffects.BLOODLOSS, 6000, Math.min(level, effect.getAmplifier() + 1)));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
