package com.lowlevel.minecraft.packet.impl;

import com.lowlevel.minecraft.packet.Packet;
import com.lowlevel.minecraft.packet.PacketDataSerializer;
import com.lowlevel.minecraft.packet.VersionRange;
import com.lowlevel.minecraft.util.ProtocolVersion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerUpdatePacket extends Packet {


    boolean onGround;
    public PlayerUpdatePacket() {
        super(new VersionRange(ProtocolVersion.V1_7, ProtocolVersion.V1_21_4), "PlayerPositionPacket");
    }

    public PlayerUpdatePacket(boolean onGround) {
        this();
        this.onGround = onGround;
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }

    @Override
    public void deserialize(byte[] data) {

    }

    public void writeOnGround(PacketDataSerializer dataSerializer) {
        dataSerializer.writeByte(onGround ? 1 : 0);
    }

    public void readOnGround(PacketDataSerializer dataSerializer) {
        this.onGround = dataSerializer.readUnsignedByte() == 1;
    }
}
