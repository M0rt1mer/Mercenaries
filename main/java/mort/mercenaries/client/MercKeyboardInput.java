package mort.mercenaries.client;

import mort.mercenaries.Mercenaries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;


/**
 *
 * @author Martin
 */
public class MercKeyboardInput{
    
    public KeyBinding commandKeys[];
    
    public MercKeyboardInput() {
		commandKeys = new KeyBinding[5];
		commandKeys[0] = new KeyBinding( "key.merc.command0", Keyboard.KEY_Z, "key.categories.misc" );
		commandKeys[1] = new KeyBinding( "key.merc.command1", Keyboard.KEY_X, "key.categories.misc" );
		commandKeys[2] = new KeyBinding( "key.merc.command2", Keyboard.KEY_C, "key.categories.misc" );
		commandKeys[3] = new KeyBinding( "key.merc.command3", Keyboard.KEY_V, "key.categories.misc" );
		commandKeys[4] = new KeyBinding( "key.merc.command4", Keyboard.KEY_B, "key.categories.misc" );
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
    				Mercenaries.merc.issueCommand(null, i, Minecraft.getMinecraft().thePlayer );
    		}
    	
    }
    
}
