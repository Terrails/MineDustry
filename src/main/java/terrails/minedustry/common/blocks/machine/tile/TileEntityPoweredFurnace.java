package terrails.minedustry.common.blocks.machine.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class TileEntityPoweredFurnace /*extends TileEntity implements ITickable, IInventory */ {

    public static final int fuel_slots = 4;
    public static final int input_slots = 5;
    public static final int output_slots = 5;
    public static final int total_slots = fuel_slots + input_slots + output_slots;

    public static final int first_fuel_slot = 0;
    public static final int first_input_slot = first_fuel_slot + fuel_slots;
    public static final int first_output_slot = first_input_slot + input_slots;

    private ItemStack[] ItemStacks = new ItemStack[total_slots];

    private int [] burnTimeRemaining = new int[fuel_slots];
    private int [] burnTimeInitialValue = new int[fuel_slots];

    private short cookTime;
    private static final short cook_time_for_completion = 200;
    private int cachedNumberOfBurningSlots = -1;

    public double fractionOfFuelRemaining(int fuelSlot){
        if(burnTimeInitialValue[fuelSlot] <= 0){
            return 0;
        }
        double fraction = burnTimeRemaining[fuelSlot] / (double)burnTimeInitialValue[fuelSlot];
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    public int secondOfFuelRemaining;
}
