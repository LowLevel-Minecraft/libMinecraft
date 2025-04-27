package com.lowlevel.minecraft.packet;

import com.lowlevel.minecraft.util.ProtocolVersion;

public class VersionRange {
    private ProtocolVersion start;
    private ProtocolVersion end;

    public VersionRange(ProtocolVersion start, ProtocolVersion end) {
        this.start = start;
        this.end = end;
    }

    public ProtocolVersion getStart() {
        return start;
    }

    public void setStart(ProtocolVersion start) {
        this.start = start;
    }

    public ProtocolVersion getEnd() {
        return end;
    }

    public void setEnd(ProtocolVersion end) {
        this.end = end;
    }

    public boolean isInRange(ProtocolVersion version) {
        return (start.getVersion() <= version.getVersion() && end.getVersion() >= version.getVersion());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VersionRange that)) return false;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode();
    }

    @Override
    public String toString() {
        return "VersionRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
