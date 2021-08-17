package com.iteale.industrialcase.core.block.comp;


import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class Components
{
    public static void init() {
        register(Energy.class, "energy");
        // register((Class)Fluids.class, "fluid");
        // register((Class)FluidReactorLookup.class, "fluidReactorLookup");
        register(Obscuration.class, "obscuration");
        // register(Process.class, "process");
        register(Redstone.class, "redstone");
        register(RedstoneEmitter.class, "redstoneEmitter");
        register(ComparatorEmitter.class, "comparatorEmitter");
        // register((Class)ProcessingComponent.class, "processingComponent");
        // register((Class)Covers.class, "covers");
    }

    public static void register(Class<? extends BlockEntityComponent> cls, String id) {
        if (idComponentMap.put(id, cls) != null) throw new IllegalStateException("duplicate id: " + id);
        if (componentIdMap.put(cls, id) != null) throw new IllegalStateException("duplicate component: " + cls.getName());

    }

    public static <T extends BlockEntityComponent> Class<T> getClass(String id) {
        if (idComponentMap.size() == 0 && componentIdMap.size() == 0)
            init();
        return (Class<T>)idComponentMap.get(id);
    }

    public static String getId(Class<? extends BlockEntityComponent> cls) {
        if (idComponentMap.size() == 0 && componentIdMap.size() == 0)
            init();
        return componentIdMap.get(cls);
    }

    private static final Map<String, Class<? extends BlockEntityComponent>> idComponentMap = new HashMap<>();
    private static final Map<Class<? extends BlockEntityComponent>, String> componentIdMap = new IdentityHashMap<>();
}
