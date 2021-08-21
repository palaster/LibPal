package palaster.libpal.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ModBlockItem extends BlockItem {
	public ModBlockItem(Block block, Properties properties) {
		super(block, properties);
		setRegistryName(block.getRegistryName());
	}
}
