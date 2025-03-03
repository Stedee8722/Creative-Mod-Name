package net.stedee.creativemodname.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;
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
        PayloadTypeRegistry.playS2C().register(CustomExplosionS2CPacket.ID, CustomExplosionS2CPacket.CODEC);
        registerS2CPacketReceivers();
    }

    public static void registerS2CPacketReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(CustomExplosionS2CPacket.ID, (payload, context) -> {
            World world = context.client().world;
            assert world != null;
            world.playSound(
                    payload.x(),
                    payload.y(),
                    payload.z(),
                    payload.soundEvent(),
                    SoundCategory.BLOCKS,
                    4.0F,
                    (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F,
                    false
            );

            if (payload.particles()) {
                ParticleEffect particleEffect;
                if (!(payload.power() < 2.0F)) {
                    particleEffect = ParticleTypes.EXPLOSION_EMITTER;
                } else {
                    particleEffect = ParticleTypes.EXPLOSION;
                }

                world.addParticle(particleEffect, payload.x(), payload.y(), payload.z(), 1.0, 0.0, 0.0);
            }
        });
    }
}
