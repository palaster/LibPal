package palaster.libpal.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import palaster.libpal.api.capabilities.EntropyCapability;
import palaster.libpal.api.capabilities.IEntropy;
import palaster.libpal.libs.LibMod;

public class ItemStopClock extends ItemModSpecial {

    public ItemStopClock() {
        super(32);
        setCreativeTab(CreativeTabs.MISC);
        setRegistryName(LibMod.MODID, "stop_clock");
        setUnlocalizedName("stop_clock");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(!playerIn.world.isRemote) {
            if(target instanceof EntityLiving) {
                IEntropy entropy = EntropyCapability.EntropyCapabilityProvider.get((EntityLiving) target);
                if(entropy != null) {
                    entropy.setMaxAge(-1);
                    stack.damageItem(1, playerIn);
                    return true;
                }
            }
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
