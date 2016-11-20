package palaster.libpal.core.helpers;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PlayerHelper {

	public static void sendChatMessageToPlayer(EntityPlayer player, String string) { player.addChatMessage(new TextComponentString(string)); }

	@Nullable
	public static EntityPlayer getPlayerFromUUID(UUID uuid) {
		for(int i = 0; i < DimensionManager.getIDs().length; i++) {
			World world = DimensionManager.getWorld(DimensionManager.getIDs()[i]);
			if(world != null && !world.isRemote)
				if(world.getPlayerEntityByUUID(uuid) != null)
					return world.getPlayerEntityByUUID(uuid);
		}
		return null;
	}
}
