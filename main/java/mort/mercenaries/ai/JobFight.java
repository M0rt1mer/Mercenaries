/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.ai;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import mort.mercenaries.EntityMercenaryOld;
import mort.mercenaries.ai.combat.CombatManeuver;
import mort.mercenaries.ai.combat.ManeuverAttackClose;
import mort.mercenaries.ai.combat.ManeuverCloseIn;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;

import java.util.LinkedList;
import java.util.TreeSet;

/**
 *
 * @author Martin
 */
public class JobFight extends MercenaryJob {

    //public static Set<CombatManeuver> maneuvers;
    public static Class [] maneuverPool;
    
    static{
    	maneuverPool = new Class[]{ ManeuverCloseIn.class, ManeuverAttackClose.class };
    }
    
    
    public CombatManeuver [] maneuvers;
	    
    public JobFight(MercenaryOrder order) {
		super(order);
		int i = 0;
		maneuvers = new CombatManeuver[ maneuverPool.length ];
		for( Class cls : maneuverPool ){
		    try{
			maneuvers[i] = (CombatManeuver)cls.getConstructor( JobFight.class )
				.newInstance( this );
			i++;
		    }catch(Exception e){ System.out.println("error creating" + e); }
		}
		nearbyFighters = new TreeSet<EntityLiving>( new EntitySorter(order.ai.merc) );
		nearbyEnemies = new LinkedList<EntityLiving>();
    }
    
    //time (ticks) to next area search. Used to ease off the CPU load in fights
    int searchCooldown;
    public TreeSet<EntityLiving> nearbyFighters;
    public LinkedList<EntityLiving> nearbyEnemies;
    //how often search for entities. Can be dynamically changed, if CPU load too high
    public static int searchTimeout = 40;
    public int attackCooldown = 0;
    
    
    @Override
    public void engage() {
	searchCooldown = 0;
    }

    @Override
    public float getPriority() {
	return nearbyEnemies.size() > 0 ? 10 : 0;
    }

    @Override
    public void update() {
		/*//int bowSlot = canShoot();
		int weaponSlot = findWeapon();
		if( weaponSlot > 0 )
		    this.order.ai.merc.inventory.activeTool = weaponSlot;
		float priority = 0;
		CombatManeuver maneuver = null;
		/*ArrayList lst = new ArrayList( nearbyFighters.size() );
		Iterables.addAll( lst, Iterables.filter( nearbyFighters, new EnemyFilter(order.ai.merc) ) );
		Collections.sort( lst, new EntitySorter(order.ai.merc) );
		for( CombatManeuver man : maneuvers ){
		    float p = man.getPriority( nearbyEnemies );
		    if( p > priority ){
			priority = p;
			maneuver = man;
		    }
		}
		if( maneuver != null )
		    maneuver.doMove( nearbyEnemies );
		//check attack
		if( order.ai.merc.attackTime <= 0 ){
		    //System.out.println( "Check attack" );
		    for( EntityLiving ent : nearbyFighters ){
				//System.out.println( "Check:" +ent );
				if( order.ai.merc.isInAttackSpace(ent) && order.ai.merc.isEnemy(ent)  ){
				    //.out.println( "Attack:" +ent );
				    order.ai.merc.attackTargetEntityWithCurrentItem(ent);
				    break;
				}
		    }
		}*/
    }

    @Override
    public void updateCache() {
		nearbyFighters.clear();
		nearbyEnemies.clear();
		for( Entity ent : order.nearbyEnt ){
		    if( ent instanceof EntityLiving && !(ent instanceof EntityAnimal) ){
			//System.out.println( "Found: "+ent );
			nearbyFighters.add( (EntityLiving)ent );
		    }
		}
		Iterables.addAll( nearbyEnemies, Iterables.filter( nearbyFighters, new EnemyFilter(order.ai.merc) ) );
    }
    
    /**
     * Checks whether mercenary can shoot. Returns slot which holds the bow
     * @return 
     */
    private int canShoot(){
		/*int bowSlot = -1;
		for( int i = 0; i<4;i++ ){
		    ItemStack stk = this.order.ai.merc.inventory.getStackInSlot(i);
		    if( stk!=null && stk.getItem() == Item.itemRegistry.getObject("bow") && stk.getItemDamage()<stk.getItem().getMaxDamage()-1 ){
			bowSlot = i;
		    }
		}
		if(bowSlot==-1)
		    return -1;
		for( int i = 5; i<16;i++ ){
		    ItemStack stk = this.order.ai.merc.inventory.getStackInSlot(i);
		    if( stk!=null && stk.getItem() == Item.itemRegistry.getObject("arrow") ){
			return bowSlot;
		    }
		}
		return -1;*/
		return -1;
    }
    
    private int findWeapon(){
		/*int slot = -1;
		float damage = 0;
		for( int i = 0; i<4;i++ ){
		    ItemStack stk = this.order.ai.merc.inventory.getStackInSlot(i);
		    //calc dmg
		    
		    if( stk != null ){
		    	float thisDmg = 0;
		    	for( AttributeModifier mod : (Iterable<AttributeModifier>)(stk.getAttributeModifiers().get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) )
		    		thisDmg += mod.getAmount();
		    	if( thisDmg>damage ){
			    	slot = i;
			    	damage = thisDmg;
			    }
		    }
		}
		return slot;*/
		return 0;
    }
    
    public class EnemyFilter implements Predicate{
		
		private EntityMercenaryOld owner;
	
		public EnemyFilter(EntityMercenaryOld owner) {
		    this.owner = owner;
		}
	
		@Override
		public boolean apply(Object t) {
		    return (t instanceof EntityLiving) && owner.isEnemy( (EntityLiving)t );
		}
		
    }
    
}
