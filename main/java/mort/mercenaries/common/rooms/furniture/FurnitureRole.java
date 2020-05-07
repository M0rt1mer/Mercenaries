package mort.mercenaries.common.rooms.furniture;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A role a piece of furniture can take (e.g. "chair")
 */
public class FurnitureRole extends ForgeRegistryEntry<FurnitureRole> {

	ArrayList<FurnitureConfiguration> configurations = new ArrayList<>();

	@OnlyIn(Dist.CLIENT)
	public ITextComponent getName() {
		return new TranslationTextComponent( String.format("furniture.%s.name", getRegistryName()) );
	}

	public void registerConfiguration(FurnitureConfiguration config)
	{
		assert config.role.get() == this;
		configurations.add(config);
	}

	public List<FurnitureConfiguration> getPossibleConfigurations()
	{
		return Collections.unmodifiableList(configurations);
	}

}
