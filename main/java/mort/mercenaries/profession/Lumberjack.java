/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.profession;

import mort.mercenaries.api.MercenaryProfession;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

/**
 * A profession about felling trees
 * Usage of JobWork's attributes:
 * lastAction - coordinates of Wood block currently trying to fell
 * timeOnAction - counter for felling time, or, if lastAction==null, counter between searches
 * @author Martin
 */
public class Lumberjack extends MercenaryProfession{

    public Lumberjack( int id){
	super( id, "lumberjack", "profLumber" );
    }
    
    @Override
    public boolean isAvaible(EntityMercenary merc) {
		return true;
    }

    @Override
    public boolean matchesItem(ItemStack stk) {
	return (stk.getItem() instanceof AxeItem);
    }


}
