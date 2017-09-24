package mort.mercenaries.profession;

import mort.mercenaries.EntityMercenary;
import mort.mercenaries.ai.JobWork;
import mort.mercenaries.api.MercenaryProfession;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Martin
 */
public class Farmer extends MercenaryProfession {

	public Farmer( int id) {
		super( id, "farmer", "profFarmer" );
	}

	@Override
	public void doMove(JobWork job) {	
		if( job.order.ai.merc.getNavigator().noPath() )
			if( job.lastAction != null )
				job.order.ai.merc.getNavigator().tryMoveToXYZ( job.lastAction.getX(), job.lastAction.getY(), job.lastAction.getZ(), 0.3f );
	}

	@Override
	public boolean hasToMove(JobWork job) {
		/*if( job.lastAction==null ){
			//search
			OrderWork ord = (OrderWork)job.order;
			search:
				for( int x = -5; x <= 5; x++ )
					for( int z = -5; z <= 5; z++ ){
						int XX = (int)ord.x + x; int ZZ = (int)ord.z + z;
						int y = job.order.ai.merc.worldObj.getHeightValue( XX, ZZ ) - 1;
						Block id = job.order.ai.merc.worldObj.getBlock( XX, y, ZZ );
						if( id == Block.blockRegistry.getObject("dirt") || id == Block.blockRegistry.getObject("grass") || id == Block.blockRegistry.getObject("tallgrass") ){
							job.lastAction = new Coordinates( XX, y, ZZ );
							break search;
						}
						if( (id==Block.blockRegistry.getObject("crops") && job.order.ai.merc.worldObj.getBlockMetadata( (int)ord.x + x , y, (int)ord.y + z)==15) ){
							job.lastAction = new Coordinates( XX, y, ZZ );
							//@todo: temp
							break search;
						}
						if( id == Block.blockRegistry.getObject("farmland") ){
							Block id2 = job.order.ai.merc.worldObj.getBlock( XX, y+1, ZZ );
							if( id2 != Block.blockRegistry.getObject("crops") ){
								if( id == Block.blockRegistry.getObject("crops") ){
									job.lastAction = new Coordinates( XX, y+1, ZZ );
									break search;
								}
								if( id.isAir(job.order.ai.merc.worldObj, XX, y, ZZ) ){
									job.lastAction = new Coordinates( XX, y, ZZ );
									break search;
								}
							}
						}
					}
		}*/
		if( job.lastAction!=null )
			System.out.println( "Farm> "+job.lastAction.getX()+" "+job.lastAction.getY() + " " + job.lastAction.getZ() );
		return super.hasToMove(job);
	}

	@Override
	public boolean isAvaible(EntityMercenary merc) {
		return true;
	}

	@Override
	public boolean matchesItem(ItemStack stk) {
		return stk.getItem() instanceof ItemHoe;
	}

	@Override
	public void performAction(JobWork job) {
		/*if( job.lastAction == null )
			return;
		//OrderWork ord = (OrderWork)job.order;
		Block id = job.order.ai.merc.worldObj.getBlock( (int)job.lastAction.x, (int)job.lastAction.y, (int)job.lastAction.z );
		if( id == Block.blockRegistry.getObject("grass") || id == Block.blockRegistry.getObject("dirt") ){
			job.order.ai.merc.worldObj.setBlock( (int)job.lastAction.x, (int)job.lastAction.y, (int)job.lastAction.z, (Block)Block.blockRegistry.getObject("farmland") );
		}
		else if( id == Block.blockRegistry.getObject("crops") || id == Block.blockRegistry.getObject("tallgrass") ){
			breakBlock( job,(int)job.lastAction.x,(int)job.lastAction.y,(int)job.lastAction.z );
		}
		else if( id == Block.blockRegistry.getObject("farmland") ){
			job.order.ai.merc.worldObj.setBlock( (int)job.lastAction.x, (int)job.lastAction.y+1, (int)job.lastAction.z, (Block)Block.blockRegistry.getObject("crops") );

		}
		job.lastAction = null;*/
	}

	public void breakBlock( JobWork job, int x, int y, int z ){
		/*World wld = job.order.ai.merc.worldObj;
		wld.getBlock( x, y, z ).dropBlockAsItem(wld, x,y,z, 1, wld.getBlockMetadata(x,y,z) );
		wld.setBlockToAir(x,y,z);*/
	}

	@Override
	public double getRange(JobWork job) {
		return 3;
	}





}
