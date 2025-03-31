package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.advancement.criterion.RecipeCraftedCriterion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.criterion.custom.ItemEntityRemovedCriterion;
import net.stedee.creativemodname.item.ModdedItems;
import net.stedee.creativemodname.util.ModdedTags;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModdedAdvancementsProvider extends FabricAdvancementProvider {
    public ModdedAdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup wrapperLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry rootAdvancement = Advancement.Builder.create()
                .display(
                        ModdedPlushieBlocks.PLUSH_AYM.asItem(),
                        Text.translatable("advancements.root.thank_you.title"),
                        Text.translatable("advancements.root.thank_you.description"),
                        Identifier.of(CreativeModName.MOD_ID, "textures/gui/config_bg.png"),
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("have_weapons",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create().tag(ModdedTags.Items.WEAPONS).build()
                        )
                )
                .criterion("have_plushies",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create().tag(ModdedTags.Items.PLUSHIES).build()
                        ))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, CreativeModName.MOD_ID + "/root");

        AdvancementEntry sacrificeAdvancement = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        ModdedItems.MOON_STAFF.asItem(),
                        Text.translatable("advancements.weapons.sacrifice.title"),
                        Text.translatable("advancements.weapons.sacrifice.description.dark_red")
                                .formatted(Formatting.BOLD, Formatting.DARK_RED)
                                .append(Text.translatable("advancements.weapons.sacrifice.description.gold")
                                        .formatted(Formatting.GOLD)),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("sacrifice",
                        ItemEntityRemovedCriterion.Conditions.create(
                                Optional.ofNullable(ItemPredicate.Builder.create().items(ModdedPlushieBlocks.PLUSH_AYM.asItem()).build()),
                                Optional.ofNullable(DamageTypes.IN_FIRE),
                                Optional.empty()
                        ))
                .build(consumer, CreativeModName.MOD_ID + "/weapons/sacrifice");

        AdvancementEntry craftCleaverAdvancement = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        ModdedItems.CLEAVER.asItem(),
                        Text.translatable("advancements.weapons.make_cleaver.title"),
                        Text.translatable("advancements.weapons.make_cleaver.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("craft_cleaver", RecipeCraftedCriterion.Conditions.create(Identifier.of(CreativeModName.MOD_ID, "cleaver")))
                .build(consumer, CreativeModName.MOD_ID + "/weapons/craft_cleaver");

        AdvancementEntry intoTheAbyssAdvancement = Advancement.Builder.create()
                .parent(craftCleaverAdvancement)
                .display(
                        ModdedItems.WORN_CLEAVER.asItem(),
                        Text.translatable("advancements.weapons.into_the_abyss.title"),
                        Text.translatable("advancements.weapons.into_the_abyss.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("into_the_abyss",
                        ItemEntityRemovedCriterion.Conditions.create(
                                Optional.ofNullable(ItemPredicate.Builder.create().items(Items.NETHER_STAR).build()),
                                Optional.ofNullable(DamageTypes.OUT_OF_WORLD),
                                Optional.ofNullable(World.END)
                        ))
                .build(consumer, CreativeModName.MOD_ID + "/weapons/into_the_abyss");

        AdvancementEntry bossRushAdvancement = Advancement.Builder.create()
                .parent(intoTheAbyssAdvancement)
                .display(
                        ModdedItems.GODLY_CLEAVER.asItem(),
                        Text.translatable("advancements.weapons.boss_rush.title"),
                        Text.translatable("advancements.weapons.boss_rush.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("kill_ender_dragon",
                        OnKilledCriterion.Conditions.createPlayerKilledEntity(
                                EntityPredicate.Builder.create().type(EntityType.ENDER_DRAGON)
                        )
                )
                .criterion("kill_warden",
                        OnKilledCriterion.Conditions.createPlayerKilledEntity(
                                EntityPredicate.Builder.create().type(EntityType.WARDEN)
                        )
                )
                .criterion("kill_wither",
                        OnKilledCriterion.Conditions.createPlayerKilledEntity(
                                EntityPredicate.Builder.create().type(EntityType.WITHER)
                        )
                )
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, CreativeModName.MOD_ID + "/weapons/boss_rush");
    }
}
