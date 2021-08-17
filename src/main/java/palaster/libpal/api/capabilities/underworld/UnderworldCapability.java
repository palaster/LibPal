package palaster.libpal.api.capabilities.underworld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import palaster.libpal.souls.Soul;

public class UnderworldCapability {

	public static class UnderworldDefault implements IUnderworld {
		private static final String NBT_SOULS = "libpal:underworld:soul_",
				NBT_SOULS_SIZE = "libpal:underworld:soulSize";
		
		private final ArrayList<Soul> souls = new ArrayList<Soul>();
		
		@Override
		public void addSoul(Soul soulToBeAdded) {
			if(soulToBeAdded != null && !souls.contains(soulToBeAdded))
				souls.add(soulToBeAdded);
		}
		
		@Override
		public List<Soul> getSouls() { return souls; }
		
		@Override
		public INBT serializeNBT() {
			CompoundNBT nbt = new CompoundNBT();
			for(int i = 0; i < souls.size(); i++)
				if(souls.get(i) != null)
					nbt.put(NBT_SOULS + i, souls.get(i).serializeNBT());
			nbt.putInt(NBT_SOULS_SIZE, souls.size());
			return nbt;
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			souls.clear();
			if(nbt instanceof CompoundNBT) {
				CompoundNBT cNBT = (CompoundNBT) nbt;
				int soulsSize = cNBT.getInt(NBT_SOULS_SIZE);
				for(int i = 0; i < soulsSize; i++) {
					Soul loadedSoul = new Soul();
					loadedSoul.deserializeNBT(cNBT.getCompound(NBT_SOULS + i));
					souls.add(loadedSoul);
				}
			}
		}
	}
	
	public static class UnderworldProvider implements ICapabilitySerializable<INBT> {
		@CapabilityInject(IUnderworld.class)
		public static final Capability<IUnderworld> UNDERWORLD_CAPABILITY = null;
		
		protected IUnderworld underworld = new UnderworldDefault();
		
		private final LazyOptional<IUnderworld> holder = LazyOptional.of(() -> underworld);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) { return UNDERWORLD_CAPABILITY.orEmpty(capability, holder); }

		@Override
		public INBT serializeNBT() { return underworld.serializeNBT(); }

		@Override
		public void deserializeNBT(INBT nbt) { underworld.deserializeNBT(nbt); }
	}
	
	public static class UnderworldStorage implements Capability.IStorage<IUnderworld> {
		@Override
		public INBT writeNBT(Capability<IUnderworld> capability, IUnderworld instance, Direction side) { return instance.serializeNBT(); }

		@Override
		public void readNBT(Capability<IUnderworld> capability, IUnderworld instance, Direction side, INBT nbt) { instance.deserializeNBT(nbt); }
	}
	
	public static class UnderworldFactory implements Callable<IUnderworld> {
		@Override
		public IUnderworld call() throws Exception { return new UnderworldDefault(); }
	}
}
