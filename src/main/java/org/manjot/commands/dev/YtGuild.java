package org.manjot.commands.dev;

import org.bson.Document;
import org.manjot.Mongo;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.Error;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class YtGuild extends Command implements CommandListener {

    public YtGuild() {
        this.setName("ytguild")
                .setAliases("ytg", "youtubeguild")
                .setDescription("Add/Remove a guild for youtube music access")
                .setUsage("ytguild <add/remove> <id>")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length < 2) {
            replyInvalidUsage(message);
            return;
        }
        MongoCollection<Document> ytGuilds = Mongo.client.getDatabase("music")
                .getCollection("ytGuilds");
        
        // switch(args[0]) {
        //     case "add":
        //         guilds.insertOne(new Document("id", args[1]));
        //         message.reply("Added guild `" + args[1] + "` to whitelist!").queue();
        //         break;
        // }
        // String name = args[0].toLowerCase();
        String id = args[1];
        Guild g;
        try {
            g = jda.getGuildById(id);
        } catch (Exception e) {
            Error.replyException(message, e);
            return;
        }
        Document ytGuild = ytGuilds.find(Filters.eq("id", id)).first();
        switch(args[0]) {
           case "add" -> {
                if (ytGuild == null) {
                    ytGuilds.insertOne(new Document("id", id));
                    message.reply("Added YT music access to " + g.getName()).queue();
                } else {
                    message.reply("YT music access already exists for " + g.getName()).queue();
                }
            }
            case "remove" -> {
                if (ytGuild != null) {
                    ytGuilds.deleteOne(Filters.eq("id", id));
                    message.reply("Removed YT music access from " + g.getName()).queue();
                } else {
                    message.reply("YT music access doesn't exist for " + g.getName()).queue();
                }
            }
            default -> {
                replyInvalidUsage(message);
            }
        }
    }
}
