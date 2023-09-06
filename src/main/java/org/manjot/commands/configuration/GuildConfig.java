package org.manjot.commands.configuration;

import java.io.IOException;
import java.util.HashMap;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.manjot.Mongo;

public class GuildConfig {
    private static String defaultPrefix = "!";
    private static MongoCollection<Document> guilds;
    private static HashMap<String, String> prefixCache;

    public static void initialize() throws IOException {
        guilds = Mongo.client.getDatabase("guildConfig").getCollection("guilds");
        prefixCache = new HashMap<String, String>();
    }

    public static void removeGuild(String guildId) {
        guilds.deleteOne(Filters.eq("id", guildId));
        prefixCache.remove(guildId);
    }

    public static void setPrefix(String guildId, String prefix) throws IOException {
        guilds.updateOne(Filters.eq("id", guildId), Updates.set("prefix", prefix));
        if (prefixCache.size() == 20)
            prefixCache.clear();
        prefixCache.put(guildId, prefix);
    }

    public static void setWelcome(String guildId, String welcomeChannel) throws IOException {
        guilds.updateOne(Filters.eq("id", guildId),
                welcomeChannel == null ? Updates.unset("welcome") : Updates.set("welcome", welcomeChannel));
    }

    public static String getPrefix(String guildId) {
        if (prefixCache.containsKey(guildId)) {
            return prefixCache.get(guildId);
        } else {
            String prefix;
            Document g = guilds.find(Filters.eq("id", guildId)).first();
            if (g == null) {
                guilds.insertOne(new Document("id", guildId));
                prefix = defaultPrefix;
            } else {
                prefix = g.getOrDefault("prefix", defaultPrefix).toString();
            }
            prefixCache.put(guildId, prefix);
            return prefix;
        }
    }

    public static String getWelcome(String guildId) {
        Document g = guilds.find(Filters.eq("id", guildId)).first();
        if (g == null) {
            guilds.insertOne(new Document("id", guildId));
            return null;
        }
        return g.containsKey("welcome") ? g.getString("welcome") : null;
    }
}
