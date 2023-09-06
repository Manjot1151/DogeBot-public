package org.manjot.commands.misc;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command implements CommandListener {
    public PingCommand() {
        this.setName("ping")
                .setAliases("latency")
                .setDescription("Get bot ping")
                .setUsage("ping")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        long restPing = jda.getRestPing().complete();
        long gatewayPing = jda.getGatewayPing();

        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setDescription("Bot: " + restPing + "ms\nAPI: " + gatewayPing + "ms");
        message.replyEmbeds(embed.build()).queue();
        // jda.getRestPing().queue((time) -> message.reply("Ping...").queue(msg ->
        // {msg.editMessageFormat("Pong! `%dms`", time).queue();}));
    }
}
