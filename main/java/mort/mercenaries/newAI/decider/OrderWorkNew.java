package mort.mercenaries.newAI.decider;

import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

public class OrderWorkNew extends MercenaryOrderNew {

    public byte time; //there are three shifts - morning, afternoon and night
    //work location
    public double x, y, z;

    public OrderWorkNew(ThreePartAI ai, LivingEntity orderer) {
        super(ai, orderer);
        this.x = ai.merc.prevPosX;
        this.y = ai.merc.prevPosY;
        this.z = ai.merc.prevPosZ;
        this.time = (byte) getTime();
    }

    public OrderWorkNew(ThreePartAI ai, CompoundNBT tag) {
        super(ai, tag );
        this.x = tag.getDouble("WorkX");
        this.y = tag.getDouble("WorkY");
        this.z = tag.getDouble("WorkZ");
        this.time = tag.getByte("ShiftTime");
        System.out.println("Order loaded: " + this);
    }

    @Override
    public void saveToNBTTag(CompoundNBT tag) {
        super.saveToNBTTag(tag);
        tag.putDouble("WorkX", x);
        tag.putDouble("WorkY", y);
        tag.putDouble("WorkZ", z);
        tag.putByte("ShiftTime", time);
    }

    public int getTime() {
        return (int) (ai.merc.getEntityWorld().getWorldInfo().getDayTime() % 20000) / 6667;
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
