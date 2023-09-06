package org.manjot.commands.misc;

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
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class InviteCommand extends Command implements CommandListener {
    public InviteCommand() {
        this.setName("invite")
                .setAliases("inv", "botinvite", "botinv")
                .setDescription("Get invite link for the bot")
                .setUsage("invite")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        message.reply("Click the button below to invite me!").setActionRow(
                Button.link(
                        "https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=1512637328502&scope=bot%20applications.commands",
                        "Invite"))
                .queue();

    }
}
