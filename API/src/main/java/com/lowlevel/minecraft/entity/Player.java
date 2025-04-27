package com.lowlevel.minecraft.entity;

import com.lowlevel.minecraft.generic.Location;

import java.util.UUID;

public interface Player {

    String getName();

    UUID getUUID();

    String getAddress();

    Location getLocation();

    void teleport(Location location);
}
