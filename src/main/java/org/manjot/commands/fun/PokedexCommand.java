package org.manjot.commands.fun;

import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PokedexCommand extends Command implements CommandListener {

    public PokedexCommand() {
        this.setName("pokedex")
                .setAliases("pokemon")
                .setDescription("Get information about a pokemon")
                .setUsage("pokedex <pokemon>")
                .setType(CommandType.FUN);
    }

    private static String api = "https://some-random-api.ml/pokedex?pokemon=";

    public static MessageEmbed get(String name) throws Exception {
        Gson gson = new Gson();
        Pokemon pokemon = gson.fromJson(Utils.readJsonUrl(api + name), Pokemon.class);
        return getAsEmbed(pokemon);
    }

    private static MessageEmbed getAsEmbed(Pokemon pokemon) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        embed.setTitle(pokemon.getName());
        embed.setThumbnail(pokemon.getSprite());

        embed.addField("ID", pokemon.getId(), true);
        embed.addField("Generation", pokemon.getGeneration(), true);
        embed.addField("Base XP", pokemon.getBaseExperience(), true);

        embed.addField("Height", pokemon.getHeight(), true);
        embed.addField("Weight", pokemon.getWeight(), true);
        embed.addField("Gender", pokemon.getGender(), true);

        embed.addField("Species", pokemon.getSpecies(), true);
        embed.addField("Type", pokemon.getType(), true);
        embed.addField("Egg Groups", pokemon.getEggGroups(), true);

        embed.addField("Stats", pokemon.getStats(), true);
        embed.addField("Abilities", pokemon.getAbilites(), true);
        embed.addField("Evolutions", pokemon.getEvolutionLine(), true);

        embed.setFooter(pokemon.getDescription(), pokemon.getSprite());
        return embed.build();
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {

        if (args.length >= 1) {
            try {
                message.replyEmbeds(get(args[0])).queue();
            } catch (Exception e) {
                Error.replyMessage(message, "Could not find that pokemon!");
            }
            return;
        }
        replyInvalidUsage(message);
    }
}

class Pokemon {
    private String name;
    private String id;
    private String[] type;
    private String[] species;
    private String[] abilities;
    private String height;
    private String weight;
    private String base_experience;
    private String[] gender;
    private String[] egg_groups;
    private JsonObject stats;
    private JsonObject family;
    private JsonObject sprites;
    private String description;
    private String generation;

    public Pokemon() {
    }

    public String getName() {
        return Utils.firstCharUpperCase(name);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        String typeString = "";
        for (String s : type) {
            typeString += "\n" + s;
        }
        return typeString;
    }

    public String getSpecies() {
        String speciesString = "";
        for (String s : species) {
            if (!s.equals("Pokémon"))
                speciesString += "\n" + s;
        }
        return speciesString;
    }

    public String getAbilites() {
        String abilityString = "";
        for (String s : abilities) {
            abilityString += "\n" + s;
        }
        return abilityString;
    }

    public String getEggGroups() {
        String eggString = "";
        for (String s : egg_groups) {
            eggString += "\n" + s;
        }
        return eggString;
    }

    public String getEvolutionLine() {
        String evolutionString = "";
        JsonArray evolutionArray = family.get("evolutionLine").getAsJsonArray();
        if (evolutionArray.size() == 0)
            evolutionArray.add(getName());
        for (JsonElement s : evolutionArray) {
            if (getName().equals(s.getAsString())) {
                evolutionString += "\n● " + s.getAsString();
            } else
                evolutionString += "\n" + s.getAsString();
        }
        return evolutionString;
    }

    public String getSprite() {
        return sprites.get("animated") != null ? sprites.get("animated").getAsString()
                : sprites.get("normal").getAsString();
    }

    public String getDescription() {
        return description;
    }

    public String getGeneration() {
        return generation;
    }

    public String getStats() {
        int hp = stats.get("hp").getAsInt();
        int attack = stats.get("attack").getAsInt();
        int defense = stats.get("defense").getAsInt();
        int sp_atk = stats.get("sp_atk").getAsInt();
        int sp_def = stats.get("sp_def").getAsInt();
        int speed = stats.get("speed").getAsInt();

        return "HP:​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ " + hp
                + "\nAttack: ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​​ ​ ​ ​ ​ ​" + attack
                + "\nDefense: ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​" + defense + "\nSpecial Attack:  ​ ​ ​ ​ ​" + sp_atk
                + "\nSpecial Defense:  ​ ​" + sp_def + "\nSpeed:  ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​ ​" + speed;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBaseExperience() {
        return base_experience;
    }

    public String getGender() {
        String genderString = "";
        for (String s : gender) {
            genderString += "\n" + Utils.firstCharUpperCase(s);
        }
        return genderString;
    }
}

/*
 * {
 * "name": "pikachu",
 * "id": "025",
 * "type": [
 * "Electric"
 * ],
 * "species": [
 * "Mouse",
 * "Pokémon"
 * ],
 * "abilities": [
 * "Static",
 * "Lightning Rod"
 * ],
 * "height": "0.4 m (1′04″)",
 * "weight": "6.0 kg (13.2 lbs)",
 * "base_experience": "112",
 * "gender": [
 * "50% male",
 * "50% female"
 * ],
 * "egg_groups": [
 * "Fairy",
 * "Field"
 * ],
 * "stats": {
 * "hp": "35",
 * "attack": "55",
 * "defense": "40",
 * "sp_atk": "50",
 * "sp_def": "50",
 * "speed": "90",
 * "total": "320"
 * },
 * "family": {
 * "evolutionStage": 2,
 * "evolutionLine": [
 * "Pichu",
 * "Pikachu",
 * "Raichu",
 * "Raichu"
 * ]
 * },
 * "sprites": {
 * "normal": "http://i.some-random-api.ml/pokemon/pikachu.png",
 * "animated": "http://i.some-random-api.ml/pokemon/pikachu.gif"
 * },
 * "description":
 * "When several of these Pokémon gather, their electricity could build and cause lightning storms."
 * ,
 * "generation": "1"
 * }
 */