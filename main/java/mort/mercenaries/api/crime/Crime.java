package mort.mercenaries.api.crime;

import net.minecraft.entity.EntityLivingBase;

/**
 *
 * @author Martin Labuť <martin.labut@volny.cz>
 */
public class Crime {
    
    public CrimeType type;
    public EntityLivingBase offender;

    public Crime(CrimeType type, EntityLivingBase offender) {
	this.type = type;
	this.offender = offender;
    }
    
}
