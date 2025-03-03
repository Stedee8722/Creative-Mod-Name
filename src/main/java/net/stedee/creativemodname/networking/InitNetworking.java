package net.stedee.creativemodname.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;
import net.stedee.creativemodname.networking.packet.MakeSoundC2SPacket;

public class InitNetworking {
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(CustomExplosionS2CPacket.ID, CustomExplosionS2CPacket.CODEC);
    }

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MakeSoundC2SPacket.ID, MakeSoundC2SPacket.CODEC);
    }
}
