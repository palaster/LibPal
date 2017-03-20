package palaster.libpal.api;

import net.minecraft.entity.player.EntityPlayer;

public interface IReceiveButton {

	public void receiveButtonEvent(int buttonId, EntityPlayer player);
}
