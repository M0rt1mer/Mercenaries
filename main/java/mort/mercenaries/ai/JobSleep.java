/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.ai;

import com.google.common.collect.Iterables;
import mort.mercenaries.EntityMercenaryOld;

/**
 *
 * @author Martin
 */
public class JobSleep extends MercenaryJob{

    private int lastBedCheck;
    
    
    public JobSleep(MercenaryOrder order) {
	super(order);
	lastBedCheck = 0;
    }

    @Override
    public void engage() {
	if( !order.ai.merc.hasBed() ){
	    lookForBed();
	}
    }

    @Override
    public float getPriority() {
	OrderWork work = (OrderWork)order;
	//System.out.println( "Time: " + work.getTime() + "Shift: " + work.time + " Sleep: " + (2-((work.time)/2)*2) );
	if( work.getTime() == 2-((work.time)/2)*2 ){ //the formula results in 0 and 1 workers to sleep in 2 and 2 workers to sleep in 0
	    return 10;
	}
	else
	    return 0;
	
    }

    @Override
    public void update() {
	/*EntityMercenaryOld merc = order.ai.merc;
	if( merc.getNavigator().noPath() ){
	    if( merc.hasBed() ){
		if( Math.abs( merc.bedX - merc.posX ) < 1 && Math.abs( merc.bedY - merc.posY ) < 1 && Math.abs( merc.bedZ - merc.posZ ) < 1 ){
		    merc.setPosition(merc.bedX -0.5, merc.bedY+0.5, merc.bedZ-0.5);
		    merc.sleeping = true;
		    merc.getNavigator().clearPathEntity();
		}
		else
		    merc.getNavigator().tryMoveToXYZ(merc.bedX-0.5, merc.bedY+0.5, merc.bedZ-0.5, 0.3f );
	    }
	    else
		lookForBed();
	}*/
    }
    
    public void lookForBed(){
		/*if( lastBedCheck <= 0 ){
		    lastBedCheck = 400;
		    //
		    int posX = (int)order.ai.merc.posX;
		    int posY = (int)order.ai.merc.posY;
		    int posZ = (int)order.ai.merc.posZ;
		    for( int x = -10; x < 11; x++ )
			for( int y = -10; y < 11; y++ )
			    for( int z = -4; z < 5; z++ ){
				if( order.ai.merc.worldObj.getBlock(posX+x, posY+y, posZ+z) == Block.blockRegistry.getObject("bed") ){
				    int meta = order.ai.merc.worldObj.getBlockMetadata(posX+x, posY+y, posZ+z);
				    if( !BlockBed.isBlockHeadOfBed( meta ) && !BlockBed.func_149976_c(meta) ){ //func_149976_c - aka isBedOccupied
					occupyBed( posX+x, posY+y, posZ+z );
				    }
				}
			    }
		    //search from 
		    if( order.ai.merc.hasBed() ){
			EntityMercenaryOld merc = getRandomMerc();
			if( merc != null && merc != order.ai.merc && merc.hasBed() ){
			    for( int x = -10; x < 11; x++ )
				for( int y = -10; y < 11; y++ )
				    for( int z = -4; z < 5; z++ ){
					if( order.ai.merc.worldObj.getBlock((int)merc.posX+x, (int)merc.posY+y, (int)merc.posZ+z) == Block.blockRegistry.getObject("bed") ){
					    int meta = order.ai.merc.worldObj.getBlockMetadata(posX+x, posY+y, posZ+z);
					    if( !BlockBed.isBlockHeadOfBed( meta ) && !BlockBed.func_149976_c(meta) ){
						occupyBed( (int)merc.posX+x, (int)merc.posY+y, (int)merc.posZ+z );
					    }
					}
				    }
			}
	    }
	}
	else
	    lastBedCheck--;*/
    }
    
    public EntityMercenaryOld getRandomMerc(){
	int i = rnd.nextInt( order.ai.merc.getGuild().activeMercenaries.size() );
	return Iterables.get( order.ai.merc.getGuild().activeMercenaries, i );
    }
    
    public void occupyBed( int x, int y, int z ){
	/*BlockBed.func_149979_a( order.ai.merc.worldObj, x, y, z, true); //func_149979_a - aka setBedOccupied
	order.ai.merc.bedX = x;
	order.ai.merc.bedY = y;
	order.ai.merc.bedZ = z;*/
    }
    
}
