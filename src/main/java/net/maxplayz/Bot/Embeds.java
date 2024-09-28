package net.maxplayz.Bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.maxplayz.Config;
import net.maxplayz.CONSTANTS;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import static net.maxplayz.Minecord.BOT;

@SuppressWarnings("ALL")
public class Embeds {
    public static void send_join_embed(Player player) throws NullPointerException {
        //noinspection DataFlowIssue
        long channelId = (long) Config.readConfig("channelId");
        String playericonurl = Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString();
        if (BOT != null) {
            TextChannel textChannel = BOT.getTextChannelById(channelId);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(String.format(CONSTANTS.JOIN_MESSAGE, player.getDisplayName()))
                    .setThumbnail(String.format(playericonurl, player.getName()))
                    .setDescription(CONSTANTS.JOIN_DESCRIPTION)
                    .setColor(CONSTANTS.GREEN)
                    .setTimestamp(Instant.now())
                    .build();

            Objects.requireNonNull(textChannel).sendMessageEmbeds(embed).queue();
        }
    }

    public static void send_leave_embed(Player player) throws NullPointerException {
        //noinspection DataFlowIssue
        long channelId = (long) Config.readConfig("channelId");
        String playericonurl = Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString();
        if (BOT != null) {
            TextChannel textChannel = BOT.getTextChannelById(channelId);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(String.format(CONSTANTS.LEAVE_MESSAGE, player.getDisplayName()))
                    .setThumbnail(String.format(playericonurl, player.getName()))
                    .setDescription(CONSTANTS.LEAVE_DESCRIPTION)
                    .setColor(CONSTANTS.RED)
                    .setTimestamp(Instant.now())
                    .build();

            Objects.requireNonNull(textChannel).sendMessageEmbeds(embed).queue();
        }
    }

    public static void send_message_embed(Player player, String message) throws NullPointerException {
        //noinspection DataFlowIssue
        long channelId = (long) Config.readConfig("channelId");
        String playericonurl = Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString();
        if (BOT != null) {
            TextChannel textChannel = BOT.getTextChannelById(channelId);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(player.getDisplayName())
                    .setThumbnail(String.format(playericonurl, player.getName()))
                    .setDescription(message)
                    .setColor(CONSTANTS.BLUE)
                    .setTimestamp(Instant.now())
                    .build();

            Objects.requireNonNull(textChannel).sendMessageEmbeds(embed).queue();
        }
    }

    public static void send_achievement_embed(Player player, String advancement) throws NullPointerException {
        //noinspection DataFlowIssue
        long channelId = (long) Config.readConfig("channelId");
        String playericonurl = Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString();
        if (BOT != null) {
            TextChannel textChannel = BOT.getTextChannelById(channelId);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(String.format(CONSTANTS.ACHIEVEMENT_MESSAGE, player.getName()))
                    .setThumbnail(String.format(playericonurl, player.getName()))
                    .setDescription(String.format(CONSTANTS.ACHIEVEMENT_DESCRIPTION, advancement))
                    .setColor(CONSTANTS.GOLD)
                    .setTimestamp(Instant.now())
                    .build();

            Objects.requireNonNull(textChannel).sendMessageEmbeds(embed).queue();
        }
    }

    public static void send_death_embed(Player player, String deathmsg) throws IOException, NullPointerException {
        String playericonurl = Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString();
            if (BOT != null) {
                //noinspection DataFlowIssue
                long channelId = (long) Config.readConfig("channelId");
                TextChannel textChannel = BOT.getTextChannelById(channelId);

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle(String.format(CONSTANTS.DEATH_MESSAGE, player.getName()))
                        .setThumbnail(String.format(playericonurl, player.getName()))
                        .setDescription(deathmsg)
                        .setColor(CONSTANTS.RED)
                        .setTimestamp(Instant.now())
                        .build();

                Objects.requireNonNull(textChannel).sendMessageEmbeds(embed).queue();
        }
    }
}
