package org.manjot.commands.dev;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DmCommand extends Command implements CommandListener {

    public DmCommand() {
        this.setName("dm")
                .setAliases("directmessage")
                .setDescription("DM a user")
                .setUsage("dm <user> <message>")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        Utils.getMentionedUser(args[0]).openPrivateChannel()
                .flatMap(dmChannel -> dmChannel.sendMessage(Utils.messageFrom(args, 1))).queue();
        message.delete().queue();
    }
}
