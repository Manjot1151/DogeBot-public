package org.manjot.commands.moderation;

import org.manjot.Error;
import org.manjot.Utils;
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

public class UnbanCommand extends Command implements CommandListener {

    public UnbanCommand() {
        this.setName("unban")
                .setAliases("pardon")
                .setDescription("Unban a specified user")
                .setUsage("unban <userID>")
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
        try {
            User userToUnban = Utils.getMentionedUser(args[0]);
            if (userToUnban == null) {
                Error.replyMessage(message, "Could not find that user!");
                return;
            }
            guild.retrieveBan(userToUnban).complete();
            guild.unban(userToUnban).queue();
            message.reply("Unbanned user `" + userToUnban.getName() + "`").queue();
        } catch (Exception e) {
            Error.replyException(message, e);
        }
    }
}
