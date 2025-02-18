package net.stedee.creativemodname.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.stedee.creativemodname.networking.packet.MakeSoundC2SPacket;

public class ModdedPackets {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MakeSoundC2SPacket.ID, MakeSoundC2SPacket.CODEC);
        registerC2SPacketReceivers();
    }

    public static void registerC2SPacketReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(MakeSoundC2SPacket.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            if (player != null) {
                StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(payload.sound().getId(), SoundCategory.PLAYERS);

                for (ServerPlayerEntity serverPlayerEntity : context.server().getPlayerManager().getPlayerList()) {
                    serverPlayerEntity.networkHandler.sendPacket(stopSoundS2CPacket);
                }
                player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), payload.sound(), SoundCategory.PLAYERS, 1F, payload.pitch());
            }
        });
    }

    public static void registerS2CPackets() {
        registerS2CPacketReceivers();
    }

    public static void registerS2CPacketReceivers() {

    }
}
