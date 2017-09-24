package mort.mercenaries.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

/**
 *
 * @author Martin
 */
public class OrderFollow extends MercenaryOrder{
    
    //private int followId;
    private EntityLiving followEntity;
    
    
    
    public OrderFollow(MercenaryAI ai, EntityLiving orderer) {
	super(ai,orderer);
	this.followEntity = orderer;
	//this.followId = orderer.entityId;
    }

    public OrderFollow(MercenaryAI ai, NBTTagCompound tag) {
	super(ai, tag);
	this.followEntity = null; //disable follow order after reload
    }
    
    @Override
    public boolean update() {
	/*if(!super.update())
	    return false;
	if( followEntity==null ){
	    /*followEntity=(EntityLiving)ai.merc.worldObj.getEntityByID(followId);
	    if(followEntity==null)
		return true;*/
	//    return false;
	//}
	/*
	if( location==null || 2 > distFromCenterSq(location, (float)followEntity.posX, (float)followEntity.posY, (float)followEntity.posZ)){
	    
	    location = getBBAroundEntity( followEntity, 8 );
	}*/
	return true;
    }
    
    
    
    
    private float distFromCenterSq(AxisAlignedBB aabb, float x, float y, float z ){
	float dx = (float)(aabb.minX+aabb.maxX)/2 - x;
	float dy = (float)(aabb.minY+aabb.maxY)/2 - y;
	float dz = (float)(aabb.minZ+aabb.maxZ)/2 - z;
	return dx*dx + dy*dy + dz*dz;
	
    }
    private AxisAlignedBB getBBAroundEntity( EntityLiving target, float width ){
	return new AxisAlignedBB( target.posX - width/2, target.posX + width/2,
		target.posY - width/2, target.posY + width/2,
		target.posZ - width/2, target.posZ + width/2);
    }
	    
}
