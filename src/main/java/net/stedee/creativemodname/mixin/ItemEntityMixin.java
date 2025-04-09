package net.stedee.creativemodname.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.stedee.creativemodname.access.IItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Override
    protected void tickInVoid() {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ((IItem) itemEntity.getStack().getItem()).creativemodname$onItemEntityDestroyed(itemEntity, itemEntity.getWorld().getDamageSources().outOfWorld());
        super.tickInVoid();
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onItemEntityDestroyed(Lnet/minecraft/entity/ItemEntity;)V"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ((IItem) this.getStack().getItem()).creativemodname$onItemEntityDestroyed(itemEntity, source);
    }
}
