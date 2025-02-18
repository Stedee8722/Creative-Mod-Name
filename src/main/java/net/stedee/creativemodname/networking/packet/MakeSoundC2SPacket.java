package net.stedee.creativemodname.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.stedee.creativemodname.CreativeModName;

public record MakeSoundC2SPacket(SoundEvent sound, float pitch) implements CustomPayload {
    public static final Id<MakeSoundC2SPacket> ID = new Id<>(Identifier.of(CreativeModName.MOD_ID, "make_sound"));
    public static final PacketCodec<ByteBuf, MakeSoundC2SPacket> CODEC =
            PacketCodec.tuple(
                    SoundEvent.PACKET_CODEC, MakeSoundC2SPacket::sound,
                    PacketCodecs.FLOAT, MakeSoundC2SPacket::pitch,
                    MakeSoundC2SPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
