/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;

/**
 *
 * @author Martin
 */
public class InventoryMercenary implements IInventory{

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.mercenary.isDead ? false : player.getDistanceSqToEntity(this.mercenary) <= 64.0D;
    }

    /**
     * 4 tools (1-4) and 12 storage slots
     */
    public ItemStack[] mainInventory = new ItemStack[4 * 4];
    public static final int invSize = 16;
    /** An array of 4 item stacks containing the currently worn armor pieces. */
    public ItemStack[] armorInventory = new ItemStack[4];
    
    
    public int activeTool = 0;
    public Entity mercenary;

    public InventoryMercenary(Entity mercenary) {
	this.mercenary = mercenary;
    }
    
    @Override
    public void markDirty() {}


    @Override
    public ItemStack decrStackSize(int slot, int count) {
	ItemStack[] inv = this.mainInventory;

        if (slot >= this.mainInventory.length)
        {
            inv = this.armorInventory;
            slot -= this.mainInventory.length;
        }

        if (inv[slot] != null)
        {
            ItemStack var4;

            if (inv[slot].getCount() <= count)
            {
                var4 = inv[slot];
                inv[slot] = null;
                return var4;
            }
            else
            {
                var4 = inv[slot].splitStack(slot);

                if (inv[slot].getCount() == 0)
                {
                    inv[slot] = null;
                }

                return var4;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getInventoryStackLimit() {
	return 64;
    }

    @Override
    public int getSizeInventory() {
	return 20;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
	if( slot>=mainInventory.length )
	    return armorInventory[slot-mainInventory.length];
	else
	    return mainInventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack var2) {
        if (slot >= mainInventory.length)
            armorInventory[slot - mainInventory.length] = var2;
        else
            mainInventory[slot] = var2;
    }

    public ItemStack getActiveItem(){
	return mainInventory[activeTool];
    }
    
    /**
     * Tries to add itemStack to inventory, modifying it's size accordingly.
     */
    public void addItemStackToInventory(ItemStack stack)
    {
	if( stack.isItemDamaged() || !stack.isStackable() ){
	    for( int i = 0; i<invSize; i++ ){
		if( mainInventory[i] == null ){
		    mainInventory[i] = stack.splitStack(stack.getCount());
		    return;
		}
	    }
	    return;
	}
	else{ //stackable
	    int free = -1;
	    for( int i = 0; i<invSize && stack.getCount() > 0; i++ ){
		if( mainInventory[i]==null ){
		    free = i;
		}
		else if( mainInventory[i].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(mainInventory[i], stack) ){
		    int transfer = Math.min( mainInventory[i].getItem().getItemStackLimit(mainInventory[i]) - mainInventory[i].getCount(), stack.getCount() );
		    mainInventory[i].setCount( mainInventory[i].getCount() + transfer);
		    stack.setCount( stack.getCount() - transfer );
		}
	    }
	    if( stack.getCount() > 0){
		if( free != -1 ){
		    mainInventory[free] = stack.splitStack(stack.getCount());
    		}
		else
		    return;
	    }
	    return;
	}
	
    }
	
	
    
    
    public void readFromNBT(NBTTagList par1NBTTagList)
    {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];

        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemStack var5 = new ItemStack(var3);

            if (var5 != null)
            {
                if (var4 >= 0 && var4 < this.mainInventory.length)
                {
                    this.mainInventory[var4] = var5;
                }

                if (var4 >= 100 && var4 < this.armorInventory.length + 100)
                {
                    this.armorInventory[var4 - 100] = var5;
                }
            }
        }
    }
    
    public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
    {
        int var2;
        NBTTagCompound var3;

        for (var2 = 0; var2 < this.mainInventory.length; ++var2)
        {
            if (this.mainInventory[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        for (var2 = 0; var2 < this.armorInventory.length; ++var2)
        {
            if (this.armorInventory[var2] != null)
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory[var2].writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        return par1NBTTagList;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        if (slot >= this.mainInventory.length) {
            slot -= this.mainInventory.length;
            ItemStack stack = armorInventory[slot];
            armorInventory[slot] = null;
            return stack;
        }
        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}
    
}
