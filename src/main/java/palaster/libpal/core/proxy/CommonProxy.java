package palaster.libpal.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyCapabilityFactory;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyCapabilityStorage;
import palaster.libpal.api.capabilities.IEntropy;
import palaster.libpal.core.handlers.ConfigurationHandler;
import palaster.libpal.core.handlers.EventHandler;
import palaster.libpal.network.PacketHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		new ConfigurationHandler(new Configuration(e.getSuggestedConfigurationFile()));
		PacketHandler.registerPackets();
		CapabilityManager.INSTANCE.register(IEntropy.class, new EntropyCapabilityStorage(), new EntropyCapabilityFactory());
	}
	
	public void init() { MinecraftForge.EVENT_BUS.register(new EventHandler()); }
	
	public void postInit() {}

	public EntityPlayer getPlayerEntity(MessageContext ctx) { return ctx.getServerHandler().player; }
	
	public IThreadListener getThreadFromContext(MessageContext ctx) { return ctx.getServerHandler().player.getServerWorld(); }
}