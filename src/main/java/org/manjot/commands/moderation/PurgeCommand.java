package org.manjot.commands.moderation;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.manjot.Error;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PurgeCommand extends Command implements CommandListener {

    public PurgeCommand() {
        this.setName("purge")
                // .setAliases()
                .setDescription("Purge a specified amount of messages from your channel")
                .setUsage("purge <count>")
                .setPermissions(Permission.MESSAGE_MANAGE)
                .setBotPermissions(Permission.MESSAGE_MANAGE)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        int deleteCount = 0;
        try {
            deleteCount = Integer.parseInt(args[0]) + 1;
        } catch (Exception e) {
            replyInvalidUsage(message);
            return;
        }
        if (deleteCount <= 1) {
            Error.replyMessage(message, "Purge count should be greater than 0");
            return;
        }
        try {
            List<Message> purgeMessages = channel.getIterableHistory().takeAsync(deleteCount).get();
            purgeMessages.removeIf(m -> m.getTimeCreated().isBefore(OffsetDateTime.now().minus(2, ChronoUnit.WEEKS)));
            channel.purgeMessages(purgeMessages);
            message.reply("Purging `" + (purgeMessages.size() - 1) + "` messages...")
                    .queue(m -> m.delete().queueAfter(2, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException e) {
            Error.replyException(message, e);
        }
    }
}