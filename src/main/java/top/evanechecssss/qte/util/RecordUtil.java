package top.evanechecssss.qte.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.minecraftforge.common.DimensionManager;
import top.evanechecssss.qte.QTE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class RecordUtil {

    public static void createCSFile(String fileName, List<String> list) throws IOException {
        File file = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + fileName + ".json");
        file.getParentFile().mkdirs();
        file.createNewFile();
        file.getParentFile().mkdirs();
        Path jsonFile = Paths.get(file.getPath());
        JsonArray users = new JsonArray();
        if (Files.isRegularFile(jsonFile)) {
            try (JsonReader r = new JsonReader(Files.newBufferedReader(jsonFile))) {
                r.beginArray();
                while (r.hasNext()) {
                    JsonObject user = new JsonObject();
                    r.beginObject();
                    r.nextName();
                    user.addProperty("name", r.nextString());
                    r.nextName();
                    user.addProperty("surname", r.nextString());
                    r.nextName();
                    user.addProperty("age", r.nextInt());
                    r.endObject();
                    users.add(user);
                }
                r.endArray();
            }
        }
        JsonObject user = new JsonObject();
        user.addProperty("name", list.get(0));
        user.addProperty("surname", list.get(1));
        user.addProperty("age", list.get(2));
        users.add(user);

        try (final BufferedWriter w = Files.newBufferedWriter(jsonFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            w.write(users.toString());
        } catch (Exception e) {
            QTE.getLogger().error("ERROR");
        }
    }

    public static void removeCSFile(String fileName) throws IOException {
        File commandSet = new File(DimensionManager.getCurrentSaveRootDirectory() + "/qte/commandSets/" + fileName + ".json");
        commandSet.getParentFile().mkdirs();
        commandSet.delete();
        commandSet.getParentFile().mkdirs();
    }

}
