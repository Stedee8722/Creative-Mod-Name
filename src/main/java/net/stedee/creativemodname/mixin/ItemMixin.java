package net.stedee.creativemodname.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.access.IItemInterface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements IItemInterface {
    @Override
    public void creativemodname$onItemEntityDestroyed(ItemEntity entity, DamageSource damageSource) {
        Item item = (Item) (Object) this;
        item.onItemEntityDestroyed(entity);
        CreativeModName.LOGGER.info("Item destroyed: {} (Reason: {})", item.getName(), damageSource.getName());
    }
}
