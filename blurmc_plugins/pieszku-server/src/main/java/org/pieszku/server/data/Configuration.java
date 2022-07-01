package org.pieszku.server.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Configuration<T> {

    private final String folderName;
    private final String configName;
    private final Class<T> tClass;
    private final File file;

    public Configuration(String folderName,String configName, Class<T> tClass){
        this.folderName = folderName;
        this.configName = configName;
        this.tClass = tClass;
        this.file = new File(configName + ".json");
    }
    public void init(){
        if(!file.exists()){
            try {
                ClassLoader classLoader = this.tClass.getClassLoader();
                InputStream stream = classLoader.getResourceAsStream(this.configName + ".json");

                if (!file.createNewFile())
                    return;

                PrintWriter pw = new PrintWriter(new FileWriter(file));
                InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);
                for (String line; (line = reader.readLine()) != null;)
                    pw.println(line);

                pw.close();
                reader.close();
                streamReader.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public T getConfiguration(){
        try {
            JsonReader reader = new JsonReader(new FileReader(file.getPath()));
            Gson gson = new GsonBuilder().create();

            return gson.fromJson(reader, this.tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
