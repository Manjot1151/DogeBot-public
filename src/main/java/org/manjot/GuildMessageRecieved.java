package org.manjot;
// package org.manjot;

// import java.awt.Color;
// import java.io.IOException;
// import java.time.Instant;
// import java.time.OffsetDateTime;
// import java.time.format.DateTimeFormatter;
// import java.time.temporal.ChronoUnit;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.List;
// import java.util.Map.Entry;
// import java.util.Random;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.TimeUnit;

// import com.google.gson.JsonObject;

// import org.auction.Fetcher;
// import org.auction.PlayerAuction;
// import org.manjot.commands.Animals;
// import org.manjot.commands.Calculator;
// import org.manjot.commands.Cat;
// import org.manjot.commands.Dog;
// import org.manjot.commands.Fact;
// import org.manjot.commands.GuildConfig;
// import org.manjot.commands.Image;
// import org.manjot.commands.Ip;
// import org.manjot.commands.Joke;
// import org.manjot.commands.Latex;
// import org.manjot.commands.Memes;
// import org.manjot.commands.Pokedex;
// import org.manjot.commands.Poll;
// import org.manjot.commands.Profile;
// import org.manjot.commands.Say;
// import org.manjot.commands.Server;
// import org.manjot.commands.Skin;
// import org.manjot.commands.Translate;
// import org.manjot.commands.Urban;

// import me.kbrewster.exceptions.APIException;
// import me.kbrewster.exceptions.InvalidPlayerException;
// import net.dv8tion.jda.api.EmbedBuilder;
// import net.dv8tion.jda.api.JDA;
// import net.dv8tion.jda.api.OnlineStatus;
// import net.dv8tion.jda.api.Permission;
// import net.dv8tion.jda.api.entities.Activity;
// import net.dv8tion.jda.api.entities.Emoji;
// import net.dv8tion.jda.api.entities.Guild;
// import net.dv8tion.jda.api.entities.GuildChannel;
// import net.dv8tion.jda.api.entities.Invite;
// import net.dv8tion.jda.api.entities.Member;
// import net.dv8tion.jda.api.entities.Message;
// import net.dv8tion.jda.api.entities.Message.MentionType;
// import net.dv8tion.jda.api.entities.MessageEmbed;
// import net.dv8tion.jda.api.entities.MessageType;
// import net.dv8tion.jda.api.entities.Role;
// import net.dv8tion.jda.api.entities.TextChannel;
// import net.dv8tion.jda.api.entities.User;
// import net.dv8tion.jda.api.entities.Activity.ActivityType;
// import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
// import net.dv8tion.jda.api.exceptions.ErrorHandler;
// import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
// import net.dv8tion.jda.api.hooks.ListenerAdapter;
// import net.dv8tion.jda.api.interactions.components.Button;
// import net.dv8tion.jda.api.managers.Presence;
// import net.dv8tion.jda.api.requests.ErrorResponse;
// import net.dv8tion.jda.api.utils.AllowedMentions;

// public class OnGuildMessageRecieved extends ListenerAdapter
// {
//     public void onGuildMessageReceived(GuildMessageReceivedEvent event) 
// 	{
// 		JDA jda = event.getJDA();
// 		Member member = event.getMember();
// 		User author = event.getAuthor();
// 		Message message = event.getMessage();
// 		Guild guild = event.getGuild();
// 		String prefix = GuildConfig.getPrefix(guild.getId());
// 		TextChannel channel = event.getChannel();
// 		Member bot = guild.getSelfMember();
// 		User me = jda.getUserById("316481084835495937");
// 		String rawInput = event.getMessage().getContentRaw();
// 		EmbedBuilder embed = new EmbedBuilder();
// 		embed.setTimestamp(OffsetDateTime.now());
// 		AllowedMentions.setDefaultMentionRepliedUser(false);
// 		AllowedMentions.setDefaultMentions(Collections.emptyList());

// 		//String input = event.getMessage().getContentRaw().substring(prefl);

// 		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");  
// 		// if (member == bot && message.getReferencedMessage()!=null)
// 		// {
// 		// 	Message referencedMessage = message.getReferencedMessage();
// 		// 	User referencedUser = referencedMessage.getAuthor();
// 		// 	System.out.print("\n[" + LocalDateTime.now().format(dtf) + "]\n[GUILD] " + referencedMessage.getGuild().getName() + "/" + referencedMessage.getChannel().getName() + "/" + referencedUser.getName() + ":\n" + referencedMessage.getContentRaw());
// 		// 	System.out.print("\nhttps://discordapp.com/channels/" + referencedMessage.getGuild().getId() + "/" + referencedMessage.getChannel().getId() + "/" + referencedMessage.getId() + "\n");	
// 		// }
// 		// if (/*!author.isBot() && */!message.isWebhookMessage())
// 		// {
// 		// 	System.out.print("\n[" + LocalDateTime.now().format(dtf) + "]\n[GUILD] " + guild.getName() + "/" + channel.getName() + "/" + author.getName() + ":\n" + rawInput);
// 		// 	System.out.print("\nhttps://discordapp.com/channels/" + guild.getId() + "/" + channel.getId() + "/" + message.getId() + "\n");	
// 		// }
// 		if (!message.isWebhookMessage())
// 		//jda.getTextChannelById(850370451826147338L).sendMessage("**[GUILD]** " + guild.getName() + "/" + channel.getName() + "/" + author.getName() + ":```" + message.getContentRaw() + "```https://discordapp.com/channels/" + guild.getId() + "/" + event.getChannel().getId() + "/" + event.getMessage().getId()).queue();	
// 		if (member == bot && message.getButtons().size()!=0)
// 		{
// 			message.editMessageComponents().queueAfter(2, TimeUnit.MINUTES);
// 		}
// 		if (Main.readback && author == me)
// 		{
// 			String unicodes = "";
// 			for (int i = 0; i < rawInput.length(); i++)
// 			{
// 				unicodes += (int)(rawInput.charAt(i));
// 			}
// 			channel.sendMessage(unicodes).queue();
// 		}
		
// 		//int l = input.length();
// 		Main.msg="";
// 		String[] args = message.getContentRaw().split(" ");
// 		if (args[0].startsWith("<@" + bot.getId() + ">"))
// 		{
// 			prefix = "<@" + bot.getId() + ">";
// 		}
// 		else if (args[0].startsWith("<@!" + bot.getId() + ">"))
// 		{
// 			prefix = "<@!" + bot.getId() + ">";
// 		}
// 		/* if (pinged && event.getAuthor().getId().equals("741412043411816488") && message.getContentRaw().equals("Ping..."))
// 		{
// 			jda.getRestPing().queue((time) -> message.editMessageFormat("Pong! ``%dms``", time).queue());
// 			pinged=false;
			
// 		} */
// 		if (!Main.botread && (event.getAuthor().isBot() || event.isWebhookMessage()))
// 		{
// 			return;
// 		}
// 		// if (!rawInput.startsWith(prefix) && message.getMentionedMembers().size() >= 1 && message.getMentionedMembers().contains(bot))
// 		// {
// 		// 	channel.sendMessage("My prefix is: `" + prefix + "`").queue(msg -> msg.delete().queueAfter(2, TimeUnit.SECONDS));
// 		// }
// 		if (Main.lock)
// 		{
// 			message.delete().queue();
// 		}
// 		if (Main.blocked.contains(event.getAuthor().getId()))
// 		{
// 			message.delete().queue();
// 			return;
// 		}
// 		// if (author.getIdLong() == 301321100736987137L)
// 		// {
// 		// 	return;
// 		// }

// 		if (args[0].startsWith(prefix))
// 		{
// 			String cmd = args[0].substring(prefix.length()).toLowerCase();
// 			switch(cmd)
// 			{
// 				case "activity":
// 				{
// 					if (author != me) break;
// 					try {
// 						ActivityType activityType = ActivityType.valueOf(args[1].toUpperCase());
// 						String activityName = args.length > 2 ? Main.messageFrom(args, 2) : "";
// 						Activity activity = Activity.of(activityType, activityName);
// 						jda.getPresence().setActivity(activity);
// 						message.reply("Set Activity to `" + activity + "` as `" + activityType + "`").queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}
// 				case "status":
// 				case "onlinestatus":
// 				{
// 					if (author != me) break;
// 					try {
// 						String statusName = args[1].toUpperCase();
// 						if (statusName.equals("DND")) statusName = "DO_NOT_DISTURB";
// 						OnlineStatus onlineStatus = OnlineStatus.valueOf(statusName);
// 						jda.getPresence().setStatus(onlineStatus);
// 						message.reply("Set OnlineStatus to `" + onlineStatus + "`").queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}
// 				case "streaming":
// 				case "activitystreaming":
// 				{
// 					if (author != me) break;
// 					try {
// 						Activity activity = Activity.streaming(Main.messageFrom(args, 2), args[1]);
// 						jda.getPresence().setActivity(activity);
// 						message.reply("Set Activity to `" + activity + "` as `" + ActivityType.STREAMING + "`").queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}
// 				case "presence":
// 				case "getpresence":
// 				{
// 					if (author != me) break;
// 					Presence presence = jda.getPresence();
// 					message.reply("Activity: `" + presence.getActivity() + "` as `" + presence.getActivity().getType() + "`\nStatus: `" + presence.getStatus() + "`").queue();
// 					break;
// 				}
// 				case "botread":
// 				{
// 					if (author == me)
// 					{
// 						if (args.length==2 && (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")))
// 						{
// 							Main.botread = Boolean.parseBoolean(args[1]);
// 						}
// 						else
// 						{
// 							Main.botread = !Main.botread;
// 						}
// 						message.reply("`botread` has been set to `" + Main.botread + "`").queue();
// 					}
// 					break;
// 				}

// 				case "readback":
// 				{
// 					if (author == me)
// 					{
// 						if (args.length==2 && (args[1].toLowerCase().equals("true") || args[1].toLowerCase().equals("false")))
// 						{
// 							Main.readback = Boolean.parseBoolean(args[1]);
// 						}
// 						else
// 						{
// 							Main.readback = !Main.readback;
// 						}
// 						message.reply("`readback` has been set to `" + Main.readback + "`").queue();
// 					}
// 					break;
					
// 				}

// 				case "dm":
// 				{
// 					if (author!=me)
// 					{
// 						break;
// 					}
// 					if (message.getMentionedUsers().isEmpty())
// 					{
// 						message.reply("Please mention a valid user!").queue();
// 					}
// 					else if (args.length==2)
// 					{
// 						message.reply("Please provide a valid message").queue();
// 					}
// 					else
// 					{
// 						// channel.reply("DM sent to " + message.getMentionedUsers().get(0).getAsMention() + " successfully (unless Clyde stopped me)").queue();
// 						for (int j = 2; j < args.length; j++)
// 						{
// 							Main.msg+=args[j]+ " ";
// 						}
// 						message.getMentionedUsers().get(0).openPrivateChannel()
// 															.flatMap(dmChannel -> dmChannel.sendMessage(Main.msg))
// 															.queue(null, new ErrorHandler()
// 																.ignore(ErrorResponse.UNKNOWN_MESSAGE)
// 																.handle(
// 																	ErrorResponse.CANNOT_SEND_TO_USER,
// 																	(e) -> channel.sendMessage("Could not send the message!").queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS))));
// 						message.delete().queue();
// 					}
// 					break;
// 				}
				
// 				case "av":
// 				case "avatar":
// 				{
// 					List<User> users = new ArrayList<User>();
// 					if (message.getMentionedUsers().isEmpty())
// 					{
// 						try {
// 							users.add(jda.retrieveUserById(args[1]).complete());
// 						} catch (Exception e) {
// 							users.add(author);
// 						}
// 					}
// 					else
// 					{
// 						users.addAll(message.getMentionedUsers());
// 					}
// 					List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();
// 					for (User u : users)
// 					{
// 						String avatarUrl = u.getEffectiveAvatarUrl() + "?size=4096";
// 						embed.setAuthor(u.getAsTag(), null, avatarUrl);
// 						embed.setDescription(u.getAsMention());
// 						embed.setImage(avatarUrl);
// 						embeds.add(embed.build());
// 					}
// 					message.replyEmbeds(embeds).queue();
// 					break;
// 				}

// 				/*case "emoji":
// 				{
// 					channel.sendMessage("<:" + args[1] + ":" + args[2] + ">").queue();
// 					message.delete().queue();
// 					break;
// 				}*/

// 				case "purge":
// 				{
// 					if (!bot.hasPermission(Permission.MESSAGE_MANAGE))
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 						break;
// 					}
// 					if (!member.hasPermission(Permission.MESSAGE_MANAGE) && author!=me)
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 						break;
// 					}
// 					else
// 					{
// 						try {
// 							int deleteCount = Integer.parseInt(args[1])+1;
// 							List<Message> messages = channel.getIterableHistory().takeAsync(deleteCount).get();
// 							messages.removeIf(m -> m.getTimeCreated().isBefore(OffsetDateTime.now().minus(2, ChronoUnit.WEEKS)));
// 							Message purgingMessage = message.reply("Purging `" + (messages.size()-1) + "` messages...").complete();
// 							messages.add(purgingMessage);
// 							channel.purgeMessages(messages).forEach(cf -> cf.complete(null));
// 						} catch (InterruptedException | ExecutionException e) {
// 							e.printStackTrace();
// 							Error.replyException(message, e);
// 						}
// 						// messages.removeIf(m -> m.getTimeCreated().isBefore(OffsetDateTime.now().minus(2, ChronoUnit.WEEKS)));
// 						// channel.purgeMessages(messages);
// 					}
// 					break;
// 				}

// 				case "createrole":
// 				{
// 					if (member.hasPermission(Permission.MANAGE_ROLES) && bot.hasPermission(Permission.MANAGE_ROLES))
// 					{
// 						String roleName = (args.length>1)? Main.messageFrom(args, 1) : "new role";
// 						if (roleName.length() > 100)
// 						{
// 							message.reply("Name of role cannot be longer than 100 characters!").queue();
// 							break;
// 						} 
// 						guild.createRole().setName(roleName).queue();
// 						message.reply("Role `" + roleName + "` was created!").queue();
// 					}
// 					else
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 					}
// 					break;
// 				}

// 				case "deleterole":
// 				{
// 					if (member.hasPermission(Permission.MANAGE_ROLES) && bot.hasPermission(Permission.MANAGE_ROLES))
// 					{
// 						String roleName = Main.messageFrom(args, 1);
// 						if (message.mentionsEveryone())
// 						{
// 							Error.replyMessage(message, "Can't delete that role!");
// 						}
// 						else if (message.getMentionedRoles().isEmpty())
// 						{
// 							if (!guild.getRolesByName(roleName, false).isEmpty())
// 							{
// 								guild.getRolesByName(roleName, false).get(0).delete().queue();
// 								message.reply("Role `" + roleName + "` was deleted!").queue();
// 							}
// 							else
// 							Error.replyMessage(message, "Couldn't find a role by name `" + roleName + "`");
// 						}
// 						else
// 						{
// 							message.reply("Role `" + message.getMentionedRoles().get(0).getName() + "` was deleted!").queue();
// 							message.getMentionedRoles().get(0).delete();
// 						}
						
						
// 					}
// 					else
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 					}
// 					break;
// 				}

// 				case "deleteallrolesbyname":
// 				{
// 					if (author!=me) return;
// 					if (member.hasPermission(Permission.MANAGE_ROLES) && bot.hasPermission(Permission.MANAGE_ROLES))
// 					{
// 						String roleName = Main.messageFrom(args, 1);
// 						if (!guild.getRolesByName(roleName, false).isEmpty())
// 						{
// 							int rolesSize = guild.getRolesByName(roleName, false).size();
// 							int deletedCount = 0;
// 							for (int i = 0; i < rolesSize; i++)
// 							{
// 								guild.getRolesByName(roleName, false).get(i).delete().queue();
// 								deletedCount++;
// 							}
// 							message.reply("`" + deletedCount + "` roles by name `" + roleName + "` were deleted").queue();
// 						}
// 						else
// 						message.reply("Couldn't find a role by name `" + roleName + "`").queue();
// 					}
// 					else
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 					}
// 					break;
// 				}

// 				case "emoji":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n```" + prefix + "emoji {text}```");
// 						break;
// 					}
// 					Main.emojiWrite(channel, Main.messageFrom(args, 1));
// 					break;
// 				}

// 				case "ping":
// 				{
// 					long restPing = jda.getRestPing().complete();
// 					long gatewayPing = jda.getGatewayPing();

// 					embed.clear();
// 					embed.setColor(Color.YELLOW);
// 					embed.setDescription("Bot: " + restPing + "ms\nAPI: " + gatewayPing + "ms");
// 					channel.sendMessageEmbeds(embed.build()).queue();
// 					//jda.getRestPing().queue((time) -> message.reply("Ping...").queue(msg -> {msg.editMessageFormat("Pong! `%dms`", time).queue();}));
// 					break;
// 				}

// 				case "uptime":
// 				{
// 					embed.clear();
// 					embed.setColor(Color.YELLOW);
// 					embed.setDescription("Uptime: " + Main.formatMilli(System.currentTimeMillis() - Main.start));
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "vote":
// 				{
// 					embed.setColor(Color.YELLOW);
// 					embed.setDescription("Click [here](https://top.gg/bot/741412043411816488/vote) to vote for the bot :D");
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "badges":
// 				{
// 					jda.retrieveUserById(args[1]).queue(user -> {
// 						String badgelist = Main.createBadgeList(user.getFlags());
// 						embed.setThumbnail(user.getEffectiveAvatarUrl());
// 						embed.setDescription(user.getAsMention());
// 						embed.setAuthor(user.getAsTag(), null, user.getEffectiveAvatarUrl());
// 						embed.setColor(Color.YELLOW);
// 						embed.addField("Badges [" + user.getFlags().size() + "]", badgelist, false);
// 						message.replyEmbeds(embed.build()).queue();
						
// 						// Debug:
// 						//channel.sendMessage("[DEBUG]: " + user.getFlags().toString()).queue();
// 					});
// 					break;
// 				}
				
// 				case "temp1":
// 				{
// 					message.delete().queue();
// 					embed.setTitle("Dungeon Carry Rules");
// 					embed.setColor(Color.CYAN);
// 					embed.setDescription("**-** If the client dies and the required score could not be reached then there will not be any refunds/re-runs.\n\n**-** If the carrier dies and the required score could not be reached then there will be a re-run.\n\n**-** The payment for the carry has to be done before the run.\n\n**-** If the client for some reason couldn't complete the run with the carrier (Wifi cuts or has to leave mid run), The run will be marked as complete and there wouldn't be any re-runs/refunds\n\n**-** If the carrier for some reason couldn't complete the run with the client (Wifi cuts or has to leave mid run), There will be a re-run/refund.\n\n**-** The loot which you get from the chests is all yours.\n\n**-** Carry scamming will result in a ban.\n\n**-** In higher floors such as floor 7, if there are 2 carriers carrying, they will split the money obtained from the carry.\n\n**-** The carrier can decide which class the client chooses for the run to go smoothly.");
// 					/*embed.addField("If the client dies and the required score could not be reached then there will not be any refunds/re-runs.", "", false);
// 					embed.addField("If the carrier dies and the required score could not be reached then there will be a re-run.", "", false);
// 					embed.addField("The payment for the carry has to be done before the run.", "", false);
// 					embed.addField("If the client for some reason couldn't complete the run with the carrier (Wifi cuts or has to leave mid run), The run will be marked as complete and there wouldn't be any re-runs/refunds.", "", false);
// 					embed.addField("If the carrier for some reason couldn't complete the run with the client (Wifi cuts or has to leave mid run), There will be a re-run.", "", false);
// 					embed.addField("The loot which you get from the chests is all yours.", "", false);
// 					embed.addField("Carry scamming will result in a ban.", "", false);
// 					embed.addField("In higher floors such as floor 7, if there are 2 carriers carrying, they will split the money obtained from the carry.", "", false);
// 					embed.addField("The carrier can decide which class the client chooses for the run to go better.", "", false);*/
// 					embed.setFooter("Skyblock Dungeons", guild.getIconUrl());
// 					channel.sendMessageEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "game":
// 				{
// 					if (author != me) break;
//                     channel.sendMessage("cool buttons below")
// 						.setActionRow(
// 							//Button.primary("left", Emoji.fromUnicode("\u276E")),
// 							Button.primary("up", Emoji.fromMarkdown("😀"))//,
// 							//Button.primary("down", Emoji.fromUnicode("\u25B2")),
// 							//Button.primary("right", "right") 
// 							//Button.primary("left", "▲")
// 							).queue();
// 					break;
// 				}

// 				case "say":
// 				{
// 					if (args.length==1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "say {user} {message}```");
// 						break;
// 					}
// 					// if (!bot.hasPermission(Permission.MANAGE_WEBHOOKS))
// 					// {
// 					// 	Error.replyMessage(message, "I require Manage Webhook perms for this command!");
// 					// 	break;
// 					// }
// 					try {
// 						User sayUser;
// 						String msg;
// 						try {
// 							sayUser = jda.retrieveUserById(args[1].replace("<", "").replace("@", "").replace("!", "").replace(">", "")).complete();
// 							if (sayUser == bot.getUser() || args.length == 2)
// 							{
// 								throw new Exception();
// 							}
// 							msg = Main.messageFrom(args, 2);
// 							Say.send(event, sayUser, msg);
// 						} catch (Exception e) {
// 							msg = Main.messageFrom(args, 1);
// 							channel.sendMessage(msg).queue();
// 						}
// 						if (bot.hasPermission(Permission.MESSAGE_MANAGE))
// 							message.delete().queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}

// 					break;
// 				}

// 				case "leaveguild":
// 				{
// 					if (author!=me) break;
// 					try {
// 						Guild guildToLeave = jda.getGuildById(args[1]);
// 						guildToLeave.leave().queue();
// 						message.reply("Left guild: `" + guildToLeave.getName() + "` (" + guildToLeave.getId() + ")").queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}

// 				case "meme":
// 				{

// 					try {
// 						String subreddit = "";
// 						boolean isNsfw = channel.isNSFW();
// 						if (args.length >= 2)
// 						{
// 							if (author == me)
// 							{
// 								if (args[1].equals("!"))
// 								{
// 									Memes.allowMe();
// 									break;
// 								}
// 								isNsfw = Memes.isMeAllowed();
// 							}
// 							if (!Memes.isWhitelisted(args[1]) && !isNsfw)
// 							{
// 								Error.replyMessage(message, "That subreddit is not whitelisted!\nYou can suggest that subreddit to be whitelisted on our [Support Server](https://discord.gg/W8hnfK5czn) or use an NSFW channel to unlock all subreddits");
// 								break;
// 							}
// 							subreddit = args[1] + "/";
// 						}
// 						message.replyEmbeds(Memes.get(subreddit, isNsfw)).queue();
// 					} catch (Exception e) {
// 						e.printStackTrace();
// 						Error.replyMessage(message, "This subreddit has no posts or doesn't exist.");
// 					}
// 					break;
// 				}

// 				case "gay":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.gay(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "glass":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.glass(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "wasted":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.wasted(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "trigger":
// 				case "triggered":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					try {
// 						embed.setImage("attachment://triggered.gif");
// 						embed.setAuthor(u.getName(), null, avatar);
// 						message.reply(Image.triggered(avatar), "triggered.gif").setEmbeds(embed.build()).queue();
// 						//(Image.triggered(avatar), "triggered.gif").setEmbeds(embed.build()).queue();
// 					} catch (IOException e) {
// 						Error.replyException(message, e);
// 					}					
// 					break;
// 				}
// 				case "jail":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.jail(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "missionpassed":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.missionPassed(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "communism":
// 				case "comrade":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.comrade(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "comment":
// 				{
// 					User u;
// 					String comment;
// 					if (message.getMentionedUsers().isEmpty())
// 					{
// 						u = author;
// 						comment = Main.messageFrom(args, 1);
// 					}
// 					else
// 					{
// 						u = message.getMentionedUsers().get(0);
// 						comment = Main.messageFrom(args, 2);
// 					}
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.comment(avatar, u.getName(), comment));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "tweet":
// 				{
// 					User u;
// 					String tweet;
// 					if (message.getMentionedUsers().isEmpty())
// 					{
// 						u = author;
// 						tweet = Main.messageFrom(args, 1);
// 					}
// 					else
// 					{
// 						u = message.getMentionedUsers().get(0);
// 						tweet = Main.messageFrom(args, 2);
// 					}
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.tweet(avatar, u.getAsTag().replace("#", ""), guild.getMember(u).getEffectiveName(), tweet));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "stupid":
// 				{
// 					// if (args.length <= 2)
// 					// {
// 					// 	Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "stupid {user} {text}\n```");
// 					// 	break;
// 					// }
// 					User u;
// 					String text;
// 					if (message.getMentionedUsers().isEmpty())
// 					{
// 						u = author;
// 						text = Main.messageFrom(args, 1);
// 					}
// 					else
// 					{
// 						u = message.getMentionedUsers().get(0);
// 						text = Main.messageFrom(args, 2);
// 					}
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.stupid(avatar, text));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "greyscale":
// 				case "grayscale":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.greyScale(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "invert":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.invert(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "invertgreyscale":
// 				case "invertgrayscale":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.invertGreyScale(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "brightness":
// 				{
// 					User u = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : author;
// 					String avatar = u.getEffectiveAvatarUrl();
// 					embed.setImage(Image.brightness(avatar));
// 					embed.setAuthor(u.getName(), null, avatar);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
				
// 				case "pokemon":
// 				case "pokedex":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments\n```\n" + prefix + "pokedex {pokemon}```");
// 						break;
// 					}
// 					try {
// 						message.replyEmbeds(Pokedex.get(Main.messageFrom(args, 1))).queue();
// 					} catch (Exception e) {
// 						Error.replyMessage(message, "Could not find that pokemon!");
// 						e.printStackTrace();
// 					}
// 					break;
// 				}

// 				case "coinflip":
// 				case "cf":
// 				{
// 					channel.sendMessage("You got **" + (new Random().nextInt(2) == 0? "Heads" : "Tails") + "**!").queue();
// 					break;
// 				}

// 				case "roll":
// 				{
// 					int faces = 6;
// 					try {
// 						if (args.length >= 2)
// 							faces = Integer.parseInt(args[1]);
// 							if (faces <= 0)
// 								throw new Exception("dice faces must be positive");
// 					} catch (Exception e) {
// 						faces = 6;
// 					}
// 					channel.sendMessage("You rolled **" + (new Random().nextInt(faces)+1) + "**!").queue();
// 					break;
// 				}

// 				case "translate":
// 				{
// 					if (args.length <= 2)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "translate {to} {text}\n```");
// 						break;
// 					}
// 					try {
// 						message.replyEmbeds(Translate.createEmbed(args[1], Main.messageFrom(args, 2))).queue();
// 					} catch (Exception e) {
// 						Error.replyMessage(message, "Make sure to type the correct language code (`en` for English)");
// 						e.printStackTrace();
// 					}
// 					break;
// 				}

// 				case "floor":
// 				{
// 					if (author != me) break;
// 					message.delete().queue();
// 					List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();
// 					embed.setColor(Color.CYAN);
// 					embed.setFooter("Skyblock Dungeons", guild.getIconUrl());

// 					embed.setAuthor("Floor 3", null, "https://cdn.discordapp.com/attachments/737246331344650261/823165751832870992/Professor_Emoji.png");
// 					embed.setTitle("The Professor");
// 					embed.setDescription("`Requirement: Catacombs Lvl. 5`");
// 					embed.addField("Completion", "100k", false);
// 					embed.addField("S Run", "150k", false);
// 					embed.addField("S+ Run", "200k", false);
// 					//embed.setDescription("**Completion:** 100k\n**S Run**: 150k\n**S+ Run**: 200k");
// 					embed.setThumbnail("https://cdn.discordapp.com/attachments/737246331344650261/823165751832870992/Professor_Emoji.png");
// 					embeds.add(embed.build());

// 					embed.clearFields();
					
// 					embed.setAuthor("Floor 4", null, "https://cdn.discordapp.com/attachments/737246331344650261/823207540313423892/Thorn_Emoji.png");
// 					embed.setTitle("Thorn");
// 					embed.setDescription("`Requirement: Catacombs Lvl. 9`");
// 					embed.addField("Completion", "200k", false);
// 					embed.addField("S Run", "270k", false);
// 					embed.addField("S+ Run", "350k", false);
// 					//embed.setDescription("**Completion:** 200k\n**S Run**: 270k\n**S+ Run**: 350k");
// 					embed.setThumbnail("https://cdn.discordapp.com/attachments/737246331344650261/823207540313423892/Thorn_Emoji.png");
// 					embeds.add(embed.build());

// 					embed.clearFields();
					
// 					embed.setAuthor("Floor 5", null,  "https://cdn.discordapp.com/attachments/737246331344650261/823208398929264640/Livid_Emoji.png");
// 					embed.setTitle("Livid");
// 					embed.setDescription("`Requirement: Catacombs Lvl. 14`");
// 					embed.addField("Completion", "400k", false);
// 					embed.addField("S Run", "520k", false);
// 					embed.addField("S+ Run", "700k", false);
// 					//embed.setDescription("**Completion:** 400k\n**S Run**: 520k\n**S+ Run**: 700k");
// 					embed.setThumbnail("https://cdn.discordapp.com/attachments/737246331344650261/823208398929264640/Livid_Emoji.png");
// 					embeds.add(embed.build());

// 					embed.clearFields();
					
// 					embed.setAuthor("Floor 6", null, "https://cdn.discordapp.com/attachments/737246331344650261/823210640474767410/Sadan_Emoji.png");
// 					embed.setTitle("Sadan");
// 					embed.setDescription("`Requirement: Catacombs Lvl. 19`");
// 					embed.addField("Completion", "550k", false);
// 					embed.addField("S Run", "900k", false);
// 					embed.addField("S+ Run", "1.4m", false);
// 					//embed.setDescription("**Completion:** 550k\n**S Run**: 900k\n**S+ Run**: 1.4m");
// 					embed.setThumbnail("https://cdn.discordapp.com/attachments/737246331344650261/823210640474767410/Sadan_Emoji.png");
// 					embeds.add(embed.build());

// 					embed.clearFields();

// 					embed.setAuthor("Floor 7", null, "https://cdn.discordapp.com/attachments/737246331344650261/823210672850468937/Necron_Emoji.png");
// 					embed.setTitle("Necron");
// 					embed.setDescription("`Requirement: Catacombs Lvl. 24`");
// 					embed.addField("Completion", "4m", false);
// 					embed.addField("S Run", "6m", false);
// 					embed.addField("S+ Run", "8m", false);
// 					//embed.setDescription("**Completion:** 4m\n**S**: 6m\n**S+**: 8m");
// 					embed.setThumbnail("https://cdn.discordapp.com/attachments/737246331344650261/823210672850468937/Necron_Emoji.png");
// 					embeds.add(embed.build());

// 					channel.sendMessage("**__Dungeon Carry Prices__**").setEmbeds(embeds).queue();
// 					/*embed.addField("Floor 3", "**Completion:** 100k\n**S Run**: 150k\n**S+ Run**: 200k", true);
// 					embed.addField("Floor 4", "**Completion:** 200k\n**S Run**: 270k\n**S+ Run**: 350k", true);
// 					embed.addField("Floor 5", "**Completion:** 400k\n**S Run**: 520k\n**S+ Run**: 700k", true);
// 					embed.addField("Floor 6", "**Completion:** 550k\n**S Run**: 900k\n**S+ Run**: 900k", true);
// 					embed.addField("Floor 7", "**Completion:** 5m\n**S**: 7m\n**S+**: 9.5m", true);*/
// 					break;
// 				}

				
// 				// Hypixel Commands


// 				case "hypixel":
// 				{
// 					if (args.length <= 2)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "hypixel status {player}```" + "```\n" + prefix + "hypixel sb ah {player}```" + "```\n" + prefix + "hypixel sb bz {item}```" + "```\n" + prefix + "hypixel sb lbin {item}```" + "```\n" + prefix + "hypixel sb lbin2 {item}```");
// 						break;
// 					}
// 					switch(args[1])
// 					{
// 						case "guild":
// 							try 
// 							{
// 								String[] hGuild = Hypixel.getGuild(args[2]); // {name, tag, description, creationDate}
// 								embed.setColor(Color.ORANGE);
// 								embed.setTitle(hGuild[0] + " [" + hGuild[1] + "]");
// 								embed.setDescription(hGuild[2]);
// 								embed.addField("Creation Date", hGuild[3], false);
// 								message.replyEmbeds(embed.build()).queue();
// 							} 
// 							catch (APIException | InvalidPlayerException | IOException | ExecutionException | InterruptedException e) 
// 							{
// 								Error.replyException(message, e);
// 							}
// 							break;

// 						case "status":
// 						{
// 							try {
// 								Player player = Hypixel.getPlayer(args[2]);
// 								embed.setTitle(player.getRankEmoji() + " " + player.getName());
// 								embed.setThumbnail(player.getAvatarUrl());
// 								embed.setColor(Color.ORANGE);
// 								embed.addField("Status", player.getOnlineStatus(), true);
// 								// if (player.isOnline())
// 								// 	embed.addField("Online For", player.getOnlineDuration(), true);
// 								// else
// 								// 	embed.addField("Last Online", player.getOfflineDuration(), true);
// 								embed.addField("Recent Game", player.getMostRecentGame(), true);
// 								embed.addBlankField(true);
// 								embed.addField("Karma", Main.numberWithCommas(player.getKarma()), true);
// 								embed.addField("Network Lvl", Integer.toString(player.getNetworkLevel()), true);
// 								embed.addBlankField(true);
// 								if (player.getGuild()!=null)
// 								{
// 									embed.addField("Guild", player.getGuild().getName(), true);
// 									embed.addField("Guild EXP", Main.numberWithCommas(player.getGuild().getExp()), true);
// 									embed.addBlankField(true);
// 								}
// 								message.replyEmbeds(embed.build()).queue();
// 							} catch (APIException | IOException | InterruptedException
// 									| ExecutionException e) {
// 								e.printStackTrace();
// 								Error.replyException(message, e);
// 							} catch (InvalidPlayerException e)
// 							{
// 								message.reply("Player `" + args[2] + "` does not exist!").queue();
// 								e.printStackTrace();
// 							}
// 						}

// 						case "skyblock":
// 						case "sb":
// 						{
// 							switch(args[2])
// 							{
// 								case "auction":
// 								case "ah":
// 								{
// 									if (args.length < 4)
// 									{
// 										Error.replyMessage(message, "```\nInvalid arguments!\n" + prefix + "hypixel sb ah {name}```");
// 										break;
// 									}
// 									if (!(args[3].length() <= 16 || args[3].length() == 32))
// 									{
// 										Error.replyMessage(message, "Invalid player!");
// 										break;
// 									}

// 									embed.setTitle("<a:loading:870242806424277004> Fetching auctions by " + args[3]);
// 									embed.setColor(Color.YELLOW);
// 									Message auctionMessage = message.replyEmbeds(embed.build()).complete();
// 									try {
// 										auctionMessage.editMessageEmbeds(PlayerAuction.get(args[3])).queue();
// 									} catch (Exception e) {
// 										e.printStackTrace();
// 										auctionMessage.delete().queue();
// 										Error.replyMessage(message, "Could not fetch auctions for that player!");
// 									}
// 									break;
// 								}
// 								case "lowestbin2":
// 								case "lbin2":
// 								{
// 									if (args.length < 4)
// 									{
// 										Error.replyMessage(message, "```\nInvalid arguments!\n" + prefix + "hypixel sb lbin2 {name}```");
// 										break;
// 									}
// 									try {
// 										embed.setAuthor(Main.firstCharUpperCase(Main.messageFrom(args,3).replaceAll("_", " ")));
// 										embed.setTitle("Lowest BIN");
// 										embed.setDescription("$" + Main.numberWithCommas(Hypixel.getLowestBin(Main.messageFrom(args, 3))));
// 										embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" + Main.messageFrom(args, 3).replaceAll(" ", "_").toUpperCase());
// 										embed.setColor(Color.green);
// 										message.replyEmbeds(embed.build()).queue();
// 									} catch (Exception e) {
// 										e.printStackTrace();
// 										message.reply("An error occured! Please ensure the item name is correct otherwise try again later.").queue();
// 									}
									
// 									break;
// 								}
// 								case "lowestbin":
// 								case "lbin":
// 								{
// 									if (args.length < 4)
// 									{
// 										Error.replyMessage(message, "Invalid arguments!\n```\n" + prefix + "hypixel sb lbin {name}```");
// 										break;
// 									}
// 									String item = Main.messageFrom(args, 3);
// 									embed.setTitle("<a:loading:870242806424277004> Fetching lowest BIN");
// 									embed.setColor(Color.YELLOW);

// 									Message lowestBinEmbed = message.replyEmbeds(embed.build()).complete();
									
// 									try {
// 										JsonObject itemInfo = Hypixel.getRealLowestBin(item, channel, lowestBinEmbed);
// 										Long endingMilli = itemInfo.get("end").getAsLong()-Instant.now().toEpochMilli();
// 										String ending = (endingMilli > 0)? Main.formatMilli(endingMilli) : "Ended!";
// 										embed.setTitle("Lowest BIN");
// 										embed.addField("Item", "```fix\n" + itemInfo.get("name").getAsString() + "```", true);
// 										embed.addField("Price", "```arm\n" + Main.numberWithCommas(itemInfo.get("price").getAsInt()) + "```", true);
// 										embed.addField("Ending", "```\n" + ending + "```", true);
// 										embed.addField("Command", "```\n/viewauction " + itemInfo.get("uuid").getAsString() + "```", false);
// 										embed.addField("Description", "```css\n" + itemInfo.get("lore").getAsString() + "```", false);
// 										embed.setFooter("Last Updated: " + itemInfo.get("lastUpdated").getAsString());
// 										embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" + Main.messageFrom(args, 3).toUpperCase().replaceAll(" ", "_"));
// 										embed.setColor(Color.green);
// 										lowestBinEmbed.editMessageEmbeds(embed.build()).queue();
// 										// message.replyEmbeds(Fetcher.getLowestBin(item)).queue();
										
// 									} catch (Exception e) {
// 										e.printStackTrace();
// 										Error.replyMessage(message, "Could not find the auction!");
// 									}
// 									break;
// 								}
// 								case "bazaar":
// 								case "bz":
// 								{
// 									try {
// 										BazaarItem item = Hypixel.getBazaarInfo(Main.messageFrom(args, 3).toUpperCase().replaceAll(" ", "_"));
// 										embed.setTitle(item.getName(), "https://bazaartracker.com/product/" + item.getId());
// 										//embed.addField("ID", item.getId(), true);
// 										//embed.addField("Name", item.getName(), true);
// 										//embed.setDescription("[**" + item.getName() + "**](" + "https://bazaartracker.com/product/" + item.getId() + ")");
// 										//embed.addField("Price", "$" + item.getPrice(), true);
// 										embed.addField("Buy Price", "$" + Main.numberWithCommas(item.getBuyPrice()), true);
// 										embed.addField("Sell Price", "$" + Main.numberWithCommas(item.getSellPrice()), true);
// 										embed.addBlankField(true);
// 										embed.addField("Buy Price (64)", "$" + Main.numberWithCommas(item.getBuyPrice()*64), true);
// 										embed.addField("Sell Price (64)", "$" + Main.numberWithCommas(item.getSellPrice()*64), true);
// 										embed.addBlankField(true);
// 										embed.addField("Buy Volume", "" + Main.numberWithCommas(item.getBuyVolume()), true);
// 										embed.addField("Sell Volume", "" + Main.numberWithCommas(item.getSellVolume()), true);
// 										embed.addBlankField(true);
// 										//embed.addField("Tag", item.getTag(), true);
// 										embed.setThumbnail("https://sky.shiiyu.moe/item.gif/" + item.getId());
// 										embed.setColor(Color.GREEN);
// 										message.replyEmbeds(embed.build()).queue();
// 									} catch (Exception e) {
// 										message.reply("An error occured!").queue();
// 										e.printStackTrace();
// 									}
// 								}
// 							}
// 							break;
// 						}
// 					}
// 					break;
// 				}


// 				// Hypixel Commands end



// 				case "dog":
// 				{
// 					String dogImage = Dog.getRandomDog();
// 					embed.clear();
// 					embed.setTitle("here's some doggo pics :D", dogImage);
// 					embed.setImage(dogImage);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("dog"));
// 					//embed.setDescription("[Image](" + dogImage + ")");
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("dog", "Next")
// 						).queue();
// 					break;
// 				}
// 				case "dmdog":
// 				{
// 					if (author!=me) break;
// 					String dogImage = Dog.getRandomDog();
// 					embed.clear();
// 					embed.setTitle("here's some doggo pics :D", dogImage);
// 					embed.setImage(dogImage);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("dog"));
// 					//embed.setDescription("[Image](" + dogImage + ")");
// 					User mentionedUser = (message.getMentionedUsers().size()!=0) ? message.getMentionedUsers().get(0) : jda.getUserById(args[1]);
// 					mentionedUser.openPrivateChannel().flatMap(dmChannel -> dmChannel.sendMessageEmbeds(embed.build()).setActionRow(Button.primary("dog", "want more? click :D"))).queue();
// 					break;
// 				}

// 				case "cat":
// 				{
// 					String catImage = Cat.getRandomCat();
// 					embed.clear();
// 					embed.setTitle("here's some catto pics :D", catImage);
// 					embed.setImage(catImage);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("cat"));
// 					//embed.setDescription("[Image](" + catImage + ")");
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("cat", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "dmcat":
// 				{
// 					if (author!=me) break;
// 					String catImage = Cat.getRandomCat();
// 					embed.clear();
// 					embed.setTitle("here's some catto pics :3", catImage);
// 					embed.setImage(catImage);
// 					embed.setColor(Color.WHITE);
// 					// embed.setFooter(Fact.get("cat"));
// 					//embed.setDescription("[Image](" + catImage + ")");
// 					User mentionedUser = (message.getMentionedUsers().size()!=0) ? message.getMentionedUsers().get(0) : jda.getUserById(args[1]);
// 					mentionedUser.openPrivateChannel().flatMap(dmChannel -> dmChannel.sendMessageEmbeds(embed.build()).setActionRow(Button.primary("cat", "want more? click :D"))).queue();
// 					break;
// 				}

// 				case "panda":
// 				{
// 					String image = Animals.get("panda");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Pandas!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("panda"));
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("panda", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "redpanda":
// 				{
// 					String image = Animals.get("red_panda");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Red Pandas!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("redpanda", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "bird":
// 				case "birb":
// 				{
// 					String image = Animals.get("birb");
// 					if (image == null) break;
// 					embed.setTitle("Birbs!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("birb"));
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("birb", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "fox":
// 				{
// 					String image = Animals.get("fox");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Foxes!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("fox"));
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("fox", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "koala":
// 				{
// 					String image = Animals.get("koala");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Koalas!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					// embed.setFooter(Fact.get("koala"));
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("koala", "Next")
// 						).queue();
// 					break;
// 				}
// 				case "raccoon":
// 				{
// 					String image = Animals.get("raccoon");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Raccoons!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("raccoon", "Next")
// 						).queue();
// 					break;
// 				}
// 				case "kangaroo":
// 				{
// 					String image = Animals.get("kangaroo");
// 					if (image == null) break;
// 					embed.clear();
// 					embed.setTitle("Kangaroos!", image);
// 					embed.setImage(image);
// 					embed.setColor(Color.YELLOW);
// 					message.replyEmbeds(embed.build())
// 						.setActionRow(
// 							Button.primary("kangaroo", "Next")
// 						).queue();
// 					break;
// 				}

// 				case "fact":
// 				{
// 					embed.clear();
// 					embed.setColor(Color.YELLOW);
// 					embed.setTitle(Main.firstCharUpperCase(Main.messageFrom(args, 1)));
// 					String animal = Main.messageFrom(args, 1).toLowerCase().replaceAll(" ", "_");
// 					String fact = Fact.get(animal);
// 					embed.setDescription(fact);
// 					if (fact.equals("Could not find any facts :("))
// 					embed.setColor(Color.RED);	
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "joke":
// 				{
// 					Joke.reply(event);
// 					break;
// 				}

// 				case "advertise":
// 				{
// 					if (author!=me) break;
// 					embed.clear();
// 					embed.setDescription("[.](https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=8&scope=bot)");
// 					message.replyEmbeds(embed.build()).queue();
// 					message.delete().queue();
// 					break;
// 				}

// 				case "everyone":
// 				{
// 					if (author!=me) break;
// 					String mentions = "";
// 					for (Member m : guild.getMembers())
// 					{
// 						mentions += m.getAsMention() + " ";
// 					}
// 					mentions += "\n";
// 					channel.sendMessage(mentions + Main.messageFrom(args, 1)).queue();
// 					break;
// 				}
				
// 				case "reverse":
// 				{
// 					String reversed = (new StringBuilder((args.length > 1)? Main.messageFrom(args, 1) : (message.getType().equals(MessageType.INLINE_REPLY)? message.getReferencedMessage().getContentRaw() : "")).reverse()).toString();
// 					//embed.setAuthor(author.getAsTag(), null, author.getAvatarUrl());
// 					embed.setDescription(reversed);
// 					embed.setColor(Color.YELLOW);
// 					message.replyEmbeds(embed.build()).queue();
// 					message.delete().queue();
// 					break;
// 				}

// 				case "welcome":
// 				{
// 					if (!(member.hasPermission(Permission.ADMINISTRATOR)) && author != me)
// 					return;
// 					if (args.length==1)
// 					{
// 						message.reply("Invalid usage:\n`" + prefix + "welcome set {channel}`\n`" + prefix + "welcome remove`").queue();
// 					}
// 					switch(args[1])
// 					{
// 						case "set":
// 							try {
// 								TextChannel welcomeChannel = (message.getMentionedChannels().size()!=0)? message.getMentionedChannels().get(0) : guild.getTextChannelById(args[2]);
// 								if (!welcomeChannel.canTalk())
// 								{
// 									Error.replyMessage(message, "I do not have permissions to send messages in " + welcomeChannel.getAsMention() + "!");
// 									break;
// 								}                 
// 								GuildConfig.setWelcome(guild.getId(), welcomeChannel.getId());
// 								message.reply(welcomeChannel.getAsMention() + " has been set as the default welcome channel!").queue();
// 							} catch (Exception e) {
// 								Error.replyMessage(message, "Invalid channel!");
// 							}
// 							break;

// 						case "remove":
// 							if (GuildConfig.getWelcome(guild.getId())!=null)
// 							{
// 								try {
// 									GuildConfig.setWelcome(guild.getId(), null);
// 								} catch (IOException e) {
// 									Error.replyMessage(message, "Could not remove the welcome channel!");
// 									e.printStackTrace();
// 								}
// 								message.reply("Welcome channel was removed!").queue();
// 							}
// 							else
// 							Error.replyMessage(message, "This server does not have a welcome channel set!");
// 							break;
						
// 						default:
// 							Error.replyMessage(message, "Invalid usage:\n`" + prefix + "welcome set {channel}`\n`" + prefix + "welcome remove`");
// 							break;
// 					}
// 					break;
// 				}
// 				case "define":
// 				case "definition":
// 				case "urban":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n```" + prefix + "urban {word}```");
// 						break;
// 					}
// 					try {
// 						// ⏪◀️▶️⏩
// 						MessageEmbed urbanEmbed = Urban.get(Main.messageFrom(args, 1), 0);
// 						Button first = Button.primary("urban_first", Emoji.fromUnicode("⏪")).asDisabled();
// 						Button previous = Button.primary("urban_prev", Emoji.fromUnicode("◀️")).asDisabled();
// 						Button next = Button.primary("urban_next", Emoji.fromUnicode("▶️"));
// 						Button last = Button.primary("urban_last", Emoji.fromUnicode("⏩"));
// 						Button delete = Button.danger("delete", Emoji.fromUnicode("🗑️"));
// 						String[] page = urbanEmbed.getFooter().getText().replace("Page ", "").replace(" of", "").split(" ");
// 						int totalPages = Integer.parseInt(page[1]);
// 						if (totalPages == 1) 
// 						message.replyEmbeds(urbanEmbed).queue();
// 						else
// 						message.replyEmbeds(urbanEmbed).setActionRow(first, previous, next, last, delete).queue();
// 					} catch (Exception e) {
// 						Error.replyMessage(message, "Could not find any definitions for that word!");
// 					}
// 					break;
// 				}
// 				case "poll":
// 				{
// 					Poll.create(event, args);
// 					break;
// 				}
// 				case "profile":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n" + prefix + "profile {username/uuid}");
// 						break;
// 					}
// 					try {
// 						channel.sendMessageEmbeds(Profile.get(args[1])).queue();
// 					} catch (Exception e) {
// 						e.printStackTrace();
// 						Error.replyMessage(message, "Could not find that player!");
// 					}
// 					break;
// 				}
// 				case "skin":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n" + prefix + "skin {username/uuid}");
// 						break;
// 					}
// 					try {
// 						message.replyEmbeds(Skin.get(args[1])).queue();
// 					} catch (Exception e) {
// 						Error.replyMessage(message, "Could not find that player or skin!");
// 						e.printStackTrace();
// 					}
// 					break;
// 				}

// 				case "minecraftserver":
// 				case "server":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\n" + prefix + "server {ip}");
// 					}
// 					try {
// 						channel.sendMessageEmbeds(Server.get(args[1])).queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 						e.printStackTrace();
// 					}
// 					break;
// 				}

// 				case "updatelbin":
// 				{
// 					if (author != me)
// 					break;
// 					Message updateMessage = message.reply("<a:loading:870242806424277004> Fetching auctions...").complete();
// 					try {
// 						Fetcher.fetchAuctions();
// 					} catch (Exception e) {
// 						updateMessage.editMessage(e.getMessage()).queue();
// 					}
// 					updateMessage.editMessage("Item list updated!").queue();
// 					break;
// 				}
// 				case "test":
// 				{
// 					if (author!=me) break;
// 					// Emote emote = jda.getEmoteById(865491740374990848L);
// 					// channel.sendMessage(emote.getImageUrl()).queue();
// 					// channel.sendMessage("⚙").queue();
// 						//Hypixel.test(args[1])
// 						break;
// 				}
// 				case "timer":
// 				{
// 					try {
// 						int seconds = Integer.parseInt(args[1]);
// 						message.reply("Timer for " + seconds + " seconds has been set").queue();
// 						AllowedMentions.setDefaultMentions(new ArrayList<MentionType>(){{add(MentionType.USER);};});
// 						message.reply(author.getAsMention() + " the timer for " + seconds + " seconds has expired!").queueAfter(seconds, TimeUnit.SECONDS);
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}
// 				case "ip":
// 				{
// 					if (args.length == 1)
// 					{
// 						Error.replyMessage(message, "Invalid arguments!\nPlease provide an IP to look up!");
// 						break;
// 					}
// 					message.replyEmbeds(Ip.trace(args[1])).queue();
// 					break;
// 				}
// 				case "readurl":
// 				{
// 					if (author!=me) break;
// 					String urlString = "";
// 					try {
// 						urlString = Main.readUrl(args[1]);
// 					} catch (Exception e1) {
// 						e1.printStackTrace();
// 						Error.replyMessage(message, "Couldn't read provided url");
// 					}
// 					try{
// 						channel.sendMessage(urlString).queue();
// 					} catch (Exception e) {
// 						System.out.println(urlString);
// 					}
// 					break;
// 				}
// 				case "bruteforceytlink":
// 				{
// 					if (author!=me) break;
// 					Message repliedMessage = message.reply("<a:loading:870242806424277004> searching for youtube link...").complete();
// 					long bruteStart = System.currentTimeMillis();
// 					String ytUrl = args[1];
// 					String[] splitted = ytUrl.split("/");
// 					String code = splitted[splitted.length-1];
// 					repliedMessage.editMessage("https://youtu.be/" + Main.bruteForceYTLink(code, 11-code.length()) + "\nTime taken: `" + (System.currentTimeMillis()-bruteStart) + "ms`").queue();
// 					break;
// 				}

// 				case "math":
// 				case "maths":
// 				case "calc":
// 				case "calculate":
// 				{
// 					try {
// 						message.reply(Double.toString(Calculator.eval(Main.messageFrom(args, 1)))).queue();
// 					} catch (Exception e) {
// 						Error.replyException(message, e);
// 					}
// 					break;
// 				}
// 				case "ginfo":
// 				case "guildinfo":
// 				{
// 					Guild g = guild;
// 					if (author == me)
// 					{
// 						try {
// 							g = jda.getGuildById(args[1]);
// 						} catch (Exception e) {}
// 					}
// 					embed.setTitle(g.getName());
// 					embed.setColor(Color.YELLOW);
// 					embed.setThumbnail(g.getIconUrl());
// 					embed.addField("ID", g.getId(), true);
// 					embed.addField("Owner", g.getOwner().getUser().getAsTag(), true);
// 					embed.addField("Members", Integer.toString(g.getMembers().size()), true);
// 					embed.addField("Channels", Integer.toString(g.getChannels().size()), true);
// 					embed.addField("Roles", Integer.toString(g.getRoles().size()), true);
// 					embed.addField("Emotes", Integer.toString(g.getEmotes().size()), true);
// 					embed.addField("Boosts", Integer.toString(g.getBoostCount()), true);
// 					embed.addField("Created At", g.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}

// 				case "guildtop":
// 				case "topguilds":
// 				case "gtop":
// 				{
// 					if (author != me) break;
// 					int guildsCount = jda.getGuilds().size();
// 					HashMap<Long, Integer> memberCountMap = new HashMap<Long, Integer>();
// 					for (int i = 0; i < guildsCount; i++)
// 					{
// 						Guild g = jda.getGuilds().get(i);
// 						memberCountMap.put(g.getIdLong(), g.getMemberCount());
// 					}
// 					embed.setTitle("Top Guilds");
// 					embed.setColor(Color.YELLOW);
// 					memberCountMap = Main.sortByValue(memberCountMap);
// 					for (Entry<Long, Integer> entry : memberCountMap.entrySet())
// 					{
// 						Guild g = jda.getGuildById(entry.getKey());
// 						embed.addField(g.getName(), Integer.toString(entry.getValue()), true);
// 					}
// 					message.replyEmbeds(embed.build()).queue();
// 					break;
// 				}
// 				case "ghostping":
// 				{
// 					if (author!=me) break;
// 					AllowedMentions.setDefaultMentions(new ArrayList<MentionType>(){{add(MentionType.USER);};});
// 					List<Message> messages = new ArrayList<Message>(){{add(message);}};
// 					User mentioned = message.getMentionedUsers().get(0);
// 					for (int i = 0; i < 5; i++)
// 					{
// 						messages.add(channel.sendMessage(mentioned.getAsMention()).complete());
// 					}
// 					try {
// 						channel.deleteMessages(messages).queue();
// 					} catch (InsufficientPermissionException e) {
// 						// for (Message m : messages)
// 						// {
// 						// 	m.delete().queue();
// 						// }
// 					}
// 					break;
// 				}
// 				case "nickname":
// 				case "nick":
// 				{
// 					if (author!=me) break;
// 					message.getMentionedMembers().get(0).modifyNickname(Main.messageFrom(args, 2)).queue();
// 					break;
// 				}

// 				case "delmsg":
// 				case "deletemsg":
// 				case "deletemessage":
// 				{
// 					if (author!=me) break;
// 					channel.deleteMessageById(args[1]).queue();
// 					message.delete().queue();
// 					break;
// 				}
// 				case "shutdown":
// 				{
// 					if (author!=me) break;
// 					message.reply("Shutting down...").complete();
// 					jda.shutdownNow();
// 					break;
// 				}
// 				case "restart":
// 				{
// 					if (author!=me) break;
// 					Message restartMsg = message.reply("Shutting down...").complete();
// 					jda.shutdown();
// 					Main.buildJDA().getTextChannelById(channel.getId()).retrieveMessageById(restartMsg.getId()).complete().editMessage("Finished restarting!").queue();
// 					break;
// 				}
// 				case "rickrollstatus":
// 				{
// 					if (author!=me) break;
// 					Message restartMsg = message.reply("Shutting down...").complete();
// 					jda.shutdown();
// 					Main.rickRollJDA().getTextChannelById(channel.getId()).retrieveMessageById(restartMsg.getId()).complete().editMessage("Finished restarting with a cool status :wink:").queue();
// 					break;
// 				}
// 				case "latex":
// 				{
// 					if (args.length == 1)
// 						Error.replyMessage(message, "No arguments provided!");

// 					String equation = Main.messageFrom(args, 1);
// 					message.reply(Latex.generate(equation)).queue();
// 				}
// 				// case "n":
// 				// {
// 				// 	if (author!=me) break;
// 				// 	String code = (args[1].startsWith("discord.gift/")? "" : "discord.gift/") + args[1];
// 				// 	for (int i = '0'; i <= '9'; i++)
// 				// 	{
// 				// 		channel.sendMessage(code + (char) i).queue();
// 				// 	}
// 				// 	for (int i = 'a'; i <= 'z'; i++)
// 				// 	{
// 				// 		channel.sendMessage(code + (char) i).queue();
// 				// 	}
// 				// 	for (int i = 'A'; i <= 'Z'; i++)
// 				// 	{
// 				// 		channel.sendMessage(code + (char) i).queue();
// 				// 	}
// 				// }
// 				// case "nn":
// 				// {
// 				// 	if (author!=me) break;
// 				// 	String preefex = "discord.gift/";
// 				// 	String code = args[1];
// 				// 	for (int i = '0'; i <= '9'; i++)
// 				// 	{
// 				// 		channel.sendMessage(preefex + (char) i + code).queue();
// 				// 	}
// 				// 	for (int i = 'a'; i <= 'z'; i++)
// 				// 	{
// 				// 		channel.sendMessage(preefex + (char) i + code).queue();
// 				// 	}
// 				// 	for (int i = 'A'; i <= 'Z'; i++)
// 				// 	{
// 				// 		channel.sendMessage(preefex + (char) i + code).queue();
// 				// 	}
// 				// }
// 				default:
// 					break;
// 			}

// 			if (cmd.equals("dm2") && author == me)
// 			{
// 				jda.getUserById(args[1]).openPrivateChannel().flatMap(dmChannel -> dmChannel.sendMessage(Main.messageFrom(args,2))).queue();
// 				message.delete().queue();
// 			}
			
// 			if ((cmd.equals("countdown") || cmd.equals("cd")) && author==me)
// 			{
// 				int count = Integer.parseInt(args[1])+1;
// 				if (count > 6)
// 				{
// 					channel.sendMessage("COUNT CANNOT BE MORE THAN 5").queue();
// 					return;
// 				}
// 				while (count-->1)
// 				{
// 					channel.sendMessage(count+"").queue();
// 				}
// 				channel.sendMessage("GO!").queue();
// 			}
			
// 			/*if (input.startsWith("log"))
// 			{
// 				event.getTextChannelById(input.substring(4)).sendMessage("Test Passed Successfully!").queue();
// 			}*/
// 			/*if (input.equals("embed"))
// 			{
				
// 					embed.setTitle("title");
// 					embed.setColor(Color.red);
// 					embed.setDescription("description");
// 					embed.addField("Title of field", "field", false);
// 					embed.setAuthor("Manjot1151#8472", null, "https://media.discordapp.net/attachments/742462874777026571/742610050908160032/Henrys_MC_PFP_Template_24.jpg?width=406&height=406");
// 					channel.sendMessage(embed.build()).queue();
// 			} */
// 			/*
// 			if (cmd.equals("lock"))
// 			{
// 				File locks = new File("locked_channels.txt");
				
// 				Boolean isLocked = false;
// 				Scanner in;
// 				try {
// 					in = new Scanner(locks);
// 					while (in.hasNextLine())
// 					{
// 						String[] locked_info = in.nextLine().split(" ");
// 						if (locked_info[0].equals(channel))
// 						{
// 							isLocked = Boolean.parseBoolean(locked_info[1]);
// 						}
// 					}
// 				} catch (FileNotFoundException e) {
// 					e.printStackTrace();
// 				}
				
// 				try {
// 					BufferedWriter writer = Files.newBufferedWriter(Paths.get("locked_channels.txt"), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
// 				} catch (IOException e) {
// 					e.printStackTrace();
// 				}
// 				if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
// 				{
// 					channel.sendMessage("Missing permission `ADMINISTRATOR`").queue();
// 					return;
// 				} 

// 				message.delete().queue();
// 				if (!lock)
// 				{
// 					embed.setColor(Color.red);
// 					embed.setTitle("Server locked!");
// 					embed.setFooter("Requested by " + event.getAuthor().getName() + " (" + event.getAuthor().getId() + ")",event.getMember().getUser().getAvatarUrl());
// 					channel.sendMessage(embed.build()).queue();	
// 				}
// 				else
// 				{
// 					channel.sendMessage("Server is already locked!").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
// 				}
// 			}
// 			if (cmd.equals("unlock"))
// 			{
// 			if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
// 			{
// 				channel.sendMessage("Missing permission `ADMINISTRATOR`").queue();
// 				return;
// 			}
// 				message.delete().queue();
// 				if (lock)
// 				{
// 					//channel.sendMessage("Server unlocked!").queue();
// 					embed.setColor(Color.green);
// 					embed.setTitle("Server unlocked!");
// 					embed.setFooter("Requested by " + event.getAuthor().getName() + " (" + event.getAuthor().getId() + ")",event.getMember().getUser().getAvatarUrl());
// 					channel.sendMessage(embed.build()).queue();
// 					Main.lock=false;
// 				}
// 				else
// 				{
// 					channel.sendMessage("Server is already unlocked!").queue();
// 				}
// 			}*/
		
			

// 			if (cmd.equals("howcool"))
// 			{
// 				if (args.length==1)
// 				{
// 					message.reply("You are " + Main.randomPercentage(0,100) + "% cool!").queue();
// 				}
// 				else if (message.getMentionedUsers().isEmpty())
// 				{
// 					message.reply("Please mention a valid user!").queue();
// 				}
// 				else
// 				{
// 					message.reply(args[1] + " is " + Main.randomPercentage(0,100) + "% cool!").queue();    
// 				}
				
// 			}
			
// 			if (cmd.equals("spam"))
// 			{
// 				//channel.sendMessage("This command has currently been disabled because spam bad").queue();
// 				if (author!=me) return;
// 				if (bot.hasPermission(Permission.MESSAGE_MANAGE))
// 				message.delete().queue();;
// 				int n = Integer.parseInt(args[1]);
// 				for (int j = 2; j < args.length; j++)
// 				{
// 					Main.msg+=args[j] + " ";
// 				}
// 				/*if (n>20) 
// 				{
// 					n=20;
// 					channel.sendMessage("Spam command is capped to 20 messages!").queue();
// 				}*/
// 				while (n-->0)
// 				{
// 					channel.sendMessage(Main.msg).queue();
// 				}
// 			} 
// 			if (cmd.equals("help"))
// 			{
// 				embed.setColor(Color.YELLOW);
// 				embed.setTitle("Help Menu");
// 				embed.setDescription("Prefix: `" + prefix + "`");
// 				embed.setThumbnail(bot.getUser().getEffectiveAvatarUrl());
// 				ArrayList<String> config = new ArrayList<String>()
// 				{{
// 					add("prefix");
// 					add("welcome");
// 				}};
// 				ArrayList<String> moderation = new ArrayList<String>()
// 				{{
// 					add("kick");
// 					add("ban");
// 					add("unban");
// 					add("channels");
// 					add("roles");
// 					add("ginfo");
// 					add("createrole");
// 					add("deleterole");
// 					add("purge");
// 					add("role");
// 				}};
// 				ArrayList<String> animals = new ArrayList<String>()
// 				{{
// 					add("dog");
// 					add("cat");
// 					add("panda");
// 					add("redpanda");
// 					add("birb");
// 					add("fox");
// 					add("koala");
// 					add("raccoon");
// 					add("kangaroo");
// 					add("fact");
// 				}};
// 				ArrayList<String> image = new ArrayList<String>()
// 				{{
// 					add("triggered");
// 					add("comment");
// 					add("tweet");
// 					add("gay");
// 					add("glass");
// 					add("wasted");
// 					add("grayscale");
// 					add("invert");
// 					add("invertgrayscale");
// 					add("brightness");
// 					add("jail");
// 					add("missionpassed");
// 					add("communism");
// 					add("stupid");
// 				}};
// 				ArrayList<String> fun = new ArrayList<String>()
// 				{{
// 					add("joke");
// 					add("meme");
// 					add("pokedex");
// 					add("coinflip");
// 					add("roll");
// 					add("poll");
// 					add("say");
// 					add("reverse");
// 					add("emoji");
// 					add("howcool");
// 					add("engrish");
// 				}};
// 				ArrayList<String> minecraft = new ArrayList<String>()
// 				{{
// 					add("profile");
// 					add("skin");
// 					add("server");
// 					add("hypixel");
// 				}};
// 				ArrayList<String> miscellaneous = new ArrayList<String>()
// 				{{
// 					add("define");
// 					add("userinfo");
// 					add("calculate");
// 					add("latex");
// 					add("translate");
// 					add("avatar");
// 					add("ping");
// 					add("uptime");
// 					add("ip");
// 					add("invite");
// 				}};
// 				LinkedHashMap<String, ArrayList<String>> commands = new LinkedHashMap<String, ArrayList<String>>()
// 				{{
// 					put("<a:gear_spinning:865491740374990848> Configuration", config);
// 					put("<:certified_moderator:850106245306384437> Moderation", moderation);
// 					put(":dog: Animals", animals);
// 					put("\uD83D\uDDBC Image", image);
// 					put(":tada: Fun", fun);
// 					put("<a:minecraft:865343543560372245> Minecraft", minecraft);
// 					put(":paperclip: Miscellaneous", miscellaneous);
// 				}};
// 				for (Entry<String, ArrayList<String>> map : commands.entrySet())
// 				{
// 					String commandList = "";
// 					for (String c : map.getValue())
// 					{
// 						commandList += ", `" + c + "`";
// 					}
// 					commandList = commandList.replaceFirst(", ", "");
// 					embed.addField(map.getKey(), commandList, false);
// 				}
// 				embed.addField("Links","[Invite](https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=805629046&scope=bot) | [Support](https://discord.gg/W8hnfK5czn) | [Vote](https://top.gg/bot/741412043411816488/vote)",false);
// 				embed.setFooter("Made by " + me.getAsTag(), me.getEffectiveAvatarUrl());
// 				message.replyEmbeds(embed.build()).queue();
// 				// //channel.sendMessage("`!prefix <str>` - Change prefix of bot\n`!ping` - Test ping of bot\n`!howcool <user>` - Check cool-o-meter of anyone\n`!say <str>` - Make the bot say anything\n`!spam <n> <msg>` - Make the bot spam your `<msg>` `<n>` times (capped at 20)\n`!lock` - Lock the server\n`!unlock` - Unlock the server\n`!block <ID>` - Block a user\n`!unblock <ID>` - Unblock a user\n`!invite` - Invite the bot to your server\nBot made by `Manjot1151#8472`").queue();
// 				// embed.setTitle("List of all Commands");
// 				// //embed.setDescription("Use Prefix `" + prefix + "`");
// 				// embed.setColor(Color.yellow);
// 				// embed.addField("Config", )
// 				// embed.addField(prefix+"prefix", "Set a custom prefix for the bot", false);
// 				// embed.addField(prefix+"welcome", "Set the default welcome message channel for the server", false);
// 				// embed.addField(prefix+"kick", "Kick the specified user from the server", false);
// 				// embed.addField(prefix+"ban", "Ban the specified user from the server", false);
// 				// embed.addField(prefix+"unban", "Unban the specified user on the server", false);
// 				// embed.addField(prefix+"userinfo", "Get detailed info about any user", false);
// 				// embed.addField(prefix+"channels", "Get a list of all channels in the server", false);
// 				// embed.addField(prefix+"roles", "Get a list of all roles in the server", false);
// 				// embed.addField(prefix+"createrole", "Create a new role by specified name", false);
// 				// embed.addField(prefix+"deleterole", "Delete a specified role", false);
// 				// //embed.addField(prefix+"deleteallrolesbyname", "Delete all roles by the same name as specified", false);
// 				// embed.addField(prefix+"purge", "Purge a specified amount of messages from a channel", false);
// 				// embed.addField(prefix+"role", "Add or remove a role from a user", false);
// 				// //embed.addField(prefix+"phone", "Specify a channel ID to open a phone link with them", false);
// 				// embed.addField(prefix+"avatar","Get avatar image of mentioned users",false);
// 				// embed.addField(prefix+"emoji", "Convert text into emoji-text", false);
// 				// //embed.addField(prefix+"dm","DM any user your message through the bot",false);
// 				// embed.addField(prefix+"say","Make the bot say your message",false);
// 				// embed.addField(prefix+"ping","Test ping of the bot",false);
// 				// embed.addField(prefix+"howcool","Check how cool a person is",false);
// 				// //embed.addField(prefix+"countdown", "Start a countdown from a specified number", false);
// 				// //embed.addField(prefix+"spam","**(disabled)** Make the bot spam your message a specified amount of times",false);
// 				// embed.addField(prefix+"dog", "Posts good boi doggo pics on your channel", false);
// 				// embed.addField(prefix+"cat", "Posts catto pics on your channel", false);
// 				// embed.addField(prefix+"invite","Invite the bot to your server", false);
// 				// //embed.addField(prefix+"lock","Lock the server to delete any new messages",false);
// 				// //embed.addField(prefix+"unlock","Unlock the server, messages will no longer be deleted",false);
// 				// //embed.addField(prefix+"block","Block a user from sending any messages",false);
// 				// //embed.addField(prefix+"unblock","Unblock a blocked user",false);
// 				// embed.addField(prefix+"hypixel", "Sub commands:\n" + prefix + "hypixel guild {ign}\n" + prefix + "hypixel status {ign}\n" + prefix + "hypixel sb ah {page} {index}\n" + prefix + "hypixel sb lbin {item}\n" + prefix + "hypixel sb reallbin {item}\n" + prefix + "hypixel sb bz {item}", false);
// 			}
			
		
// 		/*
// 			if (input.equals("admin"))
// 			{
// 				if (event.getMember().hasPermission(Permission.ADMINISTRATOR))
// 				{
// 					channel.sendMessage("u iz admin boi").queue();
// 				}
// 				else
// 				{
// 					echannel.sendMessage("u iz not admin boi").queue();
// 				}
// 			} */

// 			if (cmd.equals("kick"))
// 			{
// 				Member memberToKick = !message.getMentionedUsers().isEmpty()? message.getMentionedMembers().get(0) : guild.getMemberById(args[1]);

// 				if (!member.hasPermission(Permission.KICK_MEMBERS) || !bot.hasPermission(Permission.KICK_MEMBERS))
// 				{
// 					message.reply("Insufficient Permission!").queue();
// 					return;
// 				}
// 				if (member.getRoles().get(0).getPosition() <= memberToKick.getRoles().get(0).getPosition())
// 				{
// 					message.reply("Cannot kick a member with a role higher than or same level as yourself!").queue();
// 					return;
// 				}
// 				if (bot.getRoles().get(0).getPosition() <= memberToKick.getRoles().get(0).getPosition())
// 				{
// 					message.reply("Cannot kick a member with a role higher than or same level as myself!").queue();
// 					return;
// 				}
// 				if (memberToKick == member)
// 				{
// 					message.reply("You cannot kick yourself!").queue();
// 					return;
// 				}
// 				memberToKick.kick().queue();
// 				message.reply("Kicked User `" + memberToKick.getUser().getAsTag() + "` from the server.").queue();
// 			}

// 			if (cmd.equals("ban"))
// 			{
// 				Member memberToBan;
// 				try {
// 					memberToBan = !message.getMentionedMembers().isEmpty()? message.getMentionedMembers().get(0) : guild.retrieveMember(jda.retrieveUserById(args[1]).complete()).complete();
// 				} catch (Exception e) {
// 					memberToBan = null;
// 				}
// 				if (memberToBan == null)
// 				{
// 					message.reply("Invalid user!").queue();
// 					return;
// 				}
// 				if (!member.hasPermission(Permission.BAN_MEMBERS) || !bot.hasPermission(Permission.BAN_MEMBERS))
// 				{
// 					message.reply("Insufficient Permission!").queue();
// 					return;
// 				}
// 				if (member.getRoles().get(0).getPosition() <= memberToBan.getRoles().get(0).getPosition())
// 				{
// 					message.reply("Cannot ban a member with a role higher than or same level as yourself!").queue();
// 					return;
// 				}
// 				if (bot.getRoles().get(0).getPosition() <= memberToBan.getRoles().get(0).getPosition())
// 				{
// 					message.reply("Cannot ban a member with a role higher than or same level as myself!").queue();
// 					return;
// 				}
// 				if (memberToBan == member)
// 				{
// 					message.reply("You cannot ban yourself!").queue();
// 					return;
// 				}
// 				if (args.length>2)
// 				memberToBan.ban(0, Main.messageFrom(args, 2)).queue();
// 				else
// 				memberToBan.ban(0).queue();
// 				message.reply("Banned user `" + memberToBan.getUser().getAsTag() + "` from the server.").queue();
// 			}

// 			if (cmd.equals("unban"))
// 			{
// 				if (bot.hasPermission(Permission.BAN_MEMBERS) && member.hasPermission(Permission.BAN_MEMBERS))
// 				{
// 					User userToUnban = jda.retrieveUserById(args[1]).complete();
// 					guild.unban(userToUnban).queue();
// 					message.reply("Unbanned user `" + userToUnban.getAsTag() + "`").queue();
// 				}
// 				else
// 				{
// 					message.reply("Insuffiicient permission!").queue();
// 				}
// 			}

// 		/*if (cmd.equals("block"))
// 		{
// 			if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
// 			{
// 				channel.sendMessage("Missing permission `ADMINISTRATOR`").queue();
// 				return;
// 			}
// 			if (l==28 && !message.getMentionedUsers().isEmpty())
// 			{
// 				if (!input.substring(6).equals("<@!316481084835495937>"))
// 				{
// 					if (Main.blocked.contains(input.substring(9,l-1)))
// 					{
// 						channel.sendMessage("Mentioned user is already blocked!").queue();
// 					}
// 					else
// 					{
// 						Main.blocked.add(input.substring(9,l-1));
// 						embed.setAuthor("User Blocked!", null, message.getMentionedUsers().get(0).getAvatarUrl());
// 						embed.setDescription("**"+input.substring(6) + " has been blocked!**\n("+ message.getMentionedUsers().get(0).getId()+")");
// 						embed.setColor(Color.red);
// 						embed.setThumbnail(message.getMentionedUsers().get(0).getAvatarUrl());
// 						embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
// 						channel.sendMessage(embed.build()).queue();
// 					}
// 				}
// 				else
// 				{
// 					channel.sendMessage("You cannot block this user!").queue();
// 				}
// 			}
// 			else if (l==27 && !message.getMentionedUsers().isEmpty())
// 			{
// 				if (!input.substring(6).equals("<@316481084835495937>"))
// 				{
// 					if (Main.blocked.contains(input.substring(8,l-1)))
// 					{
// 						channel.sendMessage("User is already blocked!").queue();
// 					}
// 					else
// 					{
// 						Main.blocked.add(input.substring(8,l-1));
// 						embed.setAuthor("User Blocked!", null, message.getMentionedUsers().get(0).getAvatarUrl());
// 						embed.setDescription("**"+input.substring(6) + " has been blocked!**\n("+ message.getMentionedUsers().get(0).getId()+")");
// 						embed.setColor(Color.red);
// 						embed.setThumbnail(message.getMentionedUsers().get(0).getAvatarUrl());
// 						embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
// 						channel.sendMessage(embed.build()).queue();
// 						//channel.sendMessage(input.substring(6)+" has been blocked!").queue();
// 					}
// 				}
// 				else
// 				{
// 					channel.sendMessage("You cannot block this user!").queue();
// 				}
// 			}
// 			else
// 			{
// 				channel.sendMessage("Please mention a valid user!").queue();
// 			}
// 		}
// 		if (cmd.equals("unblock"))
// 		{	
// 			if (!event.getMember().hasPermission(Permission.ADMINISTRATOR))
// 			{
// 				channel.sendMessage("Missing permission `ADMINISTRATOR`").queue();
// 				return;
// 			}
// 			if (!message.getMentionedUsers().isEmpty() && l==30)
// 			{
// 				if (Main.blocked.indexOf(input.substring(11,l-1))!=-1)
// 				{
// 					blocked.remove(Main.blocked.indexOf(input.substring(11,l-1)));
// 					embed.setAuthor("User Unblocked!", null , message.getMentionedUsers().get(0).getAvatarUrl());
// 					embed.setDescription("**"+input.substring(8) + " has been unblocked!**\n("+ message.getMentionedUsers().get(0).getId()+")");
// 					embed.setThumbnail(message.getMentionedUsers().get(0).getAvatarUrl());
// 					embed.setColor(Color.green);
// 					embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
// 					channel.sendMessage(embed.build()).queue();
					
// 					//channel.sendMessage(input.substring(8)+" has been unblocked!").queue();
// 				}
// 				else
// 				{
// 					channel.sendMessage("Mentioned user is not blocked!").queue();
// 				}
// 			}
// 			else if (!message.getMentionedUsers().isEmpty() && l==29)
// 			{
// 				if (Main.blocked.indexOf(input.substring(10,l-1))!=-1)
// 				{
// 					blocked.remove(Main.blocked.indexOf(input.substring(10,l-1)));
// 					embed.setAuthor("User Unblocked!", null , message.getMentionedUsers().get(0).getAvatarUrl());
// 					embed.setThumbnail(message.getMentionedUsers().get(0).getAvatarUrl());
// 					embed.setDescription("**"+input.substring(8) + " has been unblocked!**\n("+ message.getMentionedUsers().get(0).getId()+")");
// 					embed.setColor(Color.green);
// 					embed.setFooter("Requested by " + event.getAuthor().getName(), event.getAuthor().getAvatarUrl());
// 					channel.sendMessage(embed.build()).queue();
					
// 					//channel.sendMessage(input.substring(8)+" has been unblocked!").queue();
// 				}
// 				else
// 				{
// 					channel.sendMessage("Mentioned user is not blocked!").queue();
// 				}
// 			}
// 			else
// 			{
// 				channel.sendMessage("Please mention a valid user!").queue();
// 			}
// 		}*/
// 		if (cmd.equals("invite"))
// 		{
// 			//channel.sendMessage("The invite link for the bot is\nhttps://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=8&scope=bot").queue();
// 			embed.setColor(Color.yellow);
// 			embed.setTitle("Click here to invite the bot!", "https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=805629046&scope=bot");
// 			//embed.setDescription("[Invite Link](https://discord.com/api/oauth2/authorize?client_id=741412043411816488&permissions=268758134&scope=bot)");
// 			message.replyEmbeds(embed.build()).queue();
// 		}
// 		if (cmd.equals("prefix"))
// 		{
// 			if (!member.hasPermission(Permission.ADMINISTRATOR) && author!=me)
// 			{
// 				Error.replyMessage(message, "Insufficient Permission!");
// 				return;
// 			}
// 			else if (args.length!=2)
// 			{
// 				Error.replyMessage(message, "Invalid arguments!\n`" + prefix + "prefix {prefix}`");
// 			}
// 			else if (args[1].length() > 8)
// 			{
// 				Error.replyMessage(message, "Prefix cannot be longer than 8 characters!");
// 			}
// 			else
// 			{
// 				try {
// 					GuildConfig.setPrefix(guild.getId(), args[1]);
// 				} catch (IOException e) {
// 					Error.replyMessage(message, "Could not set the prefix!");
// 					e.printStackTrace();
// 				}
// 				message.reply("The prefix has been set to `" + args[1] + "`").queue();
// 			}
// 			/*
// 			if (input.charAt(7)==' ')
// 			{
// 				channel.sendMessage("Invalid character at the beginning!").queue();
// 			}
// 			else if (input.length()>15)
// 			{
// 				channel.sendMessage("Prefix cannot be longer than 8 characters!").queue();
// 			}
// 			else
// 			{
// 				Main.prefix=input.substring(7);
// 				Main.prefl=input.length()-7;
// 				channel.sendMessage("The prefix has been set to `" + input.substring(7) + "`").queue();
// 			}*/
// 		}
		
// 			if (cmd.equals("role"))
// 			{
// 				if (guild.getRolesByName(Main.messageFrom(args, 3), false).isEmpty())
// 				{
// 					message.reply("Role `" + Main.messageFrom(args, 3) +"` was not found!").queue();
// 					return;
// 				}
// 				if (!member.hasPermission(Permission.MANAGE_ROLES) || !bot.hasPermission(Permission.MANAGE_ROLES))
// 				{
// 					message.reply("Insufficient Permission!").queue();
// 					return;
// 				}
// 				if (member.getRoles().get(0).getPosition() <= guild.getRolesByName(Main.messageFrom(args, 3), false).get(0).getPosition())
// 				{
// 					message.reply("Cannot interact with a role higher than or same level as yourself!").queue();
// 					return;
// 				}
// 				if (bot.getRoles().get(0).getPosition() <= guild.getRolesByName(Main.messageFrom(args, 3), false).get(0).getPosition())
// 				{
// 					message.reply("Cannot interact with a role higher than or same level as myself!").queue();
// 					return;
// 				}
// 				if (message.getMentionedUsers().isEmpty() || args.length<4 || !args[2].startsWith("<"))
// 				{
// 					message.reply("**Invalid Usage!**\n`"+prefix+"role add/remove <user> <role>`").queue();
// 				}
// 				else if (args[1].equals("add") || args[1].equals("give"))
// 				{
// 					guild.addRoleToMember(message.getMentionedUsers().get(0).getId(), guild.getRolesByName(Main.messageFrom(args, 3), false).get(0)).queue();
// 					message.reply("Role `" + Main.messageFrom(args, 3) + "` was added to "+ message.getMentionedUsers().get(0).getAsMention()).queue();
// 				}

// 				else if (args[1].equals("remove"))
// 				{
// 					guild.removeRoleFromMember(message.getMentionedUsers().get(0).getId(), guild.getRolesByName(Main.messageFrom(args, 3), false).get(0)).queue();
// 					message.reply("Role `" + Main.messageFrom(args, 3) + "` was removed from "+ message.getMentionedUsers().get(0).getAsMention()).queue();
// 				}
// 			}
			

// 			if (cmd.equals("engrish"))
// 			{
// 				message.reply(Main.englis(Main.messageFrom(args,1))).queue();
// 			}

// 			if ((cmd.equals("memberlist") || cmd.equals("members")) && author==me)
// 			{
// 				Guild server = args.length == 1? guild : (author!=me) ? guild : jda.getGuildById(args[1]);
// 				embed.setTitle("Members [" + server.getMemberCount() + "]");
// 				embed.setFooter(server.getName(), server.getIconUrl());
// 				embed.setColor(Color.LIGHT_GRAY);
// 				embed.setThumbnail(server.getIconUrl());
// 				List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();
// 				String memberList = "";
// 				for (int i = 0; i < server.getMembers().size(); i++)
// 				{
// 					if ((memberList + server.getMembers().get(i).getAsMention() + " " + server.getMembers().get(i).getUser().getName() + "\n").length()<=2048)
// 					{
// 						memberList += server.getMembers().get(i).getAsMention() + " " + server.getMembers().get(i).getUser().getName() + "\n";
// 					}
// 					else
// 					{
// 						embed.setDescription(memberList);
// 						embeds.add(embed.build());
// 						memberList = "";
// 					}
// 				}
// 				embed.setDescription(memberList);
// 				embeds.add(embed.build());
// 				for (MessageEmbed e : embeds) 
// 				{
// 					message.replyEmbeds(e).queue();
// 				}
// 			}

// 			if (cmd.equals("channellist") || cmd.equals("channels"))
// 				{
// 					if (!member.hasPermission(Permission.ADMINISTRATOR) && author!=me)
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 						return;
// 					}
					
// 					Guild server = args.length == 1? guild : (author!=me) ? guild : jda.getGuildById(args[1]);
// 					embed.setFooter(server.getName(), server.getIconUrl());
// 					embed.setColor(Color.BLUE);
// 					embed.setThumbnail(server.getIconUrl());
// 					String category = "";
// 					String description = "";
// 					boolean categoryStarted = false;
// 					int categoryCount = 0;

// 					for (int i = 0; i < server.getChannels().size(); i++)
// 					{
// 						GuildChannel listchannel = server.getChannels().get(i);
// 						if (listchannel.toString().startsWith("GC"))
// 						{
// 							categoryCount++;
// 							if (categoryStarted)
// 							{
// 								embed.addField(category, description, false);
// 								category = listchannel.getName();
// 								description = "";
// 							}
// 							else
// 							{
// 								categoryStarted = true;
// 								embed.setDescription(description);
// 								category = listchannel.getName();
// 							}
// 						}
// 						else
// 						{
// 							description += "<#" + server.getChannels().get(i).getId() + ">\n";// (" + server.getChannels().get(i).getId() + ")\n";
// 						}
// 					}
// 					embed.setTitle("Channels [" + (server.getChannels().size() - categoryCount) + "]");
// 					embed.addField(category, description, false);
					
// 					message.replyEmbeds(embed.build()).queue();
// 				}
// 				if (cmd.equals("guildrolelist") || cmd.equals ("roles") || cmd.equals ("guildroles"))
// 				{
// 					if (!member.hasPermission(Permission.ADMINISTRATOR) && author!=me)
// 					{
// 						message.reply("Insufficient Permission!").queue();
// 						return;
// 					}
// 					Guild server = args.length > 1? jda.getGuildById(args[1]) : guild;
// 					List<Role> roles = server.getRoles();
// 					embed.setTitle("Roles [" + roles.size() + "]");
// 					embed.setFooter(server.getName(), server.getIconUrl());
// 					embed.setColor(Color.CYAN);
// 					embed.setThumbnail(server.getIconUrl());
// 					List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();

// 					for (int i = 0; i < roles.size(); i++)
// 					{
// 						try
// 						{
// 							embed.appendDescription(roles.get(i).getAsMention() + " (" + roles.get(i).getId() + ")\n");
// 						}
// 						catch (Exception e)
// 						{
// 							embeds.add(embed.build());
// 							embed.setDescription("");
// 						}
// 					}
// 					embeds.add(embed.build());
// 					message.replyEmbeds(embeds).queue();
// 				}
// 				if (cmd.equals("userinfo") || cmd.equals("whois") || cmd.equals("ui"))
// 				{
// 					String userroles="";
// 					User user = !message.getMentionedUsers().isEmpty()? message.getMentionedUsers().get(0) : args.length == 1? author : jda.getUserById(args[1]);
// 					Member member_ = guild.getMember(user); 
// 					embed.setThumbnail(user.getEffectiveAvatarUrl());
// 					embed.setDescription(user.getAsMention());
// 					embed.setAuthor(user.getAsTag(), null, user.getEffectiveAvatarUrl());
// 					embed.setFooter(guild.getName(), guild.getIconUrl());
// 					embed.setColor(Color.YELLOW);
// 					for (int i = 0; i < member_.getRoles().size(); i++)
// 					{
// 						userroles += member_.getRoles().get(i).getAsMention() + " ";
// 					}

// 					String status = member_.getOnlineStatus().toString();

// 					switch(status)
// 					{
// 						case "ONLINE":
// 							status = "<:online:841667691504336896> Online";
// 							break;

// 						case "OFFLINE":
// 							status = "<:offline:841667625499230229> Offline";
// 							break;

// 						case "DO_NOT_DISTURB":
// 							status = "<:dnd:841667625549561887> Do Not Disturb";
// 							break;

// 						case "IDLE":
// 							status = "<:idle:841667625335521292> Idle";
// 							break;
// 					}

// 					//String badges = user.getFlags().size()>0? firstCharUpperCase(user.getFlags().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("_", " ")).replaceAll(",", "\n") : "";
// 					String perms = "";
// 					for (Permission permission : member.getPermissionsExplicit())
// 					{
// 						perms += Main.firstCharUpperCase(permission.toString().replaceAll("_", " ")) + ", ";
// 					}
// 					perms = perms.substring(0, perms.length() - 2);
					
// 					String badgeString = Main.createBadgeList(user.getFlags());

// 					DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
// 					embed.addField("Joined", member_.getTimeJoined().format(formatter), true);
// 					embed.addField("Registered", member_.getTimeCreated().format(formatter), true);
// 					embed.addField("Status", status, false);
// 					embed.addField("Roles [" + member_.getRoles().size() + "]", userroles, false);
// 					embed.addField("Permissions", perms, false);
// 					embed.addField("Badges [" + user.getFlags().size() + "]", badgeString, false);
// 					message.replyEmbeds(embed.build()).queue();
// 				}

// 			// private commands
// 			if (author == me)
// 			{
// 				if (cmd.equals("toguild"))
// 				{
// 					jda.getGuildById(args[1]).getDefaultChannel().sendMessage(Main.messageFrom(args, 2)).queue();
// 				}
	
// 				if (cmd.equals("tochannel"))
// 				{
// 					jda.getTextChannelById(args[1]).sendMessage(Main.messageFrom(args, 2)).queue();
// 				}
				
// 				if (cmd.equals("invitelink"))
// 				{
// 					jda.getTextChannelById(args[1]).createInvite().setMaxAge(Integer.parseInt(args[2])).queue();
// 					Main.invitelinkcreatechannel=channel.getId();
// 				}
// 				if (cmd.equals("serverlist") || cmd.equals("servers") || cmd.equals("guilds") || cmd.equals("guildlist"))
// 				{
// 					embed.setTitle("Server List [" + jda.getGuilds().size() + "]");
// 					embed.setColor(Color.RED);
// 					List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();

// 					for (int i = 0; i < jda.getGuilds().size(); i++)
// 					{
// 						Guild server = jda.getGuilds().get(i);
// 						try {
// 							embed.appendDescription("(" + server.getId() + ") `" + server.getName() + "`\n");
// 						} catch (Exception e) {
// 							embeds.add(embed.build());
// 							embed.setDescription("");
// 							embed.appendDescription("(" + server.getId() + ") `" + server.getName() + "`\n");
// 						}
// 					}
// 					embeds.add(embed.build());
// 					message.replyEmbeds(embeds).queue();
// 				}

// 				if (cmd.equals("guildinvites"))
// 				{
// 					String invitesString = "__Invites__\n";
// 					Guild invitesGuild = (args.length > 1)? jda.getGuildById(args[1]) : guild;
// 					for (Invite invite : invitesGuild.retrieveInvites().complete())
// 					{
// 						invitesString += "`" + invite.getChannel().getName() + "` `" + invite.getInviter().getAsTag() + "`\nUses: `" + invite.getUses() + "`\n" + invite.getUrl() + "\n\n";
// 					}
// 					message.reply(invitesString).queue();
// 				}

				
// 				if (cmd.equals("copyrole"))
// 				{
// 					if (author!=me) return;
// 					Role copyrole = jda.getGuildById(args[1]).getRolesByName(Main.messageFrom(args, 2), true).get(0);
// 					copyrole.createCopy().queue();
// 				}
// 				if (cmd.equals("guildrole"))
// 			{
// 				Guild server = jda.getGuildById(args[1]);
// 				Member person = server.getMemberById(args[2]);
// 				if (args[3].equals("add") || args[3].equals("give"))
// 				{
// 					if (server.getRolesByName(Main.messageFrom(args, 5), true).isEmpty())
// 					{
// 						channel.sendMessage("Role `" + Main.messageFrom(args, 5) +"` was not found!").queue();
// 					}
// 					else
// 					{	
// 						server.addRoleToMember(person.getId(), server.getRolesByName(Main.messageFrom(args, 5), true).get(Integer.parseInt(args[4]))).queue();
// 						channel.sendMessage("Role `" + Main.messageFrom(args, 5) + "` was added to "+ person.getAsMention()).queue();
// 					}
// 				}

// 				else if (args[3].equals("remove"))
// 				{
// 					if (server.getRolesByName(Main.messageFrom(args, 5), true).isEmpty())
// 					{
// 						channel.sendMessage("Role `" + Main.messageFrom(args, 5) +"` was not found!").queue();
// 					}
// 					else
// 					{
// 						server.removeRoleFromMember(person.getId(),server.getRolesByName(Main.messageFrom(args, 5), true).get(Integer.parseInt(args[4]))).queue();
// 						channel.sendMessage("Role `" + Main.messageFrom(args, 5) + "` was removed from "+ person.getAsMention()).queue();
// 					}
					
// 				}
// 			}
// 			}
			

// 			// private command end
			
// 			if (cmd.equals("phone"))
// 			{
// 				if (author != me) return;
// 				if (!Main.phonelinked)
// 				{
// 					if (!Main.phonerequest)
// 					{
// 						Main.phonechannel1 = channel.getId();
// 						Main.phonechannel2 = args[1];
// 						if (Main.phonechannel1.equals(Main.phonechannel2))
// 						{
// 							channel.sendMessage("Why are you trying to call yourself?").queue();
// 							return;
// 						}
// 						channel.sendMessage("Calling channel by ID: " + Main.phonechannel2).queue();
// 						jda.getTextChannelById(Main.phonechannel2).sendMessage("Text Channel By ID: " + Main.phonechannel1 + " is attempting to call you...\nType `!phone` to accept!").queue();
// 						Main.phonerequest=true;
// 					}
// 					else if (channel.getId().equals(Main.phonechannel2))
// 					{
// 						Main.phonerequest=false;
// 						Main.phonelinked=true;
// 						jda.getTextChannelById(Main.phonechannel1).sendMessage("Phone connection established!").queue();
// 						jda.getTextChannelById(Main.phonechannel2).sendMessage("Phone connection established!").queue();
// 						return;
// 					}
// 				}
// 				else if (channel.getId().equals(Main.phonechannel1) || channel.getId().equals(Main.phonechannel2))
// 				{		
// 					jda.getTextChannelById(Main.phonechannel1).sendMessage("Phone connection ended!").queue();
// 					jda.getTextChannelById(Main.phonechannel2).sendMessage("Phone connection ended!").queue();
// 					Main.phonelinked=false;
// 					Main.phonerequest=false;
// 					return;
// 				}
// 				else
// 				{
// 					channel.sendMessage("Only 2 text channels can be connected at once!").queue();
// 					return;
// 				}
// 			}
			
// 		}
// 		if (Main.phonelinked)
// 			{
// 				if (channel.getId().equals(Main.phonechannel1))
// 				{
// 					jda.getTextChannelById(Main.phonechannel2).sendMessage("**" + event.getAuthor().getName() + "**: " + message.getContentRaw()).queue();
// 				}
// 				else if (channel.getId().equals(Main.phonechannel2))
// 				{
// 					jda.getTextChannelById(Main.phonechannel1).sendMessage("**" + event.getAuthor().getName() + "**: " + message.getContentRaw()).queue();
// 				}
// 			}
// 	}
// }
