package org.manjot.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {

	public static void onPrivateMessageReceived(MessageReceivedEvent event) {
		JDA jda = event.getJDA();
		Message message = event.getMessage();
		User author = event.getAuthor();
		if (author.equals(jda.getSelfUser())) {
			return;
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");
		System.out.print("\n[" + LocalDateTime.now().format(dtf) + "]" + "\n[PRIVATE] (" + author.getId() + ") "
				+ author.getName() + ":\n" + message.getContentRaw() + "\n");

		jda.getTextChannelById(835991365300518912L).sendMessage(author.getAsMention() + ": " + message.getContentRaw())
				.queue();
	}
}
