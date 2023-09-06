package org.manjot.commands.misc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TranslateCommand extends Command implements CommandListener {
    public TranslateCommand() {
        this.setName("translate")
                // .setAliases()
                .setDescription("Translate a text to a specified langauge")
                .setUsage("translate <to> <text>")
                .setType(CommandType.MISCELLANEOUS)
                .setCooldown(5);
    }

    public static MessageEmbed createEmbed(String to, String text) throws Exception {
        String url = "https://just-translated.p.rapidapi.com/?lang=" + to + "&text="
                + URLEncoder.encode(text, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-rapidapi-host", "just-translated.p.rapidapi.com")
                .header("x-rapidapi-key", "d3efcf586bmsh85e35eb0940a3f6p15caccjsnbdf1ffa6d539")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject translation = JsonParser.parseString(response.body()).getAsJsonObject();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(translation.get("lang").getAsString());
        embed.addField("Original", ">>> ```" + text + "```", false);
        embed.addField("Translated", ">>> ```" + translation.get("text").getAsJsonArray().get(0).getAsString() + "```",
                false);
        return embed.build();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length < 2) {
            replyInvalidUsage(message);
            return;
        }
        JsonObject languageJson;
        try {
            languageJson = JsonParser
                    .parseReader(new FileReader("src/main/java/org/manjot/commands/misc/translateLanguages.json"))
                    .getAsJsonObject();
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            e.printStackTrace();
            Error.replyMessage(message, "Unexpected error...");
            return;
        }

        try {
            args[0] = args[0].toLowerCase();
            JsonElement languageElement = languageJson.get(args[0]);
            String languageCode;
            if (languageElement == null) {
                if (args[0].length() != 2 && args[0].length() != 3) {
                    Error.replyMessage(message, "That language doesn't seem to be supported!");
                    return;
                }
                languageCode = args[0];
            } else {
                languageCode = languageElement.getAsString();
            }
            message.replyEmbeds(createEmbed(languageCode, Utils.messageFrom(args, 1))).queue();
        } catch (Exception e) {
            e.printStackTrace();
            Error.replyMessage(message, "An error occured while translating!");
        }
    }
}
