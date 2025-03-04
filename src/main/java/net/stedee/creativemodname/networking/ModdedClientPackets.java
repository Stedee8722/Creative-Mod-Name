package net.stedee.creativemodname.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.stedee.creativemodname.networking.packet.CustomExplosionS2CPacket;
import net.stedee.creativemodname.networking.packet.RenderBeaconBeamS2CPacket;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class ModdedClientPackets {
    private static final Map<BlockPos, Pair<Integer, Integer>> activeBeams = new HashMap<>();
    private static final Identifier BEAM_TEXTURE = Identifier.ofVanilla("textures/entity/end_gateway_beam.png");

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

        ClientPlayNetworking.registerGlobalReceiver(RenderBeaconBeamS2CPacket.ID, (payload, context) -> {
            BlockPos pos = payload.blockPos();
            int duration = payload.dur();

            World world = context.player().getWorld();

            world.playSound(
                    payload.blockPos().getX(),
                    payload.blockPos().getY(),
                    payload.blockPos().getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE.value(),
                    SoundCategory.BLOCKS,
                    4.0F,
                    (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F,
                    false
            );

            context.client().execute(() -> activeBeams.put(pos, new Pair<>(duration, duration)));
        });
    }

    public static void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Camera camera, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        activeBeams.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            Vec3d cameraPos = camera.getPos();

            if (entry.getValue().getA() <= 0) return true; // Remove expired beams

            // Render the beam
            renderBeaconBeam(matrices, vertexConsumers, pos, cameraPos, (float) entry.getValue().getA() / entry.getValue().getB() * 100F);

            entry.setValue(new Pair<>(entry.getValue().getA() - 1, entry.getValue().getB()));
            return false;
        });
    }

    private static void renderBeaconBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, BlockPos pos, Vec3d cameraPos, float sizeScale) {
        float beamHeight = 256.0F;

        matrices.push();
        matrices.translate(pos.getX(), 0, pos.getZ());
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        // Center the beam

        float ratio = 150/175F;
        float baseOuterRadius = 0.05F;

        BeaconBlockEntityRenderer.renderBeam(
                matrices,
                vertexConsumers,
                BEAM_TEXTURE,
                0,
                beamHeight,
                0,
                0,
                256,
                DyeColor.PURPLE.getEntityColor(),
                ratio * baseOuterRadius * sizeScale,
                baseOuterRadius * sizeScale
        );

        matrices.pop();
    }
}
