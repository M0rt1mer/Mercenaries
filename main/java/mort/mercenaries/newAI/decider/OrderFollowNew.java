package mort.mercenaries.newAI.decider;

import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author Martin
 */
public class OrderFollowNew extends MercenaryOrderNew {

    //private int followId;
    private EntityLivingBase followEntity;


    public OrderFollowNew(ThreePartAI ai, EntityLivingBase orderer) {
        super(ai, orderer);
        this.followEntity = orderer;
        //this.followId = orderer.entityId;
    }

    public OrderFollowNew(ThreePartAI ai, NBTTagCompound tag) {
        super(ai, tag);
        this.followEntity = null; //disable follow order after reload
    }


    private float distFromCenterSq(AxisAlignedBB aabb, float x, float y, float z) {
        float dx = (float) (aabb.minX + aabb.maxX) / 2 - x;
        float dy = (float) (aabb.minY + aabb.maxY) / 2 - y;
        float dz = (float) (aabb.minZ + aabb.maxZ) / 2 - z;
        return dx * dx + dy * dy + dz * dz;

    }

    private AxisAlignedBB getBBAroundEntity(EntityLiving target, float width) {
        return new AxisAlignedBB(target.posX - width / 2, target.posX + width / 2,
                target.posY - width / 2, target.posY + width / 2,
                target.posZ - width / 2, target.posZ + width / 2);
    }

    public static MercenaryOrderFactory getFactory(){
        return ( (ai,orderer) -> new OrderFollowNew(ai,orderer) );
    }

}
