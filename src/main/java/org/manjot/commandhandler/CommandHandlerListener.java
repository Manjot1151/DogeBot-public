package org.manjot.commandhandler;

import java.util.Arrays;
import java.util.List;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commands.configuration.GuildConfig;
import org.manjot.events.PrivateMessageReceived;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandlerListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) {
            if (event.getChannelType() == ChannelType.PRIVATE) {
                PrivateMessageReceived.onPrivateMessageReceived(event);
            }
            return;
        }

        User author = event.getAuthor();
        Message message = event.getMessage();

        if (author.isBot() || message.isWebhookMessage()) {
            return;
        }

        String messageContent = message.getContentRaw();
        String botId = event.getJDA().getSelfUser().getId();

        // if starts with bot mention
        boolean isMention = false;
        if (messageContent.startsWith("<@" + botId + ">")) {
            messageContent = messageContent.substring(botId.length() + 3).trim();
            isMention = true;
        } else if (messageContent.startsWith("<@!" + botId + ">")) {
            messageContent = messageContent.substring(botId.length() + 4).trim();
            isMention = true;
        }

        // if doesn't start with mention
        if (!isMention) {
            String prefix = GuildConfig.getPrefix(event.getGuild().getId());
            if (!messageContent.startsWith(prefix)) {
                return;
            }
            messageContent = messageContent.substring(prefix.length()).trim();
        }

        String[] args = messageContent.split(" ");

        Command command = CommandHandler.getCommandByName(args[0]);
        if (command != null) {
            handleCommand(command, event, args);
        }
    }

    public void handleCommand(Command command, MessageReceivedEvent event, String[] args) {
        JDA jda = event.getJDA();
        User author = event.getAuthor();
        Member member = event.getMember();
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        System.out.println(guild + " | " + channel + "\n" + author + ": " + message.getContentRaw());

        List<Permission> botNeededPermissions = command.getBotPermissions();
        if (!botNeededPermissions.isEmpty() && !guild.getSelfMember().hasPermission(botNeededPermissions)) {
            Error.replyBotMissingPermissions(message, command);
            return;
        }

        if (!Utils.isDev(author)) {
            if (command.getType() == CommandType.PRIVATE)
                return;

            List<Permission> neededPermissions = command.getPermissions();
            if (!neededPermissions.isEmpty() && !member.hasPermission(neededPermissions)) {
                Error.replyMissingPermissions(message, command);
                return;
            }

            if (!Cooldown.canRunGlobal(author, message)) {
                return;
            }

            if (!Cooldown.canRun(author, command, message)) {
                return;
            }
        }

        command.getListener().onCommand(jda, event, author, member, guild, channel, message,
                GuildConfig.getPrefix(event.getGuild().getId()), Arrays.copyOfRange(args, 1, args.length));
    }
}
