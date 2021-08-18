package com.iteale.industrialcase.core;


import com.iteale.industrialcase.core.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class Platform
{
    public boolean isSimulating() {
        return !isRendering();
    }

    public boolean isRendering() {
        return false;
    }

    public void displayError(String error, Object... args) {
        if (args.length > 0) error = String.format(error, args);

        error = "IndustrialCase Error\n\n == = IndustrialCase Error = == \n\n" + error + "\n\n == == == == == == == == == == ==\n";
        error = error.replace("\n", System.getProperty("line.separator"));

        throw new RuntimeException(error);
    }

    public void displayError(Exception e, String error, Object... args) {
        if (args.length > 0) error = String.format(error, args);

        displayError("An unexpected Exception occured.\n\n" + getStackTrace(e) + "\n" + error, new Object[0]);
    }

    public String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        e.printStackTrace(printWriter);

        return writer.toString();
    }

    public Player getPlayerInstance() {
        return null;
    }

    /*
    public Level getWorld(String dimId) {
        return (Level)DimensionManager.getWorld(dimId);
    }
     */

    public Level getPlayerWorld() {
        return null;
    }


    public void preInit() {}


    public void messagePlayer(Player player, String message, Object... args) {
        if (player instanceof ServerPlayer) {
            TranslatableComponent textComponentTranslation;

            if (args.length > 0) {
                textComponentTranslation = new TranslatableComponent(message, (Object[])getMessageComponents(args));
            } else {
                textComponentTranslation = new TranslatableComponent(message, new Object[0]);
            }

            ((ServerPlayer)player).sendMessage(textComponentTranslation, player.getUUID());
        }
    }
    /*
    public boolean launchGui(Player player, IHasGui inventory) {
        if (!Util.isFakePlayer(player, true)) {
            ServerPlayer playerMp = (ServerPlayer)player;

            playerMp.getNextWindowId();
            playerMp.closeContainer();

            int windowId = playerMp.currentWindowId;

            ((NetworkManager)IC2.network.get(true)).initiateGuiDisplay(playerMp, inventory, windowId);

            player.openContainer = inventory.getGuiContainer(player);
            player.openContainer.windowId = windowId;
            player.openContainer.addListener((IContainerListener)playerMp);

            return true;
        }

        return false;
    }

    public boolean launchSubGui(Player player, IHasGui inventory, int ID) {
        if (!Util.isFakePlayer(player, true)) {
            EntityPlayerMP playerMp = (EntityPlayerMP)player;

            playerMp.getNextWindowId();
            playerMp.closeContainer();

            int windowId = playerMp.currentWindowId;

            ((NetworkManager)IC2.network.get(true)).initiateGuiDisplay(playerMp, inventory, windowId, Integer.valueOf(ID));

            player.openContainer = inventory.getGuiContainer(player);
            player.openContainer.windowId = windowId;
            player.openContainer.addListener((IContainerListener)playerMp);

            return true;
        }

        return false;
    }

    public boolean launchGuiClient(Player player, IHasGui inventory, boolean isAdmin) {
        return false;
    }
     */

    public void profilerStartSection(String section) {}

    public void profilerEndSection() {}


    public void profilerEndStartSection(String section) {}


    public File getMinecraftDir() {
        return new File(".");
    }


    public void playSoundSp(String sound, float f, float g) {}

    /*
    public void resetPlayerInAirTime(Player player) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }

        ObfuscationReflectionHelper.setPrivateValue(NetHandlerPlayServer.class, ((EntityPlayerMP)player).connection,

                Integer.valueOf(0), new String[] { "field_147365_f", "floatingTickCount" });
    }
     */


    public int getBlockTexture(Block block, BlockGetter world, int x, int y, int z, int side) {
        return 0;
    }

    public void removePotion(LivingEntity entity, Potion potion) {
        for (MobEffectInstance effect: potion.getEffects()) {
            entity.removeEffect(effect.getEffect());
        }
    }

    public void onPostInit() {}


    protected BaseComponent[] getMessageComponents(Object... args) {
        BaseComponent[] encodedArgs = new BaseComponent[args.length];

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String && ((String)args[i]).startsWith("ic2.")) {
                encodedArgs[i] = new TranslatableComponent((String)args[i], new Object[0]);
            } else {
                encodedArgs[i] = new TextComponent(args[i].toString());
            }
        }

        return encodedArgs;
    }

    public void requestTick(boolean simulating, Runnable runnable) {
        if (!simulating) throw new IllegalStateException();

        // FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(runnable);
    }

    public int getColorMultiplier(BlockBehaviour.BlockStateBase state, BlockGetter world, BlockPos pos, int tint) {
        throw new UnsupportedOperationException("client only");
    }
}

