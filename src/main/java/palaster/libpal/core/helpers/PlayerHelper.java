package palaster.libpal.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class PlayerHelper {

	public static void sendChatMessageToPlayer(@Nullable PlayerEntity playerEntity, String message) {
		if(playerEntity != null)
			playerEntity.sendMessage(new StringTextComponent(message), playerEntity.getUUID());
	}

	@Nullable
	public static PlayerEntity getPlayerFromUUID(@Nullable UUID uuid) {
		if(uuid != null)
			if(FMLEnvironment.dist.isDedicatedServer() && ServerLifecycleHooks.getCurrentServer() != null)
				for(ServerWorld world : ServerLifecycleHooks.getCurrentServer().getAllLevels())
					if(world != null)
						return world.getPlayerByUUID(uuid);
		return null;
	}
}
