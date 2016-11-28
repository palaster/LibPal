package palaster.libpal.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.libpal.network.PacketHandler;

public class CommonProxy {
	
	public void preInit() { PacketHandler.registerPackets(); }
	
	public void init() {}
	
	public void postInit() {}

	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().playerEntity; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().playerEntity.getServerWorld(); }
}
