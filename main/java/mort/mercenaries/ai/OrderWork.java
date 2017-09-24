package mort.mercenaries.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

public class OrderWork extends MercenaryOrder{

    public byte time; //there are three shifts - morning, afternoon and night
    //work location
    public double x, y, z;
    
    public static final Class[] workJobs = { JobIdle.class, JobWork.class, JobFight.class, JobSleep.class, JobPickupItems.class};
    
    public OrderWork(MercenaryAI ai, EntityLiving orderer) {
	super(ai, orderer, workJobs);
	this.x = ai.merc.posX;
	this.y = ai.merc.posY;
	this.z = ai.merc.posZ;
	this.time = (byte)getTime();
    }

    public OrderWork(MercenaryAI ai, NBTTagCompound tag) {
	super(ai, tag, workJobs);
	this.x = tag.getDouble( "WorkX" );
	this.y = tag.getDouble( "WorkY" );
	this.z = tag.getDouble( "WorkZ" );
	this.time = tag.getByte( "ShiftTime" );
	System.out.println( "Order loaded: "+this );
    }

    @Override
    public void saveToNBTTag(NBTTagCompound tag) {
	super.saveToNBTTag(tag);
	tag.setDouble("WorkX", x);
	tag.setDouble("WorkY", y);
	tag.setDouble("WorkZ", z);
	tag.setByte("ShiftTime", time);
    }
    
    public int getTime(){
	return (int)(ai.merc.worldObj.getWorldInfo().getWorldTime() % 20000) / 6667;
    }

    @Override
    public void reissue() {
	time = (byte)((time+1) % 3);
    }

    @Override
    public String toString() {
	return "OrderWork{" + "time=" + time + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
    
}
