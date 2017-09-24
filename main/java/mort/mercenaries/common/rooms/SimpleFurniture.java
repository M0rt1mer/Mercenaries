package mort.mercenaries.common.rooms;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class SimpleFurniture implements IFurniture {

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
	@SideOnly(Side.CLIENT)
	public String getDescription() {
		return I18n.translateToLocal("furniture." + idName + ".desc");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return  I18n.translateToLocal( "furniture."+idName+".name" );
	}
	
	

}
