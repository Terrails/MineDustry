package terrails.minedustry;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import terrails.minedustry.common.CommonProxy;
import terrails.minedustry.common.capabilities.Capabilities;


@Mod(modid = Constants.MODID,
     name = Constants.NAME,
     version = Constants.VERSION)
public class MineDustry
{
    @SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.SERVER_PROXY)
    public static CommonProxy proxy;
    @Mod.Instance
    public static MineDustry instance;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Constants.LOGGER.info("Starting PreInitialization Phase...");
        proxy.preInit(event);
        Capabilities.registerCapabilities();

        Constants.LOGGER.info("PreInitialization Finished.");

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Constants.LOGGER.info("Starting Initialization Phase...");
        proxy.init(event);
        Constants.LOGGER.info("Initialization Finished.");

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Constants.LOGGER.info("Starting PostInitialization Phase...");
        proxy.postInit(event);

        Constants.LOGGER.info("PostInitialization Finished.");

    }

}
