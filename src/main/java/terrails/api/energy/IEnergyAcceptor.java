package terrails.api.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyAcceptor extends IEnergyStorage
{
    /**
     * Transfer a certain amount of energy to this acceptor.
     * @param amount - amount to transfer
     * @return energy used
     */
    public double transferEnergyToAcceptor(EnumFacing side, double amount);

    /**
     * Whether or not this tile entity accepts energy from a certain side.
     * @param side - side to check
     * @return if tile entity accepts energy
     */
    public boolean canReceiveEnergy(EnumFacing side);
}