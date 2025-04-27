//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lowlevel.minecraft.util.mcp;


public class MovingObjectPositionBlock extends MovingObjectPosition {
    private final EnumFacing b;
    private final BlockPos c;
    private final boolean d;
    private final boolean e;

    public static MovingObjectPositionBlock a(Vec3 var0, EnumFacing var1, BlockPos var2) {
        return new MovingObjectPositionBlock(true, var0, var1, var2, false);
    }

    public MovingObjectPositionBlock(Vec3 var0, EnumFacing var1, BlockPos var2, boolean var3) {
        this(false, var0, var1, var2, var3);
    }

    private MovingObjectPositionBlock(boolean var0, Vec3 var1, EnumFacing var2, BlockPos var3, boolean var4) {
        super(var1);
        this.d = var0;
        this.b = var2;
        this.c = var3;
        this.e = var4;
    }

    public MovingObjectPositionBlock a(EnumFacing var0) {
        return new MovingObjectPositionBlock(this.d, this.hitVec, var0, this.c, this.e);
    }

    public MovingObjectPositionBlock a(BlockPos var0) {
        return new MovingObjectPositionBlock(this.d, this.hitVec, this.b, var0, this.e);
    }

    public BlockPos getBlockPos() {
        return this.c;
    }

    public EnumFacing getDirection() {
        return this.b;
    }

    public MovingObjectPosition.MovingObjectType getType() {
        return this.d ? MovingObjectType.MISS : MovingObjectType.BLOCK;
    }

    public boolean d() {
        return this.e;
    }
}
