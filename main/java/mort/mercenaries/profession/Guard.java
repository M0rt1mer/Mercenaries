package mort.mercenaries.profession;

import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.Mercenaries;
import mort.mercenaries.ai.JobWork;
import mort.mercenaries.api.MercenaryProfession;
import mort.mercenaries.api.crime.Crime;
import mort.mercenaries.api.crime.CrimeAuthority;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

/**
 *
 * @author Martin
 */
public class Guard extends MercenaryProfession implements CrimeAuthority{

	public Guard( int id ) {
		super(id, "guard", "profGuard");
	}

	@Override
	public void doMove(JobWork job) {
		super.doMove(job);
	}

	@Override
	public boolean hasToMove(JobWork job) {
		return super.hasToMove(job);
	}

	@Override
	public boolean matchesItem(ItemStack stk) {
		return stk.getItem() instanceof ItemSword;
	}

	@Override
	public void performAction(JobWork job) {
		super.performAction(job);
	}

	@Override
	public float getPriority(EntityMercenaryOld authority, Crime crm) {
		return 2;
	}

	@Override
	public void handle(EntityMercenaryOld authority, Crime crm) {
		authority.getGuild().tempDrop( Mercenaries.guildManager.getGuild(crm.offender), -0.5f );
	}



}
