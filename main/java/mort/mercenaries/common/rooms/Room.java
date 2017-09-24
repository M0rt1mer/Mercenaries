package mort.mercenaries.common.rooms;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a physical room in the game world
 * @author Martin
 */
public class Room {

	public final World wld;
	public final int x;
	public final int y;
	public final int z;
	
	public final RoomTemplate template;
	public int[] furnitureCouters;
	public Multimap<IFurniture, PlacedFurniture> furnitures;
	
	/**
	 * Creates a room at given coordinates
	 * @param wld
	 * @param x
	 * @param y
	 * @param z
	 * @param template
	 */
	public Room(World wld, int x, int y, int z, RoomTemplate template) {
		super();
		this.wld = wld;
		this.x = x;
		this.y = y;
		this.z = z;
		this.template = template;
		furnitureCouters = new int[template.furnishing.size()];
	}
	
	public void checkWorldRoom(){
		checkWorldRoom(false);
	}
	
	/**
	 * Iterates over all blocks and checks template fulfillness
	 */
	public void checkWorldRoom(boolean listFurniture){
		Arrays.fill(furnitureCouters, 0);
		furnitures = LinkedHashMultimap.create(); 
		
		//DFS
		Queue<BlockPos> open = new LinkedList<BlockPos>();
		HashSet<BlockPos> closed = new HashSet<BlockPos>();
		open.add(new BlockPos(x,y,z));
		
		while(!open.isEmpty()){
			BlockPos active = open.poll();
			//expand
			if( wld.isAirBlock(active) ){ //air block - expand
				for(EnumFacing facing : EnumFacing.values()){
					BlockPos expanded = active.offset(facing);
					if( !closed.contains(expanded))
						open.add(expanded);
				}
			}
			//furnish
			else{
				int cnt = 0;
				for( RoomTemplate.Furnishing fur : template.furnishing ){
					if( fur.furniture.checkBlock(wld, active) ){
						furnitureCouters[cnt++]++;
						if(listFurniture)
							furnitures.put(fur.furniture, new PlacedFurniture(fur.furniture, active));
					}
					
				}
				
			}
			
		}
		
	}
	
	public boolean checkTemplate(){
		int cnt = 0;
		for( RoomTemplate.Furnishing fur : template.furnishing ){
			System.out.println("Found "+furnitureCouters[cnt]+" "+fur.furniture.getDescription() );
			if( furnitureCouters[cnt++] == 0 && fur.req == RoomTemplate.Requirement.REQUIRED)
				return false;
		}
		return true;
	}

	public static class PlacedFurniture{
		public final IFurniture role;
		public final BlockPos pos;
		public PlacedFurniture(IFurniture role, BlockPos pos) {
			super();
			this.role = role;
			this.pos = pos;
		}
	}
	
}
