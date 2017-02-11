package terrails.api.energy;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public interface IOutput
    {

        /**
         * Whether or not this block can output to a cable on a specific side.
         * @param side - side to check
         * @return if the block can output
         */
        public boolean canOutputTo(EnumFacing side);
    }