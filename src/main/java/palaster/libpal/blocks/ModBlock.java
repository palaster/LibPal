package palaster.libpal.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class ModBlock extends Block {

	public ModBlock(Properties properties, ResourceLocation resourceLocation) {
		super(properties);
		setRegistryName(resourceLocation);
	}
}
