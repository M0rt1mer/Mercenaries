/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.profession;

import mort.mercenaries.EntityMercenary;
import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.Reference;
import mort.mercenaries.ai.JobWork;
import mort.mercenaries.ai.OrderWork;
import mort.mercenaries.api.MercenaryProfession;
import mort.mercenaries.utils.Coordinates;
import net.minecraft.block.Block;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A profession about felling trees
 * Usage of JobWork's attributes:
 * lastAction - coordinates of Wood block currently trying to fell
 * timeOnAction - counter for felling time, or, if lastAction==null, counter between searches
 * @author Martin
 */
public class Lumberjack extends MercenaryProfession{

    public Lumberjack( int id){
	super( id, "lumberjack", "profLumber" );
    }
    
    @Override
    public boolean isAvaible(EntityMercenary merc) {
		return true;
    }

    @Override
    public boolean matchesItem(ItemStack stk) {
	return (stk.getItem() instanceof ItemAxe);
    }

    
    //------------------------ AI
    @Override
    public void doMove( JobWork job ) {
	super.doMove( job );

		if( job.lastAction!=null && isWoodLog( job.order.ai.merc.worldObj, job.lastAction ) ){
			job.order.ai.merc.getNavigator().tryMoveToXYZ(job.lastAction.getX(), job.lastAction.getY(), job.lastAction.getZ(), 0.3f );
		}
	
    }

    @Override
    public boolean hasToMove( JobWork job ) {
	if( job.lastAction == null || !isWoodLog( job.order.ai.merc.worldObj, job.lastAction )){
	    //search upwards from previous tree slot
	    if( job.lastAction!=null ){ //search upwards
		BlockPos coor = new BlockPos( job.lastAction );
		if( isWoodLog( job.order.ai.merc.worldObj, coor ) ){
		    job.lastAction = coor;
		    job.timeOnAction = 0;
		    //faceCoords(job.order.ai.merc, coor);
//		    System.out.println("Up");
		    return false;
		}
	    }
	    //find new tree
	    for( int x = -5; x < 6; x++ )
		for( int y = -5; y < 6; y++ )
		    for( int z = -5; z < 6; z++ )
			if( isWoodLog( job.order.ai.merc.worldObj, job.order.ai.merc.getPosition().add(x,y,z) ) ){
			    job.lastAction = job.order.ai.merc.getPosition().add(x,y,z);
			    job.timeOnAction = 0;
			    //faceCoords(job.order.ai.merc, job.lastAction);
//			    System.out.println("Search");
			}
	}
	if( job.lastAction != null )
	    return false;
//	System.out.println("No work");
	OrderWork ord = (OrderWork)job.order;
	if( Math.pow(job.order.ai.merc.posX-ord.x,2) + Math.pow(job.order.ai.merc.posY-ord.y,2) + Math.pow(job.order.ai.merc.posZ-ord.z,2)> 5 )
	    return true;
//	System.out.println("Don't move");
	return false;
    }

    @Override
    public void performAction( JobWork job ) {
	super.performAction( job );
	if( job.lastAction!=null){
	    if( job.timeOnAction == 0 ){
		//job.order.ai.merc.swingItem();
	    }
	    job.order.ai.merc.getNavigator().clearPathEntity();
	    job.order.ai.merc.getMoveHelper().setMoveTo(job.lastAction.getX(), job.lastAction.getY(), job.lastAction.getZ(), 0.01f);
	    job.timeOnAction++;
	    if( job.timeOnAction>20 ){
		breakBlock(job);
		job.timeOnAction = 0;
		job.lastAction = null;
	    }
	}
    }

    public static boolean isWoodLog(World world, BlockPos blkPos){
    	return world.getBlockState( blkPos ) == Block.blockRegistry.getObject(new ResourceLocation(Reference.modId,"log"));
    }
    
    public static void breakBlock( JobWork job ){
		/*World wld = job.order.ai.merc.worldObj;
		if( isWoodLog( wld, job.lastAction ) ){
		    wld.getBlock((int)job.lastAction.x, (int)job.lastAction.y, (int)job.lastAction.z).dropBlockAsItem(
		    	wld, (int)job.lastAction.x, (int)job.lastAction.y,
			    (int)job.lastAction.z, 1, wld.getBlockMetadata((int)job.lastAction.x,
			    (int)job.lastAction.y, (int)job.lastAction.z) );
		    wld.setBlockToAir((int)job.lastAction.x, (int)job.lastAction.y, (int)job.lastAction.z);
		}*/
    }
    
    public static void faceCoords(EntityMercenaryOld merc, Coordinates cord ){
	merc.rotationYaw = (float)Math.toDegrees( Math.atan2( cord.x - merc.posX , cord.z - merc.posZ ) );
	merc.rotationPitch = (float)Math.toDegrees( Math.atan2( Math.pow(cord.y - merc.posY,2) , Math.pow(cord.x - merc.posX,2)+Math.pow(cord.z - merc.posZ,2) ) );
	System.out.println( "Facing "+merc.rotationYaw );
    }
}
