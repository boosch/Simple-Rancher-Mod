package com.boosch.simplerancher.entity.golem;

import com.boosch.simplerancher.entity.golem.ai.GolemAIHarvest;
import com.boosch.simplerancher.entity.golem.ai.GolemAIReturnHome;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Allows a HarvestGolem to harvest any basic plant.
 * plants fall into four categories
 *  - Dangerous: [eg cactus] no AI support
 *  - Trunked: [eg sugar cane] you don't break the bottom block, you break a +1 block to harvest
 *  - Basic: [eg wheat] breaks and yields crop + seeds
 *  - SelfSeeded: [eg potato] breaks into just itself
 *
 *  Selfseeded crops are not traditionally harvested - we intercept one of the yield and just
 *  magically-plant a replacement crop
 *
 *  Basic crops are broken as blocks, with the golem harvesting the seeds, and using extra seeds to replant it
 *
 *
 */
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

    //constructor for type-switching
    public EntityHarvestGolem(EntitySimpleRancherGolem oddGolem){
        super(oddGolem.world, "straw", oddGolem.getHomePosition());
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
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        return super.processInteract(player, hand);
    }

    /**
     * Harvest golem specific helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    /**
     * Harvest golem specific  helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setCanPickUpLoot(true);
    }

    /**
     * Simply giving a stub-hook to override behavior
     */
    protected void updateAITasks() {
        super.updateAITasks();
    }

    /**
     * Simply giving a stub-hook to override behavior
     */
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        //customizations go here
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

}
