package mort.mercenaries.common.rooms;

import com.google.common.collect.Multimap;
import mort.mercenaries.block.IRoomBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * Represents a physical room in the game world
 */
public class Room {

	public final World world;
	public final BlockPos pos;
	public final BlockPos[] interiorBlocks;
	public final BlockPos[] boundaryBlocks;

	public Room(World world, BlockPos pos, BlockPos[] interiorBlocks, BlockPos[] boundaryBlocks) {
		this.world = world;
		this.pos = pos;
		this.interiorBlocks = interiorBlocks;
		this.boundaryBlocks = boundaryBlocks;
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

	public void DebugDescription()
	{
		System.out.println( "Room:" );
		System.out.println( String.format("Volume: %d", interiorBlocks.length) );
		System.out.println( String.format("Surface: %d", boundaryBlocks.length) );
		Map<Block,Integer> blockCounts = new HashMap<>();
		for(BlockPos blockPos : boundaryBlocks)
		{
			Block blk = world.getBlockState(blockPos).getBlock();
			if(!blockCounts.containsKey(blk))
				blockCounts.put(blk, 1);
			else
				blockCounts.put( blk, blockCounts.get(blk) + 1 );
		}
		for(Map.Entry<Block,Integer> entry : blockCounts.entrySet())
		{
			System.out.println( String.format("# %s: %d", entry.getKey(), entry.getValue()) );
		}
	}

	public static Room TryCreateRoom(BlockState sourceBlock, BlockPos sourcePos, World world)
	{
		if( !(sourceBlock.getBlock() instanceof IRoomBlock) )
			return null;

		IRoomBlock roomBlock = (IRoomBlock)sourceBlock.getBlock();
		BlockPos startingPos = sourcePos.offset(roomBlock.GetDefaultDirection());
		if(!world.isAirBlock(startingPos))
			return null;

		ArrayList<BlockPos> insideBlock = new ArrayList<>();
		ArrayList<BlockPos> barrierBlocks = new ArrayList<>();
		Set<BlockPos> closed = new HashSet<>();

		Queue<BlockPos> open = new LinkedList<>();
		open.add(startingPos);
		closed.add(sourcePos);

		final int cntLimit = 100;

		while(!open.isEmpty() && closed.size() < cntLimit)
		{
			BlockPos current = open.poll();
			closed.add(current);

			if(!world.isAirBlock(current))
			{
				barrierBlocks.add(current);
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

		if(open.isEmpty())
		{
			return new Room( world, sourcePos, insideBlock.toArray(new BlockPos[0]), barrierBlocks.toArray(new BlockPos[0]) );
		}

		return null;
	}

}
