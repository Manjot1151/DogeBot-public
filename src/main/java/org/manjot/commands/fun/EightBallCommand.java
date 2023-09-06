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

public class EightBallCommand extends Command implements CommandListener {

    public EightBallCommand() {
        this.setName("8ball")
                .setAliases("eightball", "8b")
                .setDescription("Ask the magic 8ball your question!")
                .setUsage("8ball <question>")
                .setType(CommandType.FUN);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }

        String[] dogs = {
                "૮ ・ﻌ・ა",
                "໒・ﻌ・७",
                "૮ ºﻌºა",
                "૮ ･ ﻌ･ა",
                "૮ ♡ﻌ♡ა",
                "૮ ˙ ﻌ˙ ა",
                "૮ – ﻌ–ა",
                "૮ ̷ ̷ ̷・ﻌ ̷ ̷・ ა",
                "૮ ˘ﻌ˘ ა",
                "૮ ˆﻌˆ ა",
                "૮ ’ﻌ｀ა",
                "૮ ˶′ﻌ ‵˶ ა",
                "૮ ´• ﻌ ´• ა",
                "૮ ⚆ﻌ⚆ა",
                "૮ ◞ ﻌ ◟ ა",
                "૮ ○ﻌ ○ ა",
                "૮ฅ・ﻌ・აฅ",
                "૮ ᴖﻌᴖა",
                "｡:ﾟ૮ ˶ˆ ﻌ ˆ˶ ა ﾟ:｡",
                "૮ ꈍﻌ ꈍა",
                "♥(˘ ε˘ U)",
                "⋆୨୧˚૮ ＾ﻌ＾ა˚୨୧⋆"
        };

        String[] answers = {
                "Yes.",
                "No.",
                "Certainly...",
                "Why would you ask that?",
                "Can you not?",
                "Obviously.",
                "Ask again later.",
                "Nope.",
                "Without a doubt.",
                "Very doubtful.",
                "You may rely on it!",
                "Don't count on it.",
                "Concentrate and ask again."
        };

        Random random = new Random();

        message.reply(dogs[random.nextInt(dogs.length)] + " " + answers[random.nextInt(answers.length)]).queue();
    }
}
