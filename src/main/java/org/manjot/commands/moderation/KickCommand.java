package org.manjot.commands.moderation;

import org.manjot.Error;
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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class KickCommand extends Command implements CommandListener {

    public KickCommand() {
        this.setName("kick")
                // .setAliases()
                .setDescription("Kick a specified member")
                .setUsage("kick <@member>")
                .setPermissions(Permission.KICK_MEMBERS)
                .setBotPermissions(Permission.KICK_MEMBERS)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        Member memberToKick = Utils.getMentionedMember(guild, args[0]);
        if (memberToKick == null) {
            Error.replyMessage(message, "Invalid user!");
            return;
        }
        if (memberToKick.equals(member)) {
            Error.replyMessage(message, "Try leaving the server instead");
            return;
        }
        if (!member.canInteract(memberToKick)) {
            Error.replyMessage(message, "You cannot interact with that member!");
            return;
        }
        if (!guild.getSelfMember().canInteract(memberToKick)) {
            Error.replyMessage(message, "I cannot interact with that member!");
            return;
        }
        memberToKick.kick().queue();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Kicked User")
                .setDescription(memberToKick.getAsMention())
                .setThumbnail(memberToKick.getEffectiveAvatarUrl());
        message.replyEmbeds(embed.build()).queue();
    }
}
