package mort.mercenaries.api;

import mort.mercenaries.Reference;
import mort.mercenaries.common.EntityMercenary;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    //---------------------------------------  
/*
    @OnlyIn(Dist.CLIENT)
    public String getName(){
    	return I18n.translateToLocal( "professions.name." + innerName );
    }

    @OnlyIn(Dist.CLIENT)
    public String getDescription(){
    	return I18n.translateToLocal( "professions.desc." + innerName );
    }

    @OnlyIn(Dist.CLIENT)
    public String getRequirement(){
    	return I18n.translateToLocal( "professions.req." + innerName );

    }*/
    
    @OnlyIn(Dist.CLIENT)
    public void registerIcon( AtlasTexture map ){
    	icn = map.getSprite( new ResourceLocation( Reference.modId, "profession/" + this.registryName) );
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getResourceLocation(){
        return new ResourceLocation( Reference.modId,"textures/profession/" + this.registryName+".png");
    }
    
}
