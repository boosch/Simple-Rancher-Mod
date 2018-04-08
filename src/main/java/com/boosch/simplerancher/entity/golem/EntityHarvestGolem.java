package com.boosch.simplerancher.entity.golem;

import com.boosch.simplerancher.entity.golem.ai.GolemAIHarvest;
import com.boosch.simplerancher.entity.golem.ai.GolemAIReturnHome;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityHarvestGolem extends EntitySimpleRancherGolem {

    public EntityHarvestGolem(World worldIn){
        super(worldIn,"straw");
        this.golemCanPickup = true;
        this.setCanPickUpLoot(golemCanPickup);
    }

    public EntityHarvestGolem(World worldIn, BlockPos home){
        super(worldIn, "straw", home);
        this.golemCanPickup = true;
        this.setCanPickUpLoot(golemCanPickup);
    }

    @Override
    protected void initEntityAI() {

        this.tasks.addTask(1, new GolemAIHarvest(this, 1D));
        this.tasks.addTask(2, new GolemAIReturnHome(this, 1D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntitySimpleRancherGolem.class, 4.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    /**
     * Protected helper method to read subclass entity data from NBT.
     * We use an inventory, so we're going to read it
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setCanPickUpLoot(true);
    }


    /*
    public boolean hasEnoughFoodToBreed()
    {
        return this.hasEnoughItems(1);
    }

    public boolean canAbondonItems()
    {
        return this.hasEnoughItems(2);
    }

    public boolean wantsMoreFood()
    {
        boolean flag = this.getProfession() == 0;

        if (flag)
        {
            return !this.hasEnoughItems(5);
        }
        else
        {
            return !this.hasEnoughItems(1);
        }
    }
    */
    /**
     * Returns true if villager has enough items in inventory
     */
    private boolean hasEnoughItems(int multiplier)
    {
        //boolean flag = this.getProfession() == 0;

        for (int i = 0; i < this.golemInventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.golemInventory.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                if (itemstack.getItem() == Items.POTATO && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.CARROT && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.BEETROOT && itemstack.getCount() >= 12 * multiplier)
                {
                    return true;
                }

                if (/*flag && */itemstack.getItem() == Items.WHEAT && itemstack.getCount() >= 9 * multiplier)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if golem has seeds, potatoes or carrots in inventory
     */
    public boolean isFarmItemInInventory()
    {
        for (int i = 0; i < this.golemInventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.golemInventory.getStackInSlot(i);

            if (!itemstack.isEmpty() && (itemstack.getItem() == Items.WHEAT_SEEDS || itemstack.getItem() == Items.POTATO || itemstack.getItem() == Items.CARROT || itemstack.getItem() == Items.BEETROOT_SEEDS))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if golem has netherwart in inventory
     */
    public boolean isNetherFarmItemInInventory()
    {
        for (int i = 0; i < this.golemInventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.golemInventory.getStackInSlot(i);

            if (!itemstack.isEmpty() && (itemstack.getItem() == Items.NETHER_WART))
            {
                return true;
            }
        }

        return false;
    }


    /**
     * I have no idea what this is for - EntityVillager
     * @param inventorySlot
     * @param itemStackIn
     * @return
     */
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn)
    {
        if (super.replaceItemInInventory(inventorySlot, itemStackIn))
        {
            return true;
        }
        else
        {
            int i = inventorySlot - 300;

            if (i >= 0 && i < this.golemInventory.getSizeInventory())
            {
                this.golemInventory.setInventorySlotContents(i, itemStackIn);
                return true;
            }
            else
            {
                return false;
            }
        }
    }


}
