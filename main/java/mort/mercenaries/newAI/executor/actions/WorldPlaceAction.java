package mort.mercenaries.newAI.executor.actions;

import mort.mercenaries.newAI.executor.AiActionTemplate;
import mort.mercenaries.newAI.executor.AiExecutor;
import mort.mercenaries.newAI.executor.ParametrizedAiAction;
import mort.mercenaries.newAI.worldState.AiWorldState;

/**
 * Created by Martin on 19.06.2016.
 */
public class WorldPlaceAction extends AiActionTemplate{

    @Override
    public boolean checkAvailable(AiWorldState difference) {
        return false;
    }

    @Override
    public ParametrizedAiAction[] expand(AiWorldState state) {
        return new ParametrizedAiAction[0];
    }

    @Override
    public boolean perform(AiExecutor executor, ParametrizedAiAction action) {
        return false;
    }
}
