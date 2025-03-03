package net.stedee.creativemodname.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

public record CustomExplosionS2CPacket(double x, double y, double z, SoundEvent soundEvent, float power, boolean particles) implements CustomPayload {
    public static final Id<CustomExplosionS2CPacket> ID = new Id<>(Identifier.of(CreativeModName.MOD_ID, "custom_explosion"));
    public static final PacketCodec<ByteBuf, CustomExplosionS2CPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.DOUBLE, CustomExplosionS2CPacket::x,
                    PacketCodecs.DOUBLE, CustomExplosionS2CPacket::y,
                    PacketCodecs.DOUBLE, CustomExplosionS2CPacket::z,
                    SoundEvent.PACKET_CODEC, CustomExplosionS2CPacket::soundEvent,
                    PacketCodecs.FLOAT, CustomExplosionS2CPacket::power,
                    PacketCodecs.BOOL, CustomExplosionS2CPacket::particles,
                    CustomExplosionS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
