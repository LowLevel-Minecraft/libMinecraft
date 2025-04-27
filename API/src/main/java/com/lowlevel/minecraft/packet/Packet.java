package com.lowlevel.minecraft.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Packet {
    private final VersionRange protocolRange;
    private final String name;

    public abstract byte[] serialize();

    public abstract void deserialize(byte[] data);
}
