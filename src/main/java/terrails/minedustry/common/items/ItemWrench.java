package terrails.minedustry.common.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.core.Filter;

public class ItemWrench extends Item {

    String name = "wrench";

    public ItemWrench() {
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

  /*  @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock(x, y , z);
        boolean ret = false;
        if(block != null){
            PlayerInteractEvent e = new PlayerInteractEvent(player, ClickEvent.Action.RIGHT_CLICK_BLOCK, x, y, z, world);
            if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Filter.Result.DENY || e.RightClickBlock == Filter.Result.DENY ||
                    e.RightClickBlock == Filter.Result.DENY){
                return false;
            }
        }
    } */
}
