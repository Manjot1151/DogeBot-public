package org.manjot.commands.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

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

public class ShuffleCommand extends Command implements CommandListener {

    public ShuffleCommand() {
        this.setName("shuffle")
                .setAliases("shuf", "shuff", "mix")
                .setDescription("Shuffle the music queue")
                .setUsage("shuffle")
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

        final BlockingQueue<AudioTrack> queue = PlayerManager.getInstance().getMusicManager(guild).scheduler.queue;

        if (queue.isEmpty()) {
            Error.replyMessage(message, "Queue is currently empty!");
            return;
        }

        List<AudioTrack> tracks = new ArrayList<>();
        queue.drainTo(tracks);
        Collections.shuffle(tracks);
        queue.addAll(tracks);

        EmbedBuilder embed = Utils.getDefaultEmbed().setTitle("Shuffled queue!");
        message.replyEmbeds(embed.build()).queue();
    }
}
