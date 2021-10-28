package top.evanechecssss.qte.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.common.DimensionManager;
import top.evanechecssss.qte.QTE;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordUtil {

    public static void createCSFile(String fileName, List<String> list) throws IOException {
        File file = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + fileName + ".json");
        if (file.exists()) {
            QTE.getLogger().error("File already exist");
            return;
        }
        file.getParentFile().mkdirs();
        file.createNewFile();
        file.getParentFile().mkdirs();
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (String element : list) {
            array.add(element);
        }
        object.add("commands", array);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(object.toString());
            writer.flush();
        } catch (Exception ignored) {

        }
    }

    public static void removeCSFile(String fileName) throws IOException {
        File commandSet = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + fileName + ".json");
        commandSet.delete();
        commandSet.getParentFile().mkdirs();
    }

    public static List<String> parseCommands(String name) throws FileNotFoundException {
        List<String> list = new ArrayList<>();
        File file = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + name + ".json");
        if (!file.exists()) {
            QTE.getLogger().error("File don't exist");
            return Collections.emptyList();
        }
        JsonArray JSONArray = new JsonParser().parse(new FileReader(file)).getAsJsonObject().get("commands").getAsJsonArray();
        for (JsonElement element : JSONArray) {
            list.add(element.getAsString());
        }
        return list;
    }

    public static boolean checkExist(String name) {
        File file = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + name + ".json");

        return file.exists();

    }


    public static String readJSONFile(String name) throws FileNotFoundException {
        File file = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + name + ".json");
        if (!file.exists()) {
            QTE.getLogger().error("File don't exist");
            return null;
        }
        return new FileReader(file).toString();
    }

    public static List<String> parseJSONString(String string) {
        List<String> list = new ArrayList<>();
        JsonArray JSONArray = new JsonParser().parse(string).getAsJsonObject().get("commands").getAsJsonArray();
        for (JsonElement element : JSONArray) {
            list.add(element.getAsString());
        }
        return list;
    }
}
