package palaster.libpal.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import palaster.libpal.blocks.tile.InventoryModTileEntity;

public abstract class ContainerModBlock extends ModBlock {

	public ContainerModBlock(Properties properties, ResourceLocation resourceLocation) { super(properties, resourceLocation); }

	public abstract TileEntity createModTileEntity(BlockState state, IBlockReader world);
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) { return createModTileEntity(state, world) != null ? createModTileEntity(state, world) : super.createTileEntity(state, world); }

	@Override
	public boolean hasTileEntity(BlockState state) { return true; }
	
	@Override
	public void onRemove(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
		if(!state.is(newState.getBlock())) {
			TileEntity tileEntity = world.getBlockEntity(blockPos);
			if (tileEntity instanceof InventoryModTileEntity) {
				IItemHandler ih = ((InventoryModTileEntity) tileEntity).getItemHandler();
				for(int i = 0; i < ih.getSlots(); i++)
					InventoryHelper.dropItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ih.getStackInSlot(i));
			}
			super.onRemove(state, world, blockPos, newState, isMoving);
		}
	}
}
