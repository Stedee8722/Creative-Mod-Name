package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.util.ModdedTags;

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
    }
}
