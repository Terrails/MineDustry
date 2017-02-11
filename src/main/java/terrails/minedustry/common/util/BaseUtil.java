package terrails.minedustry.common.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import terrails.minedustry.MineDustry;

public class BaseUtil {

    /* MOD UTILS */
    @Deprecated
    public static String getModName(Item item) {

        return item.getRegistryName().getResourcePath();
    }

    /* PLAYER UTILS */
    public static EntityPlayer getClientPlayer() {

        return MineDustry.proxy.getClientPlayer();
    }

    public static boolean isPlayer(EntityPlayer player) {

        return player instanceof EntityPlayerMP;
    }

    public static boolean isFakePlayer(EntityPlayer player) {

        return (player instanceof FakePlayer);
    }

    public static boolean isOp(EntityPlayer player) {

        return MineDustry.proxy.isOp(player.getName());
    }

    public static boolean isOp(String playerName) {

        return MineDustry.proxy.isOp(playerName);
    }

    /* SERVER UTILS */
    public static boolean isClient() {

        return MineDustry.proxy.isClient();
    }

    public static boolean isServer() {

        return MineDustry.proxy.isServer();
    }

    /* ENTITY UTILS */
    public static boolean dropItemStackIntoWorld(ItemStack stack, World world, Vec3d pos) {

        return dropItemStackIntoWorld(stack, world, pos, false);
    }

    public static boolean dropItemStackIntoWorldWithVelocity(ItemStack stack, World world, BlockPos pos) {

        return dropItemStackIntoWorld(stack, world, new Vec3d(pos), true);
    }

    public static boolean dropItemStackIntoWorldWithVelocity(ItemStack stack, World world, Vec3d pos) {

        return dropItemStackIntoWorld(stack, world, pos, true);
    }

    public static boolean dropItemStackIntoWorld(ItemStack stack, World world, Vec3d pos, boolean velocity) {

        if (stack == null) {
            return false;
        }
        float x2 = 0.5F;
        float y2 = 0.0F;
        float z2 = 0.5F;

        if (velocity) {
            x2 = world.rand.nextFloat() * 0.8F + 0.1F;
            y2 = world.rand.nextFloat() * 0.8F + 0.1F;
            z2 = world.rand.nextFloat() * 0.8F + 0.1F;
        }
        EntityItem entity = new EntityItem(world, pos.xCoord + x2, pos.yCoord + y2, pos.zCoord + z2, stack.copy());

        if (velocity) {
            entity.motionX = (float) world.rand.nextGaussian() * 0.05F;
            entity.motionY = (float) world.rand.nextGaussian() * 0.05F + 0.2F;
            entity.motionZ = (float) world.rand.nextGaussian() * 0.05F;
        } else {
            entity.motionY = -0.05F;
            entity.motionX = 0;
            entity.motionZ = 0;
        }
        world.spawnEntity(entity);

        return true;
    }


}
