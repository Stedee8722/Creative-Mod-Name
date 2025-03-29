package net.stedee.creativemodname.criterion.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.entity.LootContextPredicateValidator;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.stedee.creativemodname.criterion.ModdedCriteria;

import java.util.Optional;

public class ItemEntityRemovedCriterion extends AbstractCriterion<ItemEntityRemovedCriterion.Conditions> {
    @Override
    public Codec<ItemEntityRemovedCriterion.Conditions> getConditionsCodec() {
        return ItemEntityRemovedCriterion.Conditions.CODEC;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public void trigger(ServerPlayerEntity player, ItemStack pStack, Optional<RegistryKey<DamageType>> pDamageType, World world) {
        if (pDamageType.isEmpty()) {
            return;
        }
        this.trigger(player, conditions -> conditions.matches(pStack, pDamageType.get(), world.getRegistryKey()));
    }

    public record Conditions(Optional<ItemPredicate> item, Optional<RegistryKey<DamageType>> damageType, Optional<RegistryKey<World>> world)
            implements AbstractCriterion.Conditions {
        public static final Codec<ItemEntityRemovedCriterion.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(ItemEntityRemovedCriterion.Conditions::player),
                                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(ItemEntityRemovedCriterion.Conditions::item),
                                RegistryKey.createCodec(RegistryKeys.DAMAGE_TYPE).optionalFieldOf("damage_type").forGetter(ItemEntityRemovedCriterion.Conditions::damageType),
                                RegistryKey.createCodec(RegistryKeys.WORLD).optionalFieldOf("world").forGetter(ItemEntityRemovedCriterion.Conditions::world)
                        )
                        .apply(instance, (player1, item1, damageType1, world1) -> new Conditions(item1, damageType1, world1))
        );

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        public static AdvancementCriterion<ItemEntityRemovedCriterion.Conditions> create(
                Optional<ItemPredicate> item, Optional<RegistryKey<DamageType>> damageType, Optional<RegistryKey<World>> world
        ) {
            return ModdedCriteria.ITEM_ENTITY_REMOVED.create(new ItemEntityRemovedCriterion.Conditions(item, damageType, world));
        }

        public boolean matches(ItemStack pStack, RegistryKey<DamageType> pDamageType, RegistryKey<World> world) {
            return this.world.map(worldRegistryKey -> (this.item.isPresent() && this.item.get().test(pStack)) && (this.damageType.isPresent() && this.damageType.get() == pDamageType) && (worldRegistryKey == world)).orElseGet(() -> (this.item.isPresent() && this.item.get().test(pStack)) && (this.damageType.isPresent() && this.damageType.get() == pDamageType));
        }

        @Override
        public void validate(LootContextPredicateValidator validator) {
            AbstractCriterion.Conditions.super.validate(validator);
        }

        @Override
        public Optional<LootContextPredicate> player() {
            return Optional.empty();
        }
    }
}
