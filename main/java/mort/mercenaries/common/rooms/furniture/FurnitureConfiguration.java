package mort.mercenaries.common.rooms.furniture;

import mort.mercenaries.Content;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A "recipe" for furniture role - e.g. "fence with pressure plate -> table"
 */
public abstract class FurnitureConfiguration extends ForgeRegistryEntry<FurnitureConfiguration> {

    /**
     * A role this configuration provides
     */
    public final RegistryObject<FurnitureRole> role;

    public FurnitureConfiguration(RegistryObject<FurnitureRole> role) {
        this.role = role;
    }

    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName() {
        return new TranslationTextComponent( String.format("furniture_configuration.%s.description", getRegistryName()) );
    }

    public static List<FurnitureConfiguration> getAllConfigurations(Set<FurnitureRole> roles)
    {
        List<FurnitureConfiguration> list = new ArrayList<>();
        for( Map.Entry<ResourceLocation,FurnitureConfiguration> configuration : Content.furnitureConfigurations.getEntries())
            if (roles.contains(configuration.getValue().role.get()))
                list.add(configuration.getValue());
        return list;
    }

    public abstract boolean CheckIsInPosition(World world, BlockPos pos, BlockState state);

    public static void BakeConfigurations(IForgeRegistryInternal<FurnitureConfiguration> owner, RegistryManager stage)
    {
        for(Map.Entry<ResourceLocation,FurnitureConfiguration> configuration : Content.furnitureConfigurations.getEntries() )
        {
            configuration.getValue().role.get().registerConfiguration(configuration.getValue());
        }
    }
}
