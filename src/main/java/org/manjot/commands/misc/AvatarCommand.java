package org.manjot.commands.misc;

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

public class AvatarCommand extends Command implements CommandListener {
    public AvatarCommand() {
        this.setName("avatar")
                .setAliases("av", "pfp")
                .setDescription("Get avatar image of a user")
                .setUsage("avatar (@user/userID)")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        User user = author;
        if (args.length >= 1)
            user = Utils.getMentionedUserOrDefault(args[0], author);
        EmbedBuilder embed = Utils.getDefaultEmbed();
        String avatarUrl = user.getEffectiveAvatarUrl() + "?size=4096";
        embed.setAuthor(user.getName(), null, avatarUrl)
                .setDescription(user.getAsMention())
                .setImage(avatarUrl);
        message.replyEmbeds(embed.build()).queue();
    }
}
