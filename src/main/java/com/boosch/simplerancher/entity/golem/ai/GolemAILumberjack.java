package com.boosch.simplerancher.entity.golem.ai;

import com.boosch.simplerancher.SimpleRancher;
import com.boosch.simplerancher.TreeFell.util.handlers.TreeHandler;
import com.boosch.simplerancher.entity.golem.EntityHarvestGolem;
import com.boosch.simplerancher.entity.golem.EntitySimpleRancherGolem;
import com.boosch.simplerancher.network.PacketUpdateSimpleRancherGolem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.block.Block.getBlockFromName;

public class GolemAILumberjack extends EntityAIMoveToBlock {


    /** Villager that is harvesting */
    private final EntitySimpleRancherGolem harvestingGolem;

    /** 0 => harvest, 1 => replant, -1 => none */
    private int currentTask;
    private boolean isChopping;


    /**
     * utilized for tree identification
     */
    public static Map<UUID, Boolean> tf_GolemPrintNames = new HashMap<>();
    protected static Map<UUID, GolemAILumberjack.GolemInteractionSession> tf_GolemData = new HashMap<>();
    protected TreeHandler treeHandler;

    /**
     * inner class for keeping track of which golems have tapped which trees
     */
    class GolemInteractionSession{
        public BlockPos pos;
        public float logCount;

        /**
         *
         * @param pos - the position of the block of the interaction
         * @param logCount - the number of logs to be broken by this interaction
         */
        public GolemInteractionSession(BlockPos pos, float logCount){
            this.pos = pos;
            this.logCount=logCount;
        }
    }

    public GolemAILumberjack(EntitySimpleRancherGolem harvestingGolemIn, double speedIn)
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
            this.isChopping = false;
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


    public boolean getIsAdjascentToDestination(){

        //next, check if we're ordinally adjascent to our block
        int gx = this.harvestingGolem.getPosition().getX();
        int gy = this.harvestingGolem.getPosition().getY();
        int gz = this.harvestingGolem.getPosition().getZ();

        System.out.println("evaluating destination [x"+destinationBlock.getX()+",z"+destinationBlock.getZ()+",y"+destinationBlock.getY()+"] against position [x"+gx+",z"+gz+",y"+gy+"]");

        if(this.harvestingGolem.getDistanceSq(destinationBlock) <1){
            System.out.println("|_shortcircuit true");
            return true;
        }

        /*
        if(destinationBlock.getDistance(gx, gy, gz)<=1 && gy == destinationBlock.getY()){
            System.out.println("|_shortcircuit true");
            return true;
        }*/

        if(gy != destinationBlock.getY()) return false; // we're not on the same level as the destination

        if(gz == destinationBlock.getZ() && // if we're within 1 block on the X axis...
                (gx == destinationBlock.getX()||
                gx == destinationBlock.getX()-1 ||
                gx == destinationBlock.getX()+1)){
            System.out.println("|_adjascent on Z true");
            return true;
        }
        if(gx == destinationBlock.getX()&& //if we're within 1 block on the z axis
                (gz == destinationBlock.getZ()||
                        gz == destinationBlock.getZ()-1 ||
                        gz == destinationBlock.getZ()+1)){
            System.out.println("|_adjascent on X true");
            return true;
        }

        System.out.println("evaluating destination [x"+destinationBlock.getX()+",z"+destinationBlock.getZ()+",y"+destinationBlock.getY()+"] against position [x"+gx+",z"+gz+",y"+gy+"] - ended in default");
        return false;
    }
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.harvestingGolem.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.harvestingGolem.getVerticalFaceSpeed());

        /**
         * Need to adjust this so that we're adjascent to the destination, not above it
         */
        if (this.getIsAboveDestination())//this.getIsAdjascentToDestination())//this.getIsAboveDestination())
        {
            World world = this.harvestingGolem.world;
            BlockPos blockpos = this.destinationBlock.up(); //the base-log
            BlockPos soilPos = this.destinationBlock; //the soil of the destination
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            /**
             * 0 = harvesting, 1 = planting
             */
            if (this.currentTask == 0 )
            {

                /**
                 * For my tree, play the swing animation, kill the block, sleep, repeat
                 */
                if( tf_GolemData.containsKey(this.harvestingGolem.getPersistentID())) {
                    BlockPos pos = tf_GolemData.get(this.harvestingGolem.getPersistentID()).pos;

                    if (pos.equals(blockpos)) {

                        treeHandler.DestroyTree(this.harvestingGolem.world, this.harvestingGolem);
                        SimpleRancher.network.sendToAllAround(new PacketUpdateSimpleRancherGolem(this.harvestingGolem),  new NetworkRegistry.TargetPoint(world.provider.getDimension(), this.harvestingGolem.getPosition().getX(), this.harvestingGolem.getPosition().getY(), this.harvestingGolem.getPosition().getZ(), 64));

                    }
                }
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR) // checking to see fi we killed the tree...
            {
                /**
                 * This code should only be used if we want to naturally re-plant the tree.
                 * If we're going to cheat, then we don't need this code and should replace it with simpler code.
                 */

                world.setBlockState(blockpos, Blocks.SAPLING.getDefaultState(), 3);
                boolean plantedCrop = true;

                if (plantedCrop)
                {
                    SimpleRancher.network.sendToAllAround(new PacketUpdateSimpleRancherGolem(this.harvestingGolem),  new NetworkRegistry.TargetPoint(world.provider.getDimension(), this.harvestingGolem.getPosition().getX(), this.harvestingGolem.getPosition().getY(), this.harvestingGolem.getPosition().getZ(), 64));
                }
                /*
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
                        //this.harvestingGolem.playAttackAnimation();

                        SimpleRancher.network.sendToAllAround(new PacketUpdateSimpleRancherGolem(this.harvestingGolem),  new NetworkRegistry.TargetPoint(world.provider.getDimension(), this.harvestingGolem.getPosition().getX(), this.harvestingGolem.getPosition().getY(), this.harvestingGolem.getPosition().getZ(), 64));

                    }
                }*/
            }
            //play the swing arms animation to indicate planting...
            //SimpleRancher.network.sendToAllAround(new PacketUpdateSimpleRancherGolem(this.harvestingGolem),  new NetworkRegistry.TargetPoint(world.provider.getDimension(), this.harvestingGolem.getPosition().getX(), this.harvestingGolem.getPosition().getY(), this.harvestingGolem.getPosition().getZ(), 64));
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
     * Returns if a block is made of wood or not.
     * @param world
     * @param blockPos
     * @return
     */
    protected boolean isWoodenBlock(World world, BlockPos blockPos){

        //add any other woodlogblocks to this array to let the quartz-edged-axe chop the whole tree
        Block[] woodblocks = {
                getBlockFromName("log"),
                getBlockFromName("log2")
        };

        Block ub = world.getBlockState(blockPos).getBlock();
        for(Block b : woodblocks){
            if( b == ub )
                return true;
        }

        return false;
    }

    /**
     * Return true to set given position as destination
     * We want to find a block that is __next to__ a tree block
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        pos = pos.up();
        IBlockState iblockstate = worldIn.getBlockState(pos);
        block = iblockstate.getBlock();

        if (isWoodenBlock(worldIn, pos) && (this.currentTask == 0 || this.currentTask < 0))
        {
            this.currentTask = 0;

            /**
             * Build the tree here
             */

            System.out.println("beginning processing of tree at [x"+pos.getX()+" z"+pos.getZ()+" y"+pos.getY()+"]");
            processGolemTreeInteraction(worldIn, pos);
            System.out.println("finished processing of tree at [x"+pos.getX()+" z"+pos.getZ()+" y"+pos.getY()+"]");

            return true;
        }


        if (iblockstate.getMaterial() == Material.AIR && (this.currentTask == 1 || this.currentTask < 0))
        {
            this.currentTask = 1;
            return true;
        }

        return false;
    }

    protected void processGolemTreeInteraction(World world, BlockPos logPos){

        int logCount;
        UUID pid = this.harvestingGolem.getPersistentID();

        /**
         * Check if this interaction already exists by vetting the PlayerInteractionSession against this current interaction
         */
        if(tf_GolemData.containsKey(pid) && //pid has an interaction
                tf_GolemData.get(pid).pos.equals(logPos) ){//&& //the interaction pid has is at this log
            //tf_GolemData.get(pid).axeDurability == axeCurrentDurability){ //the interaction pid has is still valid (axe durability hasn't changed)
            return; // we already have an interaction AND it's this interaction!
        }

        //this is a new interaction!
        treeHandler = new TreeHandler();
        logCount = treeHandler.AnalyzeTree(world, logPos, this.harvestingGolem);

        /**
         * The player has successfully started a new interaction - add it to the hashSet
         */
        if(logCount>1){
            tf_GolemData.put(this.harvestingGolem.getPersistentID(), new GolemAILumberjack.GolemInteractionSession(logPos, logCount));
        }else{ //there's only one log, so there's no need to keep the Interaction session alive for this player
            tf_GolemData.remove(this.harvestingGolem.getPersistentID());
        }


    }

}




