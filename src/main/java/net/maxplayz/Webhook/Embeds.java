package net.maxplayz.Webhook;

import net.maxplayz.CONSTANTS;
import net.maxplayz.Config;
import net.maxplayz.Minecord;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("ALL")
public class Embeds {
    private static final String[] SMILIES = {"https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f600_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f603_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f604_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f604_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f601_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f606_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f605_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f923_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f602_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f642_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f643_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1fae0_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f609_color.png", "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f60a_color.png"};

    public static void send_message_embed(Player player, String message) {
        Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");
        if (webhookData != null) {
            String webhookUrl = webhookData.get("url").toString();
            String playericonurl = String.format(Config.readConfig("playerHeadIcon").toString(), player.getUniqueId());

            String jsonPre = """
                {
                    "content": "%s",
                    "username": "[MC] - %s",
                    "avatar_url": "%s"
                }""";

            WebhookHandler.send_data(String.format(jsonPre, message, player.getName(), playericonurl), webhookUrl);
        }
    }

    public static void send_death_embed(Player player, String deathmessage) {
        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");

        assert webhookData != null;
        String webhookUrl = Objects.requireNonNull(webhookData).get("url").toString();
        String playericonurl = String.format(Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString(), player.getUniqueId());

        String jsonPre = """
                {
                    "content": null,
                    "username": "Deaths",
                    "avatar_url": "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f480_color.png",
                    "embeds": [
                        {
                            "title": "%s",
                            "description": "%s",
                            "thumbnail": {
                                "url": "%s"
                            },
                            "color": "%s",
                            "timestamp": "%s"
                        }
                    ]
                }""";

        String jsonFinal = String.format(jsonPre, String.format(CONSTANTS.DEATH_MESSAGE, player.getName()), deathmessage, playericonurl, Minecord.rgbToLong(CONSTANTS.RED), Instant.now().toString());

        WebhookHandler.send_data(jsonFinal, webhookUrl);
    }

    public static void send_advancment_embed(Player player, String advancment) {
        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");

        assert webhookData != null;
        String webhookUrl = Objects.requireNonNull(webhookData).get("url").toString();
        String playericonurl = String.format(Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString(), player.getUniqueId());

        String jsonPre = """
                {
                    "content": null,
                    "username": "Advancments",
                    "avatar_url": "https://images.emojiterra.com/microsoft/fluent-emoji/512px/1f3c6_color.png",
                    "embeds": [
                        {
                            "title": "%s",
                            "description": "%s",
                            "thumbnail": {
                                "url": "%s"
                            },
                            "color": "%s",
                            "timestamp": "%s"
                        }
                    ]
                }""";

        String jsonFinal = String.format(jsonPre, String.format(CONSTANTS.ACHIEVEMENT_MESSAGE, player.getName()), String.format(CONSTANTS.ACHIEVEMENT_DESCRIPTION, advancment), playericonurl, Minecord.rgbToLong(CONSTANTS.GOLD), Instant.now().toString());

        WebhookHandler.send_data(jsonFinal, webhookUrl);
    }

    public static void send_leave_embed(Player player) {
        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");

        assert webhookData != null;
        String webhookUrl = Objects.requireNonNull(webhookData).get("url").toString();
        String playericonurl = String.format(Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString(), player.getUniqueId());

        Random random = new Random();

        int randomIndex = random.nextInt(SMILIES.length);

        String randomURL = SMILIES[randomIndex];

        String jsonPre = """
                {
                    "content": null,
                    "username": "Player updates",
                    "avatar_url": "%s",
                    "embeds": [
                        {
                            "title": "%s",
                            "description": "%s",
                            "thumbnail": {
                                "url": "%s"
                            },
                            "color": "%s",
                            "timestamp": "%s"
                        }
                    ]
                }""";

        String jsonFinal = String.format(jsonPre,
                randomURL,
                String.format(CONSTANTS.LEAVE_MESSAGE, player.getName()),
                CONSTANTS.LEAVE_DESCRIPTION,
                playericonurl, Minecord.rgbToLong(CONSTANTS.RED),
                Instant.now().toString());

        WebhookHandler.send_data(jsonFinal, webhookUrl);
    }

    public static void send_join_embed(Player player) {
        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");

        assert webhookData != null;
        String webhookUrl = Objects.requireNonNull(webhookData).get("url").toString();
        String playericonurl = String.format(Objects.requireNonNull(Config.readConfig("playerHeadIcon")).toString(), player.getUniqueId());

        Random random = new Random();

        int randomIndex = random.nextInt(SMILIES.length);

        String randomURL = SMILIES[randomIndex];

        String jsonPre = """
                {
                    "content": null,
                    "username": "Player updates",
                    "avatar_url": "%s",
                    "embeds": [
                        {
                            "title": "%s",
                            "description": "%s",
                            "thumbnail": {
                                "url": "%s"
                            },
                            "color": "%s",
                            "timestamp": "%s"
                        }
                    ]
                }""";

        String jsonFinal = String.format(jsonPre,
                randomURL,
                String.format(CONSTANTS.JOIN_MESSAGE, player.getName()),
                CONSTANTS.JOIN_DESCRIPTION,
                playericonurl, Minecord.rgbToLong(CONSTANTS.GREEN),
                Instant.now().toString());

        WebhookHandler.send_data(jsonFinal, webhookUrl);
    }
}
