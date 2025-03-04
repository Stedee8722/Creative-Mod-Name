package net.stedee.creativemodname.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.stedee.creativemodname.world.CustomExplosion;

public class StaffFireballEntity extends FireballEntity {
    private int explosionPower = 1;
    private ItemStack item;

    public StaffFireballEntity(EntityType<? extends StaffFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public StaffFireballEntity(ItemStack item, World world, LivingEntity owner, Vec3d velocity, int explosionPower) {
        super(world, owner, velocity, explosionPower);
        this.explosionPower = explosionPower;
        this.item = item;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        HitResult.Type type = hitResult.getType();
        if (type == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult)hitResult;
            Entity entity = entityHitResult.getEntity();
            if (entity.getType().isIn(EntityTypeTags.REDIRECTABLE_PROJECTILE) && entity instanceof ProjectileEntity projectileEntity) {
                projectileEntity.deflect(ProjectileDeflection.REDIRECTED, this.getOwner(), this.getOwner(), true);
            }

            this.onEntityHit(entityHitResult);
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this, null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            this.onBlockHit(blockHitResult);
            BlockPos blockPos = blockHitResult.getBlockPos();
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
        }
        //if (!this.getWorld().isClient) {
            Explosion.DestructionType destructionType =
                    this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)
                            ? this.getWorld().getGameRules().getBoolean(GameRules.MOB_EXPLOSION_DROP_DECAY)
                            ? Explosion.DestructionType.DESTROY_WITH_DECAY
                            : Explosion.DestructionType.DESTROY
                            : Explosion.DestructionType.KEEP;
            CustomExplosion explosion = new CustomExplosion(this.item, this.getWorld(), this, Explosion.createDamageSource(this.getWorld(), this), null, this.getX(), this.getY(), this.getZ(), this.explosionPower, false, destructionType, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE, getOwner());
            explosion.collectBlocksAndDamageEntities();
            explosion.affectWorld(true);
            this.discard();
        //}
    }
}
