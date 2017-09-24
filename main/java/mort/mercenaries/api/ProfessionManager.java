/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.api;

import mort.mercenaries.profession.Farmer;
import mort.mercenaries.profession.Guard;
import mort.mercenaries.profession.Lumberjack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Martin
 */
public class ProfessionManager {

	public static ProfessionManager instance = new ProfessionManager();

	public final List<MercenaryProfession> professions;
	public final MercenaryProfession noProf;

	private ProfessionManager(){
		professions = new LinkedList<MercenaryProfession>();
		professions.add( new Lumberjack(1) );
		professions.add( new Farmer(2) );
		professions.add( new Guard(3) );
		noProf = new MercenaryProfession( 0, "empty", "profNo" );
	};

	public MercenaryProfession findProfession( ItemStack stk ){
		if(stk==null)
			return noProf;
		for( MercenaryProfession prof:professions ){
			if( prof.matchesItem(stk) )
				return prof;
		}
		return noProf;
	}

	public void texturePreStitch( TextureStitchEvent.Pre evnt ){
		for( MercenaryProfession prof : professions )
			prof.registerIcon( evnt.getMap() );
		noProf.registerIcon( evnt.getMap() );
	}

}
