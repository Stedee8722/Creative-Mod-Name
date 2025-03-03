package net.stedee.creativemodname.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;

public class ModdedClientPackets {
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
