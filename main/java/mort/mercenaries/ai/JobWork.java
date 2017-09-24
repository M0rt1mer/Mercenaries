/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.ai;

import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.api.MercenaryProfession;
import mort.mercenaries.api.ProfessionManager;
import net.minecraft.util.math.BlockPos;

/**
 *
 * @author Martin
 */
public class JobWork extends MercenaryJob{

    public BlockPos lastAction;
    public int timeOnAction;
    
    public JobWork(MercenaryOrder order) {
	super(order);
    }

    @Override
    public void engage() {
	order.ai.merc.getNavigator().clearPathEntity();
	lastAction = null;
	timeOnAction = 0;
    }

    @Override
    public float getPriority() {
	if( order.ai.merc.prof != null && order.ai.merc.prof != ProfessionManager.instance.noProf &&
		((OrderWork)order).time == ((OrderWork)order).getTime() ){
	    return 5;
	}
	return 0;
    }

    @Override
    public void update() {
	EntityMercenaryOld merc = order.ai.merc;
	OrderWork ord = (OrderWork)order;
	if( getSqRangeFromWorkPos() > 100 ){
	    if( order.ai.merc.getNavigator().noPath() ){
		merc.getNavigator().tryMoveToXYZ( ord.x, ord.y, ord.z, 0.3f );
	    }
	}
	
	MercenaryProfession prof = order.ai.merc.prof;
	if( prof!=null ){
	    if( prof.hasToMove( this ) ){
		prof.doMove( this );
	    }
	    else{
		prof.performAction( this );
	    }
	}
	
    }
    
    public double getSqRangeFromWorkPos(){
	EntityMercenaryOld merc = order.ai.merc;
	OrderWork ord = (OrderWork)order;
	return Math.pow(merc.posX-ord.x,2) + Math.pow(merc.posY-ord.y,2) + Math.pow(merc.posZ-ord.z,2);
    }
}
