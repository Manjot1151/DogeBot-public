package org.manjot.commands.minecraft;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import me.kbrewster.mojangapi.MojangAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkinCommand extends Command implements CommandListener {
    public SkinCommand() {
        this.setName("skin")
                .setAliases("minecraftskin", "mcskin")
                .setDescription("Get minecraft skin of a player")
                .setUsage("skin <IGN/UUID>")
                .setType(CommandType.MINECRAFT);
    }

    private static String api = "https://mc-heads.net/";

    public static MessageEmbed get(String username) throws Exception {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(MojangAPI.getProfile(username).getName().replaceAll("\\_", "\\\\_"));
        embed.setDescription("[**Download**](https://mc-heads.net/download/" + username
                + ") | [**Change**](https://mc-heads.net/change/" + username + ")");
        embed.setImage(api + "body/" + username + "/600");
        embed.setThumbnail(api + "skin/" + username);
        return embed.build();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        if (args.length == 0) {
            replyInvalidUsage(message);
            return;
        }
        try {
            message.replyEmbeds(get(args[0])).queue();
        } catch (Exception e) {
            Error.replyMessage(message, "Could not find that player or skin!");
        }
    }
}