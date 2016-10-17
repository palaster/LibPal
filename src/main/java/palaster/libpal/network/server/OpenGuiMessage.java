package palaster.libpal.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.network.AbstractMessage.AbstractServerMessage;

public class OpenGuiMessage extends AbstractServerMessage<OpenGuiMessage> {

	private Object o;
	private int id;
	private BlockPos pos;
	
	public OpenGuiMessage() {}
	
	public OpenGuiMessage(Object o, int id, BlockPos pos) {
		this.o = o;
		this.id = id;
		this.pos = pos;
	}
	
	@Override
	protected void read(PacketBuffer buffer) {
		int stringLength = buffer.readInt();
		if(stringLength > 0)
			try {
				o = Class.forName(buffer.readStringFromBuffer(stringLength)).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		id = buffer.readInt();
		pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) {
		buffer.writeInt(o.getClass().getName().length());
		buffer.writeInt(id);
		if(pos != null)
			buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) { player.openGui(o, this.id, player.worldObj, pos.getX(), pos.getY(), pos.getZ()); }
}
