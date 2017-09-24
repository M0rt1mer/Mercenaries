package mort.mercenaries.network;

import io.netty.buffer.ByteBuf;
import mort.mercenaries.common.EntityMercenary;
import mort.mercenaries.Mercenaries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author Martin
 */
public class MessageCommand implements IMessage, IMessageHandler<MessageCommand, IMessage> {

	
	private int merc;
	private int command;
	private int orderer;
	
	public MessageCommand(){} //empty contructor for MessageHandler instance
	
    public MessageCommand(EntityMercenary merc, int command, EntityPlayer orderer){
    	this.merc = merc.getEntityId();
    	this.command = command;
    	this.orderer = orderer.getEntityId();
    }

	@Override
	public IMessage onMessage(MessageCommand message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().player;
		EntityMercenary mercObj = (EntityMercenary) entityPlayer.getEntityWorld().getEntityByID(merc);
		Mercenaries.merc.issueCommand(mercObj, message.command, entityPlayer);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		merc = buf.readInt();
		command = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt( merc );
		buf.writeInt( command );
	}
    
}
