package mort.mercenaries.newAI.executor;

import mort.mercenaries.newAI.worldState.AiWorldState;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by Martin on 19.06.2016.
 */
public abstract class AiActionTemplate extends IForgeRegistryEntry.Impl<AiActionTemplate> {

    abstract public boolean checkAvailable(AiWorldState difference);

    abstract public ParametrizedAiAction[] expand(AiWorldState state);

    abstract public boolean perform( AiExecutor executor, ParametrizedAiAction action);

}
