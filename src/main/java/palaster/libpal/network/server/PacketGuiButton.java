package palaster.libpal.network.server;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public final class PacketGuiButton {
	public static void encode(PacketGuiButton msg, PacketBuffer buffer) { }
	
	public static PacketGuiButton decode(PacketBuffer buffer) { return new PacketGuiButton(); }
	
	public void handle(Supplier<NetworkEvent.Context> ctx) { ctx.get().setPacketHandled(true); }
}

/* TODO: Prior to 1.16.5
public class GuiButtonMessage extends AbstractServerMessage<GuiButtonMessage> {
	
	private int hand = -1;
	private BlockPos pos = null;
	private int id = -1;
	
	public GuiButtonMessage() {}
	
	public GuiButtonMessage(int hand, BlockPos pos, int id) {
		this.hand = hand;
		this.pos = pos;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		hand = buffer.readInt();
		pos = buffer.readBlockPos();
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(hand);
		if(pos != null)
			buffer.writeBlockPos(pos);
		buffer.writeInt(id);
	}

	@Override
	protected void process(EntityPlayer player, Side side) {
		if(hand != -1) {
			EnumHand h = hand == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND ;
			if(!player.getHeldItem(h).isEmpty())
				if(player.getHeldItem(h).getItem() instanceof IReceiveButton)
					((IReceiveButton) player.getHeldItem(h).getItem()).receiveButtonEvent(id, player);
		}
	}
}
*/