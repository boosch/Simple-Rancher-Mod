package com.boosch.simplerancher.block.Pedestal;

import com.boosch.simplerancher.block.SimpleRancherBlockBase;
import com.boosch.simplerancher.items.FlavorText;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


/**
 *
 * This is a demonstration item to explore json-defined block shapes, textures
 * as well as entities which have an inventory, and use a Tile
 *
 */
public class BlockPedestal extends SimpleRancherBlockBase implements FlavorText{

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

}
