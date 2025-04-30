package net.stedee.creativemodname.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.block.entity.custom.PlushBlockEntity;

public class ModdedBlockEntities {
    public static final BlockEntityType<PlushBlockEntity> PLUSH_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CreativeModName.MOD_ID, "plush_be"),
                    BlockEntityType.Builder.create(
                            PlushBlockEntity::new,
                            ModdedPlushieBlocks.PLUSH_ACID,
                            ModdedPlushieBlocks.PLUSH_MARSH,
                            ModdedPlushieBlocks.PLUSH_AYM,
                            ModdedPlushieBlocks.PLUSH_HYPNO_RAT,
                            ModdedPlushieBlocks.PLUSH_RAT
                    ).build(null));

    public static void registerBlockEntities() {
        CreativeModName.LOGGER.info("Registering Block Entities for " + CreativeModName.MOD_ID);
    }
}
