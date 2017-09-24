package mort.mercenaries.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * A base for all MercenaryOrders. If used by itself, it represents the idle order
 * @author Martin
 */
public class MercenaryOrder {

	public MercenaryAI ai;

	protected MercenaryJob[] pool;
	public MercenaryJob lastJob;

	public List<Entity> nearbyEnt;
	public int lastSearch;

	public static final int searchCooldown = 40;

	public MercenaryOrder( MercenaryAI ai, EntityLivingBase orderer ){
		this( ai, orderer, new Class[]{ JobIdle.class, JobFight.class } );
	}

	public MercenaryOrder( MercenaryAI ai, EntityLivingBase orderer, Class[] jobPool ) {
		this.ai = ai;
		pool = new MercenaryJob[ jobPool.length ];
		int i = 0;
		for( Class cls : jobPool ){
			try{
				pool[i] = (MercenaryJob)cls.getConstructor( MercenaryOrder.class )
						.newInstance( this );
				i++;
			}catch(Exception e){ System.out.println("error creating" + e); }
		}
		lastSearch = searchCooldown;
	}

	public MercenaryOrder( MercenaryAI ai, NBTTagCompound tag ){
		this( ai, (EntityLiving)null );
	}

	public MercenaryOrder(MercenaryAI ai, NBTTagCompound tag, Class[] pool) {
		this( ai, (EntityLiving)null, pool );
	}



	public boolean update(){
		/*if( (lastSearch++) > searchCooldown ){
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox( ai.merc.posX - 10, ai.merc.posY - 10, ai.merc.posZ - 10, ai.merc.posX + 10, ai.merc.posY + 10, ai.merc.posZ + 10);
			nearbyEnt = ai.merc.worldObj.getEntitiesWithinAABBExcludingEntity(ai.merc, aabb);
			for( MercenaryJob job : pool)
				job.updateCache();
		}
		float priority = 0;
		MercenaryJob jb = null;
		for( MercenaryJob job : pool){
			float p = job.getPriority();
			if( p > priority ){
				priority = p;
				jb = job;
			}
		}
		if( jb != null ){
			if( jb != lastJob )
				jb.engage();
			jb.update();
			lastJob = jb;
			//System.out.println( "Job: " + lastJob.getClass().getSimpleName() );
		}
		return true;*/
		return true;
	}

	public void saveToNBTTag( NBTTagCompound tag ){}

	public void reissue(){}

}
