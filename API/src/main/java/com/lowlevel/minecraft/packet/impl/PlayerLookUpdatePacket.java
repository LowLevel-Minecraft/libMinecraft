package com.lowlevel.minecraft.packet.impl;

import com.lowlevel.minecraft.packet.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerLookUpdatePacket extends PlayerUpdatePacket{

    private float yaw;
    private float pitch;

    public PlayerLookUpdatePacket(float yaw, float pitch, boolean onGround) {
        super(onGround);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public byte[] serialize() {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());

        dataSerializer.writeFloat(yaw);
        dataSerializer.writeFloat(pitch);
        writeOnGround(dataSerializer);

        return dataSerializer.array();
    }

    @Override
    public void deserialize(byte[] data) {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data));

        this.yaw = dataSerializer.readFloat();
        this.pitch = dataSerializer.readFloat();
        readOnGround(dataSerializer);
    }
}
