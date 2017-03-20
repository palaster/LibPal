package palaster.libpal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import palaster.libpal.blocks.tile.TileEntityModInventory;

public abstract class BlockModContainer extends BlockMod {

	public BlockModContainer(Material material) { super(material); }

	public abstract TileEntity createModTileEntity(World world, IBlockState state);

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) { return createModTileEntity(world, state) != null ? createModTileEntity(world, state) : super.createTileEntity(world, state); }

	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof TileEntityModInventory)
			for(int i = 0; i < ((TileEntityModInventory) te).getSizeInventory(); i++)
				if(((TileEntityModInventory) te).getItemHandler().getStackInSlot(i) != null)
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileEntityModInventory) te).getItemHandler().getStackInSlot(i)));
		super.breakBlock(worldIn, pos, state);
	}
}
