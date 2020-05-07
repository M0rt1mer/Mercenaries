package mort.mercenaries.common.rooms.furniture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class SingleBlockWithProperty<T extends Comparable<T>> extends SingleBlockConfiguration {

    IProperty<T> property;
    T value;

    public SingleBlockWithProperty(RegistryObject<FurnitureRole> role, Block block, IProperty<T> property, T value) {
        super(role, block);
        this.property = property;
        this.value = value;
    }

    @Override
    public boolean CheckIsInPosition(World world, BlockPos pos, BlockState state) {
        return  super.CheckIsInPosition(world, pos, state) && value.compareTo( (T) state.getValues().get(property) ) == 0;
    }
}
