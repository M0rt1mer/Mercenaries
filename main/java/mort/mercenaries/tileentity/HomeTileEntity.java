package mort.mercenaries.tileentity;

import mort.mercenaries.Content;
import net.minecraft.tileentity.TileEntityType;

public class HomeTileEntity extends GenericRoomTileEntity {

    public HomeTileEntity() {
        super(Content.homeTileEntity.get());
        System.out.println("HOME TILE ENTITY");
    }



}