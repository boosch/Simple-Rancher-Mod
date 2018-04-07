package com.boosch.simplerancher.entity.golem.ai;

import com.boosch.simplerancher.entity.golem.EntitySimpleRancherGolem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GolemAIHarvest extends EntityAIMoveToBlock {


    /** Villager that is harvesting */
    private final EntitySimpleRancherGolem harvestingGolem;
    private boolean hasFarmItem;
    private boolean hasNetherFarmItem;
    private boolean wantsToReapStuff;
    /** 0 => harvest, 1 => replant, -1 => none */
    private int currentTask;

    public GolemAIHarvest(EntitySimpleRancherGolem harvestingGolemIn, double speedIn)
    {
        super(harvestingGolemIn, speedIn, 16);
        this.harvestingGolem = harvestingGolemIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.runDelay <= 0)
        {
            /**
             * we want our golems to harvest no matter what - if we want to respect griefing, we enable this
             */
            /*if (!this.harvestingGolem.world.getGameRules().getBoolean("mobGriefing"))
            {
                return false;
            }*/

            this.currentTask = -1;
            this.hasFarmItem = this.harvestingGolem.isFarmItemInInventory();
            this.hasNetherFarmItem = this.harvestingGolem.isNetherFarmItemInInventory();
            this.wantsToReapStuff = true;//this.harvestingGolem.wantsMoreFood();
        }

        if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        else
        {
            this.runDelay = 10;//00 + this.harvestingGolem.getRNG().nextInt(200);
            return searchForDestination();
        }

        //return super.shouldExecute();
    }


    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * net.minecraft.entity.ai.EntityAIMoveToBlock#shouldMoveTo(World, BlockPos) EntityAIMoveToBlock#shouldMoveTo(World,
     * BlockPos)}) can be found.
     */
    private boolean searchForDestination()
    {
        int i = 16; //super.searchLength
        int j = 1;
        BlockPos blockpos = new BlockPos(this.harvestingGolem);

        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k)
        {
            for (int l = 0; l < i; ++l)
            {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                    {
                        BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                        if (this.harvestingGolem.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(this.harvestingGolem.world, blockpos1))
                        {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }



    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.currentTask >= 0 && super.shouldContinueExecuting();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.harvestingGolem.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.harvestingGolem.getVerticalFaceSpeed());

        if (this.getIsAboveDestination())
        {
            World world = this.harvestingGolem.world;
            BlockPos blockpos = this.destinationBlock.up(); //the crop
            BlockPos soilPos = this.destinationBlock; //the soil of the destination
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            /**
             * 0 = harvesting, 1 = planting
             */
            if (this.currentTask == 0 )
            {
                /**
                 * First, genercal crop handling
                 */
                if((block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate))) {

                    if(block == Blocks.WHEAT || block == Blocks.BEETROOTS ){
                        world.destroyBlock(blockpos, true);
                    }
                    else{ //we have to assume that whatever this is, it doesn't have seeds
                        processSeedlessCropDrops(world, blockpos, iblockstate);
                        world.destroyBlock(blockpos, false);
                    }
                }
                /**
                 * We cant just break the netherwart - like potatoes and carrots we have to break it, collect the drops, assign some to the golem, and world-drop the rest
                 * This is because it does not have a seed item.
                 */
                if((block instanceof BlockNetherWart && ((BlockNetherWart)block).getMetaFromState(iblockstate)>=3)){

                    processSeedlessCropDrops(world, blockpos, iblockstate);
                    world.destroyBlock(blockpos, false);
                }
                if(block instanceof BlockReed){
                    world.destroyBlock(blockpos.up(), true);
                }
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR)
            {
                InventoryBasic inventorybasic = this.harvestingGolem.getGolemInventory();

                for (int i = 0; i < inventorybasic.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = inventorybasic.getStackInSlot(i);

                    //enable infini-farmer
                   if(!itemstack.isEmpty() && itemstack.getCount()>10){
                       itemstack.setCount(10);
                   }

                    boolean plantedCrop = false;

                    //basic crops
                    if(world.getBlockState(soilPos).getBlock() instanceof BlockFarmland) {
                        if (!itemstack.isEmpty()) {
                            if (itemstack.getItem() == Items.WHEAT_SEEDS) {
                                world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 3);
                                plantedCrop = true;
                            } else if (itemstack.getItem() == Items.POTATO) {
                                world.setBlockState(blockpos, Blocks.POTATOES.getDefaultState(), 3);
                                plantedCrop = true;
                            } else if (itemstack.getItem() == Items.CARROT) {
                                world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
                                plantedCrop = true;
                            } else if (itemstack.getItem() == Items.BEETROOT_SEEDS) {
                                world.setBlockState(blockpos, Blocks.BEETROOTS.getDefaultState(), 3);
                                plantedCrop = true;
                            }
                        }
                    }

                    //netherwart
                    if(world.getBlockState(soilPos).getBlock() instanceof BlockSoulSand){
                        if (!itemstack.isEmpty()) {
                            if (itemstack.getItem() == Items.NETHER_WART) {
                                world.setBlockState(blockpos, Blocks.NETHER_WART.getDefaultState(), 3);
                                plantedCrop = true;
                            }
                        }
                    }

                    //sugarcane - unnecessary - simply don't break the base-block of sugarcane.

                    if (plantedCrop)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                        }

                        break;
                    }
                }
            }


            this.currentTask = -1;
            this.runDelay = 1;
        }
    }

    protected void processSeedlessCropDrops(World world, BlockPos cropPos, IBlockState cropBS){
        NonNullList<ItemStack> drops = NonNullList.create();

        cropBS.getBlock().getDrops(drops, world, cropPos, cropBS, 0);

        if(!drops.isEmpty()) {
            //DEBUG System.out.println("This netherblock should drop ["+drops.size()+"]");
            for (ItemStack is : drops) {
                if (true){//is.getItem() == Items.NETHER_WART) {
                    harvestingGolem.getGolemInventory().addItem(is);
                    drops.remove(is);
                    break;
                }
                //DEBUG System.out.println("\t -"+is.getDisplayName()+" ("+is.getCount()+")");
            }
            if (!harvestingGolem.world.isRemote) {
                for (ItemStack is : drops) {
                    harvestingGolem.dropItem(is.getItem(), is.getCount());
                }
            }
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (block == Blocks.FARMLAND)
        {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate) && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0))
            {
                this.currentTask = 0;
                return true;
            }

            if (iblockstate.getMaterial() == Material.AIR && this.hasFarmItem && (this.currentTask == 1 || this.currentTask < 0))
            {
                this.currentTask = 1;
                return true;
            }
        }

        if(block == Blocks.SOUL_SAND){

            pos=pos.up();
            IBlockState ibs = worldIn.getBlockState(pos);
            block = ibs.getBlock();

            if(block instanceof BlockNetherWart && ((BlockNetherWart)block).getMetaFromState(ibs)>=3 && this.wantsToReapStuff &&(this.currentTask==0 || this.currentTask<0)){

                this.currentTask=0;
                return true;
            }

            if (ibs.getMaterial() == Material.AIR && this.hasNetherFarmItem && (this.currentTask == 1 || this.currentTask < 0))
            {
                this.currentTask = 1;
                return true;
            }
        }

        Block plant = worldIn.getBlockState(pos.up()).getBlock();
        if(plant instanceof BlockReed){
            Block plant2ndLevel = worldIn.getBlockState(pos.up().up()).getBlock();
            if(plant2ndLevel instanceof BlockReed){
                this.currentTask=0;
                return true;
            }
        }



        return false;
    }
}
