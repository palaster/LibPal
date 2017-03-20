package palaster.libpal.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface ISpecialItemBlock {

	ItemBlock getSpecialItemBlock(Block block);
}
