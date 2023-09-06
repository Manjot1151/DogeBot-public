package org.manjot.commands.image;

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

public class TriggeredCommand extends Command implements CommandListener {

    public TriggeredCommand() {
        this.setName("triggered")
                .setAliases("trigger")
                .setDescription("Add triggered filter to a user avatar")
                .setUsage("triggered (@user)")
                .setType(CommandType.IMAGE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        // temp
        Error.replyMessage(message, "Sorry! That command is temporarily disabled :(");
        return;
        //temp
/* 
        User u = author;
        if (args.length >= 1)
            u = Utils.getMentionedUserOrDefault(args[0], author);
        String avatar = u.getEffectiveAvatarUrl();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        try {
            embed.setImage("attachment://triggered.gif");
            embed.setAuthor(u.getName(), null, avatar);
            // message.reply(Image.triggered(avatar), "triggered.gif").setEmbeds(embed.build()).queue();
        } catch (Exception e) {
            Error.replyException(message, e);
        }
        */
    }
}
