package palaster.libpal.network.server;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public final class PacketOpenGui {
	public static void encode(PacketOpenGui msg, PacketBuffer buffer) { }
	
	public static PacketOpenGui decode(PacketBuffer buffer) { return new PacketOpenGui(); }
	
	public void handle(Supplier<NetworkEvent.Context> ctx) { ctx.get().setPacketHandled(true); }
}

/* TODO: Prior to 1.16.5
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
*/
