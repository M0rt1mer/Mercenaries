/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Martin
 */
public final class Guild {

	public String ownerName;
	public EntityPlayer owner;
	public int id;
	public List<EntityMercenaryOld> activeMercenaries;

	public HashMap<Guild,Float> diplomaticRelation = new HashMap<Guild, Float>();
	public HashMap<Guild,Float> temporaryRelation = new HashMap<Guild, Float>();
	public float defaultRelation = 0;

	public Guild( EntityPlayer plr ){
		this( getPlayerId( plr ) );
		owner = plr;
		ownerName = plr.getName();
	}

	public Guild( NBTTagCompound comp ){
		this( comp.getInteger("GuildId") );
		load( comp );
	}

	public Guild(){
		this(0);
	}

	public Guild( int id ){
		this.id = id;
		activeMercenaries = new ArrayList<EntityMercenaryOld>();
	}

	public boolean shouldSave(){
		return false;
	}

	public void save( NBTTagCompound comp ){
		comp.setInteger( "GuildId", id);
		comp.setString( "OwnerName", ownerName);
	}

	public void load( NBTTagCompound comp ){
		ownerName = comp.getString( "OwnerName" );
	}

	@Override
	public String toString() {
		return "Guild{" + ownerName + ", " + id + ", hash = "+ super.hashCode()+'}';
	}

	public static int getPlayerId( EntityPlayer plr ){
		return plr.getName().hashCode();
	}

	public void addMercenary( EntityMercenaryOld merc ){
		activeMercenaries.add(merc);
	}

	public void removeMercenary( EntityMercenaryOld merc ){
		activeMercenaries.remove(merc);
	}

	/**
	 * Returns this guild relationship rating towards given guild.
	 * @param gld
	 * @return rating, on the scale from -1 (sworn enemies) to +1 (same guild). Less than 0 is considered enemy and will attack on sight
	 */
	public float getRelation( Guild gld ){
		if(this==gld)
			return 1;
		float relation = 0;
		if( diplomaticRelation.containsKey(gld) )
			relation = diplomaticRelation.get(gld);
		else
			relation = defaultRelation;
		if( temporaryRelation.containsKey(gld) )
			relation += temporaryRelation.get(gld);
		return relation;
	}

	public void tempDrop( Guild gld, float magnitude ){
		if( temporaryRelation.containsKey(gld ))
			temporaryRelation.put( gld, magnitude + temporaryRelation.get(gld) );
		else
			temporaryRelation.put( gld, magnitude );
	}

}
