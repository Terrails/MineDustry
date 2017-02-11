package terrails.minedustry.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import terrails.minedustry.Constants;
import terrails.minedustry.client.render.tile.PedestalTESR;
import terrails.minedustry.common.tile.PedestalTileEntity;
import terrails.minedustry.common.util.BlocksUtil;

import java.util.Random;

public class PedestalBlock extends Block implements ITileEntityProvider {

    public PedestalBlock(String name) {
        super(Material.ROCK);
        setUnlocalizedName(Constants.MODID + "." + name);
        setRegistryName(name);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(PedestalTileEntity.class, Constants.MODID + "_" + name);
        setCreativeTab(CreativeTabs.FOOD);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        // Bind our TESR to our tile entity
        ClientRegistry.bindTileEntitySpecialRenderer(PedestalTileEntity.class, new PedestalTESR());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new PedestalTileEntity();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    private PedestalTileEntity getTE(World world, BlockPos pos) {
        return (PedestalTileEntity) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            PedestalTileEntity te = getTE(world, pos);
            if (te.getStack() == null) {
                if (player.getHeldItem(hand) != null) {
                    // There is no item in the pedestal and the player is holding an item. We move that item
                    // to the pedestal
                    te.setStack(player.getHeldItem(hand));
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    // Make sure the client knows about the changes in the player inventory
                    player.openContainer.detectAndSendChanges();
                }
            } else {
                // There is a stack in the pedestal. In this case we remove it and try to put it in the
                // players inventory if there is room
                ItemStack stack = te.getStack();
                te.setStack(null);
                if (!player.inventory.addItemStackToInventory(stack)) {
                    // Not possible. Throw item in the world
                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), stack);
                    world.spawnEntity(entityItem);
                } else {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksUtil.blockPedestal);
    }

 /*   @Override
    public void breakBlock(
            World worldIn,
            BlockPos pos,
            IBlockState state)
    {
        if (!hasTileEntity())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof PedestalTileEntity)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos,
                        (PedestalTileEntity)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
*/
}