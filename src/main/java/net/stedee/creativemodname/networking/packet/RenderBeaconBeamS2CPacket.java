package net.stedee.creativemodname.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.stedee.creativemodname.CreativeModName;

public record RenderBeaconBeamS2CPacket(BlockPos blockPos, int dur) implements CustomPayload {
    public static final Id<RenderBeaconBeamS2CPacket> ID = new Id<>(Identifier.of(CreativeModName.MOD_ID, "render_beacon_beam"));
    public static final PacketCodec<ByteBuf, RenderBeaconBeamS2CPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, RenderBeaconBeamS2CPacket::blockPos,
                    PacketCodecs.INTEGER, RenderBeaconBeamS2CPacket::dur,
                    RenderBeaconBeamS2CPacket::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
