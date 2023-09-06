package org.manjot.commands.moderation;

import java.util.concurrent.TimeUnit;

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

public class BanCommand extends Command implements CommandListener {

    public BanCommand() {
        this.setName("ban")
                .setAliases("banish")
                .setDescription("Ban a specified member")
                .setUsage("ban <@member> (reason)")
                .setPermissions(Permission.BAN_MEMBERS)
                .setBotPermissions(Permission.BAN_MEMBERS)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        Member memberToBan;
        memberToBan = Utils.getMentionedMember(guild, args[0]);
        if (memberToBan == null) {
            Error.replyMessage(message, "Invalid user!");
            return;
        }
        if (memberToBan.equals(member)) {
            Error.replyMessage(message, "Why would you do that?");
            return;
        }
        if (!member.canInteract(memberToBan)) {
            Error.replyMessage(message, "You cannot interact with that member!");
            return;
        }
        if (!guild.getSelfMember().canInteract(memberToBan)) {
            Error.replyMessage(message, "I cannot interact with that member!");
            return;
        }
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Banned User")
                .setDescription(memberToBan.getAsMention())
                .setThumbnail(memberToBan.getEffectiveAvatarUrl());
        if (args.length >= 2) {
            String reason = Utils.messageFrom(args, 1);
            memberToBan.ban(0, TimeUnit.SECONDS).reason(reason).queue();
            embed.appendDescription("\nReason: " + reason);
        } else
            memberToBan.ban(0, TimeUnit.SECONDS).queue();

        message.replyEmbeds(embed.build()).queue();

    }
}
