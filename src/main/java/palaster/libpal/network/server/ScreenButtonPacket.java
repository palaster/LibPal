package palaster.libpal.network.server;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import palaster.libpal.api.IReceiveButton;

public class ScreenButtonPacket {

	private final boolean hand;
	private final int id;

    public ScreenButtonPacket(boolean hand, int id) {
    	this.hand = hand;
    	this.id = id;
    }

	public void encode(PacketBuffer buffer) {
		buffer.writeBoolean(hand);
		buffer.writeInt(id);
	}

	public static ScreenButtonPacket decode(PacketBuffer buffer) { return new ScreenButtonPacket(buffer.readBoolean(), buffer.readInt()); }

	public static void handle(ScreenButtonPacket pkt, Supplier<NetworkEvent.Context> ctx) {
		if(ctx.get().getDirection().getReceptionSide().isServer()) {
			ctx.get().enqueueWork(() -> {
				Hand hand = pkt.hand ? Hand.MAIN_HAND : Hand.OFF_HAND;
				if(!ctx.get().getSender().getItemInHand(hand).isEmpty()) {
					if(ctx.get().getSender().getItemInHand(hand).getItem() instanceof IReceiveButton)
						((IReceiveButton) ctx.get().getSender().getItemInHand(hand).getItem()).receiveButtonEvent(pkt.id, ctx.get().getSender());
				}
			});
		}
		ctx.get().setPacketHandled(true);
	}
}
