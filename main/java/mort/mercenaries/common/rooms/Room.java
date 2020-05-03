package mort.mercenaries.common.rooms;

import com.google.common.collect.Multimap;
import mort.mercenaries.block.IRoomBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Represents a physical room in the game world
 */
public class Room {

	public final World world;
	public final BlockPos pos;

	public Room(World world, BlockPos pos) {
		this.world = world;
		this.pos = pos;
	}

	public int[] furnitureCouters;
	public Multimap<IFurniture, PlacedFurniture> furnitures;

	public static class PlacedFurniture{
		public final IFurniture role;
		public final BlockPos pos;
		public PlacedFurniture(IFurniture role, BlockPos pos) {
			super();
			this.role = role;
			this.pos = pos;
		}
	}

	public static Room TryCreateRoom(BlockState sourceBlock, BlockPos pos, World world)
	{
		if( !(sourceBlock.getBlock() instanceof IRoomBlock) )
			return null;

		IRoomBlock roomBlock = (IRoomBlock)sourceBlock.getBlock();
		BlockPos startingPos = pos.offset(roomBlock.GetDefaultDirection());
		if(!world.isAirBlock(startingPos))
			return null;

		ArrayList<BlockPos> insideBlock = new ArrayList<>();
		ArrayList<BlockPos> barrierBlocks = new ArrayList<>();
		Set<BlockPos> closed = new HashSet<>();

		Queue<BlockPos> open = new LinkedList<>();
		open.add(startingPos);
		closed.add(pos);

		int cnt = 0;
		final int cntLimit = 100;

		while(!open.isEmpty() && cnt < cntLimit)
		{
			BlockPos current = open.poll();
			closed.add(current);

			if(!world.isAirBlock(pos))
			{
				barrierBlocks.add(pos);
				continue;
			}
			insideBlock.add(current);

			for(Direction dir : Direction.values())
			{
				BlockPos newPos = current.offset(dir);
				if(!closed.contains(newPos))
					open.add(newPos);
			}
		}

		return null;
	}

}
