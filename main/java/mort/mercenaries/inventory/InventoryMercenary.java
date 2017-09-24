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
import net.minecraft.util.NonNullList;
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
    public NonNullList<ItemStack> mainInventory = NonNullList.<ItemStack>withSize(4*4, ItemStack.EMPTY);
    public static final int invSize = 16;
    /** An array of 4 item stacks containing the currently worn armor pieces. */
    public NonNullList<ItemStack> armorInventory = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
    
    
    public int activeTool = 0;
    public Entity mercenary;

    public InventoryMercenary(Entity mercenary) {
	this.mercenary = mercenary;
    }
    
    @Override
    public void markDirty() {}


    @Override
    public ItemStack decrStackSize(int slot, int count) {
	    NonNullList<ItemStack> inv = this.mainInventory;

        if (slot >= this.mainInventory.size())
        {
            inv = this.armorInventory;
            slot -= this.mainInventory.size();
        }

        if (!inv.get(slot).isEmpty()) {
            ItemStack var4;

            if (inv.get(slot).getCount() <= count)
            {
                var4 = inv.get(slot);
                inv.set(slot,ItemStack.EMPTY);
                return var4;
            }
            else
            {
                var4 = inv.get(slot).splitStack(slot);

                if (inv.get(slot).getCount() == 0)
                {
                    inv.set(slot,ItemStack.EMPTY);
                }

                return var4;
            }
        }
        else
        {
            return ItemStack.EMPTY;
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
	if( slot>=mainInventory.size() )
	    return armorInventory.get(slot-mainInventory.size());
	else
	    return mainInventory.get(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack var2) {
        if (slot >= mainInventory.size())
            armorInventory.set(slot - mainInventory.size(), var2);
        else
            mainInventory.set(slot, var2);
    }

    public ItemStack getActiveItem(){
	return mainInventory.get(activeTool);
    }


    public void readFromNBT(NBTTagList par1NBTTagList)
    {
        mainInventory.clear();
        armorInventory.clear();

        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;
            ItemStack var5 = new ItemStack(var3);

            if (var5 != null)
            {
                if (var4 >= 0 && var4 < this.mainInventory.size())
                {
                    this.mainInventory.set(var4, var5);
                }

                if (var4 >= 100 && var4 < this.armorInventory.size() + 100)
                {
                    this.armorInventory.set(var4 - 100, var5);
                }
            }
        }
    }
    
    public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
    {
        int var2;
        NBTTagCompound var3;

        for (var2 = 0; var2 < this.mainInventory.size(); ++var2)
        {
            if (this.mainInventory.get(var2).isEmpty() )
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory.get(var2).writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        for (var2 = 0; var2 < this.armorInventory.size(); ++var2)
        {
            if ( this.armorInventory.get(var2).isEmpty() )
            {
                var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory.get(var2).writeToNBT(var3);
                par1NBTTagList.appendTag(var3);
            }
        }

        return par1NBTTagList;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        if (slot >= this.mainInventory.size()) {
            slot -= this.mainInventory.size();
            ItemStack stack = armorInventory.get(slot);
            armorInventory.set(slot, null);
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
