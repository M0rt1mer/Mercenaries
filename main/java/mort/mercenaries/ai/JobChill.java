package mort.mercenaries.ai;

import java.util.List;
import mort.mercenaries.EntityMercenaryOld;

/**
 * Whenever there is nothing better to do, chill. Should mostly cover the "8 hours" between work and sleep
 * @author Martin
 */
public class JobChill extends MercenaryJob{

	public EntityMercenaryOld target;
	int timeSpent;

	public JobChill(MercenaryOrder order) {
		super(order);
	}

	@Override
	public void engage() {
		order.ai.merc.getNavigator().clearPathEntity();
		target = null;
		timeSpent = 0;
	}

	@Override
	public float getPriority() {
		OrderWork work = (OrderWork)order;
		//System.out.println( "Time: " + work.getTime() + "Shift: " + work.time + " Sleep: " + (2-((work.time)/2)*2) );
		if( work.getTime() == 1-(work.time%2) ){ // 0 -> 1, 1->0, 2 -> 1
			return 7;
		}
		else
			return 0;
	}

	@Override
	public void update() {
		timeSpent++;
		if( timeSpent > 2000 ){
			target = null;
			timeSpent = 101; //search immidiately
		}
		if( target == null ){
			if( timeSpent>100 ){//timeSpent also used as search timeout
				target = lookForFun();
				if(target == null){
					goHangOutRandomly();
					return;
				}
			}
			else
				return;//timeout between searches
		}
	}

	public EntityMercenaryOld lookForFun(){
		List<EntityMercenaryOld> list = order.ai.merc.getGuild().activeMercenaries;
		EntityMercenaryOld newTarget = null;
		EntityMercenaryOld fallback = null; //if no entertainer is found, will use another chilling mercenary to chill with
		int attempts = 10;
		while( newTarget == null && attempts < 10 ){
			EntityMercenaryOld help = list.get( rnd.nextInt(list.size()) );
			if( help.ai.order.lastJob instanceof JobChill ){
				newTarget = ((JobChill)help.ai.order.lastJob).target;
				fallback = help;
			}
			else if( help.ai.order.lastJob instanceof IJobEntertainer ){
				newTarget = help;
			}
			attempts --;
		}
		if( newTarget == null )
			return fallback;
		else
			return newTarget;
	}

	public void goHangOutRandomly(){
		
		
	}
}
