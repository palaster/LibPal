package palaster.libpal.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockHelper {

	public static boolean isBlockStateInstanceofBlock(Block block, IBlockState... blockStates) {
		for(IBlockState bs : blockStates)
			if(bs == null || bs.getBlock() != block)
				return false;
		return true;
	}
	
	public static boolean isBlockStateInstanceofBlock(Class<? extends Block> clazz, IBlockState... blockStates) {
		for(IBlockState bs : blockStates)
			if(bs == null || !clazz.isInstance(bs))
				return false;
		return true;
	}
}
