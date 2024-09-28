package net.maxplayz.Bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.maxplayz.CONSTANTS;
import net.maxplayz.Minecord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Objects;

public class EventListeners extends ListenerAdapter {

    public static void reloadCommands() {
        Minecord.LOGGER.info("Reloading slash commands");
        OptionData playerOption = new OptionData(OptionType.STRING, "player", "The player you want to whisper to.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            playerOption.addChoice(player.getName(), player.getName());
        }

        Objects.requireNonNull(Minecord.GUILD).updateCommands().addCommands(
                Commands.slash("whisper", "Sends a message to a certain player only.")
                        .addOptions(playerOption)  // Add player options here
                        .addOption(OptionType.STRING, "message", "The message to whisper.")
        ).queue();
        Minecord.LOGGER.info("Reloaded slash commands, i'm hopeful.");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("whisper")) {
            if (event.getOption("player") != null && event.getOption("message") != null) {
                String player = Objects.requireNonNull(event.getOption("player")).getAsString();
                String message = Objects.requireNonNull(event.getOption("message")).getAsString();

                Player bukkitPlayer = Bukkit.getPlayerExact(Objects.requireNonNull(event.getOption("player")).getAsString());

                if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Success!")
                            .setDescription("Whispering: " + message + ", to: " + player)
                            .setColor(CONSTANTS.GREEN)
                            .setTimestamp(Instant.now())
                            .build();

                    event.replyEmbeds(embed).setEphemeral(true).queue();

                    String author = Objects.requireNonNull(event.getInteraction().getMember()).getEffectiveName();

                    Minecord.LOGGER.info("Author: " + author);
                    Minecord.LOGGER.info("Message: " + message);
                    Minecord.LOGGER.info("Player: " + player);

                    bukkitPlayer.sendMessage(ChatColor.RED + "[WHISPER] " + ChatColor.GREEN + " [" + author + "]"+ChatColor.GOLD + " --> " + ChatColor.RESET + message);
                } else {
                    MessageEmbed embed = new EmbedBuilder()
                            .setTitle("Error while using /whisper")
                            .setDescription("Please include both a player and a message, and or make sure they are online.")
                            .setColor(CONSTANTS.RED)
                            .setTimestamp(Instant.now())
                            .build();

                    event.replyEmbeds(embed).setEphemeral(true).queue();
                }
            } else {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Error while using /whisper")
                        .setDescription("Please include both a player and a message, and or make sure they are online.")
                        .setColor(CONSTANTS.RED)
                        .setTimestamp(Instant.now())
                        .build();

                event.replyEmbeds(embed).setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentStripped().equalsIgnoreCase("!reload")) {
            Minecord.LOGGER.info("Got !reload command trying to reload slash commands!");
            reloadCommands();
            Minecord.GUILD = event.getGuild();
        }

        String messageContent = event.getMessage().getContentStripped();
        String author = event.getAuthor().getName();
        String authorId = event.getAuthor().getId();

        TextComponent authorComponent = new TextComponent(author);
        authorComponent.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        authorComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "<@" + authorId + "> "));

        TextComponent finalMessage = new TextComponent(ChatColor.BLUE + "[DISCORD] ");
        finalMessage.addExtra(authorComponent);
        finalMessage.addExtra(ChatColor.GOLD + " --> " + ChatColor.RESET + messageContent);

        Bukkit.spigot().broadcast(finalMessage);
    }
}