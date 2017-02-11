package terrails.minedustry.common.blocks.machine.generator.solar;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockSolarGenerator extends Block implements ITileEntityProvider{

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.225, 1);

    String name = "solar_panel";

    public BlockSolarGenerator(){
        super(Material.ROCK);
        this.isBlockContainer = true;
        this.setHardness(1.5f);
        this.setResistance(10f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setSoundType(SoundType.METAL);
        setRegistryName(name);
        setUnlocalizedName(name);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }




    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
                                    @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntitySolarPanel te = (TileEntitySolarPanel) worldIn.getTileEntity(pos);
        if(playerIn.getActiveHand() == hand.MAIN_HAND || playerIn.getActiveHand() == hand.OFF_HAND)
        playerIn.sendStatusMessage(new TextComponentString(te.storedEnergy + "/" + te.maxEnergy));
        return true;

    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return super.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
    }

    @Override
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return super.addHitEffects(state, worldObj, target, manager);
    }

    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return super.addDestroyEffects(world, pos, manager);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntitySolarPanel();
    }
}
