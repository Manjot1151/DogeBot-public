package org.manjot.events;

import java.awt.Color;
import java.time.OffsetDateTime;

import org.manjot.commands.configuration.GuildConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeave extends ListenerAdapter
{
    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        JDA jda = event.getJDA();
        Guild guild = event.getGuild();
        TextChannel channel = jda.getTextChannelById(861659660801277972l);
        User owner = guild.retrieveOwner().complete().getUser();
        EmbedBuilder embed = new EmbedBuilder();
        GuildConfig.removeGuild(guild.getId());
        embed.setTitle("Guild Leave");
        embed.setFooter("ID: " + guild.getId());
        embed.setColor(Color.RED);
        embed.setTimestamp(OffsetDateTime.now());
        embed.setThumbnail(guild.getIconUrl());
        embed.setDescription("Name: `" + guild.getName() + "`\nOwner: `" + owner.getId() + "`\nMembers: `" + guild.getMemberCount() + "`");
        //embed.setAuthor(owner.getName(), null, (owner.getAvatarUrl()!=null)? owner.getAvatarUrl() : owner.getDefaultAvatarUrl());
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
