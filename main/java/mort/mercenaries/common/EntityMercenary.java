package mort.mercenaries.common;

import mort.mercenaries.Content;
import mort.mercenaries.inventory.ContainerMercenary;
import mort.mercenaries.inventory.InventoryMercenary;
import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import javax.annotation.Nullable;
import java.util.Collections;


public class EntityMercenary extends LivingEntity implements INamedContainerProvider {

    private static final DataParameter<String> MERC_NAME = EntityDataManager.<String>createKey(EntityMercenary.class, DataSerializers.STRING);
    private static final DataParameter<Integer> MERC_PROFESSION = EntityDataManager.<Integer>createKey(EntityMercenary.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> MERC_FLAGS = EntityDataManager.<Integer>createKey(EntityMercenary.class, DataSerializers.VARINT);

    /// synchronized data
    public InventoryMercenary inventory;
    //public MercenaryProfession prof;
    public String mercname;
    public int flags;

    public ThreePartAI ai;

    public EntityMercenary(EntityType<? extends LivingEntity> type, World worldIn) {
        super(type, worldIn);
        inventory = new InventoryMercenary( this );
        this.moveForward = 0.25f;
        if( !worldIn.isRemote )
            this.ai = new ThreePartAI(this);
        this.mercname = Content.nameRegistry.randomName();

        System.out.println(this.mercname + getDataManager().get(MERC_NAME));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register( MERC_NAME, mercname );
        this.getDataManager().register( MERC_PROFESSION, 0 );
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Collections.emptyList();
    }

    public void professionUpdate(){
        /*MercenaryProfession prof = ProfessionManager.instance.findProfession( this.inventory.mainInventory[0] );
        if( prof != null && prof.isAvaible(this) ){
            this.prof = prof;
            this.dataWatcher.set(MERC_PROFESSION, prof.id);
        }*/
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if(getEntityWorld().isRemote)
            return false;
        if(player instanceof ServerPlayerEntity)
            NetworkHooks.openGui((ServerPlayerEntity)player, this, (buffer) -> buffer.writeInt(this.getEntityId()) );
        return true;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
        return new ContainerMercenary( id, playerInventory, this );
    }


    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(mercname);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        inventory.readFromNBT( compound.getList("Inventory", 10) );
        this.mercname = compound.getString("Name");
        this.getDataManager().set(MERC_NAME, this.mercname);
        //this.ai = new MercenaryAI(this, par1NBTTagCompound.getCompoundTag("AI") );
        if(compound.hasUniqueId("bedX")){
            int bedX = compound.getInt("bedX");
            int bedY = compound.getInt("bedY");
            int bedZ = compound.getInt("bedZ");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Inventory", inventory.writeToNBT());
        compound.putString("Name", mercname);
    }

    @Override
    public void tick() {
        if( !getEntityWorld().isRemote )
            ai.update();
    }

    //used in rendering
    @OnlyIn(Dist.CLIENT)
    public String getActiveOrderName(){
        return I18n.format("order." + ( ( getDataManager().get( MERC_FLAGS ) >> 1) & 3 ) +".name" );
    }


}
