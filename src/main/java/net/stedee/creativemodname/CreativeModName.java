package net.stedee.creativemodname;

import net.fabricmc.api.ModInitializer;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.effect.ModdedEffects;
import net.stedee.creativemodname.enchantment.ModdedEnchantmentEffects;
import net.stedee.creativemodname.entity.ModdedEntities;
import net.stedee.creativemodname.item.ModdedItemGroups;
import net.stedee.creativemodname.item.ModdedItems;
import net.stedee.creativemodname.networking.InitNetworking;
import net.stedee.creativemodname.networking.ModdedServerPackets;
import net.stedee.creativemodname.sound.ModdedSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreativeModName implements ModInitializer {
    public static final String MOD_ID = "creativemodname";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModdedItemGroups.registerItemGroup();

        ModdedItems.registerModItems();
        ModdedPlushieBlocks.registerModBlocks();

        ModdedEntities.registerEntities();

        ModdedSounds.registerSounds();

        ModdedEffects.registerEffects();

        InitNetworking.registerC2SPackets();
        InitNetworking.registerS2CPackets();
        ModdedServerPackets.registerC2SPacketReceivers();

        ModdedEnchantmentEffects.registerEnchantmentEffects();
    }
}
