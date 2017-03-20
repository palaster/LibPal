package palaster.libpal.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class EntityHelper {

	@Nullable
	public static Entity getEntityFromUUID(@Nullable UUID uuid) {
		if(uuid != null)
			for(int i = 0; i < DimensionManager.getIDs().length; i++) {
				World world = DimensionManager.getWorld(DimensionManager.getIDs()[i]);
				if(world != null && !world.isRemote)
					for(Entity e : world.getLoadedEntityList())
						if(e.getUniqueID().equals(uuid))
							return e;
			}
		return null;
	}
}
