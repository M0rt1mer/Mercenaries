package mort.mercenaries.common;

import mort.mercenaries.Content;
import mort.mercenaries.Mercenaries;
import mort.mercenaries.inventory.InventoryMercenary;
import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;

import java.util.Collections;

/**
 * Created by Martin on 15.06.2016.
 */
public class EntityMercenary extends EntityLivingBase {

    private static final DataParameter<String> MERC_NAME = EntityDataManager.<String>createKey(EntityMercenary.class, DataSerializers.STRING);
    private static final DataParameter<Integer> MERC_PROFESSION = EntityDataManager.<Integer>createKey(EntityMercenary.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MERC_FLAGS = EntityDataManager.<Integer>createKey(EntityMercenary.class, DataSerializers.VARINT);

    /// synchronized data
    public InventoryMercenary inventory;
    //public MercenaryProfession prof;
    public String mercname;
    public int flags;

    public ThreePartAI ai;



    public EntityMercenary(World worldIn) {
        super(worldIn);
        inventory = new InventoryMercenary( this );
        this.moveForward = 0.25f;
        this.ai = new ThreePartAI(this);
    }


    @Override
    public void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }
    
    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    public void entityInit() {
        super.entityInit();
        this.mercname = Content.nameRegistry.randomName();
        this.getDataManager().register( MERC_NAME, mercname );
        this.getDataManager().register( MERC_PROFESSION, 0 );
        System.out.println(this.mercname + getDataManager().get(MERC_NAME));
    }

    public void professionUpdate(){
        /*MercenaryProfession prof = ProfessionManager.instance.findProfession( this.inventory.mainInventory[0] );
        if( prof != null && prof.isAvaible(this) ){
            this.prof = prof;
            this.dataWatcher.set(MERC_PROFESSION, prof.id);
        }*/
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        player.openGui(Mercenaries.merc, 1, getEntityWorld(), getEntityId(), 0, 0);
        return true;
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        inventory.readFromNBT( par1NBTTagCompound.getTagList("Inventory", 10) );
        this.mercname = par1NBTTagCompound.getString("Name");
        this.getDataManager().set(MERC_NAME, this.mercname);
        //this.ai = new MercenaryAI(this, par1NBTTagCompound.getCompoundTag("AI") );
        if(par1NBTTagCompound.hasKey("bedX")){
            int bedX = par1NBTTagCompound.getInteger("bedX");
            int bedY = par1NBTTagCompound.getInteger("bedY");
            int bedZ = par1NBTTagCompound.getInteger("bedZ");
            //bedPos = new BlockPos(bedX,bedY,bedZ);
        }
        //professionUpdate();
        //reportToGuild(true);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
        //par1NBTTagCompound.setInteger("Guild", guildAllignment);
        par1NBTTagCompound.setString("Name", this.mercname);
        //par1NBTTagCompound.setTag( "AI", this.ai.saveToNBTTag() );
        /*if(bedPos != null){
            par1NBTTagCompound.setInteger("bedX", bedPos.getX());
            par1NBTTagCompound.setInteger("bedY", bedPos.getY());
            par1NBTTagCompound.setInteger("bedZ", bedPos.getZ());
        }*/
    }

}
