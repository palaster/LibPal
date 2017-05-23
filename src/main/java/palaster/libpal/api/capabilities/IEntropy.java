package palaster.libpal.api.capabilities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEntropy extends INBTSerializable<NBTTagCompound>{

	void setAge(int age);
	
	void setMaxAge(int maxAge);
	
	int getAge();
	int getMaxAge();
	
	void update(EntityLiving living);
}