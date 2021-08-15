package palaster.libpal.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class EntityHelper {
	@Nullable
	public static Entity getEntityFromUUID(@Nullable UUID uuid) {
		if(uuid != null)
			if(FMLEnvironment.dist.isDedicatedServer() && ServerLifecycleHooks.getCurrentServer() != null)
				for(ServerWorld world : ServerLifecycleHooks.getCurrentServer().getAllLevels())
					if(world != null)
						return world.getEntity(uuid);
		return null;
	}
}
