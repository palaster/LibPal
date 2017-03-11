package palaster.libpal.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.network.AbstractMessage.AbstractServerMessage;

public class OpenGuiMessage extends AbstractServerMessage<OpenGuiMessage> {

	private String s;
	private int id;
	private BlockPos pos;
	
	public OpenGuiMessage() {}
	
	public OpenGuiMessage(String s, int id, BlockPos pos) {
		this.s = s;
		this.id = id;
		this.pos = pos;
	}
	
	@Override
	protected void read(PacketBuffer buffer) {
		int stringLength = buffer.readInt();
		if(stringLength > 0)
			s = buffer.readString(stringLength);
		id = buffer.readInt();
		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(s.length());
		buffer.writeString(s);
		buffer.writeInt(id);
		if(pos != null)
			buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) { player.openGui(s, this.id, player.world, pos.getX(), pos.getY(), pos.getZ()); }
}
