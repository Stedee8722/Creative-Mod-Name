package net.stedee.creativemodname;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.effect.ModdedEffects;
import net.stedee.creativemodname.enchantment.ModdedEnchantmentEffects;
import net.stedee.creativemodname.entity.ModdedEntities;
import net.stedee.creativemodname.event.KeyInputHandler;
import net.stedee.creativemodname.item.ModdedItemGroups;
import net.stedee.creativemodname.item.ModdedItems;
import net.stedee.creativemodname.networking.ModdedPackets;
import net.stedee.creativemodname.sound.ModdedSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreativeModName implements ModInitializer {
    public static final String MOD_ID = "creativemodname";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Block Rendering type
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_AYM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_RAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_HYPNO_RAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_ACID, RenderLayer.getCutout());

        ModdedItemGroups.registerItemGroup();

        ModdedItems.registerModItems();
        ModdedPlushieBlocks.registerModBlocks();

        ModdedEntities.registerEntities();
        EntityRendererRegistry.register(ModdedEntities.STAFF_FIREBALL_ENTITY, (context) -> new FlyingItemEntityRenderer<>(context, 3.0F, true));

        KeyInputHandler.register();

        ModdedSounds.registerSounds();

        ModdedEffects.registerEffects();

        ModdedEnchantmentEffects.registerEnchantmentEffects();

        ModdedPackets.registerC2SPackets();
        ModdedPackets.registerS2CPackets();
    }
}
