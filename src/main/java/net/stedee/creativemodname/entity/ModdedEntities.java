package net.stedee.creativemodname.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.entity.custom.StaffFireballEntity;

public class ModdedEntities {
    public static final EntityType<StaffFireballEntity> STAFF_FIREBALL_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(CreativeModName.MOD_ID, "staff_fireball"),
            EntityType.Builder.<StaffFireballEntity>create(StaffFireballEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0F, 1.0F).maxTrackingRange(4).trackingTickInterval(10).build());

    public static void registerEntities() {
        CreativeModName.LOGGER.info("Registering Mod Entities for " + CreativeModName.MOD_ID);
    }
}
