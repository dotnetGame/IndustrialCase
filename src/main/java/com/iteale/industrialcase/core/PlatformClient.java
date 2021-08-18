package com.iteale.industrialcase.core;


import com.iteale.industrialcase.core.ref.BlockName;
import com.iteale.industrialcase.core.ref.FluidName;
import com.iteale.industrialcase.core.util.LogCategory;
import com.iteale.industrialcase.core.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.swing.*;
import java.util.logging.Level;

@OnlyIn(Dist.CLIENT)
public class PlatformClient
        extends Platform
{
    public boolean isRendering() {
        return Minecraft.getInstance().isSameThread();
    }


    public void preInit() {
        /*
        ClientCommandHandler.instance.registerCommand(new CommandIc2c());
        for (BlockName name : BlockName.values) {
            if (!name.hasInstance()) {
                IndustrialCase.log.warn(LogCategory.Block, "The block " + name + " is not initialized.");
            }
            else {
                ((IBlockModelProvider)name.getInstance()).registerModels(name);
            }
        }
        for (BlockTileEntity block : TeBlockRegistry.getAllBlocks()) {
            if (!block.isIC2()) {
                block.registerModels(null);
            }
        }


        for (ItemName name : ItemName.values) {
            if (!name.hasInstance()) {
                IC2.log.warn(LogCategory.Item, "The item " + name + " is not initialized.");
            }
            else {

                ((IItemModelProvider)name.getInstance()).registerModels(name);
            }
        }

        for (FluidName name : FluidName.values) {
            if (!name.hasInstance()) {
                IC2.log.warn(LogCategory.Block, "The fluid " + name + " is not initialized.");
            }
            else {

                Fluid provider = name.getInstance();

                if (provider instanceof IFluidModelProvider) {
                    ((IFluidModelProvider)provider).registerModels(name);
                }
            }
        }

        Ic2ModelLoader loader = new Ic2ModelLoader();

        loader.register("models/block/cf/wall", (IReloadableModel)new RenderBlockWall());
        loader.register("models/block/crop/crop", (IReloadableModel)new CropModel());
        loader.register("models/block/wiring/cable", (IReloadableModel)new CableModel());
        loader.register("models/block/transport/item_pipe", (IReloadableModel)new PipeModel());

        loader.register("models/item/cell/fluid_cell", (IReloadableModel)new FluidCellModel());
        loader.register("models/item/tool/electric/obscurator", (IReloadableModel)new RenderObscurator());

        ModelLoaderRegistry.registerLoader((ICustomModelLoader)loader);

        ProfileManager.doTextureChanges();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPersonalChest.class, (TileEntitySpecialRenderer)new TileEntityPersonalChestRenderer());
        KineticGeneratorRenderer<TileEntityInventory> kineticRenderer = new KineticGeneratorRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindKineticGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterKineticGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterGenerator.class, (TileEntitySpecialRenderer)kineticRenderer);

        RenderingRegistry.registerEntityRenderingHandler(EntityIC2Explosive.class, new IRenderFactory<EntityIC2Explosive>()
        {
            public Render<EntityIC2Explosive> createRenderFor(RenderManager manager) {
                return (Render<EntityIC2Explosive>)new RenderExplosiveBlock(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityMiningLaser.class, new IRenderFactory<EntityMiningLaser>()
        {
            public Render<EntityMiningLaser> createRenderFor(RenderManager manager) {
                return (Render<EntityMiningLaser>)new RenderCrossed(manager, new ResourceLocation("ic2", "textures/models/laser.png"));
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityIC2Boat.class, new IRenderFactory<EntityBoat>()
        {
            public Render<EntityBoat> createRenderFor(RenderManager manager) {
                return (Render<EntityBoat>)new RenderIC2Boat(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new IRenderFactory<EntityDynamite>()
        {
            public Render<EntityDynamite> createRenderFor(RenderManager manager) {
                return (Render<EntityDynamite>)new RenderSnowball(manager, ItemName.dynamite.getInstance(), PlatformClient.this.mc.getRenderItem());
            }
        });

        if (Util.inDev()) RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, manager -> new RenderBeam(manager));


        GlTexture.init();
         */
    }

    public void displayError(String error, Object... args) {
        if (!this.mc.isSameThread()) {
            super.displayError(error, args);

            return;
        }
        if (args.length > 0) error = String.format(error, args);

        error = "IndustrialCase Error\n\n" + error;
        String dialogError = error.replaceAll("([^\n]{80,}?) ", "$1\n");
        error = error.replace("\n", System.getProperty("line.separator"));
        dialogError = dialogError.replace("\n", System.getProperty("line.separator"));

        IndustrialCase.log.error(LogCategory.General, "%s", error);

        this.mc.setWindowActive(false);

        Util.exit(1);
    }


    public Player getPlayerInstance() {
        return (Player)this.mc.player;
    }

    /*
    public Level getWorld(String dimId) {
        if (isSimulating()) {
            return super.getWorld(dimId);
        }

        WorldClient worldClient = this.mc.world;
        return (((Level)worldClient).provider.getDimension() == dimId) ? (World)worldClient : null;
    }


    public Level getPlayerWorld() {
        return (Level)this.mc.world;
    }


    public void messagePlayer(Player player, String message, Object... args) {
        if (args.length > 0) {
            this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentTranslation(message, (Object[])getMessageComponents(args)));
        } else {
            this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(message));
        }
    }


    public boolean launchGuiClient(EntityPlayer player, IHasGui inventory, boolean isAdmin) {
        this.mc.displayGuiScreen(inventory.getGui(player, isAdmin));

        return true;
    }


    public void profilerStartSection(String section) {
        if (isRendering()) { this.mc.mcProfiler.startSection(section); }
        else { super.profilerStartSection(section); }

    }

    public void profilerEndSection() {
        if (isRendering()) { this.mc.mcProfiler.endSection(); }
        else { super.profilerEndSection(); }

    }

    public void profilerEndStartSection(String section) {
        if (isRendering()) { this.mc.mcProfiler.endStartSection(section); }
        else { super.profilerEndStartSection(section); }

    }

    public File getMinecraftDir() {
        return this.mc.mcDataDir;
    }


    public void playSoundSp(String sound, float f, float g) {
        IC2.audioManager.playOnce(getPlayerInstance(), PositionSpec.Hand, sound, true, IC2.audioManager.getDefaultVolume());
    }


    public void onPostInit() {
        MinecraftForge.EVENT_BUS.register(new GuiOverlayer(this.mc));
        new RpcHandler();
        new ElectricItemTooltipHandler();

        Block leaves = BlockName.leaves.getInstance();
        this.mc.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                return 6723908;
            }
        },  new Block[] { leaves });
        this.mc.getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                return 6723908;
            }
        },  new Block[] { leaves });

        this.mc.getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                return (tintIndex > 0) ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
            }
        },  new Item[] { ItemName.quantum_helmet.getInstance(), ItemName.quantum_chestplate.getInstance(), ItemName.quantum_leggings.getInstance(), ItemName.quantum_boots.getInstance() });

        this.mc.getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                PipeType type = ItemFluidPipe.getPipeType(stack);
                return (type.red & 0xFF) << 16 | (type.green & 0xFF) << 8 | type.blue & 0xFF;
            }
        },  new Item[] { ItemName.pipe.getInstance() });

        this.mc.getBlockColors().registerBlockColorHandler(new IBlockColor()
        {
            public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                String variant = ((MetaTeBlock)state.getValue(((BlockTileEntity)state.getBlock()).typeProperty)).teBlock.getName();
                if (variant.endsWith("_storage_box")) {
                    switch (variant) {
                        case "wooden_storage_box":
                            return 10454093;
                        case "iron_storage_box":
                            return 13158600;
                        case "bronze_storage_box":
                            return 16744448;
                        case "steel_storage_box":
                            return 8421504;
                    }
                    return 16777215;
                }

                return 16777215;
            }
        },  new Block[] { BlockName.te.getInstance() });

        this.mc.getItemColors().registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                String variant = Objects.<String>requireNonNull(BlockName.te.getVariant(stack));
                if (variant.endsWith("_storage_box")) {
                    switch (variant) {
                        case "wooden_storage_box":
                            return 10454093;
                        case "iron_storage_box":
                            return 13158600;
                        case "bronze_storage_box":
                            return 16744448;
                        case "steel_storage_box":
                            return 8421504;
                    }
                    return 16777215;
                }

                return 16777215;
            }
        },  new Block[] { BlockName.te.getInstance() });

        this.mc.getItemColors().registerItemColorHandler((stack, tintIndex) -> { PumpCoverType type = (PumpCoverType)((ItemPumpCover)stack.getItem()).getType(stack); return (tintIndex == 1) ? type.color : 16777215; }new Item[] { ItemName.cover


                .getInstance() });
    }

    public void requestTick(boolean simulating, Runnable runnable) {
        if (simulating) {
            super.requestTick(simulating, runnable);
        } else {
            this.mc.addScheduledTask(runnable);
        }
    }


    public int getColorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tint) {
        return this.mc.getBlockColors().colorMultiplier(state, world, pos, tint);
    }
     */


    private final Minecraft mc = Minecraft.getInstance();
}
