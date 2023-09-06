package org.manjot.commands.animals;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class CatCommand extends Command implements CommandListener {

    public CatCommand() {
        this.setName("cat")
                .setAliases("cats", "catto", "cattos")
                .setDescription("Get cat images")
                .setUsage("cat")
                .setType(CommandType.ANIMALS);
    }

    public static String getRandomCat() throws Exception {
        String catUrl = "https://api.thecatapi.com/v1/images/search";
        return Utils.readJsonUrl(catUrl).getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        String catImage;
        try {
            catImage = getRandomCat();
        } catch (Exception e) {
            Error.replyMessage(message, "Could not fetch images");
            e.printStackTrace();
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Cats!", catImage);
        embed.setImage(catImage);
        // embed.setFooter(Fact.get("cat"));
        // embed.setDescription("[Image](" + catImage + ")");
        message.replyEmbeds(embed.build())
                .setActionRow(
                        Button.primary("cat", "Next"))
                .queue();

    }
}