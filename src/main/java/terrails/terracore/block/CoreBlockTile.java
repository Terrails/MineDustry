package terrails.terracore.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CoreBlockTile extends CoreBlock implements ITileEntityProvider{

    public CoreBlockTile(Material material){

        super(material);
        setSoundType(SoundType.STONE);
    }

    public CoreBlockTile(Material material, String modName){

        super(material, modName);
        setSoundType(SoundType.STONE);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state){

        return createNewTileEntity(world, state.getBlock().getMetaFromState(state));
    }
}


