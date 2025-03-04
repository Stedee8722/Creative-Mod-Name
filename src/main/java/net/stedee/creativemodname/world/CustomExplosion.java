package net.stedee.creativemodname.world;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.stedee.creativemodname.enchantment.ModdedEnchantments;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;
import net.stedee.creativemodname.util.EnchantmentsUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
    private final RegistryEntry<SoundEvent> soundEvent;
    private final ItemStack item;

    public CustomExplosion(ItemStack item, World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, ParticleEffect particle, ParticleEffect emitterParticle, RegistryEntry<SoundEvent> soundEvent, @Nullable Entity owner) {
        super(world, entity, damageSource, behavior, x, y, z, power, createFire, destructionType, particle, emitterParticle, soundEvent);
        this.world = world;
        this.entity = entity;
        this.power = power;
        this.item = item;
        this.x = x;
        this.y = y;
        this.z = z;
        this.damageSource = damageSource == null ? world.getDamageSources().explosion(this) : damageSource;
        this.behavior = behavior == null ? this.chooseBehavior(entity) : behavior;
        this.owner = owner;
        this.soundEvent = soundEvent;
    }

    @SuppressWarnings({"ReassignedVariable", "SuspiciousNameCombination"})
    @Override
    public void collectBlocksAndDamageEntities() {
        this.world.emitGameEvent(this.entity, GameEvent.EXPLODE, new Vec3d(this.x, this.y, this.z));

        if (EnchantmentsUtil.hasEnchantment(this.item, ModdedEnchantments.BLOCK_EXPLOSION)) {
            Set<BlockPos> set = Sets.newHashSet();

            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    for (int l = 0; l < 16; l++) {
                        if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                            double d = j / 15.0F * 2.0F - 1.0F;
                            double e = k / 15.0F * 2.0F - 1.0F;
                            double f = l / 15.0F * 2.0F - 1.0F;
                            double g = Math.sqrt(d * d + e * e + f * f);
                            d /= g;
                            e /= g;
                            f /= g;
                            float h = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                            double m = this.x;
                            double n = this.y;
                            double o = this.z;

                            for (; h > 0.0F; h -= 0.22500001F) {
                                BlockPos blockPos = BlockPos.ofFloored(m, n, o);
                                BlockState blockState = this.world.getBlockState(blockPos);
                                FluidState fluidState = this.world.getFluidState(blockPos);
                                if (!this.world.isInBuildLimit(blockPos)) {
                                    break;
                                }

                                Optional<Float> optional = this.behavior.getBlastResistance(this, this.world, blockPos, blockState, fluidState);
                                if (optional.isPresent()) {
                                    h -= (optional.get() + 0.3F) * 0.3F;
                                }

                                if (h > 0.0F && this.behavior.canDestroyBlock(this, this.world, blockPos, blockState, h)) {
                                    set.add(blockPos);
                                }

                                m += d * 0.3F;
                                n += e * 0.3F;
                                o += f * 0.3F;
                            }
                        }
                    }
                }
            }

            this.affectedBlocks.addAll(set);
        }

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
                        if (entity instanceof PlayerEntity playerEntity) {
                            playerEntity.currentExplosionImpactPos = playerEntity.getPos();
                            playerEntity.explodedBy = getOwner();
                            playerEntity.setIgnoreFallDamageFromCurrentExplosion(true);
                        }
                        if (this.behavior.shouldDamage(this, entity)) {
                            float damage;
                            if (!EnchantmentsUtil.hasEnchantment(this.item, ModdedEnchantments.FIREBALL_JUMPING)) {
                                if (entity != owner) {
                                    damage = this.behavior.calculateDamage(this, entity);
                                } else {
                                    damage = this.behavior.calculateDamage(this, entity) / 2;
                                }
                            } else {
                                if (entity != owner) {
                                    damage = this.behavior.calculateDamage(this, entity) / 2;
                                } else {
                                    damage = 1;
                                }
                            }
                            entity.damage(this.damageSource, damage);
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
                        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                            serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
                        }
                        if (entity instanceof PlayerEntity playerEntity) {
                            if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
                                this.affectedPlayers.put(playerEntity, vec3d2);
                            }
                        }

                        if (entity == getOwner()) {
                            return;
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

        boolean bl = this.shouldDestroy();
        if (bl) {
            this.world.getProfiler().push("explosion_blocks");
            List<Pair<ItemStack, BlockPos>> list = new ArrayList<>();
            Util.shuffle(this.affectedBlocks, this.world.random);

            for (BlockPos blockPos : this.affectedBlocks) {
                this.world.getBlockState(blockPos).onExploded(this.world, blockPos, this, (stack, pos) -> tryMergeStack(list, stack, pos));
            }

            for (Pair<ItemStack, BlockPos> pair : list) {
                Block.dropStack(this.world, pair.getSecond(), pair.getFirst());
            }

            this.world.getProfiler().pop();
        }
    }

    private static void tryMergeStack(List<Pair<ItemStack, BlockPos>> stacks, ItemStack stack, BlockPos pos) {
        for (int i = 0; i < stacks.size(); i++) {
            Pair<ItemStack, BlockPos> pair = stacks.get(i);
            ItemStack itemStack = pair.getFirst();
            if (ItemEntity.canMerge(itemStack, stack)) {
                stacks.set(i, Pair.of(ItemEntity.merge(itemStack, stack, 16), pair.getSecond()));
                if (stack.isEmpty()) {
                    return;
                }
            }
        }

        stacks.add(Pair.of(stack, pos));
    }
}
