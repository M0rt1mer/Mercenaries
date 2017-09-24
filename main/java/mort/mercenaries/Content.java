package mort.mercenaries;

import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.common.MercenaryNameRegistry;
import mort.mercenaries.common.rooms.IFurniture;
import mort.mercenaries.common.rooms.RoomTemplate;
import mort.mercenaries.newAI.executor.AiActionTemplate;
import mort.mercenaries.newAI.executor.actions.WorldPlaceAction;
import mort.mercenaries.common.rooms.SimpleFurniture;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public abstract class Content {

	//public static TextureMap textureMap;
    @GameRegistry.ObjectHolder( "money" )
	public static Item itemMoney;


	public static IForgeRegistry<RoomTemplate> roomRegistry;
	public static IForgeRegistry<AiActionTemplate> actionRegistry;

	//@GameRegistry.ObjectHolder( "chair" )
    public static IFurniture furnChair;
    //@GameRegistry.ObjectHolder( "bed" )
    public static IFurniture furnBed;
    @GameRegistry.ObjectHolder( "roomHouse" )
    public static RoomTemplate house;

    public static MercenaryNameRegistry nameRegistry;

	@SubscribeEvent
	public static void event_roomTemplate(RegistryEvent.NewRegistry event ){
		roomRegistry = new RegistryBuilder<RoomTemplate>().setType(RoomTemplate.class).setName( new ResourceLocation(Reference.modId,"rooms") ).create();
        actionRegistry = new RegistryBuilder<AiActionTemplate>().setType(AiActionTemplate.class).setName( new ResourceLocation(Reference.modId,"aiActions") ).create();
        nameRegistry = new MercenaryNameRegistry();
	}


	@SubscribeEvent
	public static void event_createBlocks( RegistryEvent.Register<Block> events ){}

    @SubscribeEvent
    public static void event_createItems( RegistryEvent.Register<Item> events ){
        IForgeRegistry<Item> registry = events.getRegistry();
        createItem( registry, new Item(), "goldCoin" );
    }

    private static void createItem( IForgeRegistry<Item> registry, Item item, String name ){
        item.setRegistryName( new ResourceLocation( Reference.modId, name ) ).setUnlocalizedName(name).setCreativeTab( CreativeTabs.MISC );
    }

    public static void event_createRooms( RegistryEvent.Register<RoomTemplate> rooms ){


        furnChair = new SimpleFurniture(new Block[]{Block.getBlockFromName("oak_stairs")}, "chair");
        furnBed = new SimpleFurniture(new Block[]{Block.getBlockFromName("bed")}, "bed");
        //house
        List<RoomTemplate.Furnishing> furn = new LinkedList<RoomTemplate.Furnishing>();
        furn.add( new RoomTemplate.Furnishing(RoomTemplate.Requirement.REQUIRED, furnBed) );
        furn.add( new RoomTemplate.Furnishing(RoomTemplate.Requirement.REQUIRED, furnChair));
        house = new RoomTemplate(furn);
    }

    public static void event_createAiActions(RegistryEvent.Register<AiActionTemplate> event ){
        IForgeRegistry<AiActionTemplate> registry = event.getRegistry();
        registry.register( new WorldPlaceAction().setRegistryName("place_block") );
    }

    public static void preInit(){}

    public static void init(){
        EntityRegistry.registerModEntity( new ResourceLocation(Reference.modId, "mercenary"), EntityMercenary.class, "Mercenary", 1, Mercenaries.merc, 8, 2, true, 0, 0 );
    }

    public static void postInit(){}

}
