package com.lowlevel.minecraft.generic;

import java.util.Objects;
import java.util.Vector;

public class Location {
    private Vector3D position;
    private Rotation2D rotation;

    public Location(Vector3D position, Rotation2D rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Location(double x, double y, double z, float yaw, float pitch) {
        this.position = new Vector3D(x, y, z);
        this.rotation = new Rotation2D(yaw, pitch);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getZ() {
        return position.getZ();
    }

    public float getYaw() {
        return rotation.getYaw();
    }

    public float getPitch() {
        return rotation.getPitch();
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public void setRotation(Rotation2D rotation) {
        this.rotation = rotation;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Rotation2D getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return Objects.equals(position, location.position) && Objects.equals(rotation, location.rotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, rotation);
    }

    @Override
    public String toString() {
        return "Location{" +
                "position=" + position +
                ", rotation=" + rotation +
                '}';
    }
}
