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

public class RollCommand extends Command implements CommandListener {

    public RollCommand() {
        this.setName("roll")
                // .setAliases()
                .setDescription("Roll a die")
                .setUsage("coinflip (faces)")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        int faces = 6;
        try {
            if (args.length >= 1)
                faces = Integer.parseInt(args[0]);
            if (faces <= 0)
                throw new Exception("Die faces must be positive");
        } catch (Exception e) {
            faces = 6;
        }
        message.reply("You rolled **" + (new Random().nextInt(faces) + 1) + "**!").queue();
    }

}
