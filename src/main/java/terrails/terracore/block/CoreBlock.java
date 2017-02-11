package terrails.terracore.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class CoreBlock extends Block {

    protected String blockName;
    protected String modName;

    public CoreBlock(Material materialIn) {
        super(materialIn);
    }

    public CoreBlock(Material material, String modname){

        super(material);
        this.modName = modName;
    }

    @Override
    public Block setUnlocalizedName(String blockName){

        this.blockName = blockName;
        blockName = modName + "." + blockName;
        return super.setUnlocalizedName(blockName);
    }
}
