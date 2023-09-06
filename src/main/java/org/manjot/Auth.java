package org.manjot;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Auth {
    public static String token;
    public static String mongo;
    public static String topgg;
    public static String hypixel;
    public static String testToken;
    public static void initialize() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("auth.json"));
        JsonObject auth = JsonParser.parseReader(reader).getAsJsonObject();
        token = auth.get("token").getAsString();
        mongo = auth.get("mongo").getAsString();
        topgg = auth.get("topgg").getAsString();
        hypixel = auth.get("hypixel").getAsString();
        testToken = auth.get("test-token").getAsString();
    }
}
