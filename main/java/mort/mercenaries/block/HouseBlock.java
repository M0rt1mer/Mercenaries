package mort.mercenaries.block;

import mort.mercenaries.tileentity.HomeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class HouseBlock extends Block implements IRoomBlock {

    public HouseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HomeTileEntity();
    }

    @Override
    public Direction GetDefaultDirection() {
        return Direction.UP;
    }


}
