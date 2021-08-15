package palaster.libpal.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockHelper {
	public static boolean isBlockStateInstanceofBlock(Block block, BlockState... blockStates) {
		for(BlockState bs : blockStates)
			if(bs == null || bs.getBlock() != block)
				return false;
		return true;
	}
	
	public static boolean isBlockStateInstanceofBlock(Class<? extends Block> clazz, BlockState... blockStates) {
		for(BlockState bs : blockStates)
			if(bs == null || !clazz.isInstance(bs))
				return false;
		return true;
	}
}
