package mort.mercenaries.client;

import mort.mercenaries.Mercenaries;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;


/**
 *
 * @author Martin
 */
public class MercKeyboardInput{
    
    public KeyBinding commandKeys[];
    
    public MercKeyboardInput() {
		commandKeys = new KeyBinding[5];
		commandKeys[0] = new KeyBinding( "key.merc.command0", 90, "key.categories.misc" );
		commandKeys[1] = new KeyBinding( "key.merc.command1", 88, "key.categories.misc" );
		commandKeys[2] = new KeyBinding( "key.merc.command2", 67, "key.categories.misc" );
		commandKeys[3] = new KeyBinding( "key.merc.command3", 86, "key.categories.misc" );
		commandKeys[4] = new KeyBinding( "key.merc.command4", 66, "key.categories.misc" );
		updateHandler();
    }
    
    private void updateHandler(){
		for( int i=0;i<commandKeys.length;i++ ){
		    ClientRegistry.registerKeyBinding(commandKeys[i]);
		}
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    		for( int i = 0; i<commandKeys.length;i++){
    			if(commandKeys[i].isPressed())
    				Mercenaries.merc.issueCommand(null, i, Minecraft.getInstance().player );
    		}
    	
    }
    
}
