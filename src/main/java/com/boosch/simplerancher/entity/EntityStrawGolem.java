package com.boosch.simplerancher.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityStrawGolem extends EntitySimpleRancherGolem {

    public EntityStrawGolem(World worldIn){
        super(worldIn);
    }
    public EntityStrawGolem(World worldIn, BlockPos home){
        super(worldIn, "straw", home);
    }


}
