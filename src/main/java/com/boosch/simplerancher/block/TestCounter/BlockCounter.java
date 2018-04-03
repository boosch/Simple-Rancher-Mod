package com.boosch.simplerancher.block.TestCounter;

import com.boosch.simplerancher.block.SimpleRancherBlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCounter extends SimpleRancherBlockTileEntity {

    public BlockCounter() {
        super(Material.ROCK, "counter");
    }


    /**
     * Register a click on our block and count up if it's on the TOP or down if it's on the BOTTOM of the block.
     *
     * @param world
     * @param pos
     * @param state
     * @param player
     * @param hand
     * @param side
     * @param hitX
     * @param hitY
     * @param hitZ
     * @return
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {

        //Ensure we're on the server, then do server things!
        if (!world.isRemote) {
            TileEntityCounter tile = (TileEntityCounter)getTileEntity(world, pos);
            if (side == EnumFacing.DOWN) {
                tile.decrementCount();
            } else if (side == EnumFacing.UP) {
                tile.incrementCount();
            }
            player.sendMessage(new TextComponentString(TextFormatting.AQUA + "" + TextFormatting.ITALIC + "Count: " + tile.getCount()));
        }
        return true;
    }

    @Override
    public Class<TileEntityCounter> getTileEntityClass() {
        return TileEntityCounter.class;
    }

    @Nullable
    @Override
    public TileEntityCounter createTileEntity(World world, IBlockState ibs) {
        return new TileEntityCounter();
    }
}
