/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.api.crime;

import mort.mercenaries.EntityMercenaryOld;

/**
 * CrimeAuthority is an interface for Professions, that answer to crimes at their position
 * @author Martin Labuť <martin.labut@volny.cz>
 */
public interface CrimeAuthority {
    
    public float getPriority(EntityMercenaryOld authority, Crime crm );
    
    public void handle(EntityMercenaryOld authority, Crime crm );
    
}
