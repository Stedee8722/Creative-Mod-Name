package net.stedee.creativemodname.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.stedee.creativemodname.networking.ModdedClientPackets;

public class BeaconBeamRenderer {
    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            assert MinecraftClient.getInstance().world != null;
            ModdedClientPackets.render(context.matrixStack(), context.consumers(), context.camera(), MinecraftClient.getInstance().world.getTime());
        });
    }
}
