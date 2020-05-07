package mort.mercenaries.client;


import mort.mercenaries.Content;
import mort.mercenaries.Reference;
import mort.mercenaries.common.CommonProxy;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

public class ClientProxy extends CommonProxy{

	private void registerSingleItemRenderer(Item item){
        //ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}

	public void clientModSetup(){
		//registerSingleItemRenderer(Content.itemMoney);
        //RenderingRegistry.registerEntityRenderingHandler(EntityMercenary.class, RenderMercenary::new );
		RenderingRegistry.registerEntityRenderingHandler(Content.mercEntityType.get(), RenderMercenary::new );
		ScreenManager.registerFactory(Content.mercContainerType.get(), GUIContainerMercenary::new);
	}

}
