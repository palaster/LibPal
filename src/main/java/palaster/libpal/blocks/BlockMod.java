package palaster.libpal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMod extends Block {

	public BlockMod(ResourceLocation rl, Material materialIn) {
		super(materialIn);
		setHardness(3F);
		setHarvestLevel("pickaxe", 0);
		register(rl);
	}
	
	public void register(ResourceLocation rl) {
		setRegistryName(rl);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(rl));
		super.setUnlocalizedName(rl.getResourcePath());
	}
}
