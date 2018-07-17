package palaster.libpal.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.LibPal;

public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler<T, IMessage> {

	protected abstract void read(PacketBuffer buffer) throws IOException;
	
	protected abstract void write(PacketBuffer buffer) throws IOException;

	protected abstract void process(EntityPlayer player, Side side);

	protected boolean isValidOnSide(Side side) { return true; }

	protected boolean requiresMainThread() { return true; }

	@Override
	public void fromBytes(ByteBuf buffer) {
		try {
			read(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		try { 
			write(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final IMessage onMessage(T msg, MessageContext ctx) {
		if(!msg.isValidOnSide(ctx.side))
			throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
		else if(msg.requiresMainThread())
			checkThreadAndEnqueue(msg, ctx);
		else
			msg.process(LibPal.proxy.getPlayerEntity(ctx), ctx.side);
		return null;
	}

	private static final <T extends AbstractMessage<T>> void checkThreadAndEnqueue(final AbstractMessage<T> msg, final MessageContext ctx) {
		LibPal.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
			@Override
			public void run() { msg.process(LibPal.proxy.getPlayerEntity(ctx), ctx.side); }
		});
	}

	public static abstract class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) { return side.isClient(); }
	}

	public static abstract class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) { return side.isServer(); }
	}
}