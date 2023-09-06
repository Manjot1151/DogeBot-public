package org.manjot.commands.dev;

import java.util.Comparator;

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

public class GuildTopCommand extends Command implements CommandListener {

    public GuildTopCommand() {
        this.setName("guildtop")
                .setAliases("gtop")
                .setDescription("Get the guilds with most members that the bot is in")
                .setUsage("guildtop")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Top Guilds");
        jda.getGuilds().stream()
                .sorted(Comparator.comparingInt(Guild::getMemberCount).reversed())
                .limit(25)
                .forEach(g -> embed.addField(g.getName(), g.getMemberCount() + "", true));
        message.replyEmbeds(embed.build()).queue();
    }
}
