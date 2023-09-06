package org.manjot.events;

import org.manjot.commands.music.lavaplayer.GuildMusicManager;
import org.manjot.commands.music.lavaplayer.PlayerManager;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceUpdate extends ListenerAdapter {
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        AudioChannel channelJoined = event.getChannelJoined();
        AudioChannel channelLeft = event.getChannelLeft();
        if (channelLeft == null)
            joinEvent(event, channelJoined);
        else if (channelJoined == null)
            leaveEvent(event, channelLeft);
        else
            moveEvent(event, channelJoined, channelLeft);
    }

    private void joinEvent(GuildVoiceUpdateEvent event, AudioChannel channel) {
        Member member = event.getMember();
        Member self = event.getGuild().getSelfMember();
        if (!member.equals(self))
            return;
        if (channel.getType() == ChannelType.STAGE) {
            StageChannel stageChannel = (StageChannel) channel;
            stageChannel.requestToSpeak().queue();
        }
    }

    private void leaveEvent(GuildVoiceUpdateEvent event, AudioChannel channel) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        if (member.getIdLong() == self.getIdLong()) {
            stopPlayer(guild);
        }

        if (channel.getMembers().contains(self)
                && channel.getMembers().stream().filter(m -> !m.getUser().isBot()).count() == 0) {
            stopPlayer(guild);
        }
    }

    private void moveEvent(GuildVoiceUpdateEvent event, AudioChannel joined, AudioChannel left) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member self = guild.getSelfMember();
        if (member.getIdLong() == self.getIdLong()) {
            if (joined.getMembers().stream().filter(m -> !m.getUser().isBot()).count() == 0) {
                stopPlayer(event.getGuild());
            } else {
                if (joined.getType() == ChannelType.STAGE) {
                    StageChannel stageChannel = (StageChannel) joined;
                    stageChannel.requestToSpeak().queue();
                }
            }
        } else {
            if (left.getMembers().contains(self)
                    && left.getMembers().stream().filter(m -> !m.getUser().isBot()).count() == 0) {
                stopPlayer(guild);
            }
        }
    }

    private static void stopPlayer(Guild guild) {
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();
        musicManager.audioPlayer.setPaused(false);
        musicManager.scheduler.queueRepeating = false;
        musicManager.scheduler.trackRepeating = false;
        guild.getAudioManager().closeAudioConnection();
    }
}
