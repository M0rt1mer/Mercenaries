package mort.mercenaries.api;

import mort.mercenaries.EntityMercenary;
import mort.mercenaries.Reference;
import mort.mercenaries.ai.JobWork;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Abstract class, parent of all professions.
 * Every profession is a singleton, whose instance should be added to ProfessionManager, otherwise they won't be recognized by any other
 * @author Martin
 */
public class MercenaryProfession {
    
    public TextureAtlasSprite icn;
    public String registryName;
    public int id;
    /**
     * Inner name of the profession. Actual name is read from "professions.name.innerName", 
     * description for "professions.desc.innerName" 
     * item requirement "professions.req.innerName"
     */
    public String innerName;
    
    public MercenaryProfession( int id, String name, String registryName ){
		this.registryName = registryName;
		this.innerName = name;
    }
    
    /**
     * Checks whether given mercenary qualifies for this profession
     * Overriding methods don't need to call this base method, it's output doesn't matter
     * @param merc
     * @return obvious, true if avaible
     */
    public boolean isAvaible( EntityMercenary merc ){
		return true;
    }
    
    /**
     * Checks whether this this profession is associated with given ItemStack.
     * For any given ItemStack, only one profession should return true, otherwise undefined behavior.
     * @param stk
     * @return obvious, true for a match
     */
    public boolean matchesItem( ItemStack stk ){
		return true;
    }
    
    //---------------------------------------  AI
    /**
     * Used to determine whether doMove or performAction should be called.
     * This prototype can be used to test range to lastAction, if specified
     * @param job
     * @return true if in range, false if outside range or no action specified
     */
    public boolean hasToMove( JobWork job ){
		/*EntityMercenary merc = job.order.ai.merc;
		if( job.lastAction != null )
		    return (Math.pow(job.lastAction.getX()-merc.posX,2) + Math.pow(job.lastAction.getY()-merc.posY,2) + Math.pow(job.lastAction.getZ()-merc.posZ,2) ) > Math.pow( getRange(job), 2);
		else
		    return false;*/
        return false;
    }
    
    public void doMove( JobWork job ){}
    
    public void performAction( JobWork job ){}
    
    /**
     * basic working range
     */
    public double getRange( JobWork job ){ return 1; }
    
    //---------------------------------------  
    
    @SideOnly( Side.CLIENT )
    public String getName(){
    	return I18n.translateToLocal( "professions.name." + innerName );
    }
    @SideOnly( Side.CLIENT )
    public String getDescription(){
    	return I18n.translateToLocal( "professions.desc." + innerName );
    }
    @SideOnly( Side.CLIENT )
    public String getRequirement(){
    	return I18n.translateToLocal( "professions.req." + innerName );

    }
    
    @SideOnly( Side.CLIENT )
    public void registerIcon( TextureMap map ){
    	icn = map.registerSprite( new ResourceLocation( Reference.modId, "profession/" + this.registryName) );
    }

    @SideOnly( Side.CLIENT )
    public ResourceLocation getResourceLocation(){
        return new ResourceLocation( Reference.modId,"textures/profession/" + this.registryName+".png");
    }
    
}
