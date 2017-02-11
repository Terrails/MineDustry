package terrails.minedustry.common.blocks.machine.battery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.walkers.BlockEntityTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import terrails.minedustry.common.blocks.base.BaseEnergyContainer;
import terrails.minedustry.common.util.BlocksUtil;
import terrails.minedustry.common.util.TileEntityUtil;

import javax.annotation.Nonnull;

import static net.minecraft.block.Block.spawnAsEntity;

public class TileEntityBasicBattery extends BaseEnergyContainer implements ITickable {

    static final int input = 2500;
    static final int output = 2500;
    static final int maxEnergy = 1000000;
    static int storedEnergy;
    private Item battery;

    public TileEntityBasicBattery() {
        setMaxEnergyStored(maxEnergy);
        setMaxInput(input);
        setMaxOutput(output);
        this.battery = battery;
        this.markDirty();
    }

    public final int getStoredEnergy()
    {
        return this.storedEnergy;
    }
    public final int getMaxEnergyExtract(){
        return this.output;
    }
    public final int getMaxEnergyInput(){
        return this.input;
    }
    public final int getMaxEnergyStored(){
        return this.maxEnergy;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        super.readFromNBT(compound);
        this.storedEnergy = compound.getInteger("energy");
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("energy", storedEnergy);
        return super.writeToNBT(compound);
    }

    public boolean containsEnergy() {
        return this.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP) != null;
    }


    @Override
    public void update() {
        if (this.hasWorld() && !this.world.isRemote && getEnergyStored() != getMaxEnergyStored()) {
            this.canReceive();
            this.storedEnergy = this.getEnergyStored();

            final TileEntity tileEntity = this.getWorld().getTileEntity(this.getPos().offset(EnumFacing.DOWN));

            if (tileEntity != null && !tileEntity.isInvalid()) {
                if (!tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN)) {
                    IEnergyStorage consumer = tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);

                    if (consumer != null)
                        this.extractEnergy(consumer.receiveEnergy(output, false), false);

                    this.markDirty();
                }
            }
        }
    }
}
