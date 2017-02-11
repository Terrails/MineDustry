package terrails.minedustry.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PowerHandlerUtil {

    public static final String STORED_ENERGY_NBT_KEY = "storedEnergyRF";

    public static int getStoredEnergyForItem(ItemStack item) {
        NBTTagCompound tag = item.getTagCompound();
        if(tag == null) {
            return 0;
        }

        if(tag.hasKey("storedEnergy")) {
            double storedMj = tag.getDouble("storedEnergy");
            return (int) (storedMj * 10);
        }

        return tag.getInteger(STORED_ENERGY_NBT_KEY);
    }

    public static void setStoredEnergyForItem(ItemStack item, int storedEnergy) {
        NBTTagCompound tag = item.getTagCompound();
        if(tag == null) {
            tag = new NBTTagCompound();
        }
        tag.setInteger(STORED_ENERGY_NBT_KEY, storedEnergy);
        item.setTagCompound(tag);
    }

}