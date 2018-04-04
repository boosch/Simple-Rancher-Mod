package com.boosch.simplerancher.block.Pedestal;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.block.SimpleRancherBlockBase;
import com.boosch.simplerancher.block.SimpleRancherBlockTileEntity;
import com.boosch.simplerancher.items.FlavorText;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.swing.plaf.basic.BasicComboBoxUI;


/**
 *
 * This is a demonstration item to explore json-defined block shapes, textures
 * as well as entities which have an inventory, and use a Tile
 *
 * This will have an inventory for displaying a single item atop it
 *
 */
public class BlockPedestal extends SimpleRancherBlockTileEntity implements FlavorText{

    protected String name;
    protected String flavortext;

    public BlockPedestal(){
        super(Material.WOOD, "pedestal", "Perfect for showing off your tools.");
    }

    public String getFlavorText(){
        return flavortext;
    }

    /**
     * Should we use isNormalCube and doesSideBlockRendering instead?
     * @return
     */
    /*
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos){
        return false;
    }
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face){
        return (face == EnumFacing.UP || face == EnumFacing.DOWN);
    }*/

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public Class<TileEntityPedestal> getTileEntityClass() {
        return TileEntityPedestal.class;
    }

    @Nullable
    @Override
    public TileEntityPedestal createTileEntity(World world, IBlockState state) {
        return new TileEntityPedestal();
    }

    @Override
    /**
     * if the player is not sneaking and has no item, attempt to take the item out of the pedestal
     * if the player is not sneaking and is holding an item, attempt to insert it
     * if the player is sneaking, then tell them what's in the item
     */
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){

        if(!world.isRemote){
            TileEntityPedestal tile = (TileEntityPedestal)world.getTileEntity(pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);

            if(!player.isSneaking()){
                if(player.getHeldItem(hand) == ItemStack.EMPTY){
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                }else{
                    itemHandler.insertItem(0, player.getHeldItem(hand), false);
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }
                tile.markDirty();
            }
            else {
                ItemStack stack = itemHandler.getStackInSlot(0);
                if (!stack.isEmpty()) {
                    String localized = stack.getDisplayName();
                    player.sendMessage(new TextComponentString(stack.getCount() + "x " + localized));
                } else {
                    //note - refer to shadowfacts tutorial https://shadowfacts.net/tutorials/forge-modding-112/tile-entities-inventory/ regarding localization instead of using getDisplay Name
                    player.sendMessage(new TextComponentString("(The "+world.getTileEntity(pos).getDisplayName()+" is Empty)"));
                }
            }
        }
    return true;
    }

    /**
     * Used to ensure that the inventory of the pedestal is dropped in the event of a break.
     * @param world
     * @param pos
     * @param state
     */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityPedestal tile = (TileEntityPedestal)getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);

            //should this work?
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }


}
