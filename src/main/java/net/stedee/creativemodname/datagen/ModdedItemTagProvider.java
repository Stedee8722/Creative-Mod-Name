package net.stedee.creativemodname.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.stedee.creativemodname.item.ModdedItems;
import net.stedee.creativemodname.util.ModdedTags;

import java.util.concurrent.CompletableFuture;

public class ModdedItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModdedItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModdedTags.Items.WEAPONS)
                .add(ModdedItems.CLEAVER)
                .add(ModdedItems.GODLY_CLEAVER)
                .add(ModdedItems.WORN_CLEAVER)
                .add(ModdedItems.MOON_STAFF);

        getOrCreateTagBuilder(ModdedTags.Items.GODLY_CLEAVER)
                .add(ModdedItems.GODLY_CLEAVER);

        getOrCreateTagBuilder(ModdedTags.Items.MOON_STAFF)
                .add(ModdedItems.MOON_STAFF);

        getOrCreateTagBuilder(ModdedTags.Items.CLEAVERS)
                .add(ModdedItems.CLEAVER)
                .add(ModdedItems.GODLY_CLEAVER)
                .add(ModdedItems.WORN_CLEAVER);
    }
}
