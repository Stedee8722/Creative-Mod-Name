package net.stedee.creativemodname.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.stedee.creativemodname.CreativeModName;
import net.stedee.creativemodname.effect.ModdedEffects;
import net.stedee.creativemodname.item.custom.CleaverItem;
import net.stedee.creativemodname.item.custom.MoonStaffItem;

public class ModdedItems {
    public static final Item MOON_STAFF = registerItem("moon_staff", new MoonStaffItem(ToolMaterials.NETHERITE, new Item.Settings()
            .rarity(Rarity.EPIC)
            .maxDamage(753)
            .fireproof()
            .maxCount(1)
            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 10, -2.4F))));

    public static final Item CLEAVER = registerItem("cleaver", new CleaverItem(ToolMaterials.IRON, new Item.Settings()
            .rarity(Rarity.UNCOMMON)
            .maxCount(508)
            .attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.IRON, 4F, -3F))
    , false));

    public static final Item WORN_CLEAVER = registerItem("worn_cleaver", new CleaverItem(ToolMaterials.DIAMOND, new Item.Settings()
            .rarity(Rarity.RARE)
            .maxCount(762)
            .attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 9F, -2.4F))
            , true, StatusEffects.HUNGER));

    public static final Item GODLY_CLEAVER = registerItem("godly_cleaver", new CleaverItem(ToolMaterials.NETHERITE, new Item.Settings()
            .rarity(Rarity.EPIC)
            .maxCount(1016)
            .attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.DIAMOND, 10F, -2.4F))
            , true, StatusEffects.HUNGER, ModdedEffects.BLOODLOSS));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(CreativeModName.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CreativeModName.LOGGER.info("Registering Mod Items for " + CreativeModName.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
        });
    }
}
