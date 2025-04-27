package com.lowlevel.minecraft.generic;

import java.util.Objects;

public class Rotation2D {
    private float yaw;
    private float pitch;

    public Rotation2D(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rotation2D that)) return false;
        return Float.compare(yaw, that.yaw) == 0 && Float.compare(pitch, that.pitch) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(yaw, pitch);
    }

    @Override
    public String toString() {
        return "Rotation2D{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }
}
