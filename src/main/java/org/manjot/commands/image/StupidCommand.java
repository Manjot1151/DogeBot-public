package org.manjot.commands.image;

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

public class StupidCommand extends Command implements CommandListener {

    public StupidCommand() {
        this.setName("stupid")
                .setDescription("Make a user say something stupid")
                .setUsage("stupid (@user) <message>")
                .setType(CommandType.IMAGE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        User u = Utils.getMentionedUser(args[0]);
        ;
        String text;
        if (u == null) {
            u = author;
            text = Utils.messageFrom(args, 0);
        } else {
            if (args.length == 1) {
                replyInvalidUsage(message);
                return;
            }
            text = Utils.messageFrom(args, 1);
        }
        String avatar = u.getEffectiveAvatarUrl();
        embed.setImage(Image.stupid(avatar, text));
        embed.setAuthor(u.getName(), null, avatar);
        message.replyEmbeds(embed.build()).queue();
    }
}
