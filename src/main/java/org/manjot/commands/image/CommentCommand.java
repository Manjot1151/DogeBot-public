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

public class CommentCommand extends Command implements CommandListener {

    public CommentCommand() {
        this.setName("comment")
                .setAliases("youtubecomment", "ytcomment")
                .setDescription("Make a user \"comment\" something on youtube")
                .setUsage("comment (@user) <comment>")
                .setType(CommandType.IMAGE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        User u = Utils.getMentionedUser(args[0]);
        ;
        String comment;
        EmbedBuilder embed = Utils.getDefaultEmbed();
        if (u == null) {
            u = author;
            comment = Utils.messageFrom(args, 0);
        } else {
            if (args.length == 1) {
                replyInvalidUsage(message);
                return;
            }
            comment = Utils.messageFrom(args, 1);
        }
        String avatar = u.getEffectiveAvatarUrl();
        embed.setImage(Image.comment(avatar, u.getName(), comment));
        embed.setAuthor(u.getName(), null, avatar);
        message.replyEmbeds(embed.build()).queue();
    }
}
