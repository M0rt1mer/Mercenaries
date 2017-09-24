package mort.mercenaries.common.rooms;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomTemplate extends IForgeRegistryEntry.Impl<RoomTemplate> {
	
	public List<Furnishing> furnishing;
	
	public RoomTemplate(List<Furnishing> furnishing) {
		this.furnishing = new ArrayList<Furnishing>(furnishing);
	}
	
	public RoomTemplate(Furnishing[] furni){
		this.furnishing = new ArrayList<Furnishing>( Arrays.asList(furni) );
	}

	public static class Furnishing{
		public final Requirement req;
		public final IFurniture furniture;
		public Furnishing(Requirement req, IFurniture furniture) {
			this.req = req;
			this.furniture = furniture;
		}
		
	}

	public static enum Requirement{
		REQUIRED, OPTIONAL, COUNTED
	}
	
}
