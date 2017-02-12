package terrails.minedustry.common.blocks.machine.battery;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import terrails.terracore.energy.BaseEnergyContainer;

public class TileEntityBasicBattery extends BaseEnergyContainer implements ITickable {

    final int input = 2500;
    final int output = 2500;
    final int maxEnergy = 1000000;
    int energyStored = getEnergyStored();


    public TileEntityBasicBattery() {
        setMaxInput(input);
        setMaxOutput(output);
        setMaxEnergyStored(maxEnergy);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

        int storedEnergy = getEnergyStored();
        storedEnergy = compound.getInteger("energy");
        // I even tried this:
        //   energyStored = compound.getInteger("energy");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", getEnergyStored());
        return compound;
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
    public int setStoredEnergy(int energy){
        energy = getEnergyStored();
        return energy;
    }
    public final int getMaxEnergyStored(){
        return maxEnergy;
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
                        this.extractEnergy(consumer.extractEnergy(getMaxEnergyExtract(), false), false);

                    this.markDirty();
                }
            }
        }
    }
}
