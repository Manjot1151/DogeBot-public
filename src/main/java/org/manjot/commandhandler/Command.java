package org.manjot.commandhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.manjot.Utils;
import org.manjot.commands.configuration.GuildConfig;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Command {

    private String name;
    private String description;
    private String usage;
    private float cooldown = 0;
    private CommandType type;
    private List<String> aliases = new ArrayList<>();
    private List<Permission> permissions = new ArrayList<>();
    private List<Permission> botPermissions = new ArrayList<>();

    public Command setAliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }

    public Command setPermissions(Permission... permissions) {
        this.permissions = Arrays.asList(permissions);
        return this;
    }

    public Command setBotPermissions(Permission... botPermissions) {
        this.botPermissions = Arrays.asList(botPermissions);
        return this;
    }

    public CommandListener getListener() {
        return (CommandListener) this;
    }

    protected void replyInvalidUsage(Message message) {
        EmbedBuilder embed = Utils.getDefaultErrorEmbed();
        embed.setTitle("Invalid Usage!")
                .setDescription("`" + GuildConfig.getPrefix(message.getGuild().getId()) + this.getUsage() + "`");
        message.replyEmbeds(embed.build()).queue();
    }

    public String toString() {
        return "Command(" + name + ")";
    }
}
