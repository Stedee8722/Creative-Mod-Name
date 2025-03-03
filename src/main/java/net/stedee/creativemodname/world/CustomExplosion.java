package net.stedee.creativemodname.world;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class CustomExplosion extends Explosion {
    private static final ExplosionBehavior DEFAULT_BEHAVIOR = new ExplosionBehavior();
    private final World world;
    private final @Nullable Entity entity;
    private final @Nullable Entity owner;
    private final double x;
    private final double y;
    private final double z;
    private final float power;
    private final ExplosionBehavior behavior;
    private final ObjectArrayList<BlockPos> affectedBlocks = new ObjectArrayList<>();
    private final Map<PlayerEntity, Vec3d> affectedPlayers = Maps.newHashMap();
    private final DamageSource damageSource;
    private final ParticleEffect particle;
    private final ParticleEffect emitterParticle;
    private final RegistryEntry<SoundEvent> soundEvent;

    public CustomExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, ParticleEffect particle, ParticleEffect emitterParticle, RegistryEntry<SoundEvent> soundEvent, @Nullable Entity owner) {
        super(world, entity, damageSource, behavior, x, y, z, power, createFire, destructionType, particle, emitterParticle, soundEvent);
        this.world = world;
        this.entity = entity;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
        this.damageSource = damageSource == null ? world.getDamageSources().explosion(this) : damageSource;
        this.behavior = behavior == null ? this.chooseBehavior(entity) : behavior;
        this.owner = owner;
        this.particle = particle;
        this.emitterParticle = emitterParticle;
        this.soundEvent = soundEvent;
    }

    @SuppressWarnings({"ReassignedVariable", "SuspiciousNameCombination"})
    @Override
    public void collectBlocksAndDamageEntities() {
        this.world.emitGameEvent(this.entity, GameEvent.EXPLODE, new Vec3d(this.x, this.y, this.z));

        float q = this.power * 2.0F;
        int k = MathHelper.floor(this.x - (double)q - 1.0);
        int lx = MathHelper.floor(this.x + (double)q + 1.0);
        int r = MathHelper.floor(this.y - (double)q - 1.0);
        int s = MathHelper.floor(this.y + (double)q + 1.0);
        int t = MathHelper.floor(this.z - (double)q - 1.0);
        int u = MathHelper.floor(this.z + (double)q + 1.0);
        List<Entity> list = this.world.getOtherEntities(this.entity, new Box(k, r, t, lx, s, u));
        Vec3d vec3d = new Vec3d(this.x, this.y, this.z);

        for (Entity entity : list) {
            if (!entity.isImmuneToExplosion(this)) {
                double v = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double)q;
                if (v <= 1.0) {
                    double w = entity.getX() - this.x;
                    double x = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y;
                    double y = entity.getZ() - this.z;
                    double z = Math.sqrt(w * w + x * x + y * y);
                    if (z != 0.0) {
                        w /= z;
                        x /= z;
                        y /= z;
                        if (this.behavior.shouldDamage(this, entity)) {
                            entity.damage(this.damageSource,  !(entity == owner) ? this.behavior.calculateDamage(this, entity) : 1);
                        }

                        double aa = (1.0 - v) * (double)getExposure(vec3d, entity) * (double)this.behavior.getKnockbackModifier(entity);
                        double ab;
                        if (entity instanceof LivingEntity livingEntity) {
                            ab = aa * (1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE));
                        } else {
                            ab = aa;
                        }

                        w *= ab;
                        x *= ab;
                        y *= ab;
                        Vec3d vec3d2 = new Vec3d(w, x, y);
                        entity.setVelocity(entity.getVelocity().add(vec3d2));
                        if (entity instanceof PlayerEntity playerEntity) {
                            if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
                                this.affectedPlayers.put(playerEntity, vec3d2);
                            }
                        }

                        entity.onExplodedBy(this.entity);
                    }
                }
            }
        }
    }

    private ExplosionBehavior chooseBehavior(@Nullable Entity entity) {
        return entity == null ? DEFAULT_BEHAVIOR : new EntityExplosionBehavior(entity);
    }

    public Map<PlayerEntity, Vec3d> getAffectedPlayers() {
        return this.affectedPlayers;
    }

    public void clearAffectedBlocks() {
        this.affectedBlocks.clear();
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.affectedBlocks;
    }

    public @Nullable Entity getOwner() {
        return owner;
    }

    @Override
    public void affectWorld(boolean particles) {
        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, BlockPos.ofFloored(this.x, this.y, this.z))) {
            ServerPlayNetworking.send(player, new CustomExplosionS2CPacket(this.x, this.y, this.z, this.soundEvent.value(), this.power, particles));
        }
        this.world.playSound(
                this.x,
                this.y,
                this.z,
                this.soundEvent.value(),
                SoundCategory.BLOCKS,
                4.0F,
                (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F,
                false
        );

        if (particles) {
            ParticleEffect particleEffect;
            if (!(this.power < 2.0F)) {
                particleEffect = this.emitterParticle;
            } else {
                particleEffect = this.particle;
            }

            this.world.addParticle(particleEffect, this.x, this.y, this.z, 1.0, 0.0, 0.0);
        }
    }
}
