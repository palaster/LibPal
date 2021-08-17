package palaster.libpal.api.capabilities.entropy;

import java.util.concurrent.Callable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EntropyCapability {

	public static class EntropyDefault implements IEntropy {
		private static final String NBT_AGE = "libpal:entropy:age",
				NBT_MAX_AGE = "libpal:entropy:maxAge";
		
		private int age = 0,
				maxAge = -1;

		@Override
		public void setAge(int age) {
			if(age > 0 && age <= maxAge && age < Integer.MAX_VALUE)
				this.age = age;
		}

		@Override
		public void setMaxAge(int maxAge) {
			if((maxAge > 0 || maxAge == -1) && maxAge < Integer.MAX_VALUE)
				this.maxAge = maxAge;
		}

		@Override
		public int getAge() { return age; }

		@Override
		public int getMaxAge() { return maxAge; }
		
		@Override
		public boolean shouldKill() { return getAge() >= getMaxAge(); }
		
		@Override
		public void update() {
			if(getAge() <= Integer.MAX_VALUE)
				setAge(getAge() + 1);
		}

		@Override
		public INBT serializeNBT() {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt(NBT_AGE, age);
			nbt.putInt(NBT_MAX_AGE, maxAge);
			return nbt;
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			if(nbt instanceof CompoundNBT) {
				CompoundNBT cNBT = (CompoundNBT) nbt;
				age = cNBT.getInt(NBT_AGE);
				maxAge = cNBT.getInt(NBT_MAX_AGE);
			}
		}
	}
	
	public static class EntropyProvider implements ICapabilitySerializable<INBT> {
		@CapabilityInject(IEntropy.class)
		public static final Capability<IEntropy> ENTROPY_CAPABILITY = null;
		
		protected IEntropy entropy = new EntropyDefault();
		
		private final LazyOptional<IEntropy> holder = LazyOptional.of(() -> entropy);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) { return ENTROPY_CAPABILITY.orEmpty(capability, holder); }

		@Override
		public INBT serializeNBT() { return entropy.serializeNBT(); }

		@Override
		public void deserializeNBT(INBT nbt) { entropy.deserializeNBT(nbt); }
	}
	
	public static class EntropyStorage implements Capability.IStorage<IEntropy> {
		@Override
		public INBT writeNBT(Capability<IEntropy> capability, IEntropy instance, Direction side) { return instance.serializeNBT(); }

		@Override
		public void readNBT(Capability<IEntropy> capability, IEntropy instance, Direction side, INBT nbt) { instance.deserializeNBT(nbt); }
	}
	
	public static class EntropyFactory implements Callable<IEntropy> {
		@Override
		public IEntropy call() throws Exception { return new EntropyDefault(); }
	}
}
