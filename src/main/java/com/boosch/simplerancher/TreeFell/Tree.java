package com.boosch.simplerancher.TreeFell;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class Tree {

    private Set<BlockPos> treeLogs;
    private Set<BlockPos> treeLeaves;
    private BlockPos currentPos;


    public Tree(){

        treeLogs=new HashSet<>();
        treeLeaves = new HashSet<>();
    }

    public BlockPos getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(BlockPos currentPos) {
        this.currentPos = currentPos;
    }

    public Set<BlockPos> getTreeLogs() {
        return treeLogs;
    }

    public Set<BlockPos> getTreeLeaves() {
        return treeLeaves;
    }

    public void AddLog(BlockPos pos){
        treeLogs.add(pos);
    }

    public void AddLeaves(BlockPos pos){
        treeLeaves.add(pos);
    }

}
