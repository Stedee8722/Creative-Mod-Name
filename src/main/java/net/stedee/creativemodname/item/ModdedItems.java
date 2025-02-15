package net.stedee.creativemodname.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.item.custom.MoonStaffItem;

public class ModdedItems {
    public static final Item MOON_STAFF = registerItem("moon_staff", new MoonStaffItem(ToolMaterials.IRON, new Item.Settings()
            .rarity(Rarity.EPIC)
            .maxDamage(753)
            .fireproof()
            .maxCount(1)
            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 10, -2.4F))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CreativeModName.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CreativeModName.LOGGER.info("Registering Mod Items for " + CreativeModName.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
        });
    }
}
