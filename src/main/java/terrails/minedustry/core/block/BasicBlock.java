package terrails.minedustry.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block {

    protected String modName;
    protected String name;

    public BasicBlock(Material material) {

        this(material, "minedustry");
    }

    public BasicBlock(Material material, String modName) {

        super(material);
        this.modName = modName;
    }

    @Override
    public Block setUnlocalizedName(String name) {

        this.name = name;
        name = modName + "." + name;
        return super.setUnlocalizedName(name);
    }

}
