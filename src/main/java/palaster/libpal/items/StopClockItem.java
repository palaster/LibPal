package palaster.libpal.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import palaster.libpal.api.capabilities.EntropyCapability.EntropyProvider;
import palaster.libpal.api.capabilities.IEntropy;
import palaster.libpal.libs.LibMod;

public class StopClockItem extends SpecialModItem {
    public StopClockItem(Properties properties) {
    	super(properties.tab(ItemGroup.TAB_MISC), 0);
        setRegistryName(LibMod.MODID, "stop_clock");
    }
    
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
    	if(!playerIn.level.isClientSide) {
    		LazyOptional<IEntropy> lazy_optional_entropy = target.getCapability(EntropyProvider.ENTROPY_CAPABILITY, null);
    		IEntropy entropy = lazy_optional_entropy.orElse(null);
    		if(entropy != null) {
    			entropy.setMaxAge(-1);
    			return ActionResultType.SUCCESS;
    		}
    	}
    	return super.interactLivingEntity(stack, playerIn, target, hand);
    }
}