package palaster.libpal.api.capabilities.entropy;

import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEntropy extends INBTSerializable<INBT> {
	
	void setAge(int age);
	
	void setMaxAge(int maxAge);
	
	int getAge();
	
	int getMaxAge();
	
	boolean shouldKill();
	
	void update();
}