package org.manjot.commandhandler;

import java.util.List;

import org.manjot.Utils;

public enum CommandType {
    HELP, CONFIGURATION, MODERATION, ANIMALS, IMAGE, FUN, MINECRAFT, MUSIC, MISCELLANEOUS, PRIVATE;

    public int commandCount() {
        return CommandHandler.getCommandsByType(this).size();
    }

    public List<Command> commands() {
        return CommandHandler.getCommandsByType(this);
    }

    public List<String> commandNames() {
        return CommandHandler.getCommandNamesByType(this);
    }

    @Override
    public String toString() {
        return Utils.firstCharUpperCase(this.name());
    }

    public String getEmoji() {
        switch (this) {
            case CONFIGURATION -> {
                return "<a:gear_spinning:865491740374990848>";
            }
            case MODERATION -> {
                return "<:certified_moderator:850106245306384437>";
            }
            case ANIMALS -> {
                return ":dog:";
            }
            case IMAGE -> {
                return ":frame_photo:";
            }
            case FUN -> {
                return ":tada:";
            }
            case MINECRAFT -> {
                return "<a:minecraft:865343543560372245>";
            }
            case MUSIC -> {
                return ":musical_note:";
            }
            case MISCELLANEOUS -> {
                return ":paperclip:";
            }
            case PRIVATE -> {
                return "<:verified_bot_developer:850104836879024129>";
            }
            default -> {
                return "";
            }
        }
    }
}