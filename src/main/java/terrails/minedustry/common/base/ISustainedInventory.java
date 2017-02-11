package terrails.minedustry.common.base;

import net.minecraft.nbt.NBTTagList;

public interface ISustainedInventory
{

    public void setInventory(NBTTagList nbtTags, Object... data);

    public NBTTagList getInventory(Object... data);
}
