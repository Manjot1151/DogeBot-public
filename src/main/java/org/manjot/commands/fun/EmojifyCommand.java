package org.manjot.commands.fun;

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

public class EmojifyCommand extends Command implements CommandListener {

    public EmojifyCommand() {
        this.setName("emojify")
                .setAliases("emoji")
                .setDescription("Convert text into emojis")
                .setUsage("emojify <text>")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }

        String[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

        StringBuilder emojify = new StringBuilder();
        for (char c : Utils.messageFrom(args, 0).toCharArray()) {
            if (Character.isAlphabetic(c)) {
                emojify.append(":regional_indicator_" + Character.toLowerCase(c) + ":");
            } else if (Character.isDigit(c)) {
                emojify.append(":" + digits[c - '0'] + ":");
            } else {
                switch (c) {
                    case ' ' -> {
                        emojify.append("   ");
                    }
                    case '!' -> {
                        emojify.append(":grey_exclamation:");
                    }
                    case '?' -> {
                        emojify.append(":grey_question:");
                    }
                    case '#' -> {
                        emojify.append(":hash:");
                    }
                    case '*' -> {
                        emojify.append(":asterisk:");
                    }
                    case '$' -> {
                        emojify.append(":heavy_dollar_sign:");
                    }
                    default -> {
                        emojify.append(c);
                    }
                }
            }
            emojify.append(' ');
        }
        message.reply(emojify).queue();
    }
}
