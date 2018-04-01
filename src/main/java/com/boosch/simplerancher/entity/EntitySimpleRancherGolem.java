package com.boosch.simplerancher.entity;

import com.boosch.simplerancher.util.Reference;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Set;

public class EntitySimpleRancherGolem extends EntityGolem {

    protected String type;
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.WHEAT, Items.STRING);
    protected BlockPos home;
    protected final float scale;
    public static final ResourceLocation LOOT = new ResourceLocation(Reference.MOD_ID, "entities/base_golem");
    protected static final DataParameter<Byte> PLAYER_CREATED = EntityDataManager.<Byte>createKey(EntityIronGolem.class, DataSerializers.BYTE);
    /**
     * deincrements, and a distance-to-home check is done at 0
     */
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;


    public EntitySimpleRancherGolem(World worldIn) {
        super(worldIn);

        this.type = "straw";

        this.setPlayerCreated(true);
        this.setSize(.36F, .75F);
        this.scale = .3F;
        this.homeCheckTimer = 30;
    }

    public EntitySimpleRancherGolem(World worldIn, String type, BlockPos home) {
        super(worldIn);

        this.type = type;
        this.home = home;

        this.setPlayerCreated(true);
        this.setSize(.36F, .75F);
        this.scale = .3F;
        this.homeCheckTimer=30;
    }

    public float getScale() {
        return scale;
    }


    protected void initEntityAI() {
        //this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        //this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));

        //this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        //this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(5, new EntityAILookAtVillager(this));

        this.tasks.addTask(1, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, false, TEMPTATION_ITEMS));//make him follow  you
        this.tasks.addTask(4, new EntityAILookIdle(this));

        //this.targetTasks.addTask(1, new EntityAIDefendVillage(this));

        //this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        /*this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>()
        {
            public boolean apply(@Nullable EntityLiving p_apply_1_)
            {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
        */

    }


    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(PLAYER_CREATED, Byte.valueOf((byte) 0));
        System.out.println("A new "+this.type+"_golem has been created!");
    }


    protected void updateAITasks() {
        if (--this.homeCheckTimer <= 0) {
            this.homeCheckTimer = 30 + this.rand.nextInt(50);

            //this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(this), 32);

            if (home == null) {
                this.detachHome();
            } else {
                //reset his home and give a radius of 5
                BlockPos blockpos = home;
                this.setHomePosAndDistance(blockpos, (int) ((float) 5));
            }
        }

        super.updateAITasks();
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!super.processInteract(player, hand)) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG) {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
        }
        return false;
    }


    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    /**
     * Decrements the entity's air supply when underwater
     */

    protected int decreaseAirSupply(int air) {
        return air;
    }


    protected void collideWithEntity(Entity entityIn) {
        /*
        if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0)
        {
            this.setAttackTarget((EntityLivingBase)entityIn);
        }
        */
        super.collideWithEntity(entityIn);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */

    public void onLivingUpdate() {
        super.onLivingUpdate();


        if (this.attackTimer > 0)
        {
            --this.attackTimer;
        }
        /*
        if (this.holdRoseTick > 0)
        {
            --this.holdRoseTick;
        }
        */
        /**
         * Unsure what this does, but think it makes the blocks crack undeneath the golem?
         */
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));

            if (iblockstate.getMaterial() != Material.AIR) {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, 4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D, ((double) this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(iblockstate));
            }
        }
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */

    public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
        if (this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls)) {
            return false;
        } else {
            return cls == EntityCreeper.class ? false : super.canAttackClass(cls);
        }
    }


    public static void registerFixesSimpeRancherGolem(DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySimpleRancherGolem.class);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
    }


    public boolean attackEntityAsMob(Entity entityIn)
    {
        this.attackTimer = 5;
        this.world.setEntityState(this, (byte)4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

        if (flag)
        {
            entityIn.motionY += 0.4000000059604645D;
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.5F);
        return flag;
    }

    /**
     * Handler for {@link World#setEntityState}
     */

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 4)
        {
            this.attackTimer = 5;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.5F);
        }
       /* else if (id == 11)
        {
            this.holdRoseTick = 400;
        }
        else if (id == 34)
        {
            this.holdRoseTick = 0;
        }*/
        else
        {
            super.handleStatusUpdate(id);
        }
    }


    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }


    public void setHoldingRose(boolean p_70851_1_)
    {
        /*
        if (p_70851_1_)
        {
            this.holdRoseTick = 400;
            this.world.setEntityState(this, (byte)11);
        }
        else
        {
            this.holdRoseTick = 0;
            this.world.setEntityState(this, (byte)34);
        }
        */
    }


    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }


    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }


    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.25F, 1.0F);
        //this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 2.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_VILLAGER;
    }


    public int getHoldRoseTick()
    {
        return this.holdRoseTick;
    }


    public boolean isPlayerCreated()
    {
        return (((Byte)this.dataManager.get(PLAYER_CREATED)).byteValue() & 1) != 0;
    }


    public void setPlayerCreated(boolean playerCreated)
    {
        byte b0 = ((Byte)this.dataManager.get(PLAYER_CREATED)).byteValue();

        if (playerCreated)
        {
            this.dataManager.set(PLAYER_CREATED, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(PLAYER_CREATED, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */

    public void onDeath(DamageSource cause)
    {
        /*
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null)
        {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }
        */
        super.onDeath(cause);
    }


}
