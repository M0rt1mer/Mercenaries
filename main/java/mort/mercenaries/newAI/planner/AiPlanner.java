package mort.mercenaries.newAI.planner;

import mort.mercenaries.newAI.ThreePartAI;
import mort.mercenaries.newAI.executor.ParametrizedAiAction;
import mort.mercenaries.newAI.plan.AiPlan;
import mort.mercenaries.newAI.worldState.AiWorldState;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Martin on 19.06.2016.
 */
public class AiPlanner {

    ThreePartAI ai;

    public AiPlanner(ThreePartAI ai) {
        this.ai = ai;
    }

    public void update(){
        if( ai.plan == null || !ai.plan.targetState.equals(ai.desiredWorldState) ){
            if(ai.desiredWorldState != null){
                newPlan();
            }
        }


    }

    private void newPlan(){
        AiPlan newPlan = new AiPlan();
        AiPlanWorldState initialState = new AiPlanWorldState( new AiWorldState(),0, null, null);

        HashSet<AiWorldState> closed = new HashSet<>();
        PriorityQueue<AiPlanWorldState> open = new PriorityQueue<>();

        while(true){






        }



    }



    private class AiPlanWorldState{

        AiWorldState state;
        AiPlanWorldState parent;
        ParametrizedAiAction action;
        float cost;
        float heuristic;

        public AiPlanWorldState(AiWorldState state, float cost, AiPlanWorldState parent, ParametrizedAiAction action) {
            this.cost = cost;
            this.state = state;
            this.parent = parent;
            this.action = action;
            if(parent == null){
                heuristic = 0;
            } else calcDifferentialHeuristic();
        }

        private void calcDifferentialHeuristic(){
            
        }
    }

}
