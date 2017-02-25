package palaster.libpal.api;

import net.minecraft.item.ItemBlock;

public interface ICustomItemBlock {

	Class<? extends ItemBlock> getCustomItemBlock();
}
