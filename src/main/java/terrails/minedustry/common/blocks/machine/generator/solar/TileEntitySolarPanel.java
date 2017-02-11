package terrails.minedustry.common.blocks.machine.generator.solar;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import terrails.minedustry.common.blocks.base.BaseEnergyContainer;

public class TileEntitySolarPanel extends BaseEnergyContainer implements ITickable{

    private final BaseEnergyContainer container;
    public int generation = 16;
    public int maxEnergy = 1250;
    public int storedEnergy;

    public TileEntitySolarPanel() {
        this.container = new BaseEnergyContainer();
        this.container.setMaxEnergyStored(maxEnergy);
    }


    @Override
    public void update() {
        if (this.hasWorld() && !this.world.isRemote) {
            if (!this.getWorld().provider.hasNoSky() && this.getWorld().canBlockSeeSky(this.getPos().offset(EnumFacing.UP))
                    && this.getWorld().getSkylightSubtracted() == 0 && this.container.getEnergyStored() != this.container.getMaxEnergyStored())
                this.container.receiveEnergy(generation, false);
            this.storedEnergy = this.container.getEnergyStored();


            final TileEntity tileEntity = this.getWorld().getTileEntity(this.getPos().offset(EnumFacing.DOWN));

            if (tileEntity != null && !tileEntity.isInvalid()) {
                if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                    IEnergyStorage consumer = tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);


                        if (consumer != null)
                            this.container.extractEnergy(consumer.receiveEnergy(generation, false), false);
                }
            }
        }
    }





/*    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.container.deserializeNBT(compound.getCompoundTag("StoredTF"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("StoredTF", this.container.serializeNBT());
        return super.writeToNBT(compound);
    } */
}