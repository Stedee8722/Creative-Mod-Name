package net.stedee.creativemodname.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;
import net.stedee.creativemodname.entity.ModdedEntities;
import net.stedee.creativemodname.event.KeyInputHandler;
import net.stedee.creativemodname.networking.ModdedPackets;

public class CreativeModNameClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // To make some parts of the block transparent (like glass, saplings and doors):
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_AYM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_RAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_HYPNO_RAT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_ACID, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModdedEntities.STAFF_FIREBALL_ENTITY, (context) -> new FlyingItemEntityRenderer<>(context, 3.0F, true));

        KeyInputHandler.register();

        ModdedPackets.registerS2CPackets();
    }
}
