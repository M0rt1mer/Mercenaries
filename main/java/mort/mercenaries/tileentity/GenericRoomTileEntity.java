package mort.mercenaries.tileentity;

import mort.mercenaries.common.rooms.Room;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class GenericRoomTileEntity extends TileEntity {

    public GenericRoomTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    Room room;

    public boolean HasValidRoom() {
        return room != null;
    }

    public Room getRoom() {
        return room;
    }

    public void TryInitializeRoom()
    {
        assert !HasValidRoom() : "Trying to recreate a room where one exists.";
        this.room = Room.TryCreateRoom( this.getBlockState(), this.getPos(), this.getWorld() );
    }

    public void OnRoomBlockBroken(BlockPos pos)
    {
        if(room == null)
            return; //can be null if two break events happen at once (breaking a door, for example)
        room.Destroy();
        this.room = Room.TryCreateRoomFromBreak(room, pos);
        System.out.println("Wall broken");
    }

    public void OnInternalBlockReplaced(BlockPos pos)
    {
        assert room != null;
        room.Destroy();
        this.room = Room.TryCreateRoomFromPlacement(room, pos);
        System.out.println("Room filled");
    }


}
