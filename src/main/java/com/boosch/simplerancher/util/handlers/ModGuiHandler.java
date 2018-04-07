package com.boosch.simplerancher.util.handlers;

import com.boosch.simplerancher.block.Pedestal.ContainerPedestal;
import com.boosch.simplerancher.block.Pedestal.GuiPedestal;
import com.boosch.simplerancher.block.Pedestal.TileEntityPedestal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler {

    /**
     * This lists all of our gui elements, with an integer ID. These are used by forge to identify which gui to render
     * for the player at a specific moment
     */
    public static final int PEDESTAL =0;

    /**
     * This will let the server load up the appropriate gui during specific calls. We need
     * to add an entry for each of our custom GUIs
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PEDESTAL:
                return new ContainerPedestal(player.inventory, (TileEntityPedestal)world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    /**
     * Similar to the server option, this will run a switch over the gui element ID and return the right GUI for the user
     * Because this may be several different objects, we use the Object return type
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case PEDESTAL:
                return new GuiPedestal(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
