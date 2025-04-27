package com.lowlevel.minecraft.packet.impl;

import com.lowlevel.minecraft.packet.PacketDataSerializer;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerPositionUpdatePacket extends PlayerUpdatePacket {

    private double x;
    private double y;
    private double z;
    public PlayerPositionUpdatePacket() {
        super();
    }

    public PlayerPositionUpdatePacket(double x, double y, double z, boolean onGround) {
        super(onGround);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public byte[] serialize() {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.buffer());

        dataSerializer.writeDouble(x);
        dataSerializer.writeDouble(y);
        dataSerializer.writeDouble(z);
        writeOnGround(dataSerializer);

        return dataSerializer.array();
    }

    @Override
    public void deserialize(byte[] data) {
        PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(data));

        this.x = dataSerializer.readDouble();
        this.y = dataSerializer.readDouble();
        this.z = dataSerializer.readDouble();
        readOnGround(dataSerializer);
    }
}
