package terrails.minedustry.common.util;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import terrails.minedustry.Constants;
import terrails.minedustry.common.blocks.machine.battery.BlockBasicBattery;
import terrails.minedustry.common.items.ItemWrench;

public final class ItemsUtil {


    public static ItemWrench itemWrench;
    public static BlockBasicBattery.ItemBlockBasicBattery itemBlockBasicBattery;

    public static void init() {
        itemWrench = new ItemWrench();
    }

    public static void registerBlock(Block block, ItemBlock itemBlock, String name, boolean addTab){
        block.setUnlocalizedName(Constants.MODID+"."+name);

        block.setRegistryName(Constants.MODID, name);
        GameRegistry.register(block);

        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(itemBlock);

        block.setCreativeTab(CreativeTabs.FOOD);
    }
}
