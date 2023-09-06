package org.manjot.commands.fun;

import java.util.concurrent.TimeUnit;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.AllowedMentions;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SayCommand extends Command implements CommandListener {

    public SayCommand() {
        this.setName("say")
                .setDescription("Make the bot/webhook say a message")
                .setUsage("say (@user) <text>")
                .setType(CommandType.FUN);
    }

    public static void send(MessageReceivedEvent event, User user, String msg) {
        // for (Member m : event.getMessage().getMentionedMembers())
        // {
        // String id = m.getId();
        // msg = msg.replaceAll("<@" + id + ">", "@" + m.getEffectiveName());
        // msg = msg.replaceAll("<@!" + id + ">", "@" + m.getEffectiveName());
        // }
        // for (Role r : event.getMessage().getMentionedRoles())
        // {
        // String id = r.getId();
        // msg = msg.replaceAll("<@&" + id + ">", "@" + r.getName());
        // }
        // msg = msg.replaceAll("@", "@​");
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        User bot = jda.getSelfUser();
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        User user = bot;
        String msg = "";
        try {
            if (guild.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE))
                message.delete().queue();
            try {
                user = Utils.getMentionedUser(args[0]);
                if (args.length == 1)
                    throw new Exception();
                msg = Utils.messageFrom(args, 1);
                if (user.equals(bot) || !guild.getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS) || channel instanceof ThreadChannel)
                    throw new Exception();

            } catch (Exception e) {
                msg = Utils.messageFrom(args, 0);
                channel.sendMessage(msg).queue();
                return;
            }
            message.delete().queue();
        } catch (Exception e) {
            Error.replyException(message, e);
        }

        String name;
        if (guild.retrieveMember(user).complete() == null)
            name = user.getName();
        else
            name = guild.retrieveMember(user).complete().getEffectiveName();
        Webhook webhook = ((TextChannel) channel).createWebhook("say-command").complete();
        WebhookClient client = WebhookClient.withUrl(webhook.getUrl());
        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder();
        messageBuilder.setAllowedMentions(AllowedMentions.none());
        messageBuilder.setUsername(name);
        messageBuilder.setAvatarUrl(user.getEffectiveAvatarUrl());
        messageBuilder.setContent(msg);
        client.send(messageBuilder.build());
        client.close();
        webhook.delete().queueAfter(2, TimeUnit.SECONDS);
    }
}
