/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.ai;

import java.util.Comparator;
import java.util.Random;
import net.minecraft.entity.Entity;

/**
 *
 * @author Martin
 */
public abstract class MercenaryJob {
    
    public MercenaryOrder order;

    public static Random rnd = new Random();
    
    public MercenaryJob( MercenaryOrder order ) {
	this.order = order;
    }
    /**
     * Calculates this job's priority, based on the mercenary's state
     * @param order
     * @return 
     */
    public abstract float getPriority();
    
    public abstract void engage();
    
    public abstract void update();
    
    public void updateCache(){}
    
    //----------- UTILS
    
    /**
     * Used to sort entities base on distance to mercenary
     */
    public static class EntitySorter implements Comparator<Entity> {
	Entity base;
	public EntitySorter( Entity base ) {
	    this.base = base;
	}
	@Override
	public int compare(Entity o1, Entity o2) {
	    return Float.compare( (float)o1.getDistanceSqToEntity(base), (float)o2.getDistanceSqToEntity(base) );
	}
    }
    
}
