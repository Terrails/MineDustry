package terrails.minedustry.common.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import terrails.minedustry.common.blocks.BlockFastFurnace;
import terrails.minedustry.common.blocks.PedestalBlock;
import terrails.minedustry.common.blocks.machine.battery.BlockBasicBattery;
import terrails.minedustry.common.blocks.machine.generator.solar.BlockSolarGenerator;
import terrails.minedustry.common.blocks.machine.smelt.BlockPoweredFurnace;


public class BlocksUtil {

    public static PedestalBlock blockPedestal;
    public static BlockFastFurnace blockFastFurnace;
    public static BlockFastFurnace blockLitFastFurnace;
    public static BlockSolarGenerator blockSolarGenerator;
    public static BlockBasicBattery blockBasicBattery;
    public static BlockPoweredFurnace blockPoweredFurnace;


    public static void init() {
        blockPedestal = new PedestalBlock("pedestalblock");
        blockFastFurnace = new BlockFastFurnace(false, "fastFurnace", 5.0F, 8.0F, "pickaxe", 0);
        blockLitFastFurnace = new BlockFastFurnace(true, "fastLitFurnace", 5.0F, 8.0F, "pickaxe", 0);
        blockSolarGenerator = new BlockSolarGenerator();
        blockBasicBattery = new BlockBasicBattery();
        blockPoweredFurnace = new BlockPoweredFurnace();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPedestal.initModel();
        blockFastFurnace.initModel();
        blockLitFastFurnace.initModel();
        blockSolarGenerator.initModel();
        blockBasicBattery.initModel();
      //  blockPoweredFurnace.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}
