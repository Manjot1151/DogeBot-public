package org.manjot.commands.fun;

import java.util.Random;

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

public class CoinFlipCommand extends Command implements CommandListener {

    public CoinFlipCommand() {
        this.setName("coinflip")
                .setAliases("cf")
                .setDescription("Flip a coin to get Heads or Tails")
                .setUsage("coinflip")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        message.reply("You got **" + (new Random().nextInt(2) == 0 ? "Heads" : "Tails") + "**!").queue();
    }

}
