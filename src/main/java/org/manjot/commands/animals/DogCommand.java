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

public class DogCommand extends Command implements CommandListener {

    public DogCommand() {
        this.setName("dog")
                .setAliases("dogs", "doggo", "doggos")
                .setDescription("Get dog images")
                .setUsage("dog")
                .setType(CommandType.ANIMALS);
    }

    public static String getRandomDog() throws Exception {
        String dogUrl = "https://api.thedogapi.com/v1/images/search";
        return Utils.readJsonUrl(dogUrl).getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        String dogImage;
        try {
            dogImage = getRandomDog();
        } catch (Exception e) {
            Error.replyMessage(message, "Could not fetch images");
            e.printStackTrace();
            return;
        }
        embed.setTitle("Dogs!", dogImage);
        embed.setImage(dogImage);
        // embed.setFooter(Fact.get("dog"));
        // embed.setDescription("[Image](" + dogImage + ")");
        message.replyEmbeds(embed.build())
                .setActionRow(
                        Button.primary("dog", "Next"))
                .queue();
    }
}