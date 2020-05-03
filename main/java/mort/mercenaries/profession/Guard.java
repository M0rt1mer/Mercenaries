package mort.mercenaries.profession;

import mort.mercenaries.api.MercenaryProfession;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class Guard extends MercenaryProfession{

	public Guard( int id ) {
		super(id, "guard", "profGuard");
	}

	@Override
	public boolean matchesItem(ItemStack stk) {
		return stk.getItem() instanceof SwordItem;
	}
}
