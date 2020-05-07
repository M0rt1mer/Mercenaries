package mort.mercenaries.common.rooms;


import mort.mercenaries.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;

@Mod.EventBusSubscriber( modid = Reference.modId )
public class RoomManager {

    private static ArrayList<Room> rooms = new ArrayList<>();

    public static void RegisterRoom(Room room)
    {
        rooms.add(room);
    }

    public static void UnregisterRoom(Room room)
    {
        rooms.remove(room);
    }

    @SubscribeEvent
    public static void OnBlockBroken(BlockEvent.BreakEvent event)
    {
        cachedBreakEvents.add(event);
    }

    public static void BlockBroken(BlockEvent.BreakEvent event)
    {
        ArrayList<Room> brokenRooms = new ArrayList<>();
        for(Room room : rooms )
        {
            if(room.world != event.getWorld())
                continue;
            for( BlockPos pos : room.boundaryBlocks )
                if (pos.equals(event.getPos()))
                    brokenRooms.add(room);
        }

        for(Room room : brokenRooms)
        {
            room.GetTileEntity().OnRoomBlockBroken(event.getPos());
        }
    }

    @SubscribeEvent
    public static void OnBlockPlaced(BlockEvent.EntityPlaceEvent event)
    {
        cachedPlaceEvents.add(event);
    }

    public static void BlockPlaced(BlockEvent.EntityPlaceEvent event)
    {
        ArrayList<Room> brokenRooms = new ArrayList<>();
        roomLoop:
        for(Room room : rooms )
        {
            if(room.world != event.getWorld())
                continue;
            for( BlockPos pos : room.interiorBlocks )
                if (pos.equals(event.getPos())) {
                    brokenRooms.add(room);
                    continue roomLoop;
                }

        }

        for(Room room : brokenRooms)
        {
            room.GetTileEntity().OnInternalBlockReplaced(event.getPos());
        }
    }

    static ArrayList<BlockEvent.BreakEvent> cachedBreakEvents = new ArrayList<>();
    static ArrayList<BlockEvent.EntityPlaceEvent> cachedPlaceEvents = new ArrayList<>();

    @SubscribeEvent
    public static void onTick(TickEvent event)
    {
        if(event.type != TickEvent.Type.SERVER)
            return;

        for(BlockEvent.BreakEvent blockEvent : cachedBreakEvents)
            BlockBroken(blockEvent);
        for(BlockEvent.EntityPlaceEvent blockEvent : cachedPlaceEvents)
            BlockPlaced(blockEvent);

        cachedPlaceEvents.clear();
        cachedBreakEvents.clear();
    }
}
