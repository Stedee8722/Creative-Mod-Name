package net.stedee.creativemodname.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.stedee.creativemodname.access.IItemInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getStack();
    @Shadow private int health;

    @Override
    protected void tickInVoid() {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ((IItemInterface) itemEntity.getStack().getItem()).creativemodname$onItemEntityDestroyed(itemEntity, itemEntity.getWorld().getDamageSources().outOfWorld());
        super.tickInVoid();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.getStack().isEmpty() && this.getStack().isOf(Items.NETHER_STAR) && source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        } else if (!this.getStack().takesDamageFrom(source)) {
            return false;
        } else if (this.getWorld().isClient) {
            return true;
        } else {
            this.velocityModified = true;
            this.health = (int)((float)this.health - amount);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
            if (this.health <= 0) {
                this.getStack().onItemEntityDestroyed(itemEntity);
                ((IItemInterface) this.getStack().getItem()).creativemodname$onItemEntityDestroyed(itemEntity, source);
                this.discard();
            }

            return true;
        }
    }
}
