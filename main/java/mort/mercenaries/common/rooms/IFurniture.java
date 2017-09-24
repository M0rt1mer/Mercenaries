package mort.mercenaries.common.rooms;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFurniture {
	
	public boolean checkBlock(World wld, BlockPos pos);

	public String getDescription();
	
	public String getName();
}
