package com.iteale.industrialcase.core.network;


import com.iteale.industrialcase.api.crops.CropCard;
import com.iteale.industrialcase.api.network.IGrowingBuffer;
import com.iteale.industrialcase.api.network.INetworkCustomEncoder;
import com.iteale.industrialcase.api.recipe.IElectrolyzerRecipeManager;
import com.iteale.industrialcase.core.IndustrialCase;
import com.iteale.industrialcase.core.block.comp.BlockEntityComponent;
import com.iteale.industrialcase.core.block.container.ICContainer;
import com.iteale.industrialcase.core.util.StackUtil;
import com.iteale.industrialcase.core.util.Tuple;
import com.iteale.industrialcase.core.util.Util;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public final class DataEncoder {
    /*
    public static void encode(GrowingBuffer os, Object o) throws IOException {
        try {
            encode(os, o, true);
        } catch (IllegalArgumentException e) {
            IndustrialCase.platform.displayError(e, "An unknown data type was attempted to be encoded for sending through\nmultiplayer.\nThis could happen due to a bug.", new Object[0]);
        }
    }

    public static void encode(IGrowingBuffer os, Object o, boolean withType) throws IOException {
        INetworkCustomEncoder ince;
        Class<?> componentClass;
        BlockPos blockPos;
        ChunkPos pos;
        int len;
        CompoundTag nbt;
        CropCard cropCard;
        IElectrolyzerRecipeManager.ElectrolyzerRecipe recipe;
        EncodedType componentType;
        IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs;
        boolean anyTypeMismatch;
        FluidStack fs;
        FluidTank tank;
        GameProfile gp;
        ICContainer slot;
        ItemStack stack;
        ResourceLocation loc;
        BlockEntity te;
        Tuple.T2<?, ?> t2;
        Tuple.T3<?, ?, ?> t;
        UUID uuid;
        Vec3 v;
        ItemStack[] contents;
        int i;
        EncodedType type = typeFromObject(o);

        if (withType) os.writeByte(idFromType(type));

        switch (type) {
            case Addon:
            case UnSafeAddon:
                assert o != null;
                ince = classToAddonType.get(o.getClass());
                if (ince == null) {
                    throw new IllegalStateException("Cannot encode an object without an encoder! Type was " + o.getClass());
                }
                os.writeString(o.getClass().getName());
                ince.encode(os, o);
                return;

            case Array:
                componentClass = o.getClass().getComponentType();
                len = Array.getLength(o);
                if (componentClass == Object.class && len > 0) {
                    boolean isEnum = false;
                    Class<?> target = null;
                    int j;
                    label174:
                    for (j = 0; j < len; j++) {
                        Object value = Array.get(o, j);

                        if (target == null) {

                            if (value instanceof Enum) {

                                target = ((Enum) value).getDeclaringClass();
                                isEnum = true;
                            } else if (value != null) {
                                target = value.getClass();
                                assert target != Object.class;
                            }
                        } else if (value != null) {
                            Class<?> valueClass = value.getClass();


                            if (valueClass != target && !target.isAssignableFrom(valueClass)) {

                                if (isEnum || value instanceof Enum) {

                                    throw new IllegalArgumentException("Array of mixed enum entries");
                                }


                                while ((target = target.getSuperclass()) != Object.class) {
                                    if (target.isAssignableFrom(valueClass)) {
                                        continue label174;
                                    }
                                }

                                for (; ++j < len; j++) {
                                    if (Array.get(o, j) instanceof Enum) {
                                        throw new IllegalArgumentException("Array of mixed enum entries");
                                    }
                                }
                                target = Object.class;

                                break;
                            }

                            assert isEnum == value instanceof Enum;
                        } else if (isEnum) {


                            throw new IllegalArgumentException("Enum array with null entry");
                        }
                    }

                    componentClass = target;
                }
                componentType = typeFromClass(componentClass);
                os.writeByte(idFromType(componentType));
                os.writeBoolean(componentClass.isPrimitive());
                if (componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || componentType == EncodedType.Enum) {
                    os.writeString(componentClass.getName());
                }
                os.writeVarInt(len);

                anyTypeMismatch = false;

                for (i = 0; i < len; i++) {
                    Object value = Array.get(o, i);

                    if (value == null || typeFromClass(value.getClass()) != componentType) {
                        anyTypeMismatch = true;

                        break;
                    }
                }
                os.writeBoolean(anyTypeMismatch);

                for (i = 0; i < len; i++) {
                    encode(os, Array.get(o, i), anyTypeMismatch);
                }
                return;


            case Block:
                encode(os, Util.getName((Block) o), false);
                return;
            case BlockPos:
                blockPos = (BlockPos) o;
                os.writeInt(blockPos.getX());
                os.writeInt(blockPos.getY());
                os.writeInt(blockPos.getZ());
                return;

            case Boolean:
                os.writeBoolean(((Boolean) o).booleanValue());
                return;
            case Byte:
                os.writeByte(((Byte) o).byteValue());
                return;
            case Character:
                os.writeChar(((Character) o).charValue());
                return;
            case ChunkPos:
                pos = (ChunkPos) o;
                os.writeInt(pos.x);
                os.writeInt(pos.z);
                return;
            case Collection:
                encode(os, ((Collection) o).toArray(), false);
                return;
            case Component:
                nbt = ((BlockEntityComponent) o).save();
                encode(os, (nbt == null) ? new CompoundTag() : nbt, false);
                return;

            case CropCard:
                cropCard = (CropCard) o;
                os.writeString(cropCard.getOwner());
                os.writeString(cropCard.getId());
                return;

            case Double:
                os.writeDouble(((Double) o).doubleValue());
                return;
            case ElectrolyzerRecipe:
                recipe = (IElectrolyzerRecipeManager.ElectrolyzerRecipe) o;
                os.writeInt(recipe.inputAmount);
                os.writeInt(recipe.EUaTick);
                os.writeInt(recipe.ticksNeeded);
                outputs = recipe.outputs;
                os.writeByte(outputs.length);
                for (IElectrolyzerRecipeManager.ElectrolyzerOutput output : outputs) {
                    os.writeString(output.fluidName);
                    os.writeInt(output.fluidAmount);
                    os.writeByte(output.tankDirection.getIndex());
                }
                return;
            case Enchantment:
                encode(os, Enchantment.REGISTRY.getNameForObject(o), false);
                return;
            case Enum:
                os.writeVarInt(((Enum) o).ordinal());
                return;
            case Float:
                os.writeFloat((Float) o);
                return;
            case Fluid:
                os.writeString(((Fluid) o).getName());
                return;
            case FluidStack:
                fs = (FluidStack) o;
                encode(os, fs.getFluid(), false);
                os.writeInt(fs.amount);
                encode(os, fs.tag, true);
                return;

            case FluidTank:
                tank = (FluidTank) o;
                encode(os, tank.getFluid(), true);
                os.writeInt(tank.getCapacity());
                return;

            case GameProfile:
                gp = (GameProfile) o;
                encode(os, gp.getId(), true);
                os.writeString(gp.getName());
                return;

            case Integer:
                os.writeInt(((Integer) o).intValue());
                return;
            case InvSlot:
                slot = (ICContainer) o;
                contents = new ItemStack[slot.getContainerSize()];

                for (i = 0; i < slot.getContainerSize(); i++) {
                    contents[i] = slot.getItem(i);
                }

                encode(os, contents, false);
                return;

            case Item:
                encode(os, Util.getName((Item) o), false);
                return;
            case ItemStack:
                stack = (ItemStack) o;

                if (StackUtil.isEmpty(stack)) {
                    os.writeByte(0);
                } else {
                    os.writeByte(StackUtil.getSize(stack));
                    encode(os, stack.getItem(), false);
                    os.writeShort(stack.getItemDamage());
                    encode(os, stack.getTagCompound(), true);
                }
                return;


            case Long:
                os.writeLong(((Long) o).longValue());
                return;
            case NBTTagCompound:
                CompressedStreamTools.write((NBTTagCompound) o, (DataOutput) os);
                return;
            case Null:
                if (!withType) throw new IllegalArgumentException("o has to be non-null without types");

                return;
            case Object:
                throw new IllegalArgumentException("unhandled class: " + o.getClass());
            case Potion:
                encode(os, Potion.REGISTRY.getNameForObject(o), false);
                return;
            case ResourceLocation:
                loc = (ResourceLocation) o;
                os.writeString(loc.getResourceDomain());
                os.writeString(loc.getResourcePath());
                return;

            case Short:
                os.writeShort(((Short) o).shortValue());
                return;
            case String:
                os.writeString((String) o);
                return;
            case TileEntity:
                te = (BlockEntity) o;
                encode(os, te.getLevel(), false);
                encode(os, te.getBlockPos(), false);
                return;

            case TupleT2:
                t2 = (Tuple.T2<?, ?>) o;
                encode(os, t2.a, true);
                encode(os, t2.b, true);
                return;

            case TupleT3:
                t = (Tuple.T3<?, ?, ?>) o;
                encode(os, t.a, true);
                encode(os, t.b, true);
                encode(os, t.c, true);
                return;

            case UUID:
                uuid = (UUID) o;
                os.writeLong(uuid.getMostSignificantBits());
                os.writeLong(uuid.getLeastSignificantBits());
                return;

            case Vec3:
                v = (Vec3) o;
                os.writeDouble(v.x);
                os.writeDouble(v.y);
                os.writeDouble(v.z);
                return;

            case World:
                os.writeInt(((Level) o).provider.getDimension());
                return;
        }
        throw new IllegalArgumentException("unhandled type: " + type);
    }


    public static Object decode(IGrowingBuffer is) throws IOException {
        try {
            return decode(is, typeFromId(is.readUnsignedByte()));
        } catch (IllegalArgumentException e) {
            String msg = "An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.";


            IndustrialCase.platform.displayError(e, msg, new Object[0]);
            return null;
        }
    }


    public static <T> T decode(IGrowingBuffer is, Class<T> clazz) throws IOException {
        EncodedType type = typeFromClass(clazz);

        if (type.threadSafe) {
            return (T) decode(is, type);
        }
        throw new IllegalArgumentException("requesting decode for non thread safe type");
    }


    public static <T extends Enum<T>> T decodeEnum(IGrowingBuffer is, Class<T> clazz) throws IOException {
        int ordinal = ((Integer) decode(is, EncodedType.Enum)).intValue();

        Enum[] arrayOfEnum = (Enum[]) clazz.getEnumConstants();
        return (ordinal >= 0 && ordinal < arrayOfEnum.length) ? (T) arrayOfEnum[ordinal] : null;
    }

    public static Object decodeDeferred(GrowingBuffer is, Class<?> clazz) throws IOException {
        EncodedType type = typeFromClass(clazz);

        return decode(is, type);
    }

    public static Object decode(final IGrowingBuffer is, EncodedType type) throws IOException {
        String aimTypeName;
        EncodedType componentType;
        final Object ret;
        int inputAmount;
        FluidStack ret;
        ItemStack[] contents;
        int size;
        final IResolvableValue<World> deferredWorld;
        final int dimensionId;
        final INetworkCustomEncoder ince;
        boolean primitive;
        int EUaTick;
        ICContainer invSlot;
        Item item;
        final BlockPos pos;
        boolean isEnum;
        int ticksNeeded;
        int i;
        int meta;
        final Class<?> componentClass;
        byte max;
        CompoundTag nbt;
        Class<?> component;
        final int len;
        IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs;
        ItemStack itemStack;
        boolean anyTypeMismatch;
        byte b1;
        boolean needsResolving;
        Object array;
        final Object tmpArray;
        switch (type) {


            case Addon:
            case UnSafeAddon:
                aimTypeName = is.readString();
                ince = classToAddonType.get(getClass(aimTypeName));

                if (ince == null) {
                    throw new IllegalStateException("Cannot decode an object without a decoder! Type was " + aimTypeName);
                }

                if (ince.isThreadSafe()) {
                    return ince.decode(is);
                }
                return new IResolvableValue() {
                    public Object get() {
                        try {
                            return ince.decode(is);
                        } catch (IOException e) {
                            throw new RuntimeException("Unexpected error", e);
                        }
                    }
                };


            case Array:
                componentType = typeFromId(is.readUnsignedByte());
                primitive = is.readBoolean();
                isEnum = (componentType == EncodedType.Enum);

                component = primitive ? unbox(componentType.cls) : componentType.cls;

                if (component == null || isEnum) {
                    assert componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || isEnum;

                    component = getClass(is.readString());
                }

                componentClass = component;

                len = is.readVarInt();
                anyTypeMismatch = is.readBoolean();
                needsResolving = !componentType.threadSafe;


                if (!needsResolving) {
                    array = Array.newInstance(componentClass, len);
                } else {
                    array = new Object[len];
                }

                if (!anyTypeMismatch) {
                    if (isEnum) {

                        Object[] constants = componentClass.getEnumConstants();
                        assert constants != null;

                        for (int j = 0; j < len; j++) {
                            Array.set(array, j, constants[((Integer) decode(is, componentType)).intValue()]);
                        }
                    } else {
                        for (int j = 0; j < len; j++) {
                            Array.set(array, j, decode(is, componentType));
                        }
                    }
                } else {
                    for (int j = 0; j < len; j++) {
                        EncodedType cType = typeFromId(is.readUnsignedByte());

                        if (!cType.threadSafe && !needsResolving) {
                            needsResolving = true;

                            if (componentClass != Object.class) {
                                Object newArray = new Object[len];
                                System.arraycopy(array, 0, newArray, 0, j);
                                array = newArray;
                            }
                        }

                        Array.set(array, j, decode(is, cType));
                    }
                }

                if (!needsResolving) {
                    return array;
                }
                tmpArray = array;

                return new IResolvableValue() {
                    public Object get() {
                        Object ret = Array.newInstance(componentClass, len);

                        for (int i = 0; i < len; i++) {
                            Array.set(ret, i, DataEncoder.getValue(Array.get(tmpArray, i)));
                        }

                        return ret;
                    }
                };


            case Block:
                return Util.getBlock((ResourceLocation) decode(is, EncodedType.ResourceLocation));
            case BlockPos:
                return new BlockPos(is.readInt(), is.readInt(), is.readInt());
            case Boolean:
                return Boolean.valueOf(is.readBoolean());
            case Byte:
                return Byte.valueOf(is.readByte());
            case Character:
                return Character.valueOf(is.readChar());
            case ChunkPos:
                return new ChunkPos(is.readInt(), is.readInt());
            case Collection:
                Object object1 = decode(is, EncodedType.Array);

                if (object1 instanceof IResolvableValue) {
                    return new IResolvableValue<List<Object>>() {
                        public List<Object> get() {
                            return Arrays.asList(((DataEncoder.IResolvableValue<Object[]>) ret).get());
                        }
                    };
                }
                return Arrays.asList((Object[]) object1);


            case Component:
                return decode(is, EncodedType.NBTTagCompound);
            case CropCard:
                return Crops.instance.getCropCard(is.readString(), is.readString());
            case Double:
                return Double.valueOf(is.readDouble());
            case ElectrolyzerRecipe:
                inputAmount = is.readInt();
                EUaTick = is.readInt();
                ticksNeeded = is.readInt();
                max = is.readByte();
                outputs = new IElectrolyzerRecipeManager.ElectrolyzerOutput[max];
                for (b1 = 0; b1 < max; b1 = (byte) (b1 + 1)) {
                    outputs[b1] = new IElectrolyzerRecipeManager.ElectrolyzerOutput(is.readString(), is.readInt(), EnumFacing.getFront(is.readByte()));
                }
                return new IElectrolyzerRecipeManager.ElectrolyzerRecipe(inputAmount, EUaTick, ticksNeeded, outputs);

            case Enchantment:
                return Enchantment.REGISTRY.getObject(decode(is, EncodedType.ResourceLocation));

            case Enum:
                return Integer.valueOf(is.readVarInt());
            case Float:
                return Float.valueOf(is.readFloat());
            case Fluid:
                return FluidRegistry.getFluid(is.readString());
            case FluidStack:
                ret = new FluidStack((Fluid) decode(is, EncodedType.Fluid), is.readInt());
                ret.setTag((CompoundTag) decode(is));

                return ret;

            case FluidTank:
                return new FluidTank((FluidStack) decode(is), is.readInt());
            case GameProfile:
                return new GameProfile((UUID) decode(is), is.readString());
            case Integer:
                return Integer.valueOf(is.readInt());
            case InvSlot:
                contents = (ItemStack[]) decode(is, EncodedType.Array);
                invSlot = new ICContainer(contents.length);

                for (i = 0; i < contents.length; i++) {
                    invSlot.setItem(i, contents[i]);
                }

                return invSlot;

            case Item:
                return Util.getItem((ResourceLocation) decode(is, EncodedType.ResourceLocation));
            case ItemStack:
                size = is.readByte();

                if (size == 0) {
                    return ItemStack.EMPTY;
                }
                item = decode(is, Item.class);
                meta = is.readShort();
                nbt = (CompoundTag) decode(is);

                itemStack = new ItemStack(item, size, meta);
                itemStack.setTag(nbt);

                return itemStack;


            case Long:
                return Long.valueOf(is.readLong());
            case NBTTagCompound:
                return CompressedStreamTools.read((DataInput) is, NBTSizeTracker.INFINITE);
            case Null:
                return null;
            case Object:
                return new Object();
            case Potion:
                return Potion.REGISTRY.getObject(decode(is, EncodedType.ResourceLocation));
            case ResourceLocation:
                return new ResourceLocation(is.readString(), is.readString());
            case Short:
                return Short.valueOf(is.readShort());
            case String:
                return is.readString();

            case TileEntity:
                deferredWorld = (IResolvableValue<Level>) decode(is, EncodedType.World);
                pos = (BlockPos) decode(is, EncodedType.BlockPos);

                return new IResolvableValue<BlockEntity>() {
                    public BlockEntity get() {
                        Level world = deferredWorld.get();
                        if (world == null) return null;

                        return world.getBlockEntity(pos);
                    }
                };

            case TupleT2:
                return new Tuple.T2(decode(is), decode(is));
            case TupleT3:
                return new Tuple.T3(decode(is), decode(is), decode(is));
            case UUID:
                return new UUID(is.readLong(), is.readLong());
            case Vec3:
                return new Vec3(is.readDouble(), is.readDouble(), is.readDouble());
            case World:
                dimensionId = is.readInt();

                return new IResolvableValue<Level>() {
                    public Level get() {
                        return IndustrialCase.platform.getWorld(dimensionId);
                    }
                };
        }

        throw new IllegalArgumentException("unhandled type: " + type);
    }

    public static <T> T getValue(Object decoded) {
        if (decoded instanceof IResolvableValue) {
            return ((IResolvableValue<T>) decoded).get();
        }
        return (T) decoded;
    }

    public static <T> boolean copyValue(T src, T dst) {
        if (src == null || dst == null) return false;

        if (dst instanceof ItemStack) {
            ItemStack srcT = (ItemStack) src;
            ItemStack dstT = (ItemStack) dst;

            if (srcT.getItem() == dstT.getItem()) {
                dstT.setCount(srcT.getCount());
                StackUtil.setRawMeta(dstT, StackUtil.getRawMeta(srcT));
                dstT.setTagCompound(srcT.getTagCompound());

                return true;
            }
            return false;
        }

        if (dst instanceof FluidTank) {
            FluidTank srcT = (FluidTank) src;
            FluidTank dstT = (FluidTank) dst;

            dstT.setFluid(srcT.getFluid());
            dstT.setCapacity(srcT.getCapacity());
        } else if (dst instanceof ICContainer) {
            ICContainer srcT = (ICContainer) src;
            ICContainer dstT = (ICContainer) dst;

            if (srcT.getContainerSize() != dstT.getContainerSize())
                throw new RuntimeException("Can't sync InvSlots with mismatched sizes.");

            for (int i = 0; i < srcT.getContainerSize(); i++) {
                if (!copyValue(srcT.getItem(i), dstT.getItem(i))) {
                    dstT.setItem(i, srcT.getItem(i));
                }
            }
        } else if (dst instanceof BlockEntityComponent) {
            CompoundTag nbt = (CompoundTag) src;

            ((BlockEntityComponent) dst).load(nbt);
        } else if (dst instanceof Collection) {
            Collection<Object> srcT = (Collection<Object>) src;
            Collection<Object> dstT = (Collection<Object>) dst;

            dstT.clear();
            dstT.addAll(srcT);
        } else {
            return false;
        }

        return true;
    }

    private static Class<?> box(Class<?> clazz) {
        if (clazz == byte.class) return Byte.class;
        if (clazz == short.class) return Short.class;
        if (clazz == int.class) return Integer.class;
        if (clazz == long.class) return Long.class;
        if (clazz == float.class) return Float.class;
        if (clazz == double.class) return Double.class;
        if (clazz == boolean.class) return Boolean.class;
        if (clazz == char.class) return Character.class;

        return clazz;
    }

    private static Class<?> unbox(Class<?> clazz) {
        if (clazz == Byte.class) return byte.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Integer.class) return int.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Character.class) return char.class;

        return clazz;
    }

    private static Class<?> getClass(String type) {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing type from the class path expected by network: " + type, e);
        }
    }

    private static int idFromType(EncodedType type) {
        return type.ordinal();
    }

    private static EncodedType typeFromId(int id) {
        if (id < 0 || id >= EncodedType.types.length) throw new IllegalArgumentException("invalid type id: " + id);

        return EncodedType.types[id];
    }

    private static EncodedType typeFromObject(Object o) {
        if (o == null) return EncodedType.Null;

        return typeFromClass(o.getClass());
    }

    private static EncodedType typeFromClass(Class<?> cls) {
        if (cls == null) return EncodedType.Null;
        if (cls.isArray()) return EncodedType.Array;

        if (cls.isPrimitive()) cls = box(cls);

        EncodedType ret = EncodedType.classToTypeMap.get(cls);
        if (ret != null) return ret;

        ret = classToTypeCache.get(cls);
        if (ret != null) return ret;

        INetworkCustomEncoder ince = classToAddonType.get(cls);
        if (ince != null) {
            ret = ince.isThreadSafe() ? EncodedType.Addon : EncodedType.UnSafeAddon;
            classToTypeCache.put(cls, ret);
            return ret;
        }

        for (EncodedType type : EncodedType.types) {
            if (type.cls != null && type.cls.isAssignableFrom(cls)) {
                classToTypeCache.put(cls, type);
                return type;
            }
        }

        throw new IllegalStateException("unmatched " + cls);
    }

    public enum EncodedType {
        Null(null), Array(null),
        Byte(Byte.class), Short(Short.class), Integer(Integer.class), Long(Long.class), Float((String) Float.class), Double((String) Double.class),
        Boolean(Boolean.class), Character(Character.class),
        String(String.class), Enum(Enum.class), UUID(UUID.class),
        Block(Block.class), Item(Item.class), TileEntity(BlockEntity.class, false), ItemStack(ItemStack.class),
        World(Level.class, false), NBTTagCompound(CompoundTag.class), ResourceLocation(ResourceLocation.class), GameProfile(GameProfile.class),
        Potion(Potion.class), Enchantment(Enchantment.class),
        BlockPos(BlockPos.class), ChunkPos(ChunkPos.class), Vec3(Vec3d.class),
        Fluid(Fluid.class), FluidStack(FluidStack.class), FluidTank(FluidTank.class),
        InvSlot(ICContainer.class), Component(BlockEntityComponent.class, false), CropCard(CropCard.class),
        ElectrolyzerRecipe(IElectrolyzerRecipeManager.ElectrolyzerRecipe.class), TupleT2(Tuple.T2.class), TupleT3(Tuple.T3.class),

        Addon(null), UnSafeAddon(null, false),

        Collection(Collection.class), Object(Object.class);

        final Class<?> cls;

        final boolean threadSafe;

        static final EncodedType[] types = values();
        static final Map<Class<?>, EncodedType> classToTypeMap = new IdentityHashMap<>(types.length - 2);

        EncodedType(Class<?> cls) {
            this(cls, true);
        }

        EncodedType(Class<?> cls, boolean threadSafe) {
            this.cls = cls;
            this.threadSafe = threadSafe;
        }

        static {
            for (EncodedType type : types) {
                if (type.cls != null) classToTypeMap.put(type.cls, type);
            }
            if (types.length > 255) throw new RuntimeException("too many types");
        }
    }

    public static void addNetworkEncoder(Class<?> typeBeingEncoded, INetworkCustomEncoder customEncoder) {
        assert typeBeingEncoded != null && customEncoder != null;

        INetworkCustomEncoder previous = classToAddonType.put(typeBeingEncoded, customEncoder);

        if (previous != null) {
            throw new IllegalStateException("Duplicate mapping for class! " + previous.getClass().getName() + " and " + customEncoder.getClass().getName() + " both map for " + typeBeingEncoded.getName() + '.');
        }
    }

    private static final Map<Class<?>, EncodedType> classToTypeCache = Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<Class<?>, INetworkCustomEncoder> classToAddonType = Collections.synchronizedMap(new IdentityHashMap<>());

    private static interface IResolvableValue<T> {
        T get();
    }
     */
}

