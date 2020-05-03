package mort.mercenaries.block;

import mort.mercenaries.tileentity.GenericRoomTileEntity;
import mort.mercenaries.tileentity.HomeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

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

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {

        if(handIn != Hand.MAIN_HAND) //prevent double calls
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
        if(worldIn.isRemote)
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);

        TileEntity ent = worldIn.getTileEntity(pos);
        if(ent == null) {
            System.err.println("House block has no tile entity");
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
        }
        if(!(ent instanceof GenericRoomTileEntity))
        {
            System.err.println("House block has invalid tile entity");
            return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
        }
        GenericRoomTileEntity grte = (GenericRoomTileEntity)ent;

        if(!grte.HasValidRoom())
            grte.TryInitializeRoom();

        if(grte.HasValidRoom())
            grte.getRoom().DebugDescription();
        else
            System.out.println("No room could be found");

        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }
}
