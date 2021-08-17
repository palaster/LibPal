package palaster.libpal.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import palaster.libpal.libs.LibMod;

public class PacketHandler {
	public static int packet_id = 0;
	
	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(LibMod.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel getInstance() { return INSTANCE; }
	
	public static final void sendTo(ServerPlayerEntity player, Object msg) { INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg); }
	
	public static final void sendToAll(Object msg) { INSTANCE.send(PacketDistributor.ALL.noArg(), msg); }
	
	public static final void sendToAllAround(PacketDistributor.TargetPoint point, Object msg) { INSTANCE.send(PacketDistributor.NEAR.with(() -> point), msg); }
	
	public static final void sendToAllAround(double x, double y, double z, double range, RegistryKey<World> worldRegistryKey, Object msg) { PacketHandler.sendToAllAround(new PacketDistributor.TargetPoint(x, y, z, range, worldRegistryKey), msg); }
	
	public static final void sendToAllAround(PlayerEntity player, double range, Object msg) { PacketHandler.sendToAllAround(player.getX(), player.getY(), player.getZ(), range, player.level.dimension(), msg); }
	
	public static final void sendToDimension(RegistryKey<World> worldRegistryKey, Object msg) { INSTANCE.send(PacketDistributor.DIMENSION.with(() -> worldRegistryKey), msg); }
	
	public static final void sendToServer(Object msg) { INSTANCE.sendToServer(msg); }
}