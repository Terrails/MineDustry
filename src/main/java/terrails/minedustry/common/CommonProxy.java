package terrails.minedustry.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import terrails.minedustry.common.util.BlocksUtil;
import terrails.minedustry.common.util.ItemsUtil;
import terrails.minedustry.common.util.TileEntityUtil;

public class CommonProxy{

    public void preInit(FMLPreInitializationEvent e) {
        BlocksUtil.init();
        ItemsUtil.init();
    }

    public void init(FMLInitializationEvent e) {
        TileEntityUtil.initTileEntities();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    public boolean isClient() {

        return false;
    }

    public boolean isServer() {

        return true;
    }

    public boolean isOp(String playerName) {

        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        playerName = playerName.trim();
        for (String a : server.getPlayerList().getOppedPlayerNames()) {
            if (playerName.equalsIgnoreCase(a)) {
                return true;
            }
        }
        return false;
    }

    public EntityPlayer getClientPlayer() {

        return null;
    }

}
