package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.util.ModdedTags;

import java.util.concurrent.CompletableFuture;

public class ModdedBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModdedBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModdedTags.Blocks.PLUSHIES)
                .add(ModdedPlushieBlocks.PLUSH_ACID)
                .add(ModdedPlushieBlocks.PLUSH_AYM)
                .add(ModdedPlushieBlocks.PLUSH_HYPNO_RAT)
                .add(ModdedPlushieBlocks.PLUSH_RAT)
                .add(ModdedPlushieBlocks.PLUSH_MARSH);
    }
}
