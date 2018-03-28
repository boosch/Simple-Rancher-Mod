package com.boosch.simplerancher.TreeFell.util.handlers;

import com.boosch.simplerancher.TreeFell.Tree;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class TreeHandler {


    private static Map<UUID, Tree> tf_Trees = new HashMap<>();
    private Tree tree;

    /**
     * A quick iterator to navigate a set of things and provide me the 'last' one
     * this will be used to iterate the tree and kill the 'furthest' block ?
     * @param elements
     * @param <T>
     * @return lastElement of the set
     */
    private static <T> T getLastElement(final Iterable<T> elements){

        final Iterator<T> itr = elements.iterator();

        T lastElement = itr.next();

        while(itr.hasNext()){
            lastElement = itr.next();
        }

        return lastElement;
    }

    /**
     * Explores the shape of the tree.
     * @param world where the tree lives
     * @param pos the block that we're starting at
     * @param p the player who is performing the action
     * @return the full count of logs that make up this tree
     */
    public int AnalyzeTree(World world, BlockPos pos, EntityPlayer p){

        Queue<BlockPos> queuedBlocks = new LinkedList<>();
        Set<BlockPos> tmpBlocks = new HashSet<>();
        Set<BlockPos> checkedBlocks = new HashSet<>();

        BlockPos currentPos = pos;
        Block logType = world.getBlockState(pos).getBlock();

        tree = new Tree();
        tree.AddLog(pos);
        queuedBlocks.add(pos);

        /**
         * Need to get a collection of what logs make up this tree.
         * While we still haven't visited each block of this tree, keep adding log blocks to checkedBlocks
         */
        while(!queuedBlocks.isEmpty()){
            currentPos = queuedBlocks.remove();
            checkedBlocks.add(currentPos);

            /**
             * navigate the whole tree of blocks of this type, creating a unique set of blocks in checkedBlocks
             */
            tmpBlocks.addAll(GetNeighbors(logType, currentPos, world, checkedBlocks));
            queuedBlocks.addAll(tmpBlocks);
            checkedBlocks.addAll(tmpBlocks);
            tmpBlocks.clear();
        }


        Set<BlockPos> tmpLeaves = new HashSet<>();
        tmpLeaves.addAll(tree.getTreeLeaves());

        /**
         * Need to get a collection of what *leaves* make up this tree.
         * These leaves will get added to the blocks, in case we want to handle leaves differently from logs
         * While we still haven't visited each block of this tree, keep adding log blocks to checkedBlocks
         */
        for(BlockPos bp : tmpLeaves){
            checkedBlocks.add(bp);
            GetNeighbors(null, bp, world, checkedBlocks);
        }

        tree.setCurrentPos(currentPos);
        tf_Trees.put(p.getPersistentID(), tree);

        return tree.getTreeLogs().size();

    }

    /**
     * Check every neighbor NESW NE, NW, SE, SW of this block for blocks like it
     * Do this by starting at Y such that Y is the world.y of the currentPosition
     * Check for all blocks on level y-1, y and y+1
     *
     * @param logBlock the type of Block to match against
     * @param currentPos the position at the center of my cube of neighbors
     * @param world the world in which to check the position
     * @param checkedBlocks the queue of blocks to NOT return - these have already been processed
     * @return a queue of blocks like this one that are adjascent to it
     */
    private Queue<BlockPos> GetNeighbors(Block logBlock, BlockPos currentPos, World world, Set<BlockPos> checkedBlocks){

        Queue<BlockPos> queuedBlocks = new LinkedList<>();
        BlockPos pos;

        for(int modY = -1; modY <=1; modY++){
            for( int modX=-1; modX <=1; modX++){
                for(int modZ=-1; modZ<=1; modZ++){

                    pos = new BlockPos(currentPos.getX()+modX, currentPos.getY()+modY, currentPos.getZ()+modZ);
                    if( CheckBlock(world, pos, checkedBlocks, logBlock)) {
                        queuedBlocks.add(pos);
                    }
                }
            }
        }


        return queuedBlocks;
    }


    private boolean CheckBlock(World world, BlockPos p, Set<BlockPos> checkedBlocks, Block logBlock){

        if(checkedBlocks.contains(p)) {return false;} // this has already been checked ... somehow

        if(world.getBlockState(p).getBlock() != logBlock){
            //TODO this is where we could handle leaves!
            return false;
        }

        tree.AddLog(p);
        return true;
    }

    public void DestroyTree(World world, EntityPlayer p){

        int soundReduced=0;

        if(tf_Trees.containsKey(p.getPersistentID())){
            Tree tmpTree = tf_Trees.get(p.getPersistentID());

            /**
             * break each block programatically, be sure to play only a subset of the blocksounds because it's too noisy otherwise
             * soundReduced's check will determine how many block sounds we show (currently 1/2)
             */
            for(BlockPos pos : tmpTree.getTreeLogs()){

                if(soundReduced<=1){
                    world.destroyBlock(pos, true);
                }
                else{
                    world.getBlockState(pos).getBlock().dropBlockAsItem(world, pos, world.getBlockState(pos), 1);
                }

                world.setBlockToAir(pos);
                soundReduced++;
            }

            //TODO if we're going to do leaves, then we'd process the leaves here...

        }
    }
}
