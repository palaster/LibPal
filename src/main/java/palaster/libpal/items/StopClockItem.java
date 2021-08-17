package palaster.libpal.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import palaster.libpal.api.capabilities.entropy.EntropyCapability.EntropyProvider;
import palaster.libpal.api.capabilities.entropy.IEntropy;

public class StopClockItem extends SpecialModItem {
    public StopClockItem(Properties properties, ResourceLocation resourceLocation) { super(properties.tab(ItemGroup.TAB_MISC), resourceLocation, 0); }
    
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