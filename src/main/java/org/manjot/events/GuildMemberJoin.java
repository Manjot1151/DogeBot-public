package org.manjot.events;

import java.util.Random;

import org.manjot.Utils;
import org.manjot.commands.configuration.GuildConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter
{
    @Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event)
	{
		Guild guild = event.getGuild();
		User user = event.getUser();
		String avatar = user.getEffectiveAvatarUrl();
		String welcomeChannel = GuildConfig.getWelcome(guild.getId());
		if (welcomeChannel == null) return;

		String[] messages = {
		"[member] joined. You must construct additional pylons.",
		"Never gonna give [member] up. Never gonna let [member] down!",
		"Hey! Listen! [member] has joined!",
		"Ha! [member] has joined! You activated my trap card!",
		"We've been expecting you, [member].",
		"It's dangerous to go alone, take [member]!",
		"Swoooosh. [member] just landed.",
		"Brace yourselves. [member] just joined the server.",
		"A wild [member] appeared.",
		"Welcome [member]. We were expecting you ( ͡° ͜ʖ ͡°)",
		"It's a bird! It's a plane! Nevermind, it's just [member].",
		"[member] just joined. Hide your bananas!",
		"[member] just arrived. Seems OP - please nerf.",
		"[member] has spawned in the server."
		};

		Random rand = new Random();
		int random = rand.nextInt(messages.length);
		EmbedBuilder embed = Utils.getDefaultEmbed();
		embed.setThumbnail(avatar);
		embed.setAuthor("Member Joined", null, avatar);
		embed.setDescription(messages[random].replace("[member]", user.getAsMention()));
		embed.setFooter("ID: " + user.getId());
		guild.getTextChannelById(welcomeChannel).sendMessageEmbeds(embed.build()).queue();
	}
}
