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

public class TweetCommand extends Command implements CommandListener {

    public TweetCommand() {
        this.setName("tweet")
                .setAliases("twitter")
                .setDescription("Make a user \"tweet\" something on twitter")
                .setUsage("tweet (@user) <message>")
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
        Member user = Utils.getMentionedMember(guild, args[0]);
        String tweet;
        if (user == null) {
            user = member;
            tweet = Utils.messageFrom(args, 0);
        } else {
            if (args.length == 1) {
                replyInvalidUsage(message);
                return;
            }
            tweet = Utils.messageFrom(args, 1);
        }
        String avatar = user.getEffectiveAvatarUrl();
        embed.setImage(Image.tweet(avatar, user.getUser().getName().replace("#", ""), user.getEffectiveName(), tweet));
        message.replyEmbeds(embed.build()).queue();
    }
}
