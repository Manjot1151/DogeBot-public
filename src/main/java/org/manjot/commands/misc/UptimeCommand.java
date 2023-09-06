package org.manjot.commands.misc;

import org.manjot.Main;
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

public class UptimeCommand extends Command implements CommandListener {
    public UptimeCommand() {
        this.setName("uptime")
                // .setAliases()
                .setDescription("Get uptime of bot")
                .setUsage("uptime")
                .setType(CommandType.MISCELLANEOUS);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setDescription("Uptime: " + Utils.formatMilli(System.currentTimeMillis() - Main.start));
        message.replyEmbeds(embed.build()).queue();
    }
}
