package net.stedee.creativemodname.criterion;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.criterion.custom.ItemEntityRemovedCriterion;

public class ModdedCriteria {
    public static final ItemEntityRemovedCriterion ITEM_ENTITY_REMOVED = registerCriteria("item_entity_removed", new ItemEntityRemovedCriterion());

    private static <T extends Criterion<?>> T registerCriteria(String name, T criteria) {
        return Criteria.register(CreativeModName.MOD_ID + ":" + name, criteria);
    }

    public static void registerCriterions() {
        CreativeModName.LOGGER.info("Registering Criterions for " + CreativeModName.MOD_ID);
    }
}
