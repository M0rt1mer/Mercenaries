package mort.mercenaries.ai;

import mort.mercenaries.EntityMercenaryOld;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class MercenaryAI {

    public static final Class[] orderTypes = {MercenaryOrder.class, OrderWork.class, OrderFollow.class};
    public int orderId;

    /**
     * Current order
     */
    public MercenaryOrder order = null;

    public EntityMercenaryOld merc;

    public MercenaryAI(EntityMercenaryOld merc) {
        this.merc = merc;
    }

    public MercenaryAI(EntityMercenaryOld merc, NBTTagCompound loadFrom) {
        this(merc);
        orderId = loadFrom.getByte("OrderId");
        try {
            order = (MercenaryOrder) orderTypes[orderId].
                    getConstructor(new Class[]{MercenaryAI.class, NBTTagCompound.class}).
                    newInstance(new Object[]{this, loadFrom});
        } catch (Exception e) {
            throw new ReportedException(new CrashReport("Mercenary order error", e));
        }
    }

    public void update() {
        if (order == null) {
            orderId = -1;
            changeOrder(0, null); //set to idle
        }
        if (!order.update())
            order = null;

    }

    public void changeOrder(int ord, EntityLivingBase orderer) {
        if (ord != orderId) {
            try {
                order = (MercenaryOrder) orderTypes[ord].
                        getConstructor(new Class[]{MercenaryAI.class, EntityLivingBase.class}).
                        newInstance(new Object[]{this, orderer});
            } catch (Exception e) {
                throw new ReportedException(new CrashReport("Mercenary order error", e));
            }
            orderId = ord;
        } else {
            order.reissue();
        }
    }

    public NBTTagCompound saveToNBTTag() {
        NBTTagCompound comp = new NBTTagCompound();
        comp.setByte("OrderId", (byte) orderId);
        order.saveToNBTTag(comp);
        return comp;
    }

}
