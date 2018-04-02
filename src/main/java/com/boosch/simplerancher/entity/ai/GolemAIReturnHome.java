package com.boosch.simplerancher.entity.ai;

import com.boosch.simplerancher.entity.EntitySimpleRancherGolem;

import net.minecraft.entity.ai.EntityAIMoveToBlock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GolemAIReturnHome extends EntityAIMoveToBlock{



    /** Golem that is walking */
    private final EntitySimpleRancherGolem golem;

    public GolemAIReturnHome(EntitySimpleRancherGolem golem, double speedIn)
    {
        super(golem, speedIn, 16);
        this.golem = golem;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        else
        {
            if( new BlockPos(this.golem) == this.golem.getHomePosition()){ return false; }

            if(this.golem.getHomePosition()!=null) {
                this.runDelay = 2;//this.golem.getRNG().nextInt(10);
                this.destinationBlock = this.golem.getHomePosition();
                return true;
            }
            else{ return false; }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() { return super.shouldContinueExecuting(); }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() { super.updateTask(); }


    public boolean shouldMoveTo(World worldIn, BlockPos pos){ return (this.golem.getEntityWorld() == worldIn && this.golem.getHomePosition()==pos); }
}
