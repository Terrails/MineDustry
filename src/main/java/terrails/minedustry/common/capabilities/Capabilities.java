package terrails.minedustry.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import terrails.api.energy.IEnergyAcceptor;
import terrails.api.energy.IEnergyStorage;
import terrails.api.energy.IOutput;
import terrails.minedustry.common.base.ITileNetwork;

public class Capabilities {

    @CapabilityInject(net.minecraftforge.energy.IEnergyStorage.class)
    public static Capability<net.minecraftforge.energy.IEnergyStorage> ENERGY = null;

    public static void registerCapabilities(){
    }
}
