package org.manjot.commands.configuration;

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
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WelcomeCommand extends Command implements CommandListener {

    public WelcomeCommand() {
        this.setName("welcome")
                .setAliases("welcomechannel", "joinchannel")
                .setDescription("Set/remove a welcome channel")
                .setUsage("welcome <set/remove> (#channel)")
                .setPermissions(Permission.MANAGE_SERVER)
                .setType(CommandType.CONFIGURATION)
                .setCooldown(5);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        switch (args[0]) {
            case "set": {
                try {
                    if (args.length == 1) {
                        replyInvalidUsage(message);
                        return;
                    }
                    TextChannel welcomeChannel = (message.getMentions().getChannels().size() != 0)
                            ? message.getMentions().getChannels(TextChannel.class).get(0)
                            : guild.getTextChannelById(args[1]);
                    if (!welcomeChannel.canTalk()) {
                        Error.replyMessage(message,
                                "I do not have permissions to send messages in " + welcomeChannel.getAsMention() + "!");
                        break;
                    }
                    GuildConfig.setWelcome(guild.getId(), welcomeChannel.getId());
                    message.reply(welcomeChannel.getAsMention() + " has been set as the default welcome channel!")
                            .queue();
                } catch (Exception e) {
                    Error.replyMessage(message, "Invalid channel!");
                }
                break;
            }

            case "remove": {
                if (GuildConfig.getWelcome(guild.getId()) != null) {
                    try {
                        GuildConfig.setWelcome(guild.getId(), null);
                    } catch (Exception e) {
                        Error.replyMessage(message, "Could not remove the welcome channel!");
                        e.printStackTrace();
                        return;
                    }
                    message.reply("Welcome channel was removed!").queue();
                } else
                    Error.replyMessage(message, "This server does not have a welcome channel set!");
                break;
            }

            default:
                Error.replyMessage(message,
                        "Invalid usage:\n`" + prefix + "welcome set {channel}`\n`" + prefix + "welcome remove`");
        }
    }
}