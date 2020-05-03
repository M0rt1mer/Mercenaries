package mort.mercenaries.inventory;

import mort.mercenaries.Content;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerMercenary extends Container {
    
    public EntityMercenary merc;

    @OnlyIn(Dist.CLIENT)
    public ContainerMercenary(int id, PlayerInventory inventory, PacketBuffer extraData)
	{
		this(id, inventory, (EntityMercenary) Minecraft.getInstance().player.getEntityWorld().getEntityByID(extraData.readInt()) );
	}

	public ContainerMercenary(int id, PlayerInventory playerInventoryIn, EntityMercenary merc) {
		super(Content.mercContainerType, id);
		this.merc = merc;
		this.merc = merc;

		//primary item
		addSlot( new Slot( merc.inventory, 0, 26, 108  ) );
		//tools
		addSlot( new Slot( merc.inventory, 1, 62, 108  ) );
		addSlot( new Slot( merc.inventory, 2, 80, 108  ) );
		addSlot( new Slot( merc.inventory, 3, 98, 108  ) );

		//inventory
		for( int x = 0; x<4; x++ )
			for( int y=0; y<3; y++)
				addSlot( new Slot( merc.inventory, 4 + x + y*4, 98 + x*18, 18 + y*18 ) );

		//armor
		for( int y = 0; y<4; y++ )
			addSlot( new SlotArmor( merc.inventory, merc.inventory.getSizeInventory() - 4 + y, 8, 18 + (3-y)*18, EquipmentSlotType.values()[5-y]) );

		//player
		for( int x = 0; x<9; x++ )
			for( int y = 0; y<3; y++)
				addSlot( new Slot( playerInventoryIn, x + y*9 + 9, 8 + x*18, 139 + y*18 ) );
		//player hotbar
		for( int x = 0; x<9; x++ )
			addSlot( new Slot( playerInventoryIn, x, 8 + x*18, 198 ) );
	
    }

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return merc.inventory.isUsableByPlayer(playerIn);
	}

    @Override
    public void putStackInSlot(int par1, ItemStack par2ItemStack) {
	super.putStackInSlot(par1, par2ItemStack);
	if( par1==0 ){
	    this.merc.professionUpdate();
	}
    }
    
    //@todo: shift-click behaviour

    /*@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
	//return super.transferStackInSlot(par1EntityPlayer, par2);
	if( this.getSlot(slot).getHasStack() ){
	    ItemStack stk = this.getSlot(slot).getStack();
	    if( slot<20 ){ //mercenary's inventory
		if( !this.mergeItemStack(stk, 20, 56, true) )
		    return null;
	    }
	    if(stk.getCount()==0){
		getSlot(slot).putStack(null);
	    }
	    else
		getSlot(slot).onSlotChanged();
	    
	}
	return null;
    }*/
    
}
