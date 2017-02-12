package terrails.minedustry.common.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import terrails.minedustry.Constants;
import terrails.minedustry.common.blocks.machine.battery.TileEntityBasicBattery;
import terrails.minedustry.common.blocks.machine.generator.solar.TileEntitySolarPanel;

public abstract class TileEntityUtil extends TileEntity implements ITickable {

    public boolean stopFromDropping;
    public final String name;

    public TileEntityUtil(String name) {
        this.name = name;
    }


    public static void initTileEntities() {
        Constants.LOGGER.info("Registering TileEntities:");
        //      GameRegistry.registerTileEntity(FastFurnaceTileEntity.class, "MD_Fast_Furnace");
        GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "SolarBasic");
        GameRegistry.registerTileEntity(TileEntityBasicBattery.class, "BasicBattery");
    }

   /* private static void register(Class<? extends TileEntityBase> tileClass) {
        try {
            //This is hacky and dirty but it works so whatever
            String name = Constants.MODID + ":" + tileClass.newInstance().this.name;
            GameRegistry.registerTileEntity(tileClass, name);
        } catch (Exception e) {
            Constants.LOGGER.fatal("Registering a TileEntity failed!", e);
        }
    } */


    @Override
    public final NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeSyncableNBT(compound, NBTType.SAVE_TILE);
        return compound;
    }

    @Override
    public final void readFromNBT(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SAVE_TILE);
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return new SPacketUpdateTileEntity(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readSyncableNBT(pkt.getNbtCompound(), NBTType.SYNC);
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeSyncableNBT(compound, NBTType.SYNC);
        return compound;
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound compound) {
        this.readSyncableNBT(compound, NBTType.SYNC);
    }

    public void writeSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.writeToNBT(compound);
        }

        if (type == NBTType.SAVE_TILE) {
            compound.setBoolean("StopDrop", this.stopFromDropping);
        }
    }

    public void readSyncableNBT(NBTTagCompound compound, NBTType type) {
        if (type != NBTType.SAVE_BLOCK) {
            super.readFromNBT(compound);
        }

        if (type == NBTType.SAVE_TILE) {
            this.stopFromDropping = compound.getBoolean("StopDrop");
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
    }

    public String getDisplayedName() {
        return StringUtil.localize("container." + Constants.MODID + "." + this.name + ".name");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(this.getDisplayedName());
    }

    public int getComparatorStrength() {
        return 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler handler = this.getItemHandler(facing);
            if (handler != null) {
                return (T) handler;
            } else if (capability == CapabilityEnergy.ENERGY) {
                IEnergyStorage storage = this.getEnergyStorage(facing);
                if (storage != null) {
                    return (T) storage;
                }
            }
        }
        return super.getCapability(capability, facing);
    }

    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return null;
    }

    public IItemHandler getItemHandler(EnumFacing facing){
        return null;
    }


    public enum NBTType {
        SAVE_TILE,
        SYNC,
        SAVE_BLOCK
    }
}
