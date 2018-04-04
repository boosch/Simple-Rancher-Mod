package com.boosch.simplerancher.block;

import com.boosch.simplerancher.items.FlavorText;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 *
 * @param <TE> This is the generic Tile Entity type for the class. With this, we can reduce the number of casts necessary to work with TileEntities in the mod
 */
public abstract class SimpleRancherBlockTileEntity<TE extends TileEntity> extends SimpleRancherBlockBase implements FlavorText{

    public SimpleRancherBlockTileEntity(Material material, String name){
        super(material, name);

    }

    public SimpleRancherBlockTileEntity(Material material, String name, String flavortext){
        super(material, name, flavortext);

    }

    public abstract Class<TE> getTileEntityClass();

    public TE getTileEntity(IBlockAccess world, BlockPos pos){
        return (TE)world.getTileEntity(pos);
    }

    @Override
    /**
     * Any member of this class has a Tile Entity.
     */
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}
