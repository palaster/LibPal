package palaster.libpal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import palaster.libpal.api.IModObject;
import palaster.libpal.api.ISpecialItemBlock;
import palaster.libpal.items.LPItemBlock;

public class BlockMod extends Block implements IModObject, ISpecialItemBlock {

	public BlockMod(Material materialIn) {
		super(materialIn);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public ItemBlock getSpecialItemBlock(Block block) { return new LPItemBlock(block); }
}
