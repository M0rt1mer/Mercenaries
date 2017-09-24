package mort.mercenaries.api;

import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.Guild;
import mort.mercenaries.Mercenaries;
import mort.mercenaries.api.crime.Crime;
import mort.mercenaries.api.crime.CrimeAuthority;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.HashMap;
import java.util.List;

/**
 * A "crime" is a player action, that should be met with violent reaction from neaby guards.
 * CrimeManager handles all manages all crime-reporting professions, and handles crime reports to apropriate authorities
 * @author Martin Labuť <martin.labut@volny.cz>
 */
public class CrimeManager {

    public static CrimeManager instance = new CrimeManager();
    public static final float crimeRange = 32;
    /**
     * Selects one CrimeAuthority per guild in range of offender and notifies them of the crime
     * @param crm 
     */
    public void reportCrime( Crime crm, Guild victim ){
	//offender has no guild
	if( Mercenaries.guildManager.getGuild(crm.offender) == null )
	    return;
	List<EntityMercenaryOld> listeners = crm.offender.worldObj.getEntitiesWithinAABB( EntityMercenaryOld.class,
		new AxisAlignedBB(crm.offender.posX - crimeRange, crm.offender.posY - crimeRange, crm.offender.posZ - crimeRange, crm.offender.posX + crimeRange, crm.offender.posY + crimeRange, crm.offender.posZ + crimeRange) );
	HashMap< Guild, EntityMercenaryOld> authorities = new HashMap<Guild, EntityMercenaryOld>();
	HashMap< Guild, Float> priorities = new HashMap<Guild, Float>();
	
	for( EntityLiving ent : listeners ){
	    if( ent instanceof EntityMercenaryOld){
		EntityMercenaryOld merc = (EntityMercenaryOld)ent;
		if( merc.prof != null && merc.prof instanceof CrimeAuthority && (victim==null||merc.getGuild()==victim) ){
		    float priority = ((CrimeAuthority)merc.prof).getPriority(merc, crm);
		    Guild gld = Mercenaries.guildManager.getGuild(merc);
		    if( !authorities.containsKey( gld ) || priority > priorities.get(gld) ){
			authorities.put(gld, merc);
			priorities.put(gld, priority);
		    }
		}
	    }
	    
	}
	
	for( EntityMercenaryOld merc : authorities.values() ){
	    ((CrimeAuthority)merc.prof).handle(merc, crm);
	}
	
    }
   
    
}
