package palaster.libpal.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.api.IReceiveButton;
import palaster.libpal.network.AbstractMessage.AbstractServerMessage;

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
			if(player.getHeldItem(h) != null)
				if(player.getHeldItem(h).getItem() instanceof IReceiveButton)
					((IReceiveButton) player.getHeldItem(h).getItem()).receiveButtonEvent(id, player);
		}
	}
}