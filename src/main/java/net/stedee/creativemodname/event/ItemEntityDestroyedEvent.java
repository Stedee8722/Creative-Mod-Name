package net.stedee.creativemodname.event;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.stedee.creativemodname.access.IServerWorld;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.criterion.ModdedCriteria;
import net.stedee.creativemodname.item.ModdedItems;
import net.stedee.creativemodname.networking.packet.RenderBeaconBeamS2CPacket;
import net.stedee.creativemodname.sound.ModdedSounds;

public class ItemEntityDestroyedEvent {
    public static void ItemEntityDestroyed(ItemEntity itemEntity, PlayerEntity owner, DamageSource reason) {
        ModdedCriteria.ITEM_ENTITY_REMOVED.trigger((ServerPlayerEntity) owner, itemEntity.getStack(), reason.getTypeRegistryEntry().getKey(), itemEntity.getWorld());
        // check nether star
        if (!itemEntity.getWorld().isClient() && itemEntity.getStack().getItem() == Items.NETHER_STAR && reason.isOf(DamageTypes.OUT_OF_WORLD) && itemEntity.getWorld().getRegistryKey() == World.END) {
            BlockPos blockPos = itemEntity.getBlockPos().withY(owner.getBlockY());
            World world = itemEntity.getWorld();
            if (!owner.isOnGround()) {
                Vec3d startPos = owner.getPos();
                Vec3d endPos = startPos.add(0, -256, 0);

                BlockHitResult result = world.raycast(new RaycastContext(
                        startPos,
                        endPos,
                        RaycastContext.ShapeType.OUTLINE,
                        RaycastContext.FluidHandling.NONE,
                        owner
                ));

                if (result.getType() != HitResult.Type.MISS) {
                    blockPos = blockPos.withY(result.getBlockPos().getY());
                }
            }
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
                ServerPlayNetworking.send(player, new RenderBeaconBeamS2CPacket(blockPos, 1000)); // tick
            }
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    for (int y = -1; y < 3; y++) {
                        BlockPos blockPos2 = blockPos.add(x, y, z);
                        BlockState blockState = y == -1 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                        if (!world.getBlockState(blockPos2).isOf(blockState.getBlock())) {
                            world.breakBlock(blockPos2, true, null);
                        }
                        world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                    }
                }
            }
            world.playSound(null, blockPos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.BLOCKS, 0.25F, world.random.nextFloat() * 0.4F + 0.8F);
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
                ((ServerWorld) world).spawnParticles(player, ParticleTypes.EXPLOSION, false, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 30, 5, 5, 5, 1);
                ((ServerWorld) world).spawnParticles(player, ParticleTypes.PORTAL, false, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 100, 0, 0, 0, 1);
            }

            BlockPos finalBlockPos = blockPos;
            ((IServerWorld) itemEntity.getWorld()).creativemodname$setTimer(50, () -> {
                ItemEntity newItemEntity = new ItemEntity(
                        world,
                        finalBlockPos.getX() + 0.5,
                        finalBlockPos.getY() + 0.5,
                        finalBlockPos.getZ() + 0.5,
                        new ItemStack(ModdedItems.WORN_CLEAVER)
                );

                newItemEntity.setVelocity(0, 0.1, 0);

                world.spawnEntity(newItemEntity);
                world.playSound(null, finalBlockPos, ModdedSounds.ITEM_POPS, SoundCategory.BLOCKS, 1F, 1F);
            });
        }

        // check plush aym
        if (!itemEntity.getWorld().isClient() && itemEntity.getStack().getItem() == ModdedPlushieBlocks.PLUSH_AYM.asItem() && reason.isOf(DamageTypes.IN_FIRE)) {
            BlockPos blockPos = itemEntity.getBlockPos();
            World world = itemEntity.getWorld();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos blockPos2 = blockPos.add(x, 0, z);
                    if (world.getBlockState(blockPos2).isOf(Blocks.FIRE)) {
                        world.breakBlock(blockPos2.add(0, -1, 0), false);
                        world.setBlockState(blockPos2.add(0, -1, 0), Blocks.SOUL_SOIL.getDefaultState(), Block.NOTIFY_ALL);
                        world.setBlockState(blockPos2, Blocks.SOUL_FIRE.getDefaultState(), Block.NOTIFY_ALL);
                        blockPos = blockPos2;
                        break;
                    } else if (world.getBlockState(blockPos2).isOf(Blocks.SOUL_FIRE)) {
                        world.breakBlock(blockPos2.add(0, -1, 0), false);
                        world.setBlockState(blockPos2.add(0, -1, 0), Blocks.NETHERRACK.getDefaultState(), Block.NOTIFY_ALL);
                        world.setBlockState(blockPos2, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
                        blockPos = blockPos2;
                        break;
                    }
                }
            }
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, blockPos)) {
                ((ServerWorld) world).spawnParticles(player, ParticleTypes.END_ROD, false, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 50, 0, 0, 0, 1);
            }
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            world.spawnEntity(lightning);
            world.playSound(null, blockPos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 2.5F, 1F);
            BlockPos finalBlockPos = blockPos;
            ((IServerWorld) itemEntity.getWorld()).creativemodname$setTimer(70, () -> {
                ItemEntity newItemEntity = new ItemEntity(
                        world,
                        finalBlockPos.getX() + 0.5,
                        finalBlockPos.getY() + 0.5,
                        finalBlockPos.getZ() + 0.5,
                        new ItemStack(ModdedItems.MOON_STAFF)
                );

                itemEntity.setVelocity(0, 0.1, 0);

                world.spawnEntity(newItemEntity);
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        world.breakBlock(finalBlockPos.add(x, 0, z), false);
                    }
                }
                world.playSound(null, finalBlockPos, ModdedSounds.ITEM_POPS, SoundCategory.BLOCKS, 1F, 1F);
            });
        }
    }
}
