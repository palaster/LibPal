package palaster.libpal.blocks.tile;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class ModTileEntity extends TileEntity implements ITickableTileEntity {
	
	public ModTileEntity(TileEntityType<?> tileEntityType) { super(tileEntityType); }
	
	@Override
	public void tick() {}

	@Override
	public CompoundNBT getUpdateTag() { return save(new CompoundNBT()); }
	
	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) { load(state, tag); }
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() { return new SUpdateTileEntityPacket(worldPosition, -999, save(new CompoundNBT())); }
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) { load(level.getBlockState(worldPosition), pkt.getTag()); }
}
