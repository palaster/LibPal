package palaster.libpal.blocks.tile;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class InventoryModTileEntity extends ModTileEntity {

	protected IItemHandler itemHandler = createItemStackHandler();
	
	private final LazyOptional<IItemHandler> holder = LazyOptional.of(() -> itemHandler);
	
	public InventoryModTileEntity(TileEntityType<?> tileEntityType) { super(tileEntityType); }

	@Override
	public void load(BlockState blockState, CompoundNBT compoundNBT) {
		super.load(blockState, compoundNBT);
		itemHandler = createItemStackHandler();
		if(itemHandler instanceof ItemStackHandler)
			((ItemStackHandler) itemHandler).deserializeNBT(compoundNBT);
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compoundNBT) {
		CompoundNBT nbt = super.save(compoundNBT);
		if(itemHandler instanceof ItemStackHandler)
			nbt.merge(((ItemStackHandler) itemHandler).serializeNBT());
		return nbt;
	}
	
	public abstract int getSizeInventory();
	
	protected IItemHandler createItemStackHandler() { return new SimpleItemStackHandler(this, true); }
	
	public IItemHandler getItemHandler() { return itemHandler; } 
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) { return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, holder) : super.getCapability(cap, side); }
	
	protected static class SimpleItemStackHandler extends ItemStackHandler {
		private final InventoryModTileEntity inventoryModTileEntity;
		private final boolean allowWrite;
		
		public SimpleItemStackHandler(InventoryModTileEntity inventoryModTileEntity, boolean allowWrite) {
			super(inventoryModTileEntity.getSizeInventory());
			this.inventoryModTileEntity = inventoryModTileEntity;
			this.allowWrite = allowWrite;
		}
		
		protected InventoryModTileEntity getInventoryModTileEntity() { return inventoryModTileEntity; }

		protected boolean canWrite() { return allowWrite; }
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) { return allowWrite ? super.insertItem(slot, stack, simulate) : stack; }
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) { return allowWrite ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY; }
		
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			inventoryModTileEntity.setChanged();
		}
	}
}
