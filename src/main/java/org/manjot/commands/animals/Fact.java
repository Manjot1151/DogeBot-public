package org.manjot.commands.animals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Fact {
    private static String api = "https://some-random-api.ml/facts/";
    private static ArrayList<String> animals = new ArrayList<String>() {
        {
            add("dog");
            add("cat");
            add("panda");
            add("fox");
            add("bird");
            add("koala");
        }
    };

    private static String getFact(String animal) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(api + animal);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            JsonObject image = JsonParser.parseString(buffer.toString()).getAsJsonObject();
            return image.get("fact").getAsString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static String get(String animal) {
        if (animals.indexOf(animal) != -1)
            try {
                return getFact(animal);
            } catch (Exception e) {
                e.printStackTrace();
                return "Could not find any facts :(";
            }

        return "Could not find any facts :(";
    }
}