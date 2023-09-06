package org.manjot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EnumSet;

import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandHandler;
import org.manjot.commandhandler.CommandHandlerListener;
import org.manjot.commandhandler.Cooldown;
import org.manjot.commands.HelpSlash;
import org.manjot.commands.configuration.GuildConfig;
import org.manjot.events.ButtonInteraction;
import org.manjot.events.GuildJoin;
import org.manjot.events.GuildLeave;
import org.manjot.events.GuildMemberJoin;
import org.manjot.events.GuildVoiceUpdate;
import org.reflections.Reflections;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.utils.messages.MessageRequest;

public class Main {
	private static void setupBot(String token) {
		start = System.currentTimeMillis();
		try {
			Reflections reflections = new Reflections("org.manjot.commands");
			reflections.getSubTypesOf(Command.class).stream()
					.sorted((c1, c2) -> c1.getName().compareTo(c2.getName()))
					.forEach((c) -> {
						try {
							CommandHandler.addCommand((Command) c.getDeclaredConstructors()[0].newInstance());
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | SecurityException e) {
							e.printStackTrace();
						}
					});

			MessageRequest.setDefaultMentionRepliedUser(false);
			MessageRequest.setDefaultMentions(Collections.emptyList());

			EnumSet<GatewayIntent> intents = EnumSet.complementOf(EnumSet.of(GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT));
			jda = JDABuilder.create(intents)
					.setToken(token)
					.addEventListeners(new GuildMemberJoin(), new ButtonInteraction(), new GuildJoin(),
							new GuildLeave(), new GuildVoiceUpdate(),
							new CommandHandlerListener(), new HelpSlash())
					.setStatus(OnlineStatus.ONLINE)
					.setActivity(Activity.watching("@Doge Bot help"))
					.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
					.setChunkingFilter(ChunkingFilter.NONE)
					.build().awaitReady();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long start;
	public static JDA jda;

	public static void main(String[] args) throws IOException {
		boolean isTest = false; // keep false by default

		Auth.initialize();
		Mongo.initialize();
		GuildConfig.initialize();

		setupBot(!isTest ? Auth.token : Auth.testToken);

		Utils.initialize();
		Cooldown.startPurger();

		if (!isTest)
			TopGG.postStats(jda);
	}
}
