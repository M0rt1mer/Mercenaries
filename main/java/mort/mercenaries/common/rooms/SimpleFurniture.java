package mort.mercenaries.common.rooms;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleFurniture implements IFurniture {

	// it's list because of helper functions
	public List<Block> avaiableBlocks;
	public String idName;
	
	public SimpleFurniture( Block[] blocks, String name ){
		this.idName = name;
		avaiableBlocks = new ArrayList<Block>( Arrays.asList( blocks ) );
	}
	
	@Override
	public boolean checkBlock(World wld, BlockPos pos) {
		return avaiableBlocks.contains( wld.getBlockState(pos).getBlock() );
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getDescription() {
		return LanguageMap.getInstance().translateKey("furniture." + idName + ".desc");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return LanguageMap.getInstance().translateKey( "furniture."+idName+".name" );
	}
	
	

}
