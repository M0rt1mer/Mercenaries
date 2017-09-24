package mort.mercenaries.ai.combat;

import java.util.List;

import mort.mercenaries.ai.JobFight;
import net.minecraft.entity.EntityLiving;

/**
 * A movement action that can be taken in combat.
 * A single instance is created in JobFight, which is then used for all AI's
 * An instance of this base class represents idle maneuver (stand ground)
 * @author Martin
 */
public class CombatManeuver {
    
    public JobFight job;
    
    public CombatManeuver( JobFight job ){
    	this.job = job;
    }
    
    public float getPriority( List<EntityLiving> sortedEnemies ){
    	return 0;
    }
    
    public void doMove( List<EntityLiving> sortedEnemies ){}
    
    
}
