package net.stedee.creativemodname.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;

public class ModdedItemGroups {
    public static final ItemGroup WEAPONS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(CreativeModName.MOD_ID, "weapons"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModdedItems.MOON_STAFF))
                    .displayName(Text.translatable("creativetab." + CreativeModName.MOD_ID + ".weapons"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModdedItems.MOON_STAFF);
                        entries.add(ModdedItems.CLEAVER);
                        entries.add(ModdedItems.WORN_CLEAVER);
                        entries.add(ModdedItems.GODLY_CLEAVER);
                    }))
                    .build());

    public static final ItemGroup PLUSHIES = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(CreativeModName.MOD_ID, "plushies"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModdedPlushieBlocks.PLUSH_AYM.asItem()))
                    .displayName(Text.translatable("creativetab." + CreativeModName.MOD_ID + ".plushies"))
                    .entries((((displayContext, entries) -> {
                        entries.add(ModdedPlushieBlocks.PLUSH_AYM.asItem());
                        entries.add(ModdedPlushieBlocks.PLUSH_RAT.asItem());
                        entries.add(ModdedPlushieBlocks.PLUSH_HYPNO_RAT.asItem());
                        entries.add(ModdedPlushieBlocks.PLUSH_ACID.asItem());
                    })))
                    .build());

    public static void registerItemGroup() {
        CreativeModName.LOGGER.info("Registering Item Groups for " + CreativeModName.MOD_ID);
    }
}
