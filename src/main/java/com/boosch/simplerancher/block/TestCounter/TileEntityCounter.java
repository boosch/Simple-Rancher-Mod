package com.boosch.simplerancher.block.TestCounter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCounter extends TileEntity {

    private int count;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound.setInteger("count",count);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        count = compound.getInteger("count");
        super.readFromNBT(compound);
    }

    public int getCount(){
        return count;
    }


    /**
     * MarkDirty informs the TE class that the TE has changed since last save and must be
     * re-saved
     */
    public void incrementCount(){
        count++;
        markDirty();
    }
    public void decrementCount(){
        count--;
        markDirty();
    }

    public TileEntityCounter(){
        count=0;
    }

}
