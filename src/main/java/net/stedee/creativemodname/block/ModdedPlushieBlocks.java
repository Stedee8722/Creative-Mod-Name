package net.stedee.creativemodname.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.custom.PlushBlock;
import net.stedee.creativemodname.item.custom.PlushieItem;

public class ModdedPlushieBlocks {
    public static final Block PLUSH_AYM = registerBlock("plush_aym",
            new PlushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .nonOpaque()
                    .strength(0.3F, 1200F), () -> {
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanBiFunction.OR);
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 1.1875, 0.8125), BooleanBiFunction.OR);

                return shape;
            }));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(CreativeModName.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(CreativeModName.MOD_ID, name),
                new PlushieItem(block, new Item.Settings()
                        .maxCount(1)
                        .rarity(Rarity.EPIC)));
    }

    public static void registerModBlocks() {
        CreativeModName.LOGGER.info("Registering Mod Blocks for " + CreativeModName.MOD_ID);
    }
}
