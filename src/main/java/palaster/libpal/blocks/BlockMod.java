package palaster.libpal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import palaster.libpal.api.IModObject;

public class BlockMod extends Block implements IModObject {

	public BlockMod(Material materialIn) {
		super(materialIn);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
	}
}
