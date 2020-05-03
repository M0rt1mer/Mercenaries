package mort.mercenaries.profession;

import mort.mercenaries.api.MercenaryProfession;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;


public class Farmer extends MercenaryProfession {

	public Farmer( int id) {
		super( id, "farmer", "profFarmer" );
	}

	@Override
	public boolean matchesItem(ItemStack stk) {
		return stk.getItem() instanceof HoeItem;
	}

}
