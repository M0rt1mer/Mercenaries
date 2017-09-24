package mort.mercenaries.ai.combat;

import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.ai.JobFight;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;

import java.util.List;


public class ManeuverAttackClose extends CombatManeuver{

    public ManeuverAttackClose(JobFight job) {
	super(job);
    }

    @Override
    public void doMove(List<EntityLiving> sortedEnemies) {
	EntityLiving target = sortedEnemies.get(0);
	EntityMercenaryOld merc = job.order.ai.merc;
	merc.rotationYaw = MathHelper.wrapAngleTo180_float( (float)Math.toDegrees( Math.atan2(target.posZ - merc.posZ,target.posX - merc.posX) ) - 90 );
    }

    @Override
    public float getPriority(List<EntityLiving> sortedEnemies) {
	/*if( job.order.ai.merc.attackTime <= 0 &&
		sortedEnemies.size() > 0 &&
		job.order.ai.merc.isInAttackRange( sortedEnemies.get(0) ) &&
		!job.order.ai.merc.isInAttackSpace( sortedEnemies.get(0) ) )
	    return 1.1f;
	else*/
	    return 0;
    }
    
}
