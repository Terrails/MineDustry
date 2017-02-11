package terrails.minedustry.common.blocks.machine.battery;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import terrails.minedustry.Constants;
import terrails.terracore.block.CoreBlock;
import terrails.terracore.block.ItemBlockBase;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class BlockBasicBattery extends CoreBlock implements ITileEntityProvider {

    private static final PropertyDirection FACING = BlockHorizontal.FACING;
    private String name = "basic_battery";
    public int energyStored;
    public int energyMax;
    public static final Set<Block> BLOCKS = new HashSet<>();


    public BlockBasicBattery() {
        super(Material.ROCK, Constants.NAME);
        setUnlocalizedName(name);
        this.setRegistryName("basic_battery");
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBlockContainer = true;
        this.setHardness(1.5f);
        this.setResistance(10f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.FOOD);
        setCreativeTab(CreativeTabs.FOOD);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }


    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drop = super.getDrops(world, pos, state, fortune);

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(state, rand, fortune);
        ItemStack stack = null;
        if (item != null) {
            stack = new ItemStack(item, 1, this.damageDropped(state));
            drop.add(stack);
        }
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityBasicBattery && stack != null) {
            if (((TileEntityBasicBattery) te).containsEnergy()) {
                NBTTagCompound compound = new NBTTagCompound();
                ((TileEntityBasicBattery) te).writeToNBT(compound);
                //     stack.setTagCompound(tag);
                compound.removeTag("x");
                compound.removeTag("y");
                compound.removeTag("z");
                compound.removeTag("id");
                stack.setTagInfo("BlockEntityTag", compound);
            }
        }
        return drop;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, entity.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
                                    @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntityBasicBattery te = (TileEntityBasicBattery) worldIn.getTileEntity(pos);
        if (playerIn.getActiveHand() == hand.MAIN_HAND || playerIn.getActiveHand() == hand.OFF_HAND)
            playerIn.sendStatusMessage(new TextComponentString(te.storedEnergy + "/" + te.maxEnergy));
        this.energyStored = te.storedEnergy;
        this.energyMax = te.maxEnergy;
        return true;

    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBasicBattery();
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }


    protected String getBaseName() {
        return this.name;
    }

    protected ItemBlockBasicBattery getItemBlock(){
        return new ItemBlockBasicBattery(this);
    }

    public class ItemBlockBasicBattery extends ItemBlock {

        public ItemBlockBasicBattery(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setRegistryName("basic_battery");
        }


        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }


    }
}
