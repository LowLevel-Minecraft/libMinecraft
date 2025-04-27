/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package com.lowlevel.minecraft.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

//Protocol Version numbers: https://wiki.vg/Protocol_version_numbers
@Getter
@AllArgsConstructor
public enum ProtocolVersion {
    V1_7(4, "v1_7_R3"),
    V1_7_10(5, "v1_7_R4"),
    V1_8(45, "v1_8_R1"),
    V1_8_5(46, "v1_8_R2"),
    V1_8_9(47, "v1_8_R3"),
    V1_9(107, "v1_9_R1"),
    V1_9_1(108, "v1_9_R1"),
    V1_9_2(109, "v1_9_R2"),
    V1_9_4(110, "v1_9_R2"),
    V1_10(210, "v1_10_R1"),
    V1_10_2(210, "v1_10_R1"),
    V1_11(316, "v1_11_R1"),
    V1_12(335, "v1_12_R1"),
    V1_12_1(338, null),
    V1_12_2(340, "v1_12_R1"),
    V1_13(350, "v1_13_R1"),
    V1_13_1(351, "v1_13_R2"),
    V1_13_2(352, "v1_13_R2"),
    V1_14(477, "v1_14_R1"),
    V1_14_1(480, "v1_14_R1"),
    V1_14_2(485, "v1_14_R1"),
    V1_14_3(490, "v1_14_R1"),
    V1_14_4(498, "v1_14_R1"),
    V1_15(573, "v1_15_R1"),
    V1_15_1(575, "v1_15_R1"),
    V1_15_2(578, "v1_15_R1"),
    V1_16(735, "v1_16_R1"),
    V1_16_1(736, "v1_16_R1"),
    V1_16_2(751, "v1_16_R2"),
    V1_16_3(753, "v1_16_R2"),
    V1_16_4(754, "v1_16_R3"),
    V1_16_5(754, "v1_16_R3"),
    V1_17(755, "v1_17_R1"),
    V1_17_1(756, "v1_17_R1"),
    V1_18(757, "v1_18_R1"),
    V1_18_2(758, "v1_18_R2"),
    V1_19(759, "v1_19_R1"),

    V1_19_1(760, "v1_19_R1"),
    V1_19_3(761, "v1_19_R1"),
    V1_19_4(762, "v1_19_R4"),
    V1_20_1(763, "v1_20_R1"),
    V1_20_2(764, "v1_20_R1"),
    V1_20_3(765, "v1_20_R1"),
    V1_20_5(766, "v1_20_R1"),
    V1_21_1(767, "v1_21_R1"),
    V1_21_2(768, "v1_21_R1"),
    V1_21_4(769, "v1_21_R1"),

    UNKNOWN(-1, "UNKNOWN");

    private final int version;
    private final String serverVersion;

    public static ProtocolVersion getVersion(int versionId) {
        for (ProtocolVersion version : values()) {
            if (version.getVersion() == versionId) return version;
        }
        return UNKNOWN;
    }

    public boolean isBelow(ProtocolVersion version) {
        return this.getVersion() < version.getVersion();
    }

    public boolean isOrBelow(ProtocolVersion version) {
        return this.getVersion() <= version.getVersion();
    }

    public boolean isAbove(ProtocolVersion version) {
        return this.getVersion() > version.getVersion();
    }

    public boolean isOrAbove(ProtocolVersion version) {
        return this.getVersion() >= version.getVersion();
    }

    public static final ProtocolVersion NEWEST_VERSION;

    static {
        ProtocolVersion newest = V1_7;

        for (ProtocolVersion version : values()) {
            if (version.getVersion() > newest.getVersion()) {
                newest = version;
            }
        }

        NEWEST_VERSION = newest;
    }
}
