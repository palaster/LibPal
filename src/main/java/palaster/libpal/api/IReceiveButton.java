package palaster.libpal.api;

import net.minecraft.entity.player.PlayerEntity;

@FunctionalInterface
public interface IReceiveButton {
	public void receiveButtonEvent(int buttonId, PlayerEntity playereEntity);
}