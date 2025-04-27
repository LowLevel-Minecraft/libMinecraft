//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lowlevel.minecraft.packet;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lowlevel.minecraft.util.mcp.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class PacketDataSerializer extends ByteBuf {
    private static final int c = 5;
    private static final int d = 10;
    private static final int e = 2097152;
    private final ByteBuf f;
    public static final short a = Short.MAX_VALUE;
    public static final int b = 262144;

    public PacketDataSerializer(ByteBuf bytebuf) {
        this.f = bytebuf;
    }

    public static int a(int i) {
        for(int j = 1; j < 5; ++j) {
            if ((i & -1 << j * 7) == 0) {
                return j;
            }
        }

        return 5;
    }

    public static int a(long i) {
        for(int j = 1; j < 10; ++j) {
            if ((i & -1L << j * 7) == 0L) {
                return j;
            }
        }

        return 10;
    }

    //TODO Implement
    /*public <T> T a(Codec<T> codec) {
        NBTTagCompound nbttagcompound = this.n();
        DataResult<T> dataresult = codec.parse(DynamicOpsNBT.a, nbttagcompound);
        dataresult.error().ifPresent((partialresult) -> {
            String s = partialresult.message();
            throw new EncoderException("Failed to decode: " + s + " " + nbttagcompound);
        });
        return (T)dataresult.result().get();
    }

    public <T> void a(Codec<T> codec, T t0) {
        DataResult<NBTBase> dataresult = codec.encodeStart(DynamicOpsNBT.a, t0);
        dataresult.error().ifPresent((partialresult) -> {
            String s = partialresult.message();
            throw new EncoderException("Failed to encode: " + s + " " + t0);
        });
        this.a((NBTTagCompound)dataresult.result().get());
    }*/

    public static <T> IntFunction<T> a(IntFunction<T> intfunction, int i) {
        return (j) -> {
            if (j > i) {
                throw new DecoderException("Value " + j + " is larger than limit " + i);
            } else {
                return intfunction.apply(j);
            }
        };
    }

    public <T, C extends Collection<T>> C a(IntFunction<C> intfunction, Function<PacketDataSerializer, T> function) {
        int i = this.j();
        C c0 = (C)(intfunction.apply(i));

        for(int j = 0; j < i; ++j) {
            c0.add(function.apply(this));
        }

        return c0;
    }

    public <T> void a(Collection<T> collection, BiConsumer<PacketDataSerializer, T> biconsumer) {
        this.d(collection.size());

        for(T t0 : collection) {
            biconsumer.accept(this, t0);
        }

    }

    public <T> List<T> a(Function<PacketDataSerializer, T> function) {
        return this.a((IntFunction<ArrayList<T>>) Lists::newArrayListWithCapacity, function);
    }

    public IntList a() {
        int i = this.j();
        IntArrayList intarraylist = new IntArrayList();

        for(int j = 0; j < i; ++j) {
            intarraylist.add(this.j());
        }

        return intarraylist;
    }

    public void a(IntList intlist) {
        this.d(intlist.size());
        intlist.forEach(this::d);
    }

    public <K, V, M extends Map<K, V>> M a(IntFunction<M> intfunction, Function<PacketDataSerializer, K> function, Function<PacketDataSerializer, V> function1) {
        int i = this.j();
        M m0 = (M)(intfunction.apply(i));

        for(int j = 0; j < i; ++j) {
            K k0 = (K)function.apply(this);
            V v0 = (V)function1.apply(this);
            m0.put(k0, v0);
        }

        return m0;
    }

    public <K, V> Map<K, V> a(Function<PacketDataSerializer, K> function, Function<PacketDataSerializer, V> function1) {
        return this.a(Maps::newHashMapWithExpectedSize, function, function1);
    }

    public <K, V> void a(Map<K, V> map, BiConsumer<PacketDataSerializer, K> biconsumer, BiConsumer<PacketDataSerializer, V> biconsumer1) {
        this.d(map.size());
        map.forEach((object, object1) -> {
            biconsumer.accept(this, object);
            biconsumer1.accept(this, object1);
        });
    }

    public void a(Consumer<PacketDataSerializer> consumer) {
        int i = this.j();

        for(int j = 0; j < i; ++j) {
            consumer.accept(this);
        }

    }

    public <T> void a(Optional<T> optional, BiConsumer<PacketDataSerializer, T> biconsumer) {
        if (optional.isPresent()) {
            this.writeBoolean(true);
            biconsumer.accept(this, optional.get());
        } else {
            this.writeBoolean(false);
        }

    }

    public <T> Optional<T> b(Function<PacketDataSerializer, T> function) {
        return this.readBoolean() ? Optional.of(function.apply(this)) : Optional.empty();
    }

    public byte[] b() {
        return this.b(this.readableBytes());
    }

    public PacketDataSerializer a(byte[] abyte) {
        this.d(abyte.length);
        this.writeBytes(abyte);
        return this;
    }

    public byte[] b(int i) {
        int j = this.j();
        if (j > i) {
            throw new DecoderException("ByteArray with size " + j + " is bigger than allowed " + i);
        } else {
            byte[] abyte = new byte[j];
            this.readBytes(abyte);
            return abyte;
        }
    }

    public PacketDataSerializer a(int[] aint) {
        this.d(aint.length);

        for(int k : aint) {
            this.d(k);
        }

        return this;
    }

    public int[] c() {
        return this.c(this.readableBytes());
    }

    public int[] c(int i) {
        int j = this.j();
        if (j > i) {
            throw new DecoderException("VarIntArray with size " + j + " is bigger than allowed " + i);
        } else {
            int[] aint = new int[j];

            for(int k = 0; k < aint.length; ++k) {
                aint[k] = this.j();
            }

            return aint;
        }
    }

    public PacketDataSerializer a(long[] along) {
        this.d(along.length);

        for(long k : along) {
            this.writeLong(k);
        }

        return this;
    }

    public long[] d() {
        return this.b((long[])null);
    }

    public long[] b(@Nullable long[] along) {
        return this.a(along, this.readableBytes() / 8);
    }

    public long[] a(@Nullable long[] along, int i) {
        int j = this.j();
        if (along == null || along.length != j) {
            if (j > i) {
                throw new DecoderException("LongArray with size " + j + " is bigger than allowed " + i);
            }

            along = new long[j];
        }

        for(int k = 0; k < along.length; ++k) {
            along[k] = this.readLong();
        }

        return along;
    }

    public @VisibleForTesting byte[] e() {
        int i = this.writerIndex();
        byte[] abyte = new byte[i];
        this.getBytes(0, (byte[])abyte);
        return abyte;
    }

    public BlockPos f() {
        return BlockPos.fromLong(this.readLong());
    }

    public PacketDataSerializer a(BlockPos BlockPos) {
        this.writeLong(BlockPos.toLong());
        return this;
    }

    public ChunkCoordIntPair g() {
        return new ChunkCoordIntPair(this.readLong());
    }

    public PacketDataSerializer a(ChunkCoordIntPair chunkcoordintpair) {
        this.writeLong(chunkcoordintpair.pair());
        return this;
    }

    public SectionPosition h() {
        return SectionPosition.a(this.readLong());
    }

    public PacketDataSerializer a(SectionPosition sectionposition) {
        this.writeLong(sectionposition.s());
        return this;
    }

    //TODO Implement
   /* public IChatBaseComponent i() {
        return ChatSerializer.a(this.e(262144));
    }

    public PacketDataSerializer a(IChatBaseComponent ichatbasecomponent) {
        return this.a(ChatSerializer.a(ichatbasecomponent), 262144);
    }*/

    public <T extends Enum<T>> T a(Class<T> oclass) {
        return (T)((Enum[])oclass.getEnumConstants())[this.j()];
    }

    public PacketDataSerializer a(Enum<?> oenum) {
        return this.d(oenum.ordinal());
    }

    public int j() {
        int i = 0;
        int j = 0;

        byte b0;
        do {
            b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while((b0 & 128) == 128);

        return i;
    }

    public long k() {
        long i = 0L;
        int j = 0;

        byte b0;
        do {
            b0 = this.readByte();
            i |= (long)(b0 & 127) << j++ * 7;
            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while((b0 & 128) == 128);

        return i;
    }

    public PacketDataSerializer a(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    public UUID l() {
        return new UUID(this.readLong(), this.readLong());
    }

    public PacketDataSerializer d(int i) {
        while((i & -128) != 0) {
            this.writeByte(i & 127 | 128);
            i >>>= 7;
        }

        this.writeByte(i);
        return this;
    }

    public PacketDataSerializer b(long i) {
        while((i & -128L) != 0L) {
            this.writeByte((int)(i & 127L) | 128);
            i >>>= 7;
        }

        this.writeByte((int)i);
        return this;
    }
//TODO Implement
/*    public PacketDataSerializer a(@Nullable NBTTagCompound nbttagcompound) {
        if (nbttagcompound == null) {
            this.writeByte(0);
        } else {
            try {
                NBTCompressedStreamTools.a(nbttagcompound, new ByteBufOutputStream(this));
            } catch (Exception ioexception) {
                throw new EncoderException(ioexception);
            }
        }

        return this;
    }

    public @Nullable NBTTagCompound m() {
        return this.a(new NBTReadLimiter(2097152L));
    }

    public @Nullable NBTTagCompound n() {
        return this.a(NBTReadLimiter.a);
    }

    public @Nullable NBTTagCompound a(NBTReadLimiter nbtreadlimiter) {
        int i = this.readerIndex();
        byte b0 = this.readByte();
        if (b0 == 0) {
            return null;
        } else {
            this.readerIndex(i);

            try {
                return NBTCompressedStreamTools.a(new ByteBufInputStream(this), nbtreadlimiter);
            } catch (IOException ioexception) {
                throw new EncoderException(ioexception);
            }
        }
    }

    public PacketDataSerializer a(ItemStack itemstack) {
        if (!itemstack.isEmpty() && itemstack.getItem() != null) {
            this.writeBoolean(true);
            Item item = itemstack.getItem();
            this.d(Item.getId(item));
            this.writeByte(itemstack.getCount());
            NBTTagCompound nbttagcompound = null;
            if (item.usesDurability() || item.q()) {
                itemstack = itemstack.cloneItemStack();
                CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
                nbttagcompound = itemstack.getTag();
            }

            this.a(nbttagcompound);
        } else {
            this.writeBoolean(false);
        }

        return this;
    }

    public ItemStack o() {
        if (!this.readBoolean()) {
            return ItemStack.b;
        } else {
            int i = this.j();
            byte b0 = this.readByte();
            ItemStack itemstack = new ItemStack(Item.getById(i), b0);
            itemstack.setTag(this.m());
            if (itemstack.getTag() != null) {
                CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
            }

            return itemstack;
        }
    }*/

    public String p() {
        return this.e(32767);
    }

    public String e(int i) {
        int j = this.j();
        if (j > i * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i * 4 + ")");
        } else if (j < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String s = this.toString(this.readerIndex(), j, StandardCharsets.UTF_8);
            this.readerIndex(this.readerIndex() + j);
            if (s.length() > i) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
            } else {
                return s;
            }
        }
    }

    public PacketDataSerializer a(String s) {
        return this.a(s, 32767);
    }

    public PacketDataSerializer a(String s, int i) {
        byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
        if (abyte.length > i) {
            throw new EncoderException("String too big (was " + abyte.length + " bytes encoded, max " + i + ")");
        } else {
            this.d(abyte.length);
            this.writeBytes(abyte);
            return this;
        }
    }

    /*public MinecraftKey q() {
        return new MinecraftKey(this.e(32767));
    }

    public PacketDataSerializer a(MinecraftKey minecraftkey) {
        this.a(minecraftkey.toString());
        return this;
    }*/

    public Date r() {
        return new Date(this.readLong());
    }

    public PacketDataSerializer a(Date date) {
        this.writeLong(date.getTime());
        return this;
    }

    public MovingObjectPositionBlock s() {
        BlockPos BlockPos = this.f();
        EnumFacing EnumFacing = this.a(EnumFacing.class);
        float f = this.readFloat();
        float f1 = this.readFloat();
        float f2 = this.readFloat();
        boolean flag = this.readBoolean();
        return new MovingObjectPositionBlock(new Vec3((double)BlockPos.getX() + (double)f, (double)BlockPos.getY() + (double)f1, (double)BlockPos.getZ() + (double)f2), EnumFacing, BlockPos, flag);
    }

    public void a(MovingObjectPositionBlock movingobjectpositionblock) {
        BlockPos BlockPos = movingobjectpositionblock.getBlockPos();
        this.a(BlockPos);
        this.a(movingobjectpositionblock.getDirection());
        Vec3 Vec3 = movingobjectpositionblock.hitVec;
        this.writeFloat((float)(Vec3.xCoord - (double)BlockPos.getX()));
        this.writeFloat((float)(Vec3.yCoord - (double)BlockPos.getY()));
        this.writeFloat((float)(Vec3.zCoord - (double)BlockPos.getZ()));
        this.writeBoolean(movingobjectpositionblock.d());
    }

    public BitSet t() {
        return BitSet.valueOf(this.d());
    }

    public void a(BitSet bitset) {
        this.a(bitset.toLongArray());
    }

    public int capacity() {
        return this.f.capacity();
    }

    public ByteBuf capacity(int i) {
        return this.f.capacity(i);
    }

    public int maxCapacity() {
        return this.f.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.f.alloc();
    }

    public ByteOrder order() {
        return this.f.order();
    }

    public ByteBuf order(ByteOrder byteorder) {
        return this.f.order(byteorder);
    }

    public ByteBuf unwrap() {
        return this.f.unwrap();
    }

    public boolean isDirect() {
        return this.f.isDirect();
    }

    public boolean isReadOnly() {
        return this.f.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.f.asReadOnly();
    }

    public int readerIndex() {
        return this.f.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        return this.f.readerIndex(i);
    }

    public int writerIndex() {
        return this.f.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        return this.f.writerIndex(i);
    }

    public ByteBuf setIndex(int i, int j) {
        return this.f.setIndex(i, j);
    }

    public int readableBytes() {
        return this.f.readableBytes();
    }

    public int writableBytes() {
        return this.f.writableBytes();
    }

    public int maxWritableBytes() {
        return this.f.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.f.isReadable();
    }

    public boolean isReadable(int i) {
        return this.f.isReadable(i);
    }

    public boolean isWritable() {
        return this.f.isWritable();
    }

    public boolean isWritable(int i) {
        return this.f.isWritable(i);
    }

    public ByteBuf clear() {
        return this.f.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.f.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.f.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.f.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.f.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.f.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.f.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int i) {
        return this.f.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean flag) {
        return this.f.ensureWritable(i, flag);
    }

    public boolean getBoolean(int i) {
        return this.f.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.f.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.f.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.f.getShort(i);
    }

    public short getShortLE(int i) {
        return this.f.getShortLE(i);
    }

    public int getUnsignedShort(int i) {
        return this.f.getUnsignedShort(i);
    }

    public int getUnsignedShortLE(int i) {
        return this.f.getUnsignedShortLE(i);
    }

    public int getMedium(int i) {
        return this.f.getMedium(i);
    }

    public int getMediumLE(int i) {
        return this.f.getMediumLE(i);
    }

    public int getUnsignedMedium(int i) {
        return this.f.getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return this.f.getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return this.f.getInt(i);
    }

    public int getIntLE(int i) {
        return this.f.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        return this.f.getUnsignedInt(i);
    }

    public long getUnsignedIntLE(int i) {
        return this.f.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        return this.f.getLong(i);
    }

    public long getLongLE(int i) {
        return this.f.getLongLE(i);
    }

    public char getChar(int i) {
        return this.f.getChar(i);
    }

    public float getFloat(int i) {
        return this.f.getFloat(i);
    }

    public double getDouble(int i) {
        return this.f.getDouble(i);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf) {
        return this.f.getBytes(i, bytebuf);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
        return this.f.getBytes(i, bytebuf, j);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.f.getBytes(i, bytebuf, j, k);
    }

    public ByteBuf getBytes(int i, byte[] abyte) {
        return this.f.getBytes(i, abyte);
    }

    public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
        return this.f.getBytes(i, abyte, j, k);
    }

    public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
        return this.f.getBytes(i, bytebuffer);
    }

    public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
        return this.f.getBytes(i, outputstream, j);
    }

    public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
        return this.f.getBytes(i, gatheringbytechannel, j);
    }

    public int getBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.f.getBytes(i, filechannel, j, k);
    }

    public CharSequence getCharSequence(int i, int j, Charset charset) {
        return this.f.getCharSequence(i, j, charset);
    }

    public ByteBuf setBoolean(int i, boolean flag) {
        return this.f.setBoolean(i, flag);
    }

    public ByteBuf setByte(int i, int j) {
        return this.f.setByte(i, j);
    }

    public ByteBuf setShort(int i, int j) {
        return this.f.setShort(i, j);
    }

    public ByteBuf setShortLE(int i, int j) {
        return this.f.setShortLE(i, j);
    }

    public ByteBuf setMedium(int i, int j) {
        return this.f.setMedium(i, j);
    }

    public ByteBuf setMediumLE(int i, int j) {
        return this.f.setMediumLE(i, j);
    }

    public ByteBuf setInt(int i, int j) {
        return this.f.setInt(i, j);
    }

    public ByteBuf setIntLE(int i, int j) {
        return this.f.setIntLE(i, j);
    }

    public ByteBuf setLong(int i, long j) {
        return this.f.setLong(i, j);
    }

    public ByteBuf setLongLE(int i, long j) {
        return this.f.setLongLE(i, j);
    }

    public ByteBuf setChar(int i, int j) {
        return this.f.setChar(i, j);
    }

    public ByteBuf setFloat(int i, float f) {
        return this.f.setFloat(i, f);
    }

    public ByteBuf setDouble(int i, double d0) {
        return this.f.setDouble(i, d0);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf) {
        return this.f.setBytes(i, bytebuf);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
        return this.f.setBytes(i, bytebuf, j);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.f.setBytes(i, bytebuf, j, k);
    }

    public ByteBuf setBytes(int i, byte[] abyte) {
        return this.f.setBytes(i, abyte);
    }

    public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
        return this.f.setBytes(i, abyte, j, k);
    }

    public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
        return this.f.setBytes(i, bytebuffer);
    }

    public int setBytes(int i, InputStream inputstream, int j) throws IOException {
        return this.f.setBytes(i, inputstream, j);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
        return this.f.setBytes(i, scatteringbytechannel, j);
    }

    public int setBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.f.setBytes(i, filechannel, j, k);
    }

    public ByteBuf setZero(int i, int j) {
        return this.f.setZero(i, j);
    }

    public int setCharSequence(int i, CharSequence charsequence, Charset charset) {
        return this.f.setCharSequence(i, charsequence, charset);
    }

    public boolean readBoolean() {
        return this.f.readBoolean();
    }

    public byte readByte() {
        return this.f.readByte();
    }

    public short readUnsignedByte() {
        return this.f.readUnsignedByte();
    }

    public short readShort() {
        return this.f.readShort();
    }

    public short readShortLE() {
        return this.f.readShortLE();
    }

    public int readUnsignedShort() {
        return this.f.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.f.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.f.readMedium();
    }

    public int readMediumLE() {
        return this.f.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.f.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.f.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.f.readInt();
    }

    public int readIntLE() {
        return this.f.readIntLE();
    }

    public long readUnsignedInt() {
        return this.f.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.f.readUnsignedIntLE();
    }

    public long readLong() {
        return this.f.readLong();
    }

    public long readLongLE() {
        return this.f.readLongLE();
    }

    public char readChar() {
        return this.f.readChar();
    }

    public float readFloat() {
        return this.f.readFloat();
    }

    public double readDouble() {
        return this.f.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.f.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        return this.f.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        return this.f.readRetainedSlice(i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf) {
        return this.f.readBytes(bytebuf);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i) {
        return this.f.readBytes(bytebuf, i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
        return this.f.readBytes(bytebuf, i, j);
    }

    public ByteBuf readBytes(byte[] abyte) {
        return this.f.readBytes(abyte);
    }

    public ByteBuf readBytes(byte[] abyte, int i, int j) {
        return this.f.readBytes(abyte, i, j);
    }

    public ByteBuf readBytes(ByteBuffer bytebuffer) {
        return this.f.readBytes(bytebuffer);
    }

    public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
        return this.f.readBytes(outputstream, i);
    }

    public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
        return this.f.readBytes(gatheringbytechannel, i);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return this.f.readCharSequence(i, charset);
    }

    public int readBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.f.readBytes(filechannel, i, j);
    }

    public ByteBuf skipBytes(int i) {
        return this.f.skipBytes(i);
    }

    public ByteBuf writeBoolean(boolean flag) {
        return this.f.writeBoolean(flag);
    }

    public ByteBuf writeByte(int i) {
        return this.f.writeByte(i);
    }

    public ByteBuf writeShort(int i) {
        return this.f.writeShort(i);
    }

    public ByteBuf writeShortLE(int i) {
        return this.f.writeShortLE(i);
    }

    public ByteBuf writeMedium(int i) {
        return this.f.writeMedium(i);
    }

    public ByteBuf writeMediumLE(int i) {
        return this.f.writeMediumLE(i);
    }

    public ByteBuf writeInt(int i) {
        return this.f.writeInt(i);
    }

    public ByteBuf writeIntLE(int i) {
        return this.f.writeIntLE(i);
    }

    public ByteBuf writeLong(long i) {
        return this.f.writeLong(i);
    }

    public ByteBuf writeLongLE(long i) {
        return this.f.writeLongLE(i);
    }

    public ByteBuf writeChar(int i) {
        return this.f.writeChar(i);
    }

    public ByteBuf writeFloat(float f) {
        return this.f.writeFloat(f);
    }

    public ByteBuf writeDouble(double d0) {
        return this.f.writeDouble(d0);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf) {
        return this.f.writeBytes(bytebuf);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
        return this.f.writeBytes(bytebuf, i);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
        return this.f.writeBytes(bytebuf, i, j);
    }

    public ByteBuf writeBytes(byte[] abyte) {
        return this.f.writeBytes(abyte);
    }

    public ByteBuf writeBytes(byte[] abyte, int i, int j) {
        return this.f.writeBytes(abyte, i, j);
    }

    public ByteBuf writeBytes(ByteBuffer bytebuffer) {
        return this.f.writeBytes(bytebuffer);
    }

    public int writeBytes(InputStream inputstream, int i) throws IOException {
        return this.f.writeBytes(inputstream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
        return this.f.writeBytes(scatteringbytechannel, i);
    }

    public int writeBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.f.writeBytes(filechannel, i, j);
    }

    public ByteBuf writeZero(int i) {
        return this.f.writeZero(i);
    }

    public int writeCharSequence(CharSequence charsequence, Charset charset) {
        return this.f.writeCharSequence(charsequence, charset);
    }

    public int indexOf(int i, int j, byte b0) {
        return this.f.indexOf(i, j, b0);
    }

    public int bytesBefore(byte b0) {
        return this.f.bytesBefore(b0);
    }

    public int bytesBefore(int i, byte b0) {
        return this.f.bytesBefore(i, b0);
    }

    public int bytesBefore(int i, int j, byte b0) {
        return this.f.bytesBefore(i, j, b0);
    }

    public int forEachByte(ByteProcessor byteprocessor) {
        return this.f.forEachByte(byteprocessor);
    }

    public int forEachByte(int i, int j, ByteProcessor byteprocessor) {
        return this.f.forEachByte(i, j, byteprocessor);
    }

    public int forEachByteDesc(ByteProcessor byteprocessor) {
        return this.f.forEachByteDesc(byteprocessor);
    }

    public int forEachByteDesc(int i, int j, ByteProcessor byteprocessor) {
        return this.f.forEachByteDesc(i, j, byteprocessor);
    }

    public ByteBuf copy() {
        return this.f.copy();
    }

    public ByteBuf copy(int i, int j) {
        return this.f.copy(i, j);
    }

    public ByteBuf slice() {
        return this.f.slice();
    }

    public ByteBuf retainedSlice() {
        return this.f.retainedSlice();
    }

    public ByteBuf slice(int i, int j) {
        return this.f.slice(i, j);
    }

    public ByteBuf retainedSlice(int i, int j) {
        return this.f.retainedSlice(i, j);
    }

    public ByteBuf duplicate() {
        return this.f.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.f.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.f.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.f.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int j) {
        return this.f.nioBuffer(i, j);
    }

    public ByteBuffer internalNioBuffer(int i, int j) {
        return this.f.internalNioBuffer(i, j);
    }

    public ByteBuffer[] nioBuffers() {
        return this.f.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int j) {
        return this.f.nioBuffers(i, j);
    }

    public boolean hasArray() {
        return this.f.hasArray();
    }

    public byte[] array() {
        return this.f.array();
    }

    public int arrayOffset() {
        return this.f.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.f.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.f.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.f.toString(charset);
    }

    public String toString(int i, int j, Charset charset) {
        return this.f.toString(i, j, charset);
    }

    public int hashCode() {
        return this.f.hashCode();
    }

    public boolean equals(Object object) {
        return this.f.equals(object);
    }

    public int compareTo(ByteBuf bytebuf) {
        return this.f.compareTo(bytebuf);
    }

    public String toString() {
        return this.f.toString();
    }

    public ByteBuf retain(int i) {
        return this.f.retain(i);
    }

    public ByteBuf retain() {
        return this.f.retain();
    }

    public ByteBuf touch() {
        return this.f.touch();
    }

    public ByteBuf touch(Object object) {
        return this.f.touch(object);
    }

    public int refCnt() {
        return this.f.refCnt();
    }

    public boolean release() {
        return this.f.release();
    }

    public boolean release(int i) {
        return this.f.release(i);
    }
}
