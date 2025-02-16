package net.stedee.creativemodname.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

import java.util.Objects;

public class BloodlossEffect extends StatusEffect {
    public BloodlossEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        float baseHealth = (float) entity.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
        float amountToRemoveEachAmp = baseHealth * 15 / 100;
        if (!entity.getWorld().isClient()) {
            EntityAttributeInstance attributes = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attributes == null) {
                return true;
            }
            if (entity.getHealth() <= baseHealth - (amountToRemoveEachAmp * 1) && amplifier >= 0) {
                EntityAttributeModifier modifier_0 = new EntityAttributeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_0"), -amountToRemoveEachAmp, EntityAttributeModifier.Operation.ADD_VALUE);
                if (!attributes.hasModifier(modifier_0.id())) {
                    attributes.addPersistentModifier(modifier_0);
                }
                if (entity.getHealth() <= baseHealth - (amountToRemoveEachAmp * 2) && amplifier >= 1) {
                    EntityAttributeModifier modifier_1 = new EntityAttributeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_1"), -amountToRemoveEachAmp, EntityAttributeModifier.Operation.ADD_VALUE);
                    if (!attributes.hasModifier(modifier_1.id())) {
                        attributes.addPersistentModifier(modifier_1);
                    }
                    if (entity.getHealth() <= baseHealth - (amountToRemoveEachAmp * 3) && amplifier >= 2) {
                        EntityAttributeModifier modifier_2 = new EntityAttributeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_2"), -amountToRemoveEachAmp, EntityAttributeModifier.Operation.ADD_VALUE);
                        if (!attributes.hasModifier(modifier_2.id())) {
                            attributes.addPersistentModifier(modifier_2);
                        }
                        if (entity.getHealth() <= baseHealth - (amountToRemoveEachAmp * 4) && amplifier >= 3) {
                            EntityAttributeModifier modifier_3 = new EntityAttributeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_3"), -amountToRemoveEachAmp, EntityAttributeModifier.Operation.ADD_VALUE);
                            if (!attributes.hasModifier(modifier_3.id())) {
                                attributes.addPersistentModifier(modifier_3);
                            }
                        }
                    }
                }
            }
            if (amplifier > 3) {amplifier = 3;}
            if (entity.getHealth() > baseHealth - (amountToRemoveEachAmp * (amplifier + 1))) {
                //float health = entity.getHealth();
                //int dur = Objects.requireNonNull(entity.getEffect(ModdedEffects.BLOODLOSS.get())).getDuration();
                float amount = 0.05F + (float) amplifier / 8;
                entity.damage(entity.getDamageSources().magic(), amount);
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        if (Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).hasModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_0"))) {
            Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_0"));
            if (Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).hasModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_1"))) {
                Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_1"));
                if (Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).hasModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_2"))) {
                    Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_2"));
                    if (Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).hasModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_3"))) {
                        Objects.requireNonNull(attributeContainer.getCustomInstance(EntityAttributes.GENERIC_MAX_HEALTH)).removeModifier(Identifier.of(CreativeModName.MOD_ID, "bloodloss_effect_amp_3"));
                    }
                }
            }
        }
        super.onRemoved(attributeContainer);
    }
}
