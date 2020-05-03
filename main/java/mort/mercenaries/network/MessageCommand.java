package mort.mercenaries.network;

import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.Mercenaries;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageCommand {

	private final int merc;
	private final int command;

	public MessageCommand(EntityMercenary merc, int command){
    	this.merc = merc.getEntityId();
    	this.command = command;
    }

	public MessageCommand(PacketBuffer buf) {
		merc = buf.readInt();
		command = buf.readInt();
	}

	public void onMessage( Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork( () -> {
			PlayerEntity entityPlayer = ctx.get().getSender();
			EntityMercenary mercObj = (EntityMercenary) entityPlayer.getEntityWorld().getEntityByID(merc);
			Mercenaries.merc.issueCommand(mercObj, command, entityPlayer);
		});
	}

	public void toBytes(PacketBuffer buf) {
		buf.writeInt( merc );
		buf.writeInt( command );
	}

}
