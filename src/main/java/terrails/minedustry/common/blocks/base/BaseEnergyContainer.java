package terrails.minedustry.common.blocks.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import terrails.minedustry.common.capabilities.Capabilities;

public class BaseEnergyContainer extends TileEntity implements IEnergyStorage, INBTSerializable<NBTTagCompound> {

    private int stored;
    private int capacity;
    private int input;
    private int output;

    public BaseEnergyContainer() {
        this(250, 250, 250);
    }

    public BaseEnergyContainer(int capacity, int input, int output) {
        this(0, capacity, input, output);
    }

    public BaseEnergyContainer(int power, int capacity, int input, int output) {
        this.stored = power;
        this.capacity = capacity;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
 /*       return capability == Capabilities.ENERGY_STORAGE_CAPABILITY
                || capability == Capabilities.ENERGY_ACCEPTOR_CAPABILITY */
 return capability == Capabilities.ENERGY
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
    /*    if(capability == Capabilities.ENERGY_STORAGE_CAPABILITY || capability == Capabilities.ENERGY_ACCEPTOR_CAPABILITY ||
                capability == Capabilities.CABLE_OUTPUTTER_CAPABILITY) */
    if(capability == Capabilities.ENERGY)
        {
            return (T)this;
        }

        return super.getCapability(capability, facing);
    }

    public BaseEnergyContainer(NBTTagCompound dataTag) {
        this.deserializeNBT(dataTag);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound dataTag = new NBTTagCompound();

        dataTag.setInteger("TFStored", this.stored);
        dataTag.setInteger("TFCapacity", this.capacity);
        dataTag.setInteger("TFInput", this.input);
        dataTag.setInteger("TFOutput", this.output);

        return dataTag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("TFStored"))
            this.stored = nbt.getInteger("TFStored");
        if (nbt.hasKey("TFCapacity"))
            this.capacity = nbt.getInteger("TFCapacity");
        if (nbt.hasKey("TFInput"))
            this.input = nbt.getInteger("TFInput");
        if (nbt.hasKey("TFOutput"))
            this.output = nbt.getInteger("TFOutput");

        if (this.stored > this.getMaxEnergyStored())
            this.stored = this.getMaxEnergyStored();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        final int acceptedPower = Math.min(this.getMaxEnergyStored() - this.getEnergyStored(), Math.min(this.getMaxInput(), maxReceive));

        if (!simulate)
            this.stored += acceptedPower;

        return this.canReceive() ? acceptedPower : 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        final int removedPower = Math.min(this.getEnergyStored(), Math.min(this.getMaxOutput(), maxExtract));

        if (!simulate)
            this.stored -= removedPower;
        return this.canExtract() ? removedPower : 0;
    }

    @Override
    public int getEnergyStored() {
        return this.stored;
    }

    @Override
    public int getMaxEnergyStored() {
        return this.capacity;
    }

    public void setMaxEnergyStored(int capacity) {
        this.capacity = capacity;

        if (this.stored > capacity)
            this.stored = capacity;
    }

    public int getMaxInput() {
        return this.input;
    }

    public void setMaxInput(int input) {
        this.input = input;
    }

    public int getMaxOutput() {
        return this.output;
    }

    public void setMaxOutput(int output) {
        this.output = output;
    }

    @Override
    public boolean canExtract() {
        return this.getMaxOutput() > 0 && this.stored > 0;
    }

    @Override
    public boolean canReceive() {
        return this.getMaxInput() > 0;
    }

}