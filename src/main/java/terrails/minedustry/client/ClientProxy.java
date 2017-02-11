package terrails.minedustry.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import terrails.minedustry.Constants;
import terrails.minedustry.common.CommonProxy;
import terrails.minedustry.common.util.BlocksUtil;


public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        OBJLoader.INSTANCE.addDomain(Constants.MODID);
        BlocksUtil.initModels();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        BlocksUtil.initItemModels();
    }


    @Override
    public boolean isOp(String playerName) {

        return true;
    }

    @Override
    public boolean isServer() {

        return false;
    }

    @Override
    public boolean isClient() {

        return true;
    }

}

