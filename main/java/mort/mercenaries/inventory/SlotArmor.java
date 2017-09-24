/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.inventory;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Martin Labut
 */
class SlotArmor extends Slot
{
    final EntityEquipmentSlot armorType;

    SlotArmor(IInventory par2IInventory, int par3, int par4, int par5, EntityEquipmentSlot armorType) {
        super(par2IInventory, par3, par4, par5);
        this.armorType = armorType;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack){
    	if (par1ItemStack == null) return false;
        return par1ItemStack.getItem().isValidArmor(par1ItemStack, armorType, null);
    }
    
}