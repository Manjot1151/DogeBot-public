package org.manjot.commands.dev;

import java.util.ArrayList;
import java.util.List;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildsCommand extends Command implements CommandListener {

    public GuildsCommand() {
        this.setName("guilds")
                .setAliases("servers", "guildlist", "serverlist")
                .setDescription("Get a list of all guilds bot is in")
                .setUsage("guilds")
                .setType(CommandType.PRIVATE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle("Server List [" + jda.getGuilds().size() + "]");
        List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();

        for (int i = 0; i < jda.getGuilds().size(); i++) {
            Guild server = jda.getGuilds().get(i);
            try {
                embed.appendDescription("(" + server.getId() + ") `" + server.getName() + "`\n");
            } catch (Exception e) {
                embeds.add(embed.build());
                embed.setDescription("");
                embed.appendDescription("(" + server.getId() + ") `" + server.getName() + "`\n");
            }
        }
        embeds.add(embed.build());
        message.replyEmbeds(embeds).queue();
    }
}
