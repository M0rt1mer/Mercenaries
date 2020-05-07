package mort.mercenaries.common.rooms;

import mort.mercenaries.Content;
import mort.mercenaries.block.IRoomBlock;
import mort.mercenaries.common.rooms.furniture.FurnitureConfiguration;
import mort.mercenaries.common.rooms.furniture.FurnitureRole;
import mort.mercenaries.tileentity.GenericRoomTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a physical room in the game world
 */
public class Room {

	public final World world;
	public final BlockPos pos;
	public final BlockPos[] interiorBlocks;
	public final BlockPos[] boundaryBlocks;
	public final PlacedFurniture[] furnitures;
	public RoomTemplate roomRole;

	public Room(World world, BlockPos pos, BlockPos[] interiorBlocks, BlockPos[] boundaryBlocks) {
		this.world = world;
		this.pos = pos;
		this.interiorBlocks = interiorBlocks;
		this.boundaryBlocks = boundaryBlocks;
		this.furnitures = FindAllFurnitures();
		this.roomRole = findRoomTemplate();
		RoomManager.RegisterRoom(this);
	}

	public void Destroy()
	{
		RoomManager.UnregisterRoom(this);
	}

	public static class PlacedFurniture{
		public final FurnitureRole role;
		public final BlockPos pos;
		public PlacedFurniture(FurnitureRole role, BlockPos pos) {
			super();
			this.role = role;
			this.pos = pos;
		}
	}

	public void DebugDescription()
	{
		System.out.println( String.format("Room [%s]:", roomRole != null ? roomRole.getName().getFormattedText() : "no room") );
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
			System.out.println(String.format("# %s: %d", entry.getKey(), entry.getValue()));
		for( PlacedFurniture furn : furnitures )
			System.out.println(String.format("[%s]: %s", furn.role.getName().getFormattedText(), furn.pos.toString()));
	}

	public RoomTemplate findRoomTemplate()
	{
		Block coreBlock = world.getBlockState(pos).getBlock();
		for(Map.Entry<ResourceLocation,RoomTemplate> templates : Content.roomRegistry.getEntries())
		{
			if(templates.getValue().coreBlock == coreBlock)
			{
				List<FurnitureRole> furnitures = Arrays.stream(this.furnitures).map( (placed) -> placed.role ).collect(Collectors.toList());
				if(templates.getValue().checkTemplateIsFulfilled(furnitures))
					return templates.getValue();
			}

		}
		return null;
	}

	public PlacedFurniture[] FindAllFurnitures()
	{
		ArrayList<PlacedFurniture> furnitures = new ArrayList<>();
		BlockState coreBlock = world.getBlockState(pos);
		List<FurnitureConfiguration> relevantFurniture = FurnitureConfiguration.getAllConfigurations( RoomTemplate.getAllFurnitureRoles(coreBlock.getBlock()) );

		interiorBlockLoop:
		for( BlockPos block : interiorBlocks )
		{
			BlockState state = world.getBlockState(block);
			for(FurnitureConfiguration configuration : relevantFurniture)
				if (configuration.CheckIsInPosition(world, block, state)) {
					furnitures.add(new PlacedFurniture(configuration.role.get(), block));
					continue interiorBlockLoop;
				}
		}

		boundaryBlockLoop:
		for( BlockPos block : boundaryBlocks )
		{
			BlockState state = world.getBlockState(block);
			for(FurnitureConfiguration configuration : relevantFurniture)
				if (configuration.CheckIsInPosition(world, block, state)) {
					furnitures.add(new PlacedFurniture(configuration.role.get(), block));
					continue boundaryBlockLoop;
				}
		}

		return furnitures.toArray(new PlacedFurniture[0]);
	}

	public static Room TryCreateRoom(BlockState sourceBlock, BlockPos sourcePos, World world)
	{
		if( !(sourceBlock.getBlock() instanceof IRoomBlock) )
			return null;

		IRoomBlock roomBlock = (IRoomBlock)sourceBlock.getBlock();
		BlockPos startingPos = sourcePos.offset(roomBlock.GetDefaultDirection());
		if(doesBlockMovement(world, startingPos))
			return null;

		ArrayList<BlockPos> insideBlock = new ArrayList<>();
		ArrayList<BlockPos> barrierBlocks = new ArrayList<>();
		Set<BlockPos> discovered = new HashSet<>();

		Queue<BlockPos> open = new LinkedList<>();
		open.add(startingPos);
		discovered.add(sourcePos);

		final int cntLimit = 500;

		while(!open.isEmpty() && discovered.size() < cntLimit)
		{
			BlockPos current = open.poll();

			if(doesBlockMovement(world, current))
			{
				barrierBlocks.add(current);
				continue;
			}

			insideBlock.add(current);
			for(Direction dir : Direction.values())
			{
				BlockPos newPos = current.offset(dir);
				if(!discovered.contains(newPos))
				{
					discovered.add(newPos);
					open.add(newPos);
				}
			}

		}

		if(open.isEmpty())
		{
			return new Room( world, sourcePos, insideBlock.toArray(new BlockPos[0]), barrierBlocks.toArray(new BlockPos[0]) );
		}

		return null;
	}

	public static Room TryCreateRoomFromBreak(Room previousRoom, BlockPos brokenBlock)
	{
		//todo: optimize
		return TryCreateRoom( previousRoom.world.getBlockState(previousRoom.pos), previousRoom.pos, previousRoom.world );
	}

	public static Room TryCreateRoomFromPlacement(Room previousRoom, BlockPos placedBlock)
	{
		//todo: optimize
		return TryCreateRoom( previousRoom.world.getBlockState(previousRoom.pos), previousRoom.pos, previousRoom.world );
	}

	public GenericRoomTileEntity GetTileEntity()
	{
		TileEntity te = world.getTileEntity(pos);
		assert te instanceof GenericRoomTileEntity;
		return (GenericRoomTileEntity) te;
	}

	private static boolean doesBlockMovement(World world, BlockPos current) {
		// block that blocks movement in it's default state
		return !world.getBlockState(current).getBlock().getDefaultState().allowsMovement(world,current, PathType.AIR);
	}


}
