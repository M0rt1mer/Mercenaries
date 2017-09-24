package mort.mercenaries.ai;

/**
 * @author Martin
 */
public class JobIdle extends MercenaryJob{

    public JobIdle(MercenaryOrder order) {
	super(order);
    }

    @Override
    public void engage() {
	
    }

    @Override
    public float getPriority() {
	return 1;
    }

    @Override
    public void update() {
	
    }

    
}
