package mort.mercenaries.client;


import mort.mercenaries.Content;
import mort.mercenaries.common.CommonProxy;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author Martin
 */
public class ClientProxy extends CommonProxy{

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Entity ent = world.getEntityByID( x );
		if( ent != null && ent instanceof EntityMercenary){
			return new GUIContainerMercenary( (EntityMercenary) ent, player );
		}
		return null;
	}

	private void registerSingleItemRenderer(Item item){
        ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}

	@SubscribeEvent
	public void event_registerModels(ModelRegistryEvent event) {
		registerSingleItemRenderer(Content.itemMoney);

        RenderingRegistry.registerEntityRenderingHandler(EntityMercenary.class, RenderMercenary::new );
	}

}
