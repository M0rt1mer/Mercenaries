package mort.mercenaries.tileentity;

import mort.mercenaries.common.rooms.Room;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

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
        if(HasValidRoom())
        {
            System.err.println("Trying to recreate a room where one exists.");
        }
        this.room = Room.TryCreateRoom( this.getBlockState(), this.getPos(), this.getWorld() );
    }



}
