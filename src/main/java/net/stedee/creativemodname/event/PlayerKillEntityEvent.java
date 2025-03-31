package net.stedee.creativemodname.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.stedee.creativemodname.item.ModdedItems;

public class PlayerKillEntityEvent {
    public static void spawnCleaverEvent(World world, Entity entity, LivingEntity livingEntity) {
        if (entity instanceof ServerPlayerEntity player) {
            int enderDragonKilled = player.getStatHandler().getStat(Stats.KILLED.getOrCreateStat(EntityType.ENDER_DRAGON));
            int wardenKilled = player.getStatHandler().getStat(Stats.KILLED.getOrCreateStat(EntityType.WARDEN));
            int witherKilled = player.getStatHandler().getStat(Stats.KILLED.getOrCreateStat(EntityType.WITHER));

            ItemEntity itemEntity = new ItemEntity(
                world,
                entity.getX() + 0.5,
                entity.getY() + 0.5,
                entity.getZ() + 0.5,
                new ItemStack(ModdedItems.GODLY_CLEAVER)
            );

            if ((livingEntity instanceof WitherEntity)) {
                if ((enderDragonKilled >= witherKilled && wardenKilled >= witherKilled))
                    world.spawnEntity(itemEntity);
            }
            if ((livingEntity instanceof EnderDragonEntity)) {
                if ((wardenKilled >= enderDragonKilled && witherKilled >= enderDragonKilled))
                    world.spawnEntity(itemEntity);
            }
            if ((livingEntity instanceof WardenEntity)) {
                 if ((enderDragonKilled >= wardenKilled && witherKilled >= wardenKilled))
                    world.spawnEntity(itemEntity);
            }
        }
    }
}
