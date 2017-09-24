package mort.mercenaries.newAI.executor;

/**
 * Created by Martin on 19.06.2016.
 */
public class ParametrizedAiAction {

    public AiActionTemplate template;
    public Object[] parameters;

    public ParametrizedAiAction(AiActionTemplate template) {
        this.template = template;
    }
}
