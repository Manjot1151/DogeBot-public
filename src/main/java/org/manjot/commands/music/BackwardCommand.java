package org.manjot.commands.music;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class BackwardCommand extends Command implements CommandListener {

    public BackwardCommand() {
        this.setName("backward")
                .setAliases("bwd", "backwards")
                .setDescription("Move backward in music")
                .setUsage("backward (seconds)")
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

        AudioTrack audioTrack = PlayerManager.getInstance().getMusicManager(guild).audioPlayer.getPlayingTrack();

        if (audioTrack == null) {
            Error.replyMessage(message, "No track is currently playing!");
            return;
        }

        if (audioTrack.getInfo().isStream) {
            Error.replyMessage(message, "Cannot move backward in a live stream");
            return;
        }

        long skipSeconds;
        try {
            skipSeconds = Math.abs(Long.parseLong(args[0]));
        } catch (Exception e) {
            skipSeconds = 10;
        }

        audioTrack.setPosition(audioTrack.getPosition() - skipSeconds * 1000);

        EmbedBuilder embed = Utils.getDefaultEmbed();
        message.replyEmbeds(
                embed.setTitle("Backward")
                        .appendDescription("Moved backward " + skipSeconds + " seconds...")
                        .appendDescription("\n\n" + Utils.getTrackSlider(audioTrack))
                        .build())
                .queue();
    }
}
