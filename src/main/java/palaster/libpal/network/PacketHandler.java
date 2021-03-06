package palaster.libpal.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import palaster.libpal.libs.LibMod;
import palaster.libpal.network.server.GuiButtonMessage;
import palaster.libpal.network.server.OpenGuiMessage;

public class PacketHandler {
	
	private static byte packetId = 0;
	private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMod.MODID);
	
	public static final void registerPackets() {
		registerMessage(OpenGuiMessage.class);
		registerMessage(GuiButtonMessage.class);
	}
	
	public static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> clazz) {
		if(AbstractMessage.AbstractClientMessage.class.isAssignableFrom(clazz))
			INSTANCE.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
		else if(AbstractMessage.AbstractServerMessage.class.isAssignableFrom(clazz))
			INSTANCE.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		else {
			INSTANCE.registerMessage(clazz, clazz, packetId, Side.CLIENT);
			INSTANCE.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		}
	}

	public static final void sendTo(IMessage message, EntityPlayerMP player) { INSTANCE.sendTo(message, player); }

	public static void sendToAll(IMessage message) { INSTANCE.sendToAll(message); }

	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) { INSTANCE.sendToAllAround(message, point); }

	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) { PacketHandler.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range)); }

	public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) { PacketHandler.sendToAllAround(message, player.world.provider.getDimension(), player.posX, player.posY, player.posZ, range); }

	public static final void sendToDimension(IMessage message, int dimensionId) { INSTANCE.sendToDimension(message, dimensionId); }

	public static final void sendToServer(IMessage message) { INSTANCE.sendToServer(message); }
}