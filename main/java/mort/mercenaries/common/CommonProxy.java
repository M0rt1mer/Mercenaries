package mort.mercenaries.common;

import mort.mercenaries.inventory.ContainerMercenary;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 *
 * @author Martin
 */
public class CommonProxy implements IGuiHandler {
    
	public static final SimpleNetworkWrapper smw = NetworkRegistry.INSTANCE.newSimpleChannel("mercenaries");

    ////--------------------------------------------------------------------------- Mod initialization

    public void registerEntityRender(){

    }

    public void registerItemBlockRender(){

    }

    ///------------------------------------------------------------------ Ingame
    /**
     * @param x abducted to represent entity ID
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Entity ent = world.getEntityByID( x );
		if( ent != null && ent instanceof EntityMercenary){
		    return new ContainerMercenary( (EntityMercenary) ent, player );
		}
		return null;
    }

    
}
