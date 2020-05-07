package mort.mercenaries;

import mort.mercenaries.block.HouseBlock;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.common.MercenaryNameRegistry;
import mort.mercenaries.common.rooms.RoomTemplate;
import mort.mercenaries.common.rooms.furniture.FurnitureConfiguration;
import mort.mercenaries.common.rooms.furniture.FurnitureRole;
import mort.mercenaries.common.rooms.furniture.SingleBlockConfiguration;
import mort.mercenaries.common.rooms.furniture.SingleBlockWithProperty;
import mort.mercenaries.inventory.ContainerMercenary;
import mort.mercenaries.newAI.executor.AiActionTemplate;
import mort.mercenaries.newAI.executor.actions.WorldPlaceAction;
import mort.mercenaries.tileentity.HomeTileEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.*;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class Content {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Reference.modId);
    public static final RegistryObject<Block> houseBlock = BLOCKS.register("house", () -> new HouseBlock( Block.Properties.create(Material.WOOD) ));

	//public static TextureMap textureMap;
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Reference.modId);
	public static final RegistryObject<Item> itemMoney = ITEMS.register( "money", () -> new Item( new Item.Properties() ) );
	public static final RegistryObject<Item> houseBlockItem = ITEMS.register( "house", () -> new BlockItem( houseBlock.get(), new Item.Properties() ) );

	public static IForgeRegistry<AiActionTemplate> actionRegistry;



	public static IForgeRegistry<FurnitureRole> furnitureRoles;
	public static DeferredRegister<FurnitureRole> FURNITURE_ROLES;
    public static RegistryObject<FurnitureRole> furnChair;
    public static RegistryObject<FurnitureRole> furnBed;

	public static IForgeRegistry<FurnitureConfiguration> furnitureConfigurations;
	public static DeferredRegister<FurnitureConfiguration> FURNITURE_CONFIGURATIONS;
    public static RegistryObject<FurnitureConfiguration> fcChairStairs;
    public static RegistryObject<FurnitureConfiguration> fcBedHead;

	public static IForgeRegistry<RoomTemplate> roomRegistry;
    public static DeferredRegister<RoomTemplate> ROOMS;
    public static RegistryObject<RoomTemplate> house;

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Reference.modId);
    public static RegistryObject<ContainerType<ContainerMercenary>> mercContainerType = CONTAINERS.register("merc_container", () ->
                                    new ContainerType<>((IContainerFactory<ContainerMercenary>)ContainerMercenary::new) );

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
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        furnitureRoles = new RegistryBuilder<FurnitureRole>()
                .setType(FurnitureRole.class)
                .setName(new ResourceLocation(Reference.modId, "furniture_roles"))
                .create();
        furnitureConfigurations = new RegistryBuilder<FurnitureConfiguration>()
                .setType(FurnitureConfiguration.class)
                .setName(new ResourceLocation(Reference.modId, "furniture_configurations"))
                .add( (IForgeRegistry.BakeCallback<FurnitureConfiguration>) FurnitureConfiguration::BakeConfigurations )
                .create();
        roomRegistry = new RegistryBuilder<RoomTemplate>()
                .setType(RoomTemplate.class)
                .setName(new ResourceLocation(Reference.modId, "rooms"))
                .create();
        actionRegistry = new RegistryBuilder<AiActionTemplate>()
                .setType(AiActionTemplate.class)
                .setName(new ResourceLocation(Reference.modId, "ai_actions"))
                .create();
        nameRegistry = new MercenaryNameRegistry();

        FURNITURE_ROLES = new DeferredRegister<>(furnitureRoles, Reference.modId);
        FURNITURE_ROLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        furnChair = FURNITURE_ROLES.register( "chair", FurnitureRole::new);
        furnBed = FURNITURE_ROLES.register( "bed", FurnitureRole::new);

        FURNITURE_CONFIGURATIONS = new DeferredRegister<>(furnitureConfigurations, Reference.modId);
        FURNITURE_CONFIGURATIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        fcChairStairs = FURNITURE_CONFIGURATIONS.register("chair", () -> new SingleBlockConfiguration(furnChair, Blocks.OAK_STAIRS));
        fcChairStairs = FURNITURE_CONFIGURATIONS.register("bed", () -> new SingleBlockWithProperty<>(furnBed, Blocks.RED_BED, BedBlock.PART, BedPart.HEAD));

        ROOMS = new DeferredRegister<>(roomRegistry, Reference.modId);
        ROOMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        house = ROOMS.register("house", () -> new RoomTemplate( houseBlock.get(), new RegistryObject[]{furnBed, furnChair}, new RegistryObject[0], new RegistryObject[0] ) );



    }


    public static class Registration {

        @SubscribeEvent
        public static void event_createAiActions(RegistryEvent.Register<AiActionTemplate> event) {
            IForgeRegistry<AiActionTemplate> registry = event.getRegistry();
            registry.register(new WorldPlaceAction().setRegistryName("place_block"));
        }
    }
}
