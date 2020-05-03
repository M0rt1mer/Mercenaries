package mort.mercenaries.newAI;

import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.newAI.executor.AiExecutor;
import mort.mercenaries.newAI.decider.MercenaryOrderNew;
import mort.mercenaries.newAI.decider.OrderFollowNew;
import mort.mercenaries.newAI.decider.OrderWorkNew;
import mort.mercenaries.newAI.plan.AiPlan;
import mort.mercenaries.newAI.planner.AiPlanner;
import mort.mercenaries.newAI.worldState.AiWorldState;
import net.minecraft.entity.LivingEntity;


public class ThreePartAI {

    public static MercenaryOrderNew.MercenaryOrderFactory[] orderTypes = {MercenaryOrderNew::new, OrderWorkNew::new, OrderFollowNew::new};

    public EntityMercenary merc;

    //region Order
    public MercenaryOrderNew order = null;
    private int orderId;
    //endregion

    public AiWorldState desiredWorldState;
    public AiPlanner planner;
    public AiPlan plan;
    public AiExecutor executor;

    public ThreePartAI(EntityMercenary merc) {
        this.merc = merc;
        planner = new AiPlanner(this);
        executor = new AiExecutor(this);
    }

    public void update(){
        if( order==null ){
            orderId = -1;
            changeOrder(0, null ); //set to idle
        }
        if( order.update() )
            order = null;

        if(desiredWorldState != null)
            planner.update();

        if(plan!=null)
            executor.update();

    }

    public void changeOrder( int ord, LivingEntity orderer ){
        if( ord != orderId ){
            order = orderTypes[ord].instantiate(this, orderer);
            orderId = ord;
        }
        else{
            order.reissue();
        }
    }

}
