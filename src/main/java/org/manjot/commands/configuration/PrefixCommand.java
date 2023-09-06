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
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PrefixCommand extends Command implements CommandListener {

    public PrefixCommand() {
        this.setName("prefix")
                .setAliases("dogeprefix")
                .setDescription("Change the bot prefix in your server")
                .setUsage("prefix <prefix>")
                .setPermissions(Permission.MANAGE_SERVER)
                .setType(CommandType.CONFIGURATION)
                .setCooldown(5);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length != 1) {
            replyInvalidUsage(message);
            return;
        }

        if (args[0].length() > 8) {
            Error.replyMessage(message, "Prefix cannot be longer than 8 characters!");
            return;
        }

        try {
            GuildConfig.setPrefix(guild.getId(), args[0]);
        } catch (Exception e) {
            Error.replyMessage(message, "Could not set the prefix!");
            e.printStackTrace();
        }
        message.reply("The prefix has been set to `" + args[0] + "`").queue();
    }

}
