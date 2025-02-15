package net.stedee.creativemodname.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.stedee.creativemodname.block.ModdedPlushieBlocks;

public class CreativeModNameClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // To make some parts of the block transparent (like glass, saplings and doors):
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedPlushieBlocks.PLUSH_AYM, RenderLayer.getCutout());
    }
}
