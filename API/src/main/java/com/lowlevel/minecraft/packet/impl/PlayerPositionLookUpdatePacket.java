package com.lowlevel.minecraft.packet.impl;

import com.lowlevel.minecraft.packet.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerPositionLookUpdatePacket extends PlayerUpdatePacket{

    private double x, y, z;
    private float yaw, pitch;

    public PlayerPositionLookUpdatePacket(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        super(onGround);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public byte[] serialize() {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());

        dataSerializer.writeDouble(x);
        dataSerializer.writeDouble(y);
        dataSerializer.writeDouble(z);
        dataSerializer.writeFloat(yaw);
        dataSerializer.writeFloat(pitch);
        writeOnGround(dataSerializer);

        return dataSerializer.array();
    }

    @Override
    public void deserialize(byte[] data) {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data));

        this.x = dataSerializer.readDouble();
        this.y = dataSerializer.readDouble();
        this.z = dataSerializer.readDouble();
        this.yaw = dataSerializer.readFloat();
        this.pitch = dataSerializer.readFloat();
        readOnGround(dataSerializer);
    }
}
