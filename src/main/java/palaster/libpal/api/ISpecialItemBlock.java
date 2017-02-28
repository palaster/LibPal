package palaster.libpal.api;

import net.minecraft.item.ItemBlock;

public interface ISpecialItemBlock {

	Class<? extends ItemBlock> getSpecialItemBlock();
}
