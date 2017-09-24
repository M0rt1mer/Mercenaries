package mort.mercenaries.ai;

import java.util.TreeSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;

/**
 *
 * @author Martin
 */
public class JobPickupItems extends MercenaryJob{

    TreeSet<EntityItem> items;
    
    private static final int ITEM_PICKUP_RANGE_SQUARE = 4;
    
    public JobPickupItems(MercenaryOrder order) {
	super(order);
	items = new TreeSet<EntityItem>( new EntitySorter( order.ai.merc ) );
    }

    @Override
    public void engage() {
	order.ai.merc.getNavigator().clearPathEntity();
    }

    @Override
    public float getPriority() {
	if( items.size() > 0 ){
	    if( items.first().getDistanceSqToEntity( order.ai.merc ) < ITEM_PICKUP_RANGE_SQUARE )
		return 10;
	    return 10 - (float)Math.max( items.first().getDistanceSqToEntity( order.ai.merc ) - ITEM_PICKUP_RANGE_SQUARE, 0);
	}
	else
	    return 0;
    }

    @Override
    public void update() {
	EntityItem itm = items.first();
	if( order.ai.merc.getNavigator().noPath() || itm.isDead )
	{
	    while( items.size() > 0 && itm.isDead ){
		items.pollFirst();
		itm = items.first();
	    }
	    if(itm!=null)
		order.ai.merc.getNavigator().tryMoveToXYZ( itm.posX , itm.posY, itm.posZ, 0.3f );
	}
	if( itm.getDistanceSqToEntity(order.ai.merc) < 4 ){
	    order.ai.merc.onItemPickup(itm, itm.getEntityItem().stackSize );
	    //itm.getEntityItem() = order.ai.merc.inventory.addItemStackToInventory(itm.item);
	    //@todo: fix pickup
	    order.ai.merc.inventory.addItemStackToInventory( itm.getEntityItem() );
	}
    }

    @Override
    public void updateCache() {
	items.clear();
	for( Entity ent : order.nearbyEnt ){
	    if( ent instanceof EntityItem && !ent.isDead ){
		items.add( (EntityItem)ent );
	    }
	}
    }
    
}
