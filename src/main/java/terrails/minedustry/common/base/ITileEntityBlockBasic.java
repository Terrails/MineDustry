package terrails.minedustry.common.base;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public interface ITileEntityBlockBasic {


    //Networking
    public void handlePacketData(ByteBuf dataStream) throws Exception;
    public ArrayList<Object> getNetworkedData(ArrayList<Object> data);

    //Component
    public void tick();
    public void read(NBTTagCompound nbtTags);
    public void read(ByteBuf dataStream);
    public void write(NBTTagCompound nbtTags);
    public void write(ArrayList<Object> data);
    public void invalidate();
}
