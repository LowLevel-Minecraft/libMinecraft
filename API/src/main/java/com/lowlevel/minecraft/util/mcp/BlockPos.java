package com.lowlevel.minecraft.util.mcp;

import com.google.common.collect.AbstractIterator;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BlockPos extends Vec3i {
    
    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
    private static final int field_177990_b = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
    private static final int field_177991_c = field_177990_b;
    private static final int field_177989_d = 64 - field_177990_b - field_177991_c;
    private static final int field_177987_f = field_177991_c;
    private static final int field_177988_g = field_177987_f + field_177989_d;
    private static final long field_177994_h = (1L << field_177990_b) - 1L;
    private static final long field_177995_i = (1L << field_177989_d) - 1L;
    private static final long field_177993_j = (1L << field_177991_c) - 1L;
    // private static final String __OBFID = "CL_00002334";

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(double x, double y, double z) {
        super(x, y, z);
    }


    public BlockPos(Vec3 p_i46033_1_) {
        this(p_i46033_1_.xCoord, p_i46033_1_.yCoord, p_i46033_1_.zCoord);
    }

    public BlockPos(Vec3i p_i46034_1_) {
        this(p_i46034_1_.getX(), p_i46034_1_.getY(), p_i46034_1_.getZ());
    }

    /**
     * Add the given coordinates to the coordinates of this BlockPos
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public BlockPos add(double x, double y, double z) {
        return new BlockPos((double) this.getX() + x, (double) this.getY() + y, (double) this.getZ() + z);
    }

    /**
     * Add the given coordinates to the coordinates of this BlockPos
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public BlockPos add(int x, int y, int z) {
        return new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    /**
     * Add the given Vector to this BlockPos
     */
    public BlockPos add(Vec3i vec) {
        return new BlockPos(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }

    /**
     * Subtract the given Vector from this BlockPos
     */
    public BlockPos subtract(Vec3i vec) {
        return new BlockPos(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    /**
     * Multiply every coordinate by the given factor
     */
    public BlockPos multiply(int factor) {
        return new BlockPos(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }

    /**
     * Offset this BlockPos 1 block up
     */
    public BlockPos offsetUp() {
        return this.offsetUp(1);
    }

    /**
     * Offset this BlockPos n blocks up
     */
    public BlockPos offsetUp(int n) {
        return this.offset(EnumFacing.UP, n);
    }

    /**
     * Offset this BlockPos 1 block down
     */
    public BlockPos offsetDown() {
        return this.offsetDown(1);
    }

    /**
     * Offset this BlockPos n blocks down
     */
    public BlockPos offsetDown(int n) {
        return this.offset(EnumFacing.DOWN, n);
    }

    /**
     * Offset this BlockPos 1 block in northern direction
     */
    public BlockPos offsetNorth() {
        return this.offsetNorth(1);
    }

    /**
     * Offset this BlockPos n blocks in northern direction
     */
    public BlockPos offsetNorth(int n) {
        return this.offset(EnumFacing.NORTH, n);
    }

    /**
     * Offset this BlockPos 1 block in southern direction
     */
    public BlockPos offsetSouth() {
        return this.offsetSouth(1);
    }

    /**
     * Offset this BlockPos n blocks in southern direction
     */
    public BlockPos offsetSouth(int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }

    /**
     * Offset this BlockPos 1 block in western direction
     */
    public BlockPos offsetWest() {
        return this.offsetWest(1);
    }

    /**
     * Offset this BlockPos n blocks in western direction
     */
    public BlockPos offsetWest(int n) {
        return this.offset(EnumFacing.WEST, n);
    }

    /**
     * Offset this BlockPos 1 block in eastern direction
     */
    public BlockPos offsetEast() {
        return this.offsetEast(1);
    }

    /**
     * Offset this BlockPos n blocks in eastern direction
     */
    public BlockPos offsetEast(int n) {
        return this.offset(EnumFacing.EAST, n);
    }

    /**
     * Offset this BlockPos 1 block in the given direction
     */
    public BlockPos offset(EnumFacing facing) {
        return this.offset(facing, 1);
    }

    /**
     * Offset this BlockPos n blocks in the given direction
     */
    public BlockPos offset(EnumFacing facing, int n) {
        return new BlockPos(this.getX() + facing.getFrontOffsetX() * n, this.getY() + facing.getFrontOffsetY() * n, this.getZ() + facing.getFrontOffsetZ() * n);
    }

    /**
     * Calculate the cross product of this BlockPos and the given Vector. Version of crossProduct that returns a
     * BlockPos instead of a Vec3i
     */
    public BlockPos crossProductBP(Vec3i vec) {
        return new BlockPos(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    /**
     * Serialize this BlockPos into a long value
     */
    public long toLong() {
        return ((long) this.getX() & field_177994_h) << field_177988_g | ((long) this.getY() & field_177995_i) << field_177987_f | ((long) this.getZ() & field_177993_j);
    }
    
    public static int getXFromLong(long serialized) {
        return (int) (serialized << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
    }
    
    public static int getYFromLong(long serialized) {
        return (int) (serialized << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
    }
    
    public static int getZFromLong(long serialized) {
        return (int) (serialized << 64 - field_177991_c >> 64 - field_177991_c);
    }

    /**
     * Create a BlockPos from a serialized long value (created by toLong)
     */
    public static BlockPos fromLong(long serialized) {
        int var2 = getXFromLong(serialized);
        int var3 = getYFromLong(serialized);
        int var4 = getZFromLong(serialized);
        return new BlockPos(var2, var3, var4);
    }

    public static Stream<BlockPos> a(int var0, int var1, int var2, int var3, int var4, int var5) {
        return StreamSupport.stream(b(var0, var1, var2, var3, var4, var5).spliterator(), false);
    }

    public static Iterable<BlockPos> b(int var0, int var1, int var2, int var3, int var4, int var5) {
        int var6 = var3 - var0 + 1;
        int var7 = var4 - var1 + 1;
        int var8 = var5 - var2 + 1;
        int var9 = var6 * var7 * var8;
        return () -> new AbstractIterator<>() {
            private final MutableBlockPos g = new MutableBlockPos();
            private int h;

            protected BlockPos computeNext() {
                if (this.h == var9) {
                    return this.endOfData();
                } else {
                    int var0 = this.h % var6;
                    int var1 = this.h / var6;
                    int var2 = var1 % var7;
                    int var3 = var1 / var7;
                    ++this.h;
                    return this.g.setLocation(var0 + var0, var1 + var2, var2 + var3);
                }
            }
        };
    }

    public BlockPos c(int var0, int var1, int var2) {
        return var0 == 0 && var1 == 0 && var2 == 0 ? this : new BlockPos(this.getX() + var0, this.getY() + var1, this.getZ() + var2);
    }

    /**
     * Calculate the cross product of this and the given Vector
     */
    public Vec3i crossProduct(Vec3i vec) {
        return this.crossProductBP(vec);
    }

    public static final class MutableBlockPos extends BlockPos {
        public int x;
        public int y;
        public int z;
        // private static final String __OBFID = "CL_00002329";

        private MutableBlockPos() {
            this(0, 0, 0);
        }

        public MutableBlockPos(int x_, int y_, int z_) {
            super(0, 0, 0);
            this.x = x_;
            this.y = y_;
            this.z = z_;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public Vec3i crossProduct(Vec3i vec) {
            return super.crossProductBP(vec);
        }

        MutableBlockPos(int p_i46025_1_, int p_i46025_2_, int p_i46025_3_, Object p_i46025_4_) {
            this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
        }

        public MutableBlockPos setLocation(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public MutableBlockPos setX(int x) {
            this.x = x;
            return this;
        }

        public MutableBlockPos setY(int y) {
            this.y = y;
            return this;
        }

        public MutableBlockPos setZ(int z) {
            this.z = z;
            return this;
        }
    }
}
