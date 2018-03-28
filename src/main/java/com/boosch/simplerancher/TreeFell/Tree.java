package com.boosch.simplerancher.TreeFell;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class Tree {

    private Set<BlockPos> treeLogs;
    private Set<BlockPos> treeLeaves;

    public BlockPos getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(BlockPos currentPos) {
        this.currentPos = currentPos;
    }

    private BlockPos currentPos;

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
