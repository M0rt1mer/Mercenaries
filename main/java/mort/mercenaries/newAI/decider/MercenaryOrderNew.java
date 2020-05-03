package mort.mercenaries.newAI.decider;

import mort.mercenaries.newAI.ThreePartAI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;

/**
 * A base for all MercenaryOrders. If used by itself, it represents the idle order
 *
 * @author Martin
 */
public class MercenaryOrderNew {

    public ThreePartAI ai;

    //protected MercenaryJob[] pool;
    //public MercenaryJob lastJob;

    public List<Entity> nearbyEnt;
    public int lastSearch;

    public static final int searchCooldown = 40;

    public MercenaryOrderNew(ThreePartAI ai, LivingEntity orderer) {
    }

    /**
     * Updates the order once
     * @return true if the order is complete and should stop
     */
    public boolean update(){
        return false;
    }

    //region Save/Load
    public MercenaryOrderNew(ThreePartAI ai, CompoundNBT tag) {
        this(ai, (LivingEntity) null);
    }

    public void saveToNBTTag(CompoundNBT tag) {
    }
    //endregion

    public void reissue() {
    }


    //region Factory
    public interface MercenaryOrderFactory {
        MercenaryOrderNew instantiate(ThreePartAI ai, LivingEntity orderer);
    }

    public static MercenaryOrderFactory getFactory() {
        return ((ai, orderer) -> new MercenaryOrderNew(ai, orderer));
    }

    //--------------------constructor
    //endregion


}
