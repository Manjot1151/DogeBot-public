package org.manjot;

import org.discordbots.api.client.DiscordBotListAPI;

import net.dv8tion.jda.api.JDA;

public class TopGG {
    public static DiscordBotListAPI api = new DiscordBotListAPI.Builder()
            .token(Auth.topgg)
            .botId("741412043411816488")
            .build();

    public static void postStats(JDA jda) {
        api.setStats(jda.getGuilds().size());
    }
}
