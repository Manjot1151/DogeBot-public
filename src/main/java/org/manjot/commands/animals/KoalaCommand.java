package org.manjot.commands.animals;

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

public class KoalaCommand extends Command implements CommandListener {

    public KoalaCommand() {
        this.setName("koala")
                // .setAliases()
                .setDescription("Get koala images")
                .setUsage("koala")
                .setType(CommandType.ANIMALS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        String image = Animals.get("koala");
        embed.clear();
        embed.setTitle("Koalas!", image);
        embed.setImage(image);
        message.replyEmbeds(embed.build())
                .setActionRow(
                        Button.primary("koala", "Next"))
                .queue();
    }
}
