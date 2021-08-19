package com.iteale.industrialcase.core.ref;


import com.iteale.industrialcase.core.block.TileEntityBlock;
import com.iteale.industrialcase.core.block.BlockTileEntity;
import com.iteale.industrialcase.core.block.ITeBlock;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

import java.lang.annotation.*;
import java.util.Set;

public enum TeBlock implements ITeBlock, ITeBlock.ITeBlockCreativeRegisterer
{
    invalid(null, 0, false, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, 5.0F, 10.0F, Rarity.COMMON);

    // barrel(TileEntityBarrel.class, -1, true, Util.horizontalFacings, false, HarvestTool.Axe, DefaultDrop.None, 2.0F, 6.0F, EnumRarity.COMMON),
    //
    // wall(TileEntityWall.class, -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 3.0F, 30.0F, EnumRarity.COMMON),
    //
    // itnt(ITnt.class, 1, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0F, 0.0F, EnumRarity.COMMON),
    // nuke(TileEntityNuke.delegate(), 2, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0F, 0.0F, EnumRarity.UNCOMMON),
    //
    // generator(TileEntityGenerator.class, 3, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // geo_generator(TileEntityGeoGenerator.class, 4, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // kinetic_generator(TileEntityKineticGenerator.class, 5, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // rt_generator(TileEntityRTGenerator.class, 6, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // semifluid_generator(TileEntitySemifluidGenerator.class, 7, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // solar_generator(TileEntitySolarGenerator.class, 8, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // stirling_generator(TileEntityStirlingGenerator.class, 9, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // water_generator(TileEntityWaterGenerator.class, 10, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // wind_generator(TileEntityWindGenerator.class, 11, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    //
    // electric_heat_generator(TileEntityElectricHeatGenerator.class, 12, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // fluid_heat_generator(TileEntityFluidHeatGenerator.class, 13, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // rt_heat_generator(TileEntityRTHeatGenerator.class, 14, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // solid_heat_generator(TileEntitySolidHeatGenerator.class, 15, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    //
    // electric_kinetic_generator(TileEntityElectricKineticGenerator.class, 16, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // manual_kinetic_generator(TileEntityManualKineticGenerator.class, 17, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
    // steam_kinetic_generator(TileEntitySteamKineticGenerator.class, 18, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // stirling_kinetic_generator(TileEntityStirlingKineticGenerator.class, 19, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // water_kinetic_generator(TileEntityWaterKineticGenerator.class, 20, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // wind_kinetic_generator(TileEntityWindKineticGenerator.class, 21, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    //
    // nuclear_reactor(TileEntityNuclearReactorElectric.class, 22, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // reactor_access_hatch(TileEntityReactorAccessHatch.class, 23, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
    // reactor_chamber(TileEntityReactorChamberElectric.class, 24, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // reactor_fluid_port(TileEntityReactorFluidPort.class, 25, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
    // reactor_redstone_port(TileEntityReactorRedstonePort.class, 26, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
// 
    // condenser(TileEntityCondenser.class, 27, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // fluid_bottler(TileEntityFluidBottler.class, 28, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // fluid_distributor(TileEntityFluidDistributor.class, 29, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // fluid_regulator(TileEntityFluidRegulator.class, 30, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // liquid_heat_exchanger(TileEntityLiquidHeatExchanger.class, 31, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // pump(TileEntityPump.class, 32, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // solar_distiller(TileEntitySolarDestiller.class, 33, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // steam_generator(TileEntitySteamGenerator.class, 34, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
// 
    // item_buffer(TileEntityItemBuffer.class, 35, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // luminator_flat(TileEntityLuminator.class, 36, true, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
    // magnetizer(TileEntityMagnetizer.class, 37, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // sorting_machine(TileEntitySortingMachine.class, 38, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // teleporter(TileEntityTeleporter.class, 39, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
    // terraformer(TileEntityTerra.class, 40, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // tesla_coil(TileEntityTesla.class, 41, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
// 
    // canner(TileEntityCanner.delegate(), 42, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // compressor(TileEntityCompressor.class, 43, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // electric_furnace(TileEntityElectricFurnace.class, 44, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // extractor(TileEntityExtractor.class, 45, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // iron_furnace(TileEntityIronFurnace.class, 46, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
    // macerator(TileEntityMacerator.class, 47, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // recycler(TileEntityRecycler.class, 48, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // solid_canner(TileEntitySolidCanner.class, 49, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
// 
    // blast_furnace(TileEntityBlastFurnace.class, 50, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // block_cutter(TileEntityBlockCutter.class, 51, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // centrifuge(TileEntityCentrifuge.class, 52, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // fermenter(TileEntityFermenter.class, 53, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // induction_furnace(TileEntityInduction.class, 54, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // metal_former(TileEntityMetalFormer.class, 55, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // ore_washing_plant(TileEntityOreWashing.class, 56, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
// 
    // advanced_miner(TileEntityAdvMiner.class, 57, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // crop_harvester(TileEntityCropHarvester.class, 58, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // cropmatron(TileEntityCropmatron.delegate(), 59, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // miner(TileEntityMiner.class, 60, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
// 
    // mass_fabricator(TileEntityMassFabricator.delegate(), 92, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
    // uu_assembly_bench(TileEntityAssemblyBench.class, 93, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // matter_generator(TileEntityMatter.class, 61, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
    // pattern_storage(TileEntityPatternStorage.class, 62, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // replicator(TileEntityReplicator.class, 63, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // scanner(TileEntityScanner.class, 64, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
// 
    // energy_o_mat(TileEntityEnergyOMat.class, 65, false, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.COMMON),
    // personal_chest(TileEntityPersonalChest.class, 66, false, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.UNCOMMON),
    // trade_o_mat(TileEntityTradeOMat.class, 67, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.COMMON),
    // trading_terminal(TileEntityTradingTerminal.class, 94, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // 
    // cable(TileEntityCable.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.CLOTH, true),
    // detector_cable(TileEntityCableDetector.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.CLOTH, false),
    // splitter_cable(TileEntityCableSplitter.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.CLOTH, false),
    //
    // chargepad_batbox(TileEntityChargepadBatBox.class, 68, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // chargepad_cesu(TileEntityChargepadCESU.class, 69, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // chargepad_mfe(TileEntityChargepadMFE.class, 70, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // chargepad_mfsu(TileEntityChargepadMFSU.class, 71, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // 
    // batbox(TileEntityElectricBatBox.class, 72, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // cesu(TileEntityElectricCESU.class, 73, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // mfe(TileEntityElectricMFE.delegate(), 74, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // mfsu(TileEntityElectricMFSU.delegate(), 75, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // electrolyzer(TileEntityElectrolyzer.delegate(), 76, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // 
    // lv_transformer(TileEntityTransformerLV.class, 77, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // mv_transformer(TileEntityTransformerMV.class, 78, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // hv_transformer(TileEntityTransformerHV.class, 79, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // ev_transformer(TileEntityTransformerEV.class, 80, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // 
    // 
    // tank(TileEntityTank.class, 81, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // chunk_loader(TileEntityChunkloader.class, 82, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // item_buffer_2(TileEntityBetterItemBuffer.class, 83, false, Util.noFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // creative_generator(TileEntityCreativeGenerator.class, 86, true, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, EnumRarity.COMMON),
    // steam_repressurizer(TileEntitySteamRepressurizer.class, 87, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // weighted_fluid_distributor(TileEntityWeightedFluidDistributor.class, 90, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // weighted_item_distributor(TileEntityWeightedItemDistributor.class, 91, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // 
    // 
    // rci_rsh(TileEntityRCI_RSH.class, 84, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // rci_lzh(TileEntityRCI_LZH.class, 85, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // 
    // crop(TileEntityCrop.class, -1, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 0.8F, 0.2F, EnumRarity.COMMON, Material.PLANTS, true),
    // 
    // 
    // industrial_workbench(TileEntityIndustrialWorkbench.class, 88, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // batch_crafter(TileEntityBatchCrafter.class, 89, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // 
    // fluid_pipe(TileEntityFluidPipe.class, -1, false, Util.allFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 5.0F, EnumRarity.COMMON, IC2Material.PIPE, true),
    // 
    // coke_kiln(TileEntityCokeKiln.class, 100, true, Util.horizontalFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.ROCK, false),
    // coke_kiln_hatch(TileEntityCokeKilnHatch.class, 101, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.ROCK, false),
    // coke_kiln_grate(TileEntityCokeKilnGrate.class, 102, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.ROCK, false),
    // 
    // wooden_storage_box(TileEntityWoodenStorageBox.class, 111, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 1.0F, 10.0F, EnumRarity.COMMON, Material.WOOD, false),
    // iron_storage_box(TileEntityIronStorageBox.class, 112, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // bronze_storage_box(TileEntityBronzeStorageBox.class, 113, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
    // steel_storage_box(TileEntitySteelStorageBox.class, 114, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 20.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
    // iridium_storage_box(TileEntityIridiumStorageBox.class, 115, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 100.0F, EnumRarity.EPIC, IC2Material.MACHINE, false),
    // 
    // bronze_tank(TileEntityBronzeTank.class, 131, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
    // iron_tank(TileEntityIronTank.class, 132, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
    // steel_tank(TileEntitySteelTank.class, 133, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 4.0F, 20.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
    // iridium_tank(TileEntityIridiumTank.class, 134, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 5.0F, 100.0F, EnumRarity.COMMON, IC2Material.MACHINE, true);

    private static final ResourceLocation loc;

    private final Class<? extends TileEntityBlock> teClass;
    private final int itemMeta;
    private final boolean hasActive;
    private final Set<Direction> supportedFacings;
    private final boolean allowWrenchRotating;
    private final HarvestTool harvestTool;
    private final DefaultDrop defaultDrop;
    private final float hardness;

    TeBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<Direction> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, Rarity rarity, Material material, boolean transparent) {
        this.teClass = teClass;
        this.itemMeta = itemMeta;
        this.hasActive = hasActive;
        this.supportedFacings = supportedFacings;
        this.allowWrenchRotating = allowWrenchRotating;
        this.harvestTool = harvestTool;
        this.defaultDrop = defaultDrop;
        this.hardness = hardness;
        this.explosionResistance = explosionResistance;
        this.rarity = rarity;
        this.material = material;
        this.transparent = transparent;
    }
    private final float explosionResistance;
    private final Rarity rarity;
    private final Material material;
    private final boolean transparent;
    private TileEntityBlock dummyTe;
    private ITePlaceHandler placeHandler;
    public static final TeBlock[] values;
    private static final String teIdPrefix = "ic2:";
    private static final String teClassicPrefix = "Old";

    public boolean hasItem() {
        return (this.teClass != null && this.itemMeta != -1);
    }

    public String getName() {
        return name();
    }
    static {
        loc = new ResourceLocation("ic2", "te");
        values = values();
    }

    public ResourceLocation getIdentifier() {
        return loc;
    }

    public Class<? extends TileEntityBlock> getTeClass() {
        return this.teClass;
    }

    public boolean hasActive() {
        return this.hasActive;
    }

    public int getId() {
        return this.itemMeta;
    }

    public float getHardness() {
        return this.hardness;
    }

    public HarvestTool getHarvestTool() {
        return this.harvestTool;
    }

    public DefaultDrop getDefaultDrop() {
        return this.defaultDrop;
    }

    public float getExplosionResistance() {
        return this.explosionResistance;
    }

    public boolean allowWrenchRotating() {
        return this.allowWrenchRotating;
    }

    public Set<Direction> getSupportedFacings() {
        return this.supportedFacings;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public Material getMaterial() {
        return this.material;
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    @Override
    public void addSubBlocks(NonNullList<ItemStack> param1NonNullList, BlockTileEntity param1BlockTileEntity, ItemBlockTileEntity param1ItemBlockTileEntity, CreativeTabs param1CreativeTabs) {

    }
    /*
    public void addSubBlocks(NonNullList<ItemStack> list, EntityBlockBase block, ItemBlockTileEntity item, CreativeModeTab tab) {
        if (tab == IndustrialCase.TAB_IC || tab == CreativeModeTab.TAB_SEARCH)
            for (TeBlock type : values) {
                if (type.hasItem() && Version.shouldEnable(type.teClass)) {
                    list.add(block.getItemStack(type));
                    if (type.getDummyTe() instanceof IEnergyStorage) {
                        ItemStack filled = block.getItemStack(type);
                        StackUtil.getOrCreateNbtData(filled).putDouble("energy", ((IEnergyStorage)type.getDummyTe()).getCapacity());
                        list.add(filled);
                    }
                }
            }
    }
     */

    public void setPlaceHandler(ITePlaceHandler handler) {
        if (this.placeHandler != null)
            throw new RuntimeException("duplicate place handler");
        this.placeHandler = handler;
    }

    public ITePlaceHandler getPlaceHandler() {
        return this.placeHandler;
    }

    public static void buildDummies() {
        ModContainer mc = ModLoadingContext.get().getActiveContainer();
        if (mc == null || !"ic2".equals(mc.getModId()))
            throw new IllegalAccessError("Don't mess with this please.");
        for (TeBlock block : values) {
            if (block.teClass != null)
                try {
                    block.dummyTe = block.teClass.newInstance();
                } catch (Exception e) {
                    if (Util.inDev())
                        e.printStackTrace();
                }
        }
    }

    @Deprecated
    public TileEntityBlock getDummyTe() {
        return this.dummyTe;
    }

    /*
    public static void registerTeMappings() {
        for (TeBlock block : values) {
            if (block.teClass != null)
                if (block.teClass.isAnnotationPresent(Delegated.class)) {
                    Delegated delegation = block.teClass.<Delegated>getAnnotation(Delegated.class);
                    assert delegation != null;
                    GameRegistry.registerTileEntity(delegation.current(), "ic2:" + block.getName());
                    GameRegistry.registerTileEntity(delegation.old(), "ic2:Old" + block.getName());
                    TeBlockRegistry.ensureMapping(block, delegation.current());
                    TeBlockRegistry.ensureMapping(block, delegation.old());
                } else {
                    GameRegistry.registerTileEntity(block.teClass, "ic2:" + block.getName());
                }
        }
    }
     */

    @Inherited
    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Delegated {
        Class<? extends TileEntityBlock> old();

        Class<? extends TileEntityBlock> current();
    }

    public enum HarvestTool {
        None(null, -1),
        Pickaxe("pickaxe", 0),
        Shovel("shovel", 0),
        Axe("axe", 0),
        Wrench("wrench", 0);
        public final String toolClass;
        public final int level;

        HarvestTool(String toolClass, int level) {
            this.toolClass = toolClass;
            this.level = level;
        }
    }

    public enum DefaultDrop {
        Self, None, Generator, Machine, AdvMachine;
    }

    public static interface ITePlaceHandler {
        boolean canReplace(Level param1World, BlockPos param1BlockPos, Direction param1Direction, ItemStack param1ItemStack);
    }
}