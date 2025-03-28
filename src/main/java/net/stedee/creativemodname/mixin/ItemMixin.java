package net.stedee.creativemodname.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.stedee.creativemodname.access.IItem;
import net.stedee.creativemodname.event.ItemEntityDestroyedEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements IItem {
    @Override
    public void creativemodname$onItemEntityDestroyed(ItemEntity entity, DamageSource damageSource) {
        Item item = (Item) (Object) this;
        item.onItemEntityDestroyed(entity);
        if (entity.getOwner() != null && entity.getOwner() instanceof PlayerEntity playerEntity) {
        ItemEntityDestroyedEvent.ItemEntityDestroyed(entity, playerEntity, damageSource);
        }
    }
}
