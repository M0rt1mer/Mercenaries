package mort.mercenaries.client;


import mort.mercenaries.Content;
import mort.mercenaries.common.CommonProxy;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 *
 * @author Martin
 */
public class ClientProxy extends CommonProxy implements IRenderFactory<EntityMercenary>{

	@Override
	public void registerEntityRender() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMercenary.class, this );
	}

	@Override
	public void registerItemBlockRender() {
		super.registerItemBlockRender();
		registerSingleItemRenderer(Content.itemMoney);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Entity ent = world.getEntityByID( x );
		if( ent != null && ent instanceof EntityMercenary){
			return new GUIContainerMercenary( (EntityMercenary) ent, player );
		}
		return null;
	}

	@Override
	public Render<? super EntityMercenary> createRenderFor(RenderManager manager) {
		return new RenderMercenary(manager);
	}

	private void registerSingleItemRenderer(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}

}
