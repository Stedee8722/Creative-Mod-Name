package net.stedee.creativemodname.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.custom.PlushBlock;
import net.stedee.creativemodname.item.custom.PlushieItem;

import java.util.function.ToIntFunction;

public class ModdedPlushieBlocks {
    public static final Block PLUSH_AYM = registerBlock("plush_aym",
            new PlushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .nonOpaque()
                    .luminance(createLightLevelFromLitBlockState(13))
                    .strength(0.3F, 1200F), () -> {
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanBiFunction.OR);
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 1.1875, 0.8125), BooleanBiFunction.OR);

                return shape;
            }));

    public static final Block PLUSH_RAT = registerBlock("plush_rat",
            new PlushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .nonOpaque()
                    .luminance(createLightLevelFromLitBlockState(13))
                    .strength(0.3F, 1200F), () -> {
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.28125, 0.71875, 0.9375, 0.71875), BooleanBiFunction.OR);

                return shape;
            }));

    public static final Block PLUSH_HYPNO_RAT = registerBlock("plush_hypno_rat",
            new PlushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .nonOpaque()
                    .luminance(createLightLevelFromLitBlockState(13))
                    .strength(0.3F, 1200F), () -> {
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.28125, 0.71875, 0.9375, 0.71875), BooleanBiFunction.OR);

                return shape;
            }));

    public static final Block PLUSH_ACID = registerBlock("plush_acid",
            new PlushBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CLEAR)
                    .nonOpaque()
                    .luminance(createLightLevelFromLitBlockState(13))
                    .strength(0.3F, 1200F), () -> {
                VoxelShape shape = VoxelShapes.empty();
                shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.28125, 0, 0.28125, 0.71875, 1.25, 0.71875), BooleanBiFunction.OR);

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

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }
}
