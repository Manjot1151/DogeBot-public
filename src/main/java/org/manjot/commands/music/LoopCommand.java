package org.manjot.commands.music;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;
import org.manjot.commands.music.lavaplayer.TrackScheduler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoopCommand extends Command implements CommandListener {

    public LoopCommand() {
        this.setName("loop")
                .setAliases("repeat", "repeatmusic", "repeatsong")
                .setDescription("Repeats music.\n🔁 loop queue\n🔂 loop track")
                .setUsage("loop")
                .setType(CommandType.MUSIC);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        if (!memberVoiceState.inAudioChannel()) {
            Error.replyMessage(message, "You need to be in a voice channel!");
            return;
        }
        if (memberVoiceState.isDeafened()) {
            Error.replyMessage(message, "You need to be undeafened to use that command");
            return;
        }
        if (!selfVoiceState.inAudioChannel()) {
            Error.replyMessage(message, "I am not in a voice channel!");
            return;
        } else if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            Error.replyMessage(message, "You need to be in the same voice channel as me!");
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        final TrackScheduler scheduler = musicManager.scheduler;

        EmbedBuilder embed = Utils.getDefaultEmbed();

        if (!scheduler.trackRepeating && !scheduler.queueRepeating) {
            scheduler.queueRepeating = true;
            embed.setTitle("Repeating queue...");
        } else if (!scheduler.trackRepeating && scheduler.queueRepeating) {
            scheduler.trackRepeating = true;
            scheduler.queueRepeating = false;
            embed.setTitle("Repeating track...");
        } else {
            scheduler.trackRepeating = false;
            scheduler.queueRepeating = false;
            embed.setTitle("Repeat disabled!");
        }

        message.replyEmbeds(embed.build()).queue();
    }
}
