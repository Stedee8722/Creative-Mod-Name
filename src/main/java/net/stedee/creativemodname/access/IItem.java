package net.stedee.creativemodname.access;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;

public interface IItem {
    default void creativemodname$onItemEntityDestroyed(ItemEntity entity, DamageSource damageSource) {}
}
