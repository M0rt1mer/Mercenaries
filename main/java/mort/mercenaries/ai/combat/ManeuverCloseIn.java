/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.ai.combat;

import mort.mercenaries.ai.JobFight;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;

import java.util.List;

/**
 * Maneuver - closes in on single enemy
 * @author Martin
 */
public class ManeuverCloseIn extends CombatManeuver{

    public ManeuverCloseIn(JobFight job) {
    	super(job);
    }
    
    PathEntity path = null;
    EntityLiving target = null;
    int pathfinderCooldown = 0;
    
    public static final int PATHFINDING_TIMEOUT = 20;

    @Override
    public void doMove(List<EntityLiving> sortedEnemies) {
		EntityLiving newTarget = sortedEnemies.get(0);
		//-----------
		if( newTarget != target ){
		    pathfinderCooldown = PATHFINDING_TIMEOUT;
		    //path = this.job.order.ai.merc.worldObj.getPathEntityToEntity(job.order.ai.merc, sortedEnemies.get(0), 0,false, true, true, true);
		}
		target = newTarget;
		if( path!=null ){
		    if( pathfinderCooldown == PATHFINDING_TIMEOUT ){
			//only if the path has just been found
			job.order.ai.merc.getNavigator().setPath( path, job.order.ai.merc.getMoveSpeed() );
		    }
		}
		else{
		    job.order.ai.merc.getNavigator().tryMoveToEntityLiving(target, job.order.ai.merc.getMoveSpeed() );
		}
    }

    @Override
    public float getPriority(List<EntityLiving> sortedEnemies) {
		if( sortedEnemies.isEmpty() )
		    return 0;
		else{
		    //@todo: properly choose target
		    EntityLiving target = sortedEnemies.get(0);
		    if( job.order.ai.merc.isInAttackRange( target ) )
			return 0;
		    //calculate priority
		    float support = 1; //how much enemy support the target has
		    for( EntityLiving ent : sortedEnemies ){
		    	support += Math.max( 0 , 4 - ent.getDistanceSqToEntity(target) );
		    }
		    return 1/support;
		}
    }
    

}
