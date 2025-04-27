//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lowlevel.minecraft.util.mcp;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class ChunkCoordIntPair {
    public static final long a = pair(1875016, 1875016);
    private static final long d = 32L;
    private static final long e = 4294967295L;
    private static final int f = 5;
    private static final int g = 31;
    public final int x;
    public final int z;
    private static final int h = 1664525;
    private static final int i = 1013904223;
    private static final int j = -559038737;

    public ChunkCoordIntPair(int var0, int var1) {
        this.x = var0;
        this.z = var1;
    }

    public ChunkCoordIntPair(BlockPos var0) {
        this.x = SectionPosition.a(var0.getX());
        this.z = SectionPosition.a(var0.getZ());
    }

    public ChunkCoordIntPair(long var0) {
        this.x = (int)var0;
        this.z = (int)(var0 >> 32);
    }

    public long pair() {
        return pair(this.x, this.z);
    }

    public static long pair(int var0, int var1) {
        return (long)var0 & 4294967295L | ((long)var1 & 4294967295L) << 32;
    }

    public static long a(BlockPos var0) {
        return pair(SectionPosition.a(var0.getX()), SectionPosition.a(var0.getZ()));
    }

    public static int getX(long var0) {
        return (int)(var0 & 4294967295L);
    }

    public static int getZ(long var0) {
        return (int)(var0 >>> 32 & 4294967295L);
    }

    public int hashCode() {
        int var0 = 1664525 * this.x + 1013904223;
        int var1 = 1664525 * (this.z ^ -559038737) + 1013904223;
        return var0 ^ var1;
    }

    public boolean equals(Object var0) {
        if (this == var0) {
            return true;
        } else if (!(var0 instanceof ChunkCoordIntPair)) {
            return false;
        } else {
            ChunkCoordIntPair var1 = (ChunkCoordIntPair)var0;
            return this.x == var1.x && this.z == var1.z;
        }
    }

    public int b() {
        return this.a(8);
    }

    public int c() {
        return this.b(8);
    }

    public int d() {
        return SectionPosition.c(this.x);
    }

    public int e() {
        return SectionPosition.c(this.z);
    }

    public int f() {
        return this.a(15);
    }

    public int g() {
        return this.b(15);
    }

    public int getRegionX() {
        return this.x >> 5;
    }

    public int getRegionZ() {
        return this.z >> 5;
    }

    public int j() {
        return this.x & 31;
    }

    public int k() {
        return this.z & 31;
    }

    public BlockPos a(int var0, int var1, int var2) {
        return new BlockPos(this.a(var0), var1, this.b(var2));
    }

    public int a(int var0) {
        return SectionPosition.a(this.x, var0);
    }

    public int b(int var0) {
        return SectionPosition.a(this.z, var0);
    }

    public BlockPos c(int var0) {
        return new BlockPos(this.b(), var0, this.c());
    }

    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    public BlockPos l() {
        return new BlockPos(this.d(), 0, this.e());
    }

    public int a(ChunkCoordIntPair var0) {
        return Math.max(Math.abs(this.x - var0.x), Math.abs(this.z - var0.z));
    }

    public static Stream<ChunkCoordIntPair> stream(ChunkCoordIntPair center, int radius) {
        return stream(new ChunkCoordIntPair(center.x - radius, center.z - radius), new ChunkCoordIntPair(center.x + radius, center.z + radius));
    }

    public static Stream<ChunkCoordIntPair> stream(ChunkCoordIntPair pos1, ChunkCoordIntPair pos2) {
        int i = Math.abs(pos1.x - pos2.x) + 1;
        int j = Math.abs(pos1.z - pos2.z) + 1;
        final int k = pos1.x < pos2.x ? 1 : -1;
        final int l = pos1.z < pos2.z ? 1 : -1;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkCoordIntPair>(i * j, 64) {
            @Nullable
            private ChunkCoordIntPair position;

            public boolean tryAdvance(Consumer<? super ChunkCoordIntPair> consumer) {
                if (this.position == null) {
                    this.position = pos1;
                } else {
                    int ix = this.position.x;
                    int jx = this.position.z;
                    if (ix == pos2.x) {
                        if (jx == pos2.z) {
                            return false;
                        }

                        this.position = new ChunkCoordIntPair(pos1.x, jx + l);
                    } else {
                        this.position = new ChunkCoordIntPair(ix + k, jx);
                    }
                }

                consumer.accept(this.position);
                return true;
            }
        }, false);
    }
}
