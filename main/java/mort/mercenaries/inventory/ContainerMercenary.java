package mort.mercenaries.inventory;

import mort.mercenaries.common.EntityMercenary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Martin
 */
public class ContainerMercenary extends Container{
    
    public EntityMercenary merc;
    public EntityPlayer plr;

    public ContainerMercenary(EntityMercenary merc, EntityPlayer plr) {
	this.merc = merc;
	this.plr = plr;
		merc.inventory.openInventory(plr);
	
	//primary item
	addSlotToContainer( new Slot( merc.inventory, 0, 26, 108  ) );
	//tools
	addSlotToContainer( new Slot( merc.inventory, 1, 62, 108  ) );
	addSlotToContainer( new Slot( merc.inventory, 2, 80, 108  ) );
	addSlotToContainer( new Slot( merc.inventory, 3, 98, 108  ) );
	
	//inventory
	for( int x = 0; x<4; x++ )
	    for( int y=0; y<3; y++)
		addSlotToContainer( new Slot( merc.inventory, 4 + x + y*4, 98 + x*18, 18 + y*18 ) );
	
	//armor
	for( int y = 0; y<4; y++ )
	    addSlotToContainer( new SlotArmor( merc.inventory, merc.inventory.getSizeInventory() - 4 + y, 8, 18 + (3-y)*18, EntityEquipmentSlot.values()[5-y]) );
	
	//player
	for( int x = 0; x<9; x++ )
	    for( int y = 0; y<3; y++)
		addSlotToContainer( new Slot( plr.inventory, x + y*9 + 9, 8 + x*18, 139 + y*18 ) );
	//player hotbar
	for( int x = 0; x<9; x++ )
		addSlotToContainer( new Slot( plr.inventory, x, 8 + x*18, 198 ) );
	
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer var1) {
		return merc.inventory.isUsableByPlayer(var1);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		merc.inventory.closeInventory(par1EntityPlayer);
    }

    @Override
    public void putStackInSlot(int par1, ItemStack par2ItemStack) {
	super.putStackInSlot(par1, par2ItemStack);
	if( par1==0 ){
	    this.merc.professionUpdate();
	}
    }
    
    //@todo: shift-click behaviour

    @Override
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
    }
    
}
