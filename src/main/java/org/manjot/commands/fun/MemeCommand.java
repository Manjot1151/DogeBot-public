package org.manjot.commands.fun;

import java.io.FileNotFoundException;

import org.bson.Document;
import org.manjot.Error;
import org.manjot.Mongo;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MemeCommand extends Command implements CommandListener {

    public MemeCommand() {
        this.setName("meme")
                .setAliases("memes", "reddit")
                .setDescription("Get a meme from reddit")
                .setUsage("meme (subreddit)")
                .setType(CommandType.FUN);
    }

    private static String api = "https://meme-api.com/gimme";
    private static boolean devPerms = false;

    public static MessageEmbed getMeme(String subreddit, boolean channelIsNsfw) throws Exception {
        String url = api + (!subreddit.isEmpty() ? subreddit + "/10" : "10");
        JsonObject jsonObject = Utils.readJsonUrl(url).getAsJsonObject();
        JsonElement errorMessage = jsonObject.get("message");
        if (errorMessage != null) {
            throw new Exception(errorMessage.getAsString());
        }
        JsonArray jsonArray = jsonObject.get("memes").getAsJsonArray();
        JsonObject memeObject = null;
        for (JsonElement meme : jsonArray) {
            JsonObject object = meme.getAsJsonObject();
            boolean memeIsNsfw = object.get("nsfw").getAsBoolean();
            if ((memeIsNsfw && channelIsNsfw) || !memeIsNsfw) {
                memeObject = object;
                break;
            }
        }
        return getAsEmbed(memeObject);
    }

    public static boolean isWhitelisted(String subreddit) {
        MongoCollection<Document> subreddits = Mongo.client.getDatabase("reddit")
                .getCollection("whitelist");
        return subreddits.find(Filters.eq("name", subreddit.toLowerCase())).first() != null;
    }

    private static MessageEmbed getAsEmbed(JsonObject json) {
        EmbedBuilder embed = Utils.getDefaultEmbed();

        return embed.build();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        String subreddit = args.length >= 1 ? args[0] : "";

        if (subreddit.startsWith("r/")) {
            subreddit = subreddit.substring(2);
        }

        if (Utils.isDev(author) && subreddit.equals("!")) {
            devPerms = !devPerms;
            return;
        }

        if (!subreddit.matches("^(|\\w{3,21})$")) {
            Error.replyMessage(message, "This subreddit doesn't exist.");
            return;
        }

        boolean canSendNsfwMeme = Utils.isNSFW(channel) || (Utils.isDev(author) && devPerms);
        int memeCount = canSendNsfwMeme ? 1 : 10;
        String url = api + "/" + (subreddit.isEmpty() ? "" : subreddit + "/") + memeCount;

        JsonObject jsonObject;
        try {
            jsonObject = Utils.readJsonUrl(url).getAsJsonObject();
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                Error.replyMessage(message, "This subreddit has no posts or doesn't exist.");
                return;
            }
            Error.replyMessage(message, "An error occured while fetching memes.");
            return;
        }

        // API returned an error
        if (jsonObject.has("code")) {
            Error.replyMessage(message, jsonObject.get("message").getAsString());
            return;
        }

        // subreddit is not whitelisted && cant send nsfw meme
        if (!subreddit.isEmpty() && !isWhitelisted(subreddit) && !canSendNsfwMeme) {
            Error.replyMessage(message,
                    "That subreddit is not whitelisted!\nYou can suggest that subreddit to be whitelisted on our [Support Server](https://discord.gg/W8hnfK5czn) or use an NSFW channel to unlock all subreddits");
            return;
        }

        JsonArray memeArray = jsonObject.get("memes").getAsJsonArray();
        JsonObject memeObject = null;
        if (canSendNsfwMeme) {
            memeObject = memeArray.get(0).getAsJsonObject();
        } else
            for (JsonElement meme : memeArray) {
                if (!meme.getAsJsonObject().get("nsfw").getAsBoolean()) {
                    memeObject = meme.getAsJsonObject();
                    break;
                }
            }

        if (memeObject == null) {
            Error.replyMessage(message, "Could not find any non-nsfw memes on that subreddit.");
        }

        EmbedBuilder embed = Utils.getDefaultEmbed();

        String upvoteEmoji = "<:upvote:731886006474768425>";
        String downvoteEmoji = "<:downvote:731886006139355167>";
        embed.setTitle(memeObject.get("title").getAsString(), memeObject.get("postLink").getAsString())
                .setDescription(upvoteEmoji + " " + memeObject.get("ups").getAsInt() + " " + downvoteEmoji)
                .setImage(memeObject.get("url").getAsString())
                .setFooter("Posted in r/" + memeObject.get("subreddit").getAsString() + " by u/"
                        + memeObject.get("author").getAsString());

        message.replyEmbeds(embed.build()).queue();

        // String subreddit = args.length >= 1 ? args[0] : "";
        // if (Utils.isDev(author) && subreddit.equals("!")) {
        // allowMe();
        // return;
        // }

        // if (Utils.isNSFW(channel) || (Utils.isDev(author) && meAllowed)) {
        // try {
        // message.replyEmbeds(getMeme(subreddit, true)).queue();
        // } catch (Exception e) {
        // Error.replyMessage(message, "An error occurred while fetching memes!");
        // }

        // } else if (isWhitelisted(subreddit) || subreddit.isEmpty()) {
        // try {
        // message.replyEmbeds(getMeme(subreddit, false)).queue();
        // } catch (Exception e) {
        // Error.replyMessage(message, "An error occurred while fetching memes!");
        // }
        // } else {
        // Error.replyMessage(message,
        // "That subreddit is not whitelisted!\nYou can suggest that subreddit to be
        // whitelisted on our [Support Server](https://discord.gg/W8hnfK5czn) or use an
        // NSFW channel to unlock all subreddits");
        // }
    }
}
