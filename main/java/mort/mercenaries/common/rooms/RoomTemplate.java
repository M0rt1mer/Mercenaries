package mort.mercenaries.common.rooms;

import mort.mercenaries.Content;
import mort.mercenaries.common.rooms.furniture.FurnitureRole;
import mort.mercenaries.common.rooms.furniture.IFurniture;
import net.minecraft.block.Block;
import net.minecraft.util.INameable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;

public class RoomTemplate extends ForgeRegistryEntry<RoomTemplate> {

	public final Block coreBlock;
	public final List<Furnishing> furnishing;

	public RoomTemplate( Block coreBlock, RegistryObject<FurnitureRole>[] required, RegistryObject<FurnitureRole>[] optional, RegistryObject<FurnitureRole>[] counted )
	{
		this.coreBlock = coreBlock;
		furnishing = new ArrayList<>();
		for(RegistryObject<FurnitureRole> role : required)
			furnishing.add(new Furnishing(Requirement.REQUIRED, role));
		for(RegistryObject<FurnitureRole> role : optional)
			furnishing.add(new Furnishing(Requirement.OPTIONAL, role));
		for(RegistryObject<FurnitureRole> role : counted)
			furnishing.add(new Furnishing(Requirement.COUNTED, role));
	}

	public void addRelevantRoles(Set<FurnitureRole> relevantRoles)
	{
		for(Furnishing furnishing : this.furnishing )
		{
			relevantRoles.add(furnishing.furniture.get());
		}
	}

	public ITextComponent getName()
	{
		return new TranslationTextComponent( "room." + getRegistryName() + ".name" );
	}

	public static Set<FurnitureRole> getAllFurnitureRoles(Block coreBlock)
	{
		Set<FurnitureRole> relevantRoles = new HashSet<>();
		for(Map.Entry<ResourceLocation,RoomTemplate> entry : Content.roomRegistry.getEntries() )
		{
			if(entry.getValue().coreBlock == coreBlock)
				entry.getValue().addRelevantRoles(relevantRoles);
		}
		return relevantRoles;
	}

	public boolean checkTemplateIsFulfilled( List<FurnitureRole> role )
	{
		for( Furnishing furnishing : furnishing )
		{
			if(furnishing.req != Requirement.REQUIRED)
				continue;
			int idx = role.indexOf(furnishing.furniture.get());
			if(idx == -1)
				return false;
			role.remove(idx);
		}
		return true;
	}

	public static class Furnishing{
		public final Requirement req;
		public final RegistryObject<FurnitureRole> furniture;
		public Furnishing(Requirement req, RegistryObject<FurnitureRole> furniture) {
			this.req = req;
			this.furniture = furniture;
		}
	}

	public static enum Requirement{
		REQUIRED, OPTIONAL, COUNTED
	}
	
}
