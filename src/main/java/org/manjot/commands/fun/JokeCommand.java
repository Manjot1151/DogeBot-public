package org.manjot.commands.fun;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class JokeCommand extends Command implements CommandListener {
    public JokeCommand() {
        this.setName("joke")
                .setAliases("jokes")
                .setDescription("Get a funny joke")
                .setUsage("joke")
                .setType(CommandType.FUN);
    }

    private static final String api = "https://v2.jokeapi.dev/joke/Any?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=twopart";

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel,
            Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        try {
            JsonObject joke = JsonParser.parseString(Utils.readUrl(api)).getAsJsonObject();
            embed.setTitle(joke.get("setup").getAsString());
            embed.setDescription(joke.get("delivery").getAsString());
            embed.setFooter("Category: " + joke.get("category").getAsString());
            message.replyEmbeds(embed.build()).queue();
        } catch (Exception e) {
            Error.replyMessage(message, "An error occured while fetching a joke!");
            e.printStackTrace();
        }
    }
}
