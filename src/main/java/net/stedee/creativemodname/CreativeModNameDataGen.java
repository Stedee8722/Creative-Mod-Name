package net.stedee.creativemodname;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.stedee.creativemodname.datagen.*;
import net.stedee.creativemodname.enchantment.ModdedEnchantments;

public class CreativeModNameDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModdedBlockTagProvider::new);
        pack.addProvider(ModdedItemTagProvider::new);
        pack.addProvider(ModdedLootTableProvider::new);
        pack.addProvider(ModdedRegistryProvider::new);
        pack.addProvider(ModdedRecipeProvider::new);
        pack.addProvider(ModdedAdvancementsProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModdedEnchantments::bootstrap);
    }
}
