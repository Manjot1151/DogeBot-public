package org.manjot.commands;

import org.manjot.Utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpSlash extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("help")) {
            event.reply("Prefix: " + Utils.bot.getAsMention() + "\n`" + Utils.bot.getAsMention() + " help`").setEphemeral(true).queue();
        }
    }
}
