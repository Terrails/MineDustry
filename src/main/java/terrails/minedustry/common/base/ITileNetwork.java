package terrails.minedustry.common.base;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

public interface ITileNetwork
    {

        public void handlePacketData(ByteBuf dataStream) throws Exception;

        public ArrayList<Object> getNetworkedData(ArrayList<Object> data);
    }
