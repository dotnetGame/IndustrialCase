package com.iteale.industrialcase.core.ref;

import com.iteale.industrialcase.core.IndustrialCase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

public enum FluidName
{
    air(false),
    biogas(false),
    biomass,
    construction_foam,
    coolant,

    distilled_water,
    hot_coolant,
    hot_water,
    pahoehoe_lava(false),
    steam(false),
    superheated_steam(false),
    uu_matter,
    weed_ex(false),
    oxygen(false),
    hydrogen(false),
    heavy_water,
    deuterium(false),
    creosote,


    molten_brass(false),
    molten_bronze(false),
    molten_copper(false),
    molten_gold(false),
    molten_iron(false),
    molten_lead(false),
    molten_silver(false),
    molten_steel(false),
    molten_tin(false),
    molten_zinc(false),

    milk;

    public static final FluidName[] values;

    private final boolean hasFlowTexture;
    private Fluid instance;

    FluidName() {
        this(true);
    }

    FluidName(boolean hasFlowTexture) {
        this.hasFlowTexture = hasFlowTexture;
    }


    public String getName() {
        return "ic2" + name();
    }


    public int getId() {
        throw new UnsupportedOperationException();
    }

    public ResourceLocation getTextureLocation(boolean flowing) {
        if (name().startsWith("molten_")) {
            return new ResourceLocation(IndustrialCase.MODID, "blocks/fluid/molten_metal");
        }

        String type = (flowing && this.hasFlowTexture) ? "flow" : "still";

        return new ResourceLocation(IndustrialCase.MODID, "blocks/fluid/" + name() + "_" + type);
    }

    public boolean hasInstance() {
        return (this.instance != null);
    }

    public Fluid getInstance() {
        if (this.instance == null) throw new IllegalStateException("the requested fluid instance for " + name() + " isn't set (yet)");

        return this.instance;
    }

    public void setInstance(Fluid fluid) {
        if (fluid == null) throw new NullPointerException("null fluid");
        if (this.instance != null) throw new IllegalStateException("conflicting instance");

        this.instance = fluid;
    }
    static {
        values = values();
    }
}

