package org.manjot.commands.minecraft;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.auction.PlayerAuction;
import org.manjot.Error;
import org.manjot.Utils;
import org.manjot.commandhandler.Command;
import org.manjot.commandhandler.CommandListener;
import org.manjot.commandhandler.CommandType;
import org.manjot.hypixel.BazaarItem;
import org.manjot.hypixel.Hypixel;
import org.manjot.hypixel.Player;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HypixelCommand extends Command implements CommandListener {
    public HypixelCommand() {
        this.setName("hypixel")
                .setAliases("hyp", "hy")
                .setDescription("Hypixel Commands - Type this command for further help")
                .setUsage("hypixel")
                .setType(CommandType.MINECRAFT)
                .setCooldown(2);
    }

    @Override
    public void onCommand(JDA jda, MessageReceivedEvent event, User author, Member member, Guild guild,
            MessageChannel channel, Message message, String prefix, String[] args) {
        EmbedBuilder embed = Utils.getDefaultEmbed();
        if (args.length <= 1) {
            Error.replyMessage(message,
                    "Invalid arguments!\n```\n" + prefix + "hypixel status {player}```" + "```\n" + prefix
                            + "hypixel sb ah {player}```" + "```\n" + prefix + "hypixel sb bz {item}```" + "```\n"
                            + prefix + "hypixel sb lbin {item}```" + "```\n" + prefix + "hypixel sb lbin2 {item}```");
            return;
        }
        switch (args[0]) {
            case "guild":
                try {
                    String[] hGuild = Hypixel.getGuild(args[1]); // {name, tag, description, creationDate}
                    embed.setTitle(hGuild[0] + " [" + hGuild[1] + "]");
                    embed.setDescription(hGuild[2]);
                    embed.addField("Creation Date", hGuild[3], false);
                    message.replyEmbeds(embed.build()).queue();
                } catch (APIException | InvalidPlayerException | IOException | ExecutionException
                        | InterruptedException e) {
                    Error.replyException(message, e);
                    return;
                }
                return;

            case "status":
                try {
                    Player player = Hypixel.getPlayer(args[1]);
                    embed.setTitle(player.getRankEmoji() + " " + player.getName());
                    embed.setThumbnail(player.getAvatarUrl());
                    embed.addField("Status", player.getOnlineStatus(), true);
                    // if (player.isOnline())
                    // embed.addField("Online For", player.getOnlineDuration(), true);
                    // else
                    // embed.addField("Last Online", player.getOfflineDuration(), true);
                    embed.addField("Recent Game", player.getMostRecentGame(), true);
                    embed.addBlankField(true);
                    embed.addField("Karma", Utils.numberWithCommas(player.getKarma()), true);
                    embed.addField("Network Lvl", Integer.toString(player.getNetworkLevel()), true);
                    embed.addBlankField(true);
                    if (player.getGuild() != null) {
                        embed.addField("Guild", player.getGuild().getName(), true);
                        embed.addField("Guild EXP", Utils.numberWithCommas(player.getGuild().getExperience()), true);
                        embed.addBlankField(true);
                    }
                    message.replyEmbeds(embed.build()).queue();
                } catch (APIException | IOException | InterruptedException
                        | ExecutionException e) {
                    e.printStackTrace();
                    Error.replyException(message, e);
                } catch (InvalidPlayerException e) {
                    message.reply("Player `" + args[1] + "` does not exist!").queue();
                    e.printStackTrace();
                }
                return;

            case "skyblock":
            case "sb": {
                switch (args[1]) {
                    case "auction":
                    case "ah": {
                        if (args.length < 3) {
                            Error.replyMessage(message,
                                    "```\nInvalid arguments!\n" + prefix + "hypixel sb ah {name}```");
                            return;
                        }
                        if (!(args[2].length() <= 16 || args[2].length() == 32)) {
                            Error.replyMessage(message, "Invalid player!");
                            return;
                        }

                        embed.setTitle("<a:loading:870242806424277004> Fetching auctions by " + args[2]);
                        Message auctionMessage = message.replyEmbeds(embed.build()).complete();
                        try {
                            auctionMessage.editMessageEmbeds(PlayerAuction.get(args[2])).queue();
                        } catch (Exception e) {
                            e.printStackTrace();
                            auctionMessage.delete().queue();
                            Error.replyMessage(message, "Could not fetch auctions for that player!");
                        }
                        return;
                    }
                    case "lowestbin2":
                    case "lbin2": {
                        if (args.length < 3) {
                            Error.replyMessage(message,
                                    "```\nInvalid arguments!\n" + prefix + "hypixel sb lbin2 {name}```");
                            return;
                        }
                        try {
                            embed.setAuthor(Utils.firstCharUpperCase(Utils.messageFrom(args, 2).replaceAll("_", " ")));
                            embed.setTitle("Lowest BIN");
                            embed.setDescription(
                                    "$" + Utils.numberWithCommas(Hypixel.getLowestBin(Utils.messageFrom(args, 2))));
                            embed.setThumbnail("https://sky.shiiyu.moe/item.gif/"
                                    + Utils.messageFrom(args, 3).replaceAll(" ", "_").toUpperCase());
                            message.replyEmbeds(embed.build()).queue();
                        } catch (Exception e) {
                            e.printStackTrace();
                            message.reply(
                                    "An error occured! Please ensure the item name is correct otherwise try again later.")
                                    .queue();
                        }

                        return;
                    }
                    case "lowestbin":
                    case "lbin": {
                        // TEMP
                        Error.replyMessage(message, "This command is currently disabled! use lbin2 instead.");
                        return;
                        // TEMP

                        // if (args.length < 3)
                        // {
                        // Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "hypixel
                        // sb lbin {name}```");
                        // return;
                        // }
                        // String item = Utils.messageFrom(args, 2);
                        // embed.setTitle("<a:loading:870242806424277004> Fetching lowest BIN");

                        // Message lowestBinEmbed = message.replyEmbeds(embed.build()).complete();

                        // try {
                        // JsonObject itemInfo = Hypixel.getRealLowestBin(item, channel,
                        // lowestBinEmbed);
                        // Long endingMilli =
                        // itemInfo.get("end").getAsLong()-Instant.now().toEpochMilli();
                        // String ending = (endingMilli > 0)? Utils.formatMilli(endingMilli) : "Ended!";
                        // embed.setTitle("Lowest BIN");
                        // embed.addField("Item", "```fix\n" + itemInfo.get("name").getAsString() +
                        // "```", true);
                        // embed.addField("Price", "```arm\n" +
                        // Main.numberWithCommas(itemInfo.get("price").getAsInt()) + "```", true);
                        // embed.addField("Ending", "```\n" + ending + "```", true);
                        // embed.addField("Command", "```\n/viewauction " +
                        // itemInfo.get("uuid").getAsString() + "```", false);
                        // embed.addField("Description", "```css\n" + itemInfo.get("lore").getAsString()
                        // + "```", false);
                        // embed.setFooter("Last Updated: " +
                        // itemInfo.get("lastUpdated").getAsString());
                        // embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" +
                        // Utils.messageFrom(args, 2).toUpperCase().replaceAll(" ", "_"));
                        // lowestBinEmbed.editMessageEmbeds(embed.build()).queue();
                        // // message.replyEmbeds(Fetcher.getLowestBin(item)).queue();

                        // } catch (Exception e) {
                        // e.printStackTrace();
                        // Error.replyMessage(message, "Could not find the auction!");
                        // }
                        // return;
                    }
                    case "bazaar":
                    case "bz": {
                        try {
                            BazaarItem item = Hypixel
                                    .getBazaarInfo(Utils.messageFrom(args, 2).toUpperCase().replaceAll(" ", "_"));
                            embed.setTitle(item.getName(), "https://bazaartracker.com/product/" + item.getId());
                            // embed.addField("ID", item.getId(), true);
                            // embed.addField("Name", item.getName(), true);
                            // embed.setDescription("[**" + item.getName() + "**](" +
                            // "https://bazaartracker.com/product/" + item.getId() + ")");
                            // embed.addField("Price", "$" + item.getPrice(), true);
                            embed.addField("Buy Price", "$" + Utils.numberWithCommas(item.getBuyPrice()), true);
                            embed.addField("Sell Price", "$" + Utils.numberWithCommas(item.getSellPrice()), true);
                            embed.addBlankField(true);
                            embed.addField("Buy Price (64)", "$" + Utils.numberWithCommas(item.getBuyPrice() * 64),
                                    true);
                            embed.addField("Sell Price (64)", "$" + Utils.numberWithCommas(item.getSellPrice() * 64),
                                    true);
                            embed.addBlankField(true);
                            embed.addField("Buy Volume", "" + Utils.numberWithCommas(item.getBuyVolume()), true);
                            embed.addField("Sell Volume", "" + Utils.numberWithCommas(item.getSellVolume()), true);
                            embed.addBlankField(true);
                            // embed.addField("Tag", item.getTag(), true);
                            embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" + item.getId());
                            message.replyEmbeds(embed.build()).queue();
                        } catch (Exception e) {
                            message.reply("An error occured!").queue();
                            e.printStackTrace();
                        }
                    }
                }
                return;
            }
        }

    }

}
