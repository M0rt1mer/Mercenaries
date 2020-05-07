package mort.mercenaries.common.rooms.furniture;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class SingleBlockConfiguration extends FurnitureConfiguration{

    Block block;

    public SingleBlockConfiguration(RegistryObject<FurnitureRole> role, Block block) {
        super(role);
        this.block = block;
    }

    @Override
    public boolean CheckIsInPosition(World world, BlockPos pos, BlockState state) {
        return state.getBlock() == block;
    }
}
