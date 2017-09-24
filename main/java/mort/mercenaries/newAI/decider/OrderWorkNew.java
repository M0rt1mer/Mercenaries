package mort.mercenaries.newAI.decider;

import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class OrderWorkNew extends MercenaryOrderNew {

    public byte time; //there are three shifts - morning, afternoon and night
    //work location
    public double x, y, z;

    public OrderWorkNew(ThreePartAI ai, EntityLivingBase orderer) {
        super(ai, orderer);
        this.x = ai.merc.posX;
        this.y = ai.merc.posY;
        this.z = ai.merc.posZ;
        this.time = (byte) getTime();
    }

    public OrderWorkNew(ThreePartAI ai, NBTTagCompound tag) {
        super(ai, tag );
        this.x = tag.getDouble("WorkX");
        this.y = tag.getDouble("WorkY");
        this.z = tag.getDouble("WorkZ");
        this.time = tag.getByte("ShiftTime");
        System.out.println("Order loaded: " + this);
    }

    @Override
    public void saveToNBTTag(NBTTagCompound tag) {
        super.saveToNBTTag(tag);
        tag.setDouble("WorkX", x);
        tag.setDouble("WorkY", y);
        tag.setDouble("WorkZ", z);
        tag.setByte("ShiftTime", time);
    }

    public int getTime() {
        return (int) (ai.merc.getEntityWorld().getWorldInfo().getWorldTime() % 20000) / 6667;
    }

    @Override
    public void reissue() {
        time = (byte) ((time + 1) % 3);
    }

    @Override
    public String toString() {
        return "OrderWork{" + "time=" + time + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }



    //region Factory
    public static MercenaryOrderFactory getFactory(){
        return ( (ai,orderer) -> new OrderWorkNew(ai,orderer) );
    }

    //endregion



}
