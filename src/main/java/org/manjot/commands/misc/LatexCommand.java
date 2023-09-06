package org.manjot.commands.misc;

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

public class LatexCommand extends Command implements CommandListener {
    public LatexCommand() {
        this.setName("latex")
                // .setAliases()
                .setDescription("Generate mathematical expressions with latex")
                .setUsage("latex <expression>")
                .setType(CommandType.MISCELLANEOUS);
    }

    private static final String api = "https://latex.codecogs.com/png.image?\\dpi{300}{\\color{White}<exp>}";

    public static String generate(String expression) {
        return api.replace("<exp>", expression.trim().replaceAll("\\s", "&space;"));
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        String equation = Utils.messageFrom(args, 0);
        message.reply(generate(equation)).queue();

    }
}