package mort.mercenaries;

import com.sun.media.jfxmedia.logging.Logger;
import mort.mercenaries.block.HouseBlock;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.common.MercenaryNameRegistry;
import mort.mercenaries.common.rooms.IFurniture;
import mort.mercenaries.common.rooms.RoomTemplate;
import mort.mercenaries.common.rooms.SimpleFurniture;
import mort.mercenaries.inventory.ContainerMercenary;
import mort.mercenaries.newAI.executor.AiActionTemplate;
import mort.mercenaries.newAI.executor.actions.WorldPlaceAction;
import mort.mercenaries.tileentity.HomeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.LinkedList;
import java.util.List;

public abstract class Content {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Reference.modId);
    public static final RegistryObject<Block> houseBlock = BLOCKS.register("house", () -> new HouseBlock( Block.Properties.create(Material.WOOD) ));

	//public static TextureMap textureMap;
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Reference.modId);
	public static final RegistryObject<Item> itemMoney = ITEMS.register( "money", () -> new Item( new Item.Properties() ) );
	public static final RegistryObject<Item> houseBlockItem = ITEMS.register( "house", () -> new BlockItem( houseBlock.get(), new Item.Properties() ) );

	public static IForgeRegistry<RoomTemplate> roomRegistry;
	public static IForgeRegistry<AiActionTemplate> actionRegistry;

    public static IFurniture furnChair;
    public static IFurniture furnBed;
    public static RoomTemplate house;

    public static ContainerType<ContainerMercenary> mercContainerType;

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Reference.modId);
    public static final RegistryObject<TileEntityType<HomeTileEntity>> homeTileEntity = TILE_ENTITY.register("home", () -> TileEntityType.Builder.create(HomeTileEntity::new, houseBlock.get()).build(null));

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Reference.modId);
    public static final RegistryObject<EntityType<EntityMercenary>> mercEntityType = ENTITIES.register( "money", () ->
                                    EntityType.Builder.create(EntityMercenary::new, EntityClassification.MISC)
                                    .size(0.6F, 1.95F)
                                    .build("mercenary") );

    public static MercenaryNameRegistry nameRegistry;

    //called from mod setup
    static void RegisterAllDeferred()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(modid = Reference.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void event_roomTemplate(RegistryEvent.NewRegistry event) {
            roomRegistry = new RegistryBuilder<RoomTemplate>()
                    .setType(RoomTemplate.class)
                    .setName(new ResourceLocation(Reference.modId, "rooms"))
                    .create();
            actionRegistry = new RegistryBuilder<AiActionTemplate>()
                    .setType(AiActionTemplate.class)
                    .setName(new ResourceLocation(Reference.modId, "ai_actions"))
                    .create();
            nameRegistry = new MercenaryNameRegistry();
        }

        @SubscribeEvent
        public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
            mercContainerType = new ContainerType( (IContainerFactory<ContainerMercenary>)ContainerMercenary::new );
            mercContainerType.setRegistryName( "merc_container" );
            event.getRegistry().registerAll(mercContainerType);
        }

        @SubscribeEvent
        public static void event_createRooms(RegistryEvent.Register<RoomTemplate> rooms) {
            furnChair = new SimpleFurniture(new Block[]{Blocks.OAK_STAIRS}, "chair");
            furnBed = new SimpleFurniture(new Block[]{Blocks.RED_BED}, "bed");
            //house
            List<RoomTemplate.Furnishing> furn = new LinkedList<RoomTemplate.Furnishing>();
            furn.add(new RoomTemplate.Furnishing(RoomTemplate.Requirement.REQUIRED, furnBed));
            furn.add(new RoomTemplate.Furnishing(RoomTemplate.Requirement.REQUIRED, furnChair));
            house = new RoomTemplate(furn);
        }

        @SubscribeEvent
        public static void event_createAiActions(RegistryEvent.Register<AiActionTemplate> event) {
            IForgeRegistry<AiActionTemplate> registry = event.getRegistry();
            registry.register(new WorldPlaceAction().setRegistryName("place_block"));
        }
    }
}
