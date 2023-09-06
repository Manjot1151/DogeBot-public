package org.manjot.commands.fun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class EngrishCommand extends Command implements CommandListener {

    public EngrishCommand() {
        this.setName("engrish")
                .setAliases("inglish")
                .setDescription("Make your text as if a 9yo typed it")
                .setUsage("engrish <text>")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        message.reply(engrish(Utils.messageFrom(args, 0))).queue();
    }

    private static Pattern pattern = Pattern.compile("(?<=\\b\\w)\\w+(?=\\w\\b)");

    public static String engrish(String s) {
        StringBuilder shuffled = new StringBuilder();
        Matcher matcher = pattern.matcher(s);
        int start = 0;
        while (matcher.find()) {
            shuffled.append(s.substring(start, matcher.start()))
                    .append(shuffle(matcher.group()));
            start = matcher.end();
        }
        if (start < s.length()) {
            shuffled.append(s.substring(start, s.length()));
        }
        return shuffled.toString();
    }

    private static String shuffle(String s) {
        List<String> charList = Arrays.asList(s.split(""));
        Collections.shuffle(charList);
        return String.join("", charList);
    }
}
