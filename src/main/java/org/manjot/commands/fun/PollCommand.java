package org.manjot.commands.fun;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PollCommand extends Command implements CommandListener {

    public PollCommand() {
        this.setName("poll")
                .setDescription("Create a poll")
                .setUsage("poll <text>")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }

        String poll = Utils.messageFrom(args, 0);
        String avatarUrl = author.getAvatarUrl() != null ? author.getAvatarUrl() : author.getDefaultAvatarUrl();

        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Poll");
        embed.setDescription(poll);
        embed.setAuthor(author.getName(), null, avatarUrl);
        embed.setFooter("ID: " + author.getId());

        Message pollMessage = channel.sendMessageEmbeds(embed.build()).complete();
        pollMessage.addReaction(Emoji.fromFormatted("a:tick:867456379455406120")).queue();
        pollMessage.addReaction(Emoji.fromFormatted("a:cross:867456397276479498")).queue();

        if (guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            message.delete().queue();
        }

    }
}
