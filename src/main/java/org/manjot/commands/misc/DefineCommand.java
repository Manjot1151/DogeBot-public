package org.manjot.commands.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class DefineCommand extends Command implements CommandListener {
    public DefineCommand() {
        this.setName("define")
                .setAliases("urban", "definition", "dictionary")
                .setDescription("Get definition of a word or a phrase")
                .setUsage("define <word/phrase>")
                .setType(CommandType.MISCELLANEOUS)
                .setCooldown(2);
    }

    private static HashMap<String, JsonArray> wordData = new HashMap<String, JsonArray>();

    public static MessageEmbed get(String word, int index) throws Exception {

        JsonArray jsonList = getData(word);
        if (jsonList.size() == 0)
            return null;
        JsonObject jsonData = jsonList.get(index).getAsJsonObject();
        jsonData.addProperty("index", index);
        jsonData.addProperty("pages", jsonList.size());
        Gson gson = new Gson();
        UrbanInfo info = gson.fromJson(jsonData, UrbanInfo.class);
        if (wordData.size() == 20)
            wordData.clear();
        return getAsEmbed(info, word);
    }

    private static JsonArray getData(String word) throws Exception {
        JsonArray jsonList = new JsonArray();
        if (wordData.containsKey(word)) {
            jsonList = wordData.get(word);
        } else {
            jsonList = fetchAPIData(word).get("list").getAsJsonArray();
            wordData.put(word, jsonList);
        }
        return jsonList;
    }

    private static MessageEmbed getAsEmbed(UrbanInfo info, String word) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(info.getWord(), info.getUrl());
        embed.setDescription("Search results for \"" + word + "\"");
        embed.addField("Definition", info.getDefinition(), false);
        embed.addField("Examples", info.getExample(), false);
        embed.addField("Author", info.getAuthor(), true);
        embed.addField("Creation Date", info.getCreationDate(), true);
        embed.addField("Rating", info.getRating(), false);
        // embed.setThumbnail("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRf1zMBUf8t5Orb6d2TQVK624xgoYupJ-BCEnXv_tkYORmZ4BU6xsyXiGb2iRpRlPUhrrU&usqp=CAU");
        embed.setFooter("Page " + (info.getPage() + 1) + " of " + info.getTotalPages());
        return embed.build();
    }

    private static JsonObject fetchAPIData(String word) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.urbandictionary.com/v0/define?term=" + URLEncoder.encode(word, StandardCharsets.UTF_8));
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return JsonParser.parseString(buffer.toString()).getAsJsonObject();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        try {
            // ⏪◀️▶️⏩
            MessageEmbed urbanEmbed = get(Utils.messageFrom(args, 0), 0);
            Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪")).asDisabled();
            Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️")).asDisabled();
            Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️"));
            Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩"));
            Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
            String[] page = urbanEmbed.getFooter().getText().replace("Page ", "").replace(" of", "").split(" ");
            int totalPages = Integer.parseInt(page[1]);
            if (totalPages == 1)
                message.replyEmbeds(urbanEmbed).queue();
            else
                message.replyEmbeds(urbanEmbed).setActionRow(first, previous, next, last, delete).queue();
        } catch (Exception e) {
            Error.replyMessage(message, "Could not find any definitions for that word!");
        }
    }
}

class UrbanInfo {
    private String definition;
    private String permalink;
    private String thumbs_up;
    private String thumbs_down;
    private String author;
    private String word;
    private String example;
    private String written_on;
    private int index;
    private int pages;

    public UrbanInfo() {
    }

    public String getWord() {
        return word.replaceAll("(?=[*_|`])", "\\\\");
    }

    public String getUrl() {
        return permalink;
    }

    public String getDefinition() {
        definition = definition.replaceAll("(?=[*_|`])", "\\\\");
        definition = definition.replaceAll("(?:[\\[\\]]|  +)", "").trim();
        if (definition.length() > 1024)
            definition = definition.substring(0, 1021) + "...";
        return definition;
    }

    public String getRating() {
        return "<:thumbsup:868235992308514826> **" + thumbs_up + "** | <:thumbsdown:868236138358398986> **"
                + thumbs_down + "**";
    }

    public String getAuthor() {
        return author.replaceAll("(?=[*_|`])", "\\\\");
    }

    public String getCreationDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        return formatter.format(Date.from(Instant.parse(written_on)));
    }

    public String getExample() {
        example = example.replaceAll("\\[", "").replaceAll("\\]", "").trim().replaceAll(" +", " ")
                .replaceAll("(?=[*_|`])", "\\\\");
        if (example.length() > 1024)
            example = example.substring(0, 1021) + "...";
        return example.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    public int getPage() {
        return index;
    }

    public int getTotalPages() {
        return pages;
    }
}
