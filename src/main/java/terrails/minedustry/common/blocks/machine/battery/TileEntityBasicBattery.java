package terrails.minedustry.common.blocks.machine.battery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import terrails.minedustry.common.blocks.base.BaseEnergyContainer;
import terrails.minedustry.common.util.BlocksUtil;
import terrails.minedustry.common.util.TileEntityUtil;

import javax.annotation.Nonnull;

import static net.minecraft.block.Block.spawnAsEntity;

public class TileEntityBasicBattery extends BaseEnergyContainer implements ITickable {

    final int input = 2500;
    final int output = 2500;
    final int maxEnergy = 1000000;


    public TileEntityBasicBattery() {
        setMaxInput(input);
        setMaxOutput(output);
        setMaxEnergyStored(maxEnergy);
        this.markDirty();
    }

    public final int getStoredEnergy()
    {
        return getEnergyStored();
    }
    public final int getMaxEnergyExtract(){
        return output;
    }
    public final int getMaxEnergyInput(){
        return input;
    }
    public final int getMaxEnergyStored(){
        return maxEnergy;
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        int storedEnergy = getEnergyStored();
        super.readFromNBT(compound);

        storedEnergy = compound.getInteger("energy");
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
      //  super.writeToNBT(compound);
        compound.setInteger("energy", getEnergyStored());
        return compound;
    }

    public boolean containsEnergy() {
        return this.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP) != null;
    }


    @Override
    public void update() {
        if (this.hasWorld() && !this.world.isRemote && getEnergyStored() != getMaxEnergyStored() && this.canReceive()) {

            final TileEntity tileEntity = this.getWorld().getTileEntity(this.getPos().offset(EnumFacing.DOWN));

            if (tileEntity != null && !tileEntity.isInvalid()) {
                if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN)) {
                    IEnergyStorage consumer = tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN);

                    if (consumer != null)
                        this.extractEnergy(consumer.extractEnergy(output, false), false);

                    this.markDirty();
                }
            }
        }
    }
}
