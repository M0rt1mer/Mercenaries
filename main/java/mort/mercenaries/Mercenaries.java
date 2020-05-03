
package mort.mercenaries;

import mort.mercenaries.client.ClientProxy;
import mort.mercenaries.client.GUIContainerMercenary;
import mort.mercenaries.common.CommonProxy;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.network.MessageCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


@Mod(Reference.modId)
public class Mercenaries {

	public static CommonProxy proxy = DistExecutor.runForDist( () -> ClientProxy::new, () -> CommonProxy::new );

	public static Mercenaries merc;

	public Mercenaries() {
		merc = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::EventSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::EventClientSetup);

		Content.RegisterAllDeferred();
	}

	public void EventSetup(final FMLCommonSetupEvent event)	{
		simpleChannel.registerMessage(0, MessageCommand.class, MessageCommand::toBytes, MessageCommand::new, MessageCommand::onMessage );
	}

	public void EventClientSetup(final FMLClientSetupEvent event)
	{
		proxy.clientModSetup();
	}

	/*@Mod.EventHandler
	public void PreInit( FMLPreInitializationEvent event ){

		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::openGui);
		NetworkRegistry.INSTANCE.registerGuiHandler(merc, proxy);
		MinecraftForge.EVENT_BUS.register(new MercEventHandler());
		MinecraftForge.EVENT_BUS.register(proxy);
		smw.registerMessage(MessageCommand.class, MessageCommand.class, 0, Side.SERVER);
		Content.preInit();
		new MercKeyboardInput();
	}*/


	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel simpleChannel = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Reference.modId, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	/**
	 * Issues a command to given mercenary. If no mercenary is given, a rayCast is cast to find one (client only)
	 */
	public void issueCommand(EntityMercenary ent, int order, PlayerEntity orderer ){
		if( ent == null ){
			RayTraceResult mpo = Minecraft.getInstance().objectMouseOver;
			if( mpo!=null && mpo.getType() == RayTraceResult.Type.ENTITY ){
				Entity entity = ((EntityRayTraceResult) mpo).getEntity();
				if( entity instanceof EntityMercenary){
					ent = (EntityMercenary)entity;
				}
			}
		}
		if( ent == null )
			return;
		if( orderer.world.isRemote ){
			simpleChannel.sendToServer( new MessageCommand(ent, order) );
		}
		else{
			//if( ent.getGuild() == Mercenaries.guildManager.getGuild(orderer) )
				ent.ai.changeOrder(order, orderer);
		}
	}

}
