package org.manjot.commands.dev;

import org.bson.Document;
import org.manjot.Mongo;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WhitelistSubreddit extends Command implements CommandListener {

    public WhitelistSubreddit() {
        this.setName("whitelistsubreddit")
                .setAliases("whitelistsub", "whitesub", "wlsub", "wls")
                .setDescription("Whitelits a subreddit for meme command")
                .setUsage("whitelistsubreddit <subreddit>")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        MongoCollection<Document> subreddits = Mongo.client.getDatabase("reddit")
                .getCollection("whitelist");
        String name = args[0].toLowerCase();
        Document subreddit = subreddits.find(Filters.eq("name", name)).first();
        if (subreddit == null) {
            subreddits.insertOne(new Document("name", name));
            message.reply("Added subreddit `" + name + "` to whitelist!").queue();
        } else {
            subreddits.deleteOne(Filters.eq("name", name));
            message.reply("Removed subreddit `" + name + "` from whitelist!").queue();
        }
    }
}
