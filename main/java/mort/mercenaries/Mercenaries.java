
package mort.mercenaries;

import mort.mercenaries.client.MercKeyboardInput;
import mort.mercenaries.common.CommonProxy;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.network.MessageCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static mort.mercenaries.common.CommonProxy.smw;

@Mod(modid=Reference.modId,version="0.1",name=Reference.modName)
public class Mercenaries {

	@SidedProxy(clientSide="mort.mercenaries.client.ClientProxy",serverSide="mort.mercenaries.common.CommonProxy")
	public static CommonProxy proxy;
	@Mod.Instance
	public static Mercenaries merc;

	@Mod.EventHandler
	public void PreInit( FMLPreInitializationEvent event ){

		NetworkRegistry.INSTANCE.registerGuiHandler(merc, proxy);
		MinecraftForge.EVENT_BUS.register(new MercEventHandler());
		MinecraftForge.EVENT_BUS.register(proxy);
		smw.registerMessage(MessageCommand.class, MessageCommand.class, 0, Side.SERVER);
		Content.preInit();
		new MercKeyboardInput();
	}

	@Mod.EventHandler
	public void Init( FMLInitializationEvent event ){
        Content.init();
	}

	@Mod.EventHandler
	public void PostInit( FMLPostInitializationEvent event ){
		Content.postInit();
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartedEvent event){

	}
	/**
	 * Issues a command to given mercenary. If no mercenary is given, a rayCast is cast to find one (client only)
	 */
	public void issueCommand(EntityMercenary ent, int order, EntityPlayer orderer ){
		if( ent == null ){
			RayTraceResult mpo = Minecraft.getMinecraft().objectMouseOver;
			if( mpo!=null && mpo.typeOfHit == RayTraceResult.Type.ENTITY ){
				if( mpo.entityHit instanceof EntityMercenary){
					ent = (EntityMercenary)mpo.entityHit;
				}
			}
		}
		if( ent == null )
			return;
		if( orderer.world.isRemote ){
			smw.sendToServer( new MessageCommand(ent, order, orderer) );
		}
		else{
			//if( ent.getGuild() == Mercenaries.guildManager.getGuild(orderer) )
				ent.ai.changeOrder(order, orderer);
		}
	}

}
