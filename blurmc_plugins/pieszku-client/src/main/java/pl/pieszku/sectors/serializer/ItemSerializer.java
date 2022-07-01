package pl.pieszku.sectors.serializer;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSerializer {

    public static String decodeItems(ItemStack[] itemStacks){
        if(itemStacks == null)return null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStacks);
            String encoded = Base64Coder.encodeLines(outputStream.toByteArray());
            outputStream.close();
            dataOutput.close();
            return encoded;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save itemstack array", e);
        }
    }

    public static ItemStack[] encodeItem(String encode)  {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(encode));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] read = (ItemStack[]) dataInput.readObject();
            inputStream.close();
            dataInput.close();
            return read;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return new ItemStack[]{};
    }

    public static byte[] serializeBukkitRaw(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BukkitObjectOutputStream out = null;
        try {
            out = new BukkitObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();

            return bos.toByteArray();

        } catch (Throwable e) {

            e.printStackTrace();
            return null;

        } finally {
            try {
                bos.close();
                out.close();
            } catch (Throwable ex) {
            }
        }

    }

    public static <T> T deserializeBukkitRaw(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BukkitObjectInputStream oit = null;


        try {
            oit = new BukkitObjectInputStream(bis);
            ItemStack[] itemStacks = (ItemStack[]) oit.readObject();
            List<ItemStack> itemStackList = new ArrayList<>(Arrays.asList(itemStacks));
            itemStacks = itemStackList.toArray(new ItemStack[]{});
            return (T) itemStacks;

        } catch (Throwable e) {
            return null;

        } finally {
            try {
                bis.close();
                oit.close();
            } catch (Throwable ex) {
            }
        }

    }

    public static <T> byte[] decodeItem(T object) {
        return serializeBukkitRaw(object);
    }
    public static <T> T encodeItems(byte[] bytes) {
        return deserializeBukkitRaw(bytes);
    }
}
