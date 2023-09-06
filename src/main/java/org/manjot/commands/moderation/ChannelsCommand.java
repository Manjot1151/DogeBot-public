package org.manjot.commands.moderation;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ChannelsCommand extends Command implements CommandListener {

    public ChannelsCommand() {
        this.setName("channels")
                .setAliases("channellist")
                .setDescription("Get a list of all channels in the server")
                .setUsage("channels")
                .setPermissions(Permission.ADMINISTRATOR)
                .setType(CommandType.MODERATION);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        Guild server = (args.length == 0 || !Utils.isDev(author)) ? guild : jda.getGuildById(args[0]);
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setFooter(server.getName(), server.getIconUrl());
        embed.setThumbnail(server.getIconUrl());
        String category = "";
        String description = "";
        boolean categoryStarted = false;
        int categoryCount = 0;

        for (int i = 0; i < server.getChannels().size(); i++) {
            GuildChannel listchannel = server.getChannels().get(i);
            if (listchannel.toString().startsWith("GC")) {
                categoryCount++;
                if (categoryStarted) {
                    embed.addField(category, description, false);
                    category = listchannel.getName();
                    description = "";
                } else {
                    categoryStarted = true;
                    embed.setDescription(description);
                    category = listchannel.getName();
                }
            } else {
                description += "<#" + server.getChannels().get(i).getId() + ">\n";// (" +
                                                                                  // server.getChannels().get(i).getId()
                                                                                  // + ")\n";
            }
        }
        embed.setTitle("Channels [" + (server.getChannels().size() - categoryCount) + "]");
        embed.addField(category, description, false);

        message.replyEmbeds(embed.build()).queue();

    }
}
