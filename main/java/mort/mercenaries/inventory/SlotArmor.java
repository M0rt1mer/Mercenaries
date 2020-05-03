/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.inventory;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

class SlotArmor extends Slot
{
    final EquipmentSlotType armorType;

    SlotArmor(IInventory par2IInventory, int par3, int par4, int par5, EquipmentSlotType armorType) {
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
    	return par1ItemStack.getItem().canEquip( par1ItemStack, armorType, null );
    }
    
}