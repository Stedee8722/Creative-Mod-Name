package net.stedee.creativemodname.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

public class ModdedTags {
    public static class Blocks {
        public static final TagKey<Block> PLUSHIES = createTag("plushies");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(CreativeModName.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> WEAPONS = createTag("weapons");

        public static final TagKey<Item> CLEAVERS = createTag("cleavers");

        public static final TagKey<Item> GODLY_CLEAVER = createTag("godly_cleaver");

        public static final TagKey<Item> MOON_STAFF = createTag("moon_staff");

        public static final TagKey<Item> PLUSHIES = createTag("plushies");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(CreativeModName.MOD_ID, name));
        }
    }
}
