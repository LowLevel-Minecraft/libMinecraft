package com.lowlevel.minecraft.util.mcp;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SectionPosition extends BlockPos {
    public static final int a = 4;
    public static final int b = 16;
    private static final int g = 15;
    public static final int c = 8;
    public static final int d = 15;
    private static final int h = 22;
    private static final int i = 20;
    private static final int j = 22;
    private static final long k = 4194303L;
    private static final long l = 1048575L;
    private static final long m = 4194303L;
    private static final int n = 0;
    private static final int o = 20;
    private static final int p = 42;
    private static final int q = 8;
    private static final int r = 0;
    private static final int s = 4;

    SectionPosition(int var0, int var1, int var2) {
        super(var0, var1, var2);
    }

    public static SectionPosition a(int var0, int var1, int var2) {
        return new SectionPosition(var0, var1, var2);
    }

    public static SectionPosition a(BlockPos var0) {
        return new SectionPosition(a(var0.getX()), a(var0.getY()), a(var0.getZ()));
    }

    public static SectionPosition a(ChunkCoordIntPair var0, int var1) {
        return new SectionPosition(var0.x, var1, var0.z);
    }

    public static SectionPosition a(long var0) {
        return new SectionPosition(b(var0), c(var0), d(var0));
    }

    public static long a(long var0, EnumFacing var2) {
        return a(var0, var2.getFrontOffsetX(), var2.getFrontOffsetY(), var2.getFrontOffsetZ());
    }

    public static long a(long var0, int var2, int var3, int var4) {
        return b(b(var0) + var2, c(var0) + var3, d(var0) + var4);
    }

    public static int a(double var0) {
        return a(MathHelper.floor_double(var0));
    }

    public static int a(int var0) {
        return var0 >> 4;
    }

    public static int b(int var0) {
        return var0 & 15;
    }

    public static short b(BlockPos var0) {
        int var1 = b(var0.getX());
        int var2 = b(var0.getY());
        int var3 = b(var0.getZ());
        return (short)(var1 << 8 | var3 << 4 | var2 << 0);
    }

    public static int a(short var0) {
        return var0 >>> 8 & 15;
    }

    public static int b(short var0) {
        return var0 >>> 0 & 15;
    }

    public static int c(short var0) {
        return var0 >>> 4 & 15;
    }

    public int d(short var0) {
        return this.d() + a(var0);
    }

    public int e(short var0) {
        return this.e() + b(var0);
    }

    public int f(short var0) {
        return this.f() + c(var0);
    }

    public BlockPos g(short var0) {
        return new BlockPos(this.d(var0), this.e(var0), this.f(var0));
    }

    public static int c(int var0) {
        return var0 << 4;
    }

    public static int a(int var0, int var1) {
        return c(var0) + var1;
    }

    public static int b(long var0) {
        return (int)(var0 << 0 >> 42);
    }

    public static int c(long var0) {
        return (int)(var0 << 44 >> 44);
    }

    public static int d(long var0) {
        return (int)(var0 << 22 >> 42);
    }

    public int a() {
        return this.getX();
    }

    public int b() {
        return this.getY();
    }

    public int c() {
        return this.getZ();
    }

    public int d() {
        return c(this.a());
    }

    public int e() {
        return c(this.b());
    }

    public int f() {
        return c(this.c());
    }

    public int g() {
        return a(this.a(), 15);
    }

    public int h() {
        return a(this.b(), 15);
    }

    public int i() {
        return a(this.c(), 15);
    }

    public static long e(long var0) {
        return b(a(BlockPos.getXFromLong(var0)), a(BlockPos.getYFromLong(var0)), a(BlockPos.getZFromLong(var0)));
    }

    public static long f(long var0) {
        return var0 & -1048576L;
    }

    public BlockPos p() {
        return new BlockPos(c(this.a()), c(this.b()), c(this.c()));
    }

    public BlockPos q() {
        int var0 = 8;
        return this.p().c(8, 8, 8);
    }

    public ChunkCoordIntPair r() {
        return new ChunkCoordIntPair(this.a(), this.c());
    }

    public static long c(BlockPos var0) {
        return b(a(var0.getX()), a(var0.getY()), a(var0.getZ()));
    }

    public static long b(int var0, int var1, int var2) {
        long var3 = 0L;
        var3 |= ((long)var0 & 4194303L) << 42;
        var3 |= ((long)var1 & 1048575L) << 0;
        var3 |= ((long)var2 & 4194303L) << 20;
        return var3;
    }

    public long s() {
        return b(this.a(), this.b(), this.c());
    }

    public SectionPosition c(int var0, int var1, int var2) {
        return var0 == 0 && var1 == 0 && var2 == 0 ? this : new SectionPosition(this.a() + var0, this.b() + var1, this.c() + var2);
    }

    public Stream<BlockPos> t() {
        return BlockPos.a(this.d(), this.e(), this.f(), this.g(), this.h(), this.i());
    }

    public static Stream<SectionPosition> stream(SectionPosition center, int radius) {
        int i = center.getX();
        int j = center.getY();
        int k = center.getZ();
        return stream(i - radius, j - radius, k - radius, i + radius, j + radius, k + radius);
    }

    public static Stream<SectionPosition> stream(ChunkCoordIntPair center, int radius, int minY, int maxY) {
        int i = center.x;
        int j = center.z;
        return stream(i - radius, minY, j - radius, i + radius, maxY, j + radius);
    }

    public static Stream<SectionPosition> stream(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<>((long) (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1), Spliterator.SIZED) {
            final CursorPosition iterator = new CursorPosition(minX, minY, minZ, maxX, maxY, maxZ);

            public boolean tryAdvance(Consumer<? super SectionPosition> consumer) {
                if (this.iterator.step()) {
                    consumer.accept(new SectionPosition(this.iterator.getX(), this.iterator.getY(), this.iterator.getZ()));
                    return true;
                } else {
                    return false;
                }
            }
        }, false);
    }
}
