package palaster.libpal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMod extends Block {

	public BlockMod(Material materialIn) {
		super(materialIn);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
	}
}
