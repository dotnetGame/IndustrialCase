package com.iteale.industrialcase.core.init;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksItems {
    /*
    public static void init() {
        initPotions();
        initBlocks();
        initFluids();
        initItems();
        initMigration();
    }

    private static void initPotions() {
        Info.POTION_RADIATION = (Potion)(IC2Potion.radiation = new IC2Potion("radiation", true, 5149489, new ItemStack[0]));
    }

    private static void initBlocks() {
        TeBlockRegistry.addAll(TeBlock.class, TeBlock.invalid.getIdentifier());
        TeBlockRegistry.addCreativeRegisterer((ITeBlock)TeBlock.invalid);
        TeBlock.reactor_chamber.setPlaceHandler(ItemHandlers.reactorChamberPlace);
        TeBlockRegistry.buildBlocks();

        ItemBlockTileEntity itemTeBlock = TeBlockRegistry.get(TeBlock.itnt.getIdentifier()).getItem();
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemTeBlock, new BehaviorTeBlockDispense());

        BlockOre.create();
        BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.copper_ore));
        BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.lead_ore));
        BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.tin_ore));
        BlockName.resource.getInstance().setHarvestLevel("pickaxe", 2, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.uranium_ore));
        BlockName.resource.getInstance().setHarvestLevel("pickaxe", 2, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.reinforced_stone));


        new Ic2Leaves();
        OreDictionary.registerOre("woodRubber", (Block)new BlockRubWood());
        BlockName.rubber_wood.getInstance().setHarvestLevel("axe", 0);
        new Ic2Sapling();

        BlockScaffold.create();
        BlockIC2Fence.create();
        BlockSheet.create();
        BlockTexGlass.create();
        BlockFoam.create();
        BlockWall.create();
        BlockMiningPipe.create();
        new BlockIC2Door();

        new BlockDynamite();

        new BlockRefractoryBricks();
    }

    private static void initFluids() {
        MaterialLiquid materialLiquid = new MaterialLiquid(MapColor.SILVER);

        registerIC2fluid(FluidName.uu_matter, Material.WATER, 3867955, 3000, 3000, 0, 300, false);
        registerIC2fluid(FluidName.construction_foam, Material.WATER, 2105376, 10000, 50000, 0, 300, false);
        registerIC2fluid(FluidName.coolant, Material.WATER, 1333866, 1000, 3000, 0, 300, false);
        registerIC2fluid(FluidName.creosote, Material.WATER, 4012298, 10000, 50000, 0, 300, false);
        registerIC2fluid(FluidName.hot_coolant, Material.WATER, 11872308, 1000, 3000, 0, 1200, false);
        registerIC2fluid(FluidName.pahoehoe_lava, Material.WATER, 8090732, 50000, 250000, 10, 1200, false);
        registerIC2fluid(FluidName.biomass, Material.WATER, 3632933, 1000, 3000, 0, 300, false);
        registerIC2fluid(FluidName.biogas, Material.WATER, 10983500, 1000, 3000, 0, 300, true);
        registerIC2fluid(FluidName.distilled_water, Material.WATER, 4413173, 1000, 1000, 0, 300, false);
        registerIC2fluid(FluidName.superheated_steam, (Material)materialLiquid, 13291985, -3000, 100, 0, 600, true);
        registerIC2fluid(FluidName.steam, (Material)materialLiquid, 12369084, -800, 300, 0, 420, true);
        registerIC2fluid(FluidName.hot_water, Material.WATER, 4644607, 1000, 1000, 0, 350, false);
        registerIC2fluid(FluidName.weed_ex, Material.WATER, 478996, 1000, 1000, 0, 300, false);
        registerIC2fluid(FluidName.air, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
        registerIC2fluid(FluidName.hydrogen, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
        registerIC2fluid(FluidName.oxygen, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
        registerIC2fluid(FluidName.heavy_water, Material.WATER, 4413173, 1000, 1000, 0, 300, false);
        registerIC2fluid(FluidName.milk, Material.WATER, 16579836, 1050, 1000, 0, 300, false);
    }

    private static void initItems() {
        ItemArmor.ArmorMaterial bronzeArmorMaterial = EnumHelper.addArmorMaterial("IC2_BRONZE", "IC2_BRONZE", 15, new int[] { 2, 5, 6, 2 }, 9, null, 0.0F);
        ItemArmor.ArmorMaterial alloyArmorMaterial = EnumHelper.addArmorMaterial("IC2_ALLOY", "IC2_ALLOY", 50, new int[] { 4, 7, 9, 4 }, 12, null, 2.0F);


        new ItemArmorAdvBatpack();
        new ItemArmorIC2(ItemName.alloy_chestplate, alloyArmorMaterial, "alloy", EntityEquipmentSlot.CHEST, ItemName.crafting.getItemStack((Enum)CraftingItemType.alloy));
        new ItemArmorBatpack();
        new ItemArmorIC2(ItemName.bronze_boots, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.FEET, "ingotBronze");
        new ItemArmorIC2(ItemName.bronze_chestplate, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.CHEST, "ingotBronze");
        new ItemArmorIC2(ItemName.bronze_helmet, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.HEAD, "ingotBronze");
        new ItemArmorIC2(ItemName.bronze_leggings, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.LEGS, "ingotBronze");
        new ItemArmorCFPack();
        new ItemArmorEnergypack();
        new ItemArmorHazmat(ItemName.hazmat_chestplate, EntityEquipmentSlot.CHEST);
        new ItemArmorHazmat(ItemName.hazmat_helmet, EntityEquipmentSlot.HEAD);
        new ItemArmorHazmat(ItemName.hazmat_leggings, EntityEquipmentSlot.LEGS);
        new ItemArmorJetpack();
        new ItemArmorJetpackElectric();
        new ItemArmorLappack();
        new ItemArmorNanoSuit(ItemName.nano_boots, EntityEquipmentSlot.FEET);
        new ItemArmorNanoSuit(ItemName.nano_chestplate, EntityEquipmentSlot.CHEST);
        new ItemArmorNanoSuit(ItemName.nano_helmet, EntityEquipmentSlot.HEAD);
        new ItemArmorNanoSuit(ItemName.nano_leggings, EntityEquipmentSlot.LEGS);
        new ItemArmorNightvisionGoggles();
        new ItemArmorQuantumSuit(ItemName.quantum_boots, EntityEquipmentSlot.FEET);
        new ItemArmorQuantumSuit(ItemName.quantum_chestplate, EntityEquipmentSlot.CHEST);
        new ItemArmorQuantumSuit(ItemName.quantum_helmet, EntityEquipmentSlot.HEAD);
        new ItemArmorQuantumSuit(ItemName.quantum_leggings, EntityEquipmentSlot.LEGS);
        new ItemArmorHazmat(ItemName.rubber_boots, EntityEquipmentSlot.FEET);
        new ItemArmorSolarHelmet();
        new ItemArmorStaticBoots();


        new ItemIC2Boat();


        new ItemBarrel();
        new ItemMug();
        new ItemBooze();


        ItemMulti.create(ItemName.crushed, OreResourceType.class);
        ItemMulti.create(ItemName.purified, OreResourceType.class);
        ItemMulti.create(ItemName.dust, DustResourceType.class);
        ItemMulti.create(ItemName.ingot, IngotResourceType.class);

        ItemMulti.create(ItemName.plate, PlateResourceType.class);
        ItemMulti.create(ItemName.casing, CasingResourceType.class);
        ItemNuclearResource itemNuclearResource = new ItemNuclearResource();
        itemNuclearResource.setUpdateHandler(null, ItemHandlers.radioactiveUpdate);
        ItemMulti<MiscResourceType> miscResource = ItemMulti.create(ItemName.misc_resource, MiscResourceType.class);
        miscResource.setRarity((Enum)MiscResourceType.matter, EnumRarity.RARE);
        miscResource.setRarity((Enum)MiscResourceType.iridium_ore, EnumRarity.RARE);
        miscResource.setRarity((Enum)MiscResourceType.iridium_shard, EnumRarity.UNCOMMON);
        miscResource.setUseHandler((Enum)MiscResourceType.resin, ItemHandlers.resinUse);
        miscResource.setUseHandler((Enum)MiscResourceType.water_sheet, ItemHandlers.getFluidPlacer((Block)Blocks.WATER));
        miscResource.setUseHandler((Enum)MiscResourceType.lava_sheet, ItemHandlers.getFluidPlacer((Block)Blocks.LAVA));


        ItemMulti<CraftingItemType> crafting = ItemMulti.create(ItemName.crafting, CraftingItemType.class);
        crafting.setRarity((Enum)CraftingItemType.advanced_circuit, EnumRarity.UNCOMMON);
        crafting.setRarity((Enum)CraftingItemType.iridium, EnumRarity.RARE);
        crafting.setRightClickHandler((Enum)CraftingItemType.cf_powder, ItemHandlers.cfPowderApply);
        crafting.setRightClickHandler((Enum)CraftingItemType.scrap_box, ItemHandlers.scrapBoxUnpack);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(crafting, new BehaviorScrapboxDispense());

        new BlockCuttingBlade();

        new UpgradeKit();


        new ItemCrop();


        ItemMulti.create(ItemName.crop_res, CropResItemType.class);
        new ItemTerraWart();
        new ItemCropnalyzer();


        new ItemBattery(ItemName.re_battery, 10000.0D, 100.0D, 1);
        new ItemBattery(ItemName.advanced_re_battery, 100000.0D, 256.0D, 2)
        {
            protected boolean isEnabled() {
                return !IC2.version.isClassic();
            }
        };
        new ItemBattery(ItemName.energy_crystal, 1000000.0D, 2048.0D, 3);
        (new ItemBattery(ItemName.lapotron_crystal, 1.0E7D, 8092.0D, 4)).setRarity(EnumRarity.UNCOMMON);
        new ItemBatterySU(ItemName.single_use_battery, 1200, 1);
        new ItemBatteryChargeHotbar(ItemName.charging_re_battery, 40000.0D, 128.0D, 1);
        new ItemBatteryChargeHotbar(ItemName.advanced_charging_re_battery, 400000.0D, 1024.0D, 2);
        new ItemBatteryChargeHotbar(ItemName.charging_energy_crystal, 4000000.0D, 8192.0D, 3);
        (new ItemBatteryChargeHotbar(ItemName.charging_lapotron_crystal, 4.0E7D, 32768.0D, 4)).setRarity(EnumRarity.UNCOMMON);


        new ItemReactorHeatStorage(ItemName.heat_storage, 10000);
        new ItemReactorHeatStorage(ItemName.tri_heat_storage, 30000);
        new ItemReactorHeatStorage(ItemName.hex_heat_storage, 60000);
        new ItemReactorPlating(ItemName.plating, 1000, 0.95F);
        new ItemReactorPlating(ItemName.heat_plating, 2000, 0.99F);
        new ItemReactorPlating(ItemName.containment_plating, 500, 0.9F);
        new ItemReactorHeatSwitch(ItemName.heat_exchanger, 2500, 12, 4);
        new ItemReactorHeatSwitch(ItemName.reactor_heat_exchanger, 5000, 0, 72);
        new ItemReactorHeatSwitch(ItemName.component_heat_exchanger, 5000, 36, 0);
        new ItemReactorHeatSwitch(ItemName.advanced_heat_exchanger, 10000, 24, 8);
        new ItemReactorVent(ItemName.heat_vent, 1000, 6, 0);
        new ItemReactorVent(ItemName.reactor_heat_vent, 1000, 5, 5);
        new ItemReactorVent(ItemName.overclocked_heat_vent, 1000, 20, 36);
        new ItemReactorVentSpread(ItemName.component_heat_vent, 4);
        new ItemReactorVent(ItemName.advanced_heat_vent, 1000, 12, 0);
        new ItemReactorReflector(ItemName.neutron_reflector, 30000);
        new ItemReactorReflector(ItemName.thick_neutron_reflector, 120000);
        new ItemReactorIridiumReflector(ItemName.iridium_reflector);
        new ItemReactorCondensator(ItemName.rsh_condensator, 20000);
        new ItemReactorCondensator(ItemName.lzh_condensator, 100000);
        new ItemReactorHeatpack(1000, 1);


        new ItemReactorUranium(ItemName.uranium_fuel_rod, 1);
        new ItemReactorUranium(ItemName.dual_uranium_fuel_rod, 2);
        new ItemReactorUranium(ItemName.quad_uranium_fuel_rod, 4);
        new ItemReactorMOX(ItemName.mox_fuel_rod, 1);
        new ItemReactorMOX(ItemName.dual_mox_fuel_rod, 2);
        new ItemReactorMOX(ItemName.quad_mox_fuel_rod, 4);
        new ItemReactorLithiumCell();

        new ItemReactorDepletedUranium();


        new Tfbp();


        Item.ToolMaterial bronzeToolMaterial = EnumHelper.addToolMaterial("IC2_BRONZE", 2, 350, 6.0F, 2.0F, 13).setRepairItem(ItemName.ingot.getItemStack((Enum)IngotResourceType.bronze));
        new Ic2Axe(bronzeToolMaterial);
        new Ic2Hoe(bronzeToolMaterial);
        new Ic2Pickaxe(bronzeToolMaterial);
        new Ic2Shovel(bronzeToolMaterial);
        new Ic2Sword(bronzeToolMaterial);

        new ItemToolCutter();
        new ItemDebug();
        if (!IC2.version.isClassic()) { new ItemSprayer(); } else { new ItemClassicSprayer(); }
        new ItemToolHammer();
        new ItemFrequencyTransmitter();
        new ItemToolMeter();
        new ItemToolbox();
        new ItemTreetap();
        new ItemToolWrench();
        new ItemToolWrenchNew();
        new ItemToolCrowbar();
        new ItemContainmentbox();


        new ItemWeedingTrowel();
        new ItemCropSeed();


        new ItemScannerAdv();
        new ItemElectricToolChainsaw();
        new ItemDrill(ItemName.diamond_drill, 80, HarvestLevel.Diamond, 30000, 100, 1, 16.0F);
        new ItemDrill(ItemName.drill, 50, HarvestLevel.Iron, 30000, 100, 1, 8.0F);
        new ItemElectricToolHoe();
        new ItemTreetapElectric();
        new ItemToolWrenchElectric();
        new ItemDrillIridium();
        new ItemToolMiningLaser();
        new ItemNanoSaber();
        new ItemObscurator();

        new ItemScanner();
        new ItemWindmeter();


        new ItemToolPainter();


        new ItemFluidCell();
        ItemClassicCell itemClassicCell = new ItemClassicCell();
        itemClassicCell.setUseHandler((Enum)CellType.empty, ItemHandlers.emptyCellFill);
        itemClassicCell.addCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, stack -> {
            CellType type = (CellType)cell.getType(stack);

            return type.isFluidContainer() ? (IFluidHandlerItem)new CellType.CellFluidHandler(stack, cell::getType) : null;
        });

        new ItemCable();


        ItemUpgradeModule itemUpgradeModule = new ItemUpgradeModule();
        itemUpgradeModule.setRightClickHandler((Enum)ItemUpgradeModule.UpgradeType.advanced_ejector, ItemHandlers.openAdvancedUpgradeGUI);
        itemUpgradeModule.setRightClickHandler((Enum)ItemUpgradeModule.UpgradeType.advanced_pulling, ItemHandlers.openAdvancedUpgradeGUI);


        new ItemTinCan();
        new ItemFilledFuelCan();


        new ItemIodineTablet();


        new ItemCrystalMemory();


        new ItemWindRotor(ItemName.rotor_wood, 5, 10800, 0.25F, 10, 60, new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png"));
        new ItemWindRotor(ItemName.rotor_bronze, 7, 86400, 0.5F, 14, 75, new ResourceLocation("ic2", "textures/items/rotor/bronze_rotor_model.png"));
        new ItemWindRotor(ItemName.rotor_iron, 7, 86400, 0.5F, 14, 75, new ResourceLocation("ic2", "textures/items/rotor/iron_rotor_model.png"));
        new ItemWindRotor(ItemName.rotor_steel, 9, 172800, 0.75F, 17, 90, new ResourceLocation("ic2", "textures/items/rotor/steel_rotor_model.png"));
        new ItemWindRotor(ItemName.rotor_carbon, 11, 604800, 1.0F, 20, 110, new ResourceLocation("ic2", "textures/items/rotor/carbon_rotor_model.png"));

        new ItemDynamite(ItemName.dynamite);
        new ItemDynamite(ItemName.dynamite_sticky);
        new ItemRemote();

        new ItemFluidPipe();
        ItemPumpCover itemPumpCover = new ItemPumpCover();


        new ItemCoke();
    }

    private static void initMigration() {}

    private static void registerIC2fluid(FluidName name, Material material, int color, int density, int viscosity, int luminosity, int temperature, boolean isGaseous) {
        Fluid fluid = (new Ic2Fluid(name)).setDensity(density).setViscosity(viscosity).setLuminosity(luminosity).setTemperature(temperature).setGaseous(isGaseous);

        if (!FluidRegistry.registerFluid(fluid)) {
            fluid = FluidRegistry.getFluid(name.getName());
        }

        if (!fluid.canBePlacedInWorld()) {
            BlockIC2Fluid blockIC2Fluid = new BlockIC2Fluid(name, fluid, material, color);
            fluid.setBlock((Block)blockIC2Fluid);
            fluid.setUnlocalizedName(blockIC2Fluid.getUnlocalizedName().substring(4));
        } else {
            Block block = fluid.getBlock();
        }

        name.setInstance(fluid);


        FluidRegistry.addBucketForFluid(fluid);
    }
    */

    public static <T extends Item> T registerItem(T item, ResourceLocation rl) {
        item.setRegistryName(rl);
        return registerItem(item);
    }

    public static <T extends Item> T registerItem(T item) {
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

    public static <T extends Block> T registerBlock(T item, ResourceLocation rl) {
        item.setRegistryName(rl);
        return registerBlock(item);
    }

    public static <T extends Block> T registerBlock(T item) {
        ForgeRegistries.BLOCKS.register(item);
        return item;
    }
}

