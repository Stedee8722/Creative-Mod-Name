package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;

import java.util.concurrent.CompletableFuture;

public class ModdedLootTableProvider extends FabricBlockLootTableProvider {
    public ModdedLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModdedPlushieBlocks.PLUSH_ACID, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_ACID));
        addDrop(ModdedPlushieBlocks.PLUSH_AYM, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_AYM));
        addDrop(ModdedPlushieBlocks.PLUSH_RAT, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_RAT));
        addDrop(ModdedPlushieBlocks.PLUSH_HYPNO_RAT, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_HYPNO_RAT));
        addDrop(ModdedPlushieBlocks.PLUSH_MARSH, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_MARSH));
        addDrop(ModdedPlushieBlocks.PLUSH_GREY, shulkerBoxDrops(ModdedPlushieBlocks.PLUSH_GREY));
    }
}
