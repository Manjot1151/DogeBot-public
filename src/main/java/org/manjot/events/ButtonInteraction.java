package org.manjot.events;

import org.manjot.Utils;
import org.manjot.commands.animals.Animals;
import org.manjot.commands.animals.CatCommand;
import org.manjot.commands.animals.DogCommand;
import org.manjot.commands.misc.DefineCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonInteraction extends ListenerAdapter
{
	@Override
    public void onButtonInteraction(ButtonInteractionEvent event)
    {
        event.deferEdit().queue();
        MessageChannel channel = event.getChannel();
        User user = event.getUser();
        Message message = event.getMessage();
        String buttonId = event.getComponentId();
        EmbedBuilder embed = Utils.getDefaultEmbed();
		Message referencedMessage = channel.retrieveMessageById(message.getId()).complete().getReferencedMessage();
		User messageOwner;
		if (referencedMessage != null)
		{
			messageOwner = channel.retrieveMessageById(message.getId()).complete().getReferencedMessage().getAuthor();
			if (user!=messageOwner) return;
		}
		System.out.println(buttonId);

        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");
        // System.out.print("\n[" + LocalDateTime.now().format(dtf) + "]\n[BUTTON] (" + user.getId() + ") " + user.getName() + ": " + buttonId + "\n");
        switch (buttonId)
        {
			case "delete":
			{
				message.editMessageComponents().queue();
				break;
			}
            case "dog":
            {
                String dogImage;
				try {
					dogImage = DogCommand.getRandomDog();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
                embed.setTitle("doggo owo", dogImage);
                embed.setImage(dogImage);
				// embed.setFooter(Fact.get("dog"));
                //embed.setDescription("[Image](" + dogImage + ")");
                message.editMessageEmbeds(embed.build()).queue();
                break;
            }
            case "cat":
            {
                String catImage;
				try {
					catImage = CatCommand.getRandomCat();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
                embed.setTitle("catto uwu", catImage);
				embed.setImage(catImage);
				// embed.setFooter(Fact.get("cats"));
                //embed.setDescription("[Image](" + catImage + ")");
                message.editMessageEmbeds(embed.build()).queue();
                break;
            }
            case "panda":
				{
					String image = Animals.get("panda");
					if (image == null) break;
					embed.setTitle("Pandas!", image);
					embed.setImage(image);
					// embed.setFooter(Fact.get("panda"));
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}

				case "redpanda":
				{
					String image = Animals.get("red_panda");
					if (image == null) break;
					embed.setTitle("Red Pandas!", image);
					embed.setImage(image);
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}

				case "birb":
				{
					String image = Animals.get("birb");
					if (image == null) break;
					embed.setTitle("Birbs!", image);
					embed.setImage(image);
					// embed.setFooter(Fact.get("birb"));
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}

				case "fox":
				{
					String image = Animals.get("fox");
					if (image == null) break;
					embed.setTitle("Foxes!", image);
					embed.setImage(image);
					// embed.setFooter(Fact.get("fox"));
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}

				case "koala":
				{
					String image = Animals.get("koala");
					if (image == null) break;
					embed.setTitle("Koalas!", image);
					embed.setImage(image);
					// embed.setFooter(Fact.get("koala"));
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}
				case "raccoon":
				{
					String image = Animals.get("raccoon");
					if (image == null) break;
					embed.setTitle("Raccoons!", image);
					embed.setImage(image);
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}
				case "kangaroo":
				{
					String image = Animals.get("kangaroo");
					if (image == null) break;
					embed.setTitle("Kangaroos!", image);
					embed.setImage(image);
					message.editMessageEmbeds(embed.build()).queue();
					break;
				}
				// ⏪◀️▶️⏩
				case "urban_next":
				{
					MessageEmbed urbanEmbed = message.getEmbeds().get(0);
					String[] page = urbanEmbed.getFooter().getText().replace("Page ", "").replace(" of", "").split(" ");
					int currentPage = Integer.parseInt(page[0]);
					int totalPages = Integer.parseInt(page[1]);
					Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪"));
					Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️"));
					Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️"));
					Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩"));
					Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
					if (currentPage+1 == totalPages)
					{
						next = next.asDisabled();
						last = last.asDisabled();
					}
					try {
						String word = urbanEmbed.getDescription().replace("Search results for ", "").replaceAll("\"", "");
						message.editMessageEmbeds(DefineCommand.get(word, currentPage)).setActionRow(first, previous, next, last, delete).queue();
					} catch (Exception e) {
						e.printStackTrace();
					};
					break;
				}
				case "urban_prev":
				{
					MessageEmbed urbanEmbed = message.getEmbeds().get(0);
					String[] page = urbanEmbed.getFooter().getText().replace("Page ", "").replace(" of", "").split(" ");
					int currentPage = Integer.parseInt(page[0]);
					Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪"));
					Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️"));
					Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️"));
					Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩"));
					Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
					if (currentPage-2 == 0)
					{
						previous = previous.asDisabled();
						first = first.asDisabled();
					}
					try {
						String word = urbanEmbed.getDescription().replace("Search results for ", "").replaceAll("\"", "");
						message.editMessageEmbeds(DefineCommand.get(word, currentPage-2)).setActionRow(first, previous, next, last, delete).queue();
					} catch (Exception e) {
						e.printStackTrace();
					};
					break;
				}
				case "urban_first":
				{
					MessageEmbed urbanEmbed = message.getEmbeds().get(0);
					Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪")).asDisabled();
					Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️")).asDisabled();
					Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️"));
					Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩"));
					Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
					try {
						String word = urbanEmbed.getDescription().replace("Search results for ", "").replaceAll("\"", "");
						message.editMessageEmbeds(DefineCommand.get(word, 0)).setActionRow(first, previous, next, last, delete).queue();
					} catch (Exception e) {
						e.printStackTrace();
					};
					break;
				}
				case "urban_last":
				{
					MessageEmbed urbanEmbed = message.getEmbeds().get(0);
					Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪"));
					Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️"));
					Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️")).asDisabled();
					Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩")).asDisabled();
					Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
					String[] page = urbanEmbed.getFooter().getText().replace("Page ", "").replace(" of", "").split(" ");
					int totalPages = Integer.parseInt(page[1]);
					try {
						String word = urbanEmbed.getDescription().replace("Search results for ", "").replaceAll("\"", "");
						message.editMessageEmbeds(DefineCommand.get(word, totalPages-1)).setActionRow(first, previous, next, last, delete).queue();
					} catch (Exception e) {
						e.printStackTrace();
					};
					break;
				}
        }
    }
}
