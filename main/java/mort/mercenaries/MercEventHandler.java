package mort.mercenaries;

import mort.mercenaries.common.EntityMercenary;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author Martin
 */
public class MercEventHandler {

	@SubscribeEvent
	public void onEntityInteract(EntityInteractSpecific evnt){
		ItemStack heldItem = evnt.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
		if( heldItem!= null && heldItem.getItem() == Content.itemMoney ){
			if( evnt.getTarget() instanceof EntityVillager ){
				EntityVillager villager = (EntityVillager)evnt.getTarget();
				if( !villager.isChild() ){
					if(villager.getWorld().isRemote)
						return;
					EntityMercenary merc = new EntityMercenary(villager.getWorld());
					//merc.guildAllignment = Mercenaries.guildManager.getGuild(evnt.getEntityPlayer()).id;
					//System.out.println( "Allign: "+merc.guildAllignment + merc.getGuild() );
					merc.setLocationAndAngles(villager.posX, villager.posY, villager.posZ, villager.rotationYaw, villager.rotationPitch);
					villager.getWorld().spawnEntity(merc);
					villager.getWorld().removeEntity( villager );
					return;
				}
			}
		}
	}

	@SubscribeEvent
	public void onSpawn( EntityJoinWorldEvent evnt ){
		if( evnt.getEntity() instanceof EntityZombie || evnt.getEntity() instanceof EntitySkeleton ){
			//    ((EntityLiving)evnt.entity).targetTasks.addTask(2, new EntityAINearestAttackableTarget((EntityLiving)evnt.entity, EntityMercenaryOld.class, 16.0f, 0, true)  );
		}

	}

	/*
	@SubscribeEvent
	public void texturePreStitch( TextureStitchEvent.Pre evnt ){
		ProfessionManager.instance.texturePreStitch(evnt);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldLoadClient( Load evnt ){
		if( evnt.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD )
			Mercenaries.guildManager.load( evnt.getWorld(), evnt.getWorld().getSaveHandler().getWorldDirectory() );
	}
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onWorldLoadServ( Load evnt ){
        if( evnt.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD )
			Mercenaries.guildManager.load( evnt.getWorld(), evnt.getWorld().getSaveHandler().getWorldDirectory() );
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldUnloadClient( Unload evnt ){
        if( evnt.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD )
			Mercenaries.guildManager.save( evnt.getWorld(), evnt.getWorld().getSaveHandler().getWorldDirectory() );
	}
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onWorldUnloadServer( Unload evnt ){
        if( evnt.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD )
			Mercenaries.guildManager.save( evnt.getWorld(), evnt.getWorld().getSaveHandler().getWorldDirectory() );
	}

	@SubscribeEvent
	public void onChunkUnload( net.minecraftforge.event.world.ChunkEvent.Unload evnt ){
		for (int var3 = 0; var3 < evnt.getChunk().getEntityLists().length; ++var3)
		{
			for( Object ent : evnt.getChunk().getEntityLists()[var3] )
				if( ent instanceof EntityMercenaryOld)
					((EntityMercenaryOld)ent).reportToGuild(false);
		}
	}/*/

	/*@SubscribeEvent
	public void onAttack( LivingAttackEvent evnt ){
		if( evnt.source.getEntity() instanceof EntityLiving )
			if( Mercenaries.guildManager.getGuild(evnt.entityLiving) != null ){
				//Crime crm = new Crime(CrimeType.assault, ((EntityLivingBase)evnt.source.getEntity()) );
				//CrimeManager.instance.reportCrime( crm, Mercenaries.guildManager.getGuild(evnt.entityLiving) );
			}
	}

	@SubscribeEvent
	public void onInteract( PlayerInteractEvent evnt ){
		if( evnt.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK ){
			if( evnt.entityPlayer.worldObj.getTileEntity(evnt.x, evnt.y, evnt.z) != null ){
				//theft
				//Crime crm = new Crime(CrimeType.theft, evnt.entityLiving );
				//CrimeManager.instance.reportCrime( crm, null );
			}

		}
	}*/
/*
	@SubscribeEvent
	public void onPlayerLogin( PlayerEvent.PlayerLoggedInEvent event ) {
		Mercenaries.guildManager.playerConnect( event.player );
	}*/


}
