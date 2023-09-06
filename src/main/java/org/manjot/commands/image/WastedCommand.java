package org.manjot.commands.image;

import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WastedCommand extends Command implements CommandListener {

    public WastedCommand() {
        this.setName("wasted")
                .setAliases("kill")
                .setDescription("Add wasted filter to a user avatar")
                .setUsage("wasted (@user)")
                .setType(CommandType.IMAGE);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        User u = author;
        if (args.length >= 1)
            u = Utils.getMentionedUserOrDefault(args[0], author);
        String avatar = u.getEffectiveAvatarUrl();
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setImage(Image.wasted(avatar));
        embed.setAuthor(u.getName(), null, avatar);
        message.replyEmbeds(embed.build()).queue();
    }
}
