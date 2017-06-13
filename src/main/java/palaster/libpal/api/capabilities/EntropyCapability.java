package palaster.libpal.api.capabilities;

import java.util.concurrent.Callable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class EntropyCapability {

	public static class EntropyCapabilityDefault implements IEntropy {
	
		public static final String TAG_STRING_AGE = "EntropyAge",
				TAG_STRING_MAX_AGE = "EntropyMaxAge";
		
		private int age = 0,
				maxAge = -1;

		@Override
		public void setAge(int age) {
			if(age > 0 && age <= maxAge && age < Integer.MAX_VALUE)
				this.age = age;
		}

		@Override
		public void setMaxAge(int maxAge) {
			if(maxAge > 0 && maxAge < Integer.MAX_VALUE)
				this.maxAge = maxAge;
		}

		@Override
		public int getAge() { return age; }

		@Override
		public int getMaxAge() { return maxAge; }
		
		@Override
		public void update(EntityLiving living) {
			if(getAge() <= Integer.MAX_VALUE) {
				if(getAge() == getMaxAge())
					living.setDead();
				else
					setAge(getAge() + 1);
			}
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(TAG_STRING_AGE, age);
			nbt.setInteger(TAG_STRING_MAX_AGE, maxAge);
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			age = nbt.getInteger(TAG_STRING_AGE);
			maxAge = nbt.getInteger(TAG_STRING_MAX_AGE);
		}
	}

	public static class EntropyCapabilityFactory implements Callable<IEntropy> {

		@Override
		public IEntropy call() throws Exception { return new EntropyCapabilityDefault(); }
	}
	
	public static class EntropyCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
	
		@CapabilityInject(IEntropy.class)
		public static final Capability<IEntropy> ENTROPY_CAP = null;
		
		protected IEntropy entropy = null;
		
		public EntropyCapabilityProvider(EntityLiving living) {
			entropy = new EntropyCapabilityDefault();
			if(living instanceof EntityAgeable)
				entropy.setMaxAge(84000);
		}
		
		public static IEntropy get(EntityLiving living) {
			if(living.hasCapability(ENTROPY_CAP, null))
				return living.getCapability(ENTROPY_CAP, null);
			return null;
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) { return ENTROPY_CAP != null && capability == ENTROPY_CAP; }

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(ENTROPY_CAP != null && capability == ENTROPY_CAP)
				return ENTROPY_CAP.cast(entropy);
			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() { return entropy.serializeNBT(); }

		@Override
		public void deserializeNBT(NBTTagCompound nbt) { entropy.deserializeNBT(nbt); }
	}
	
	public static class EntropyCapabilityStorage implements Capability.IStorage<IEntropy> {

		@Override
		public NBTBase writeNBT(Capability<IEntropy> capability, IEntropy instance, EnumFacing side) { return instance.serializeNBT(); }

		@Override
		public void readNBT(Capability<IEntropy> capability, IEntropy instance, EnumFacing side, NBTBase nbt) { instance.deserializeNBT((NBTTagCompound) nbt); }
	}
}
