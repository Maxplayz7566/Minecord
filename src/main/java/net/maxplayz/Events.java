package net.maxplayz;

import net.dv8tion.jda.api.EmbedBuilder;
import net.maxplayz.Bot.Embeds;
import net.maxplayz.Bot.EventListeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Minecord.LOGGER.info("Sending player joined embed");
        EventListeners.reloadCommands();

        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");
        if ((boolean) Objects.requireNonNull(webhookData).get("enabled")) {
            net.maxplayz.Webhook.Embeds.send_join_embed(event.getPlayer());
        } else {
            Embeds.send_join_embed(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Minecord.LOGGER.info("Sending player left embed");
        EventListeners.reloadCommands();

        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");
        if ((boolean) Objects.requireNonNull(webhookData).get("enabled")) {
            net.maxplayz.Webhook.Embeds.send_leave_embed(event.getPlayer());
        } else {
            Embeds.send_leave_embed(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");

        assert webhookData != null;
        boolean webhookEnabled = (boolean) webhookData.get("enabled");

        if (webhookEnabled) {
            Minecord.LOGGER.info(String.format("Sending message: '%s' with webhook, from: '%s'.", event.getMessage(), event.getPlayer().getName()));
            net.maxplayz.Webhook.Embeds.send_message_embed(event.getPlayer(), event.getMessage());
        } else {
            Minecord.LOGGER.info(String.format("Sending message: '%s' with bot, from: '%s'.", event.getMessage(), event.getPlayer().getName()));
            Embeds.send_message_embed(event.getPlayer(), event.getMessage());
        }
    }
    
    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        if (event.getAdvancement() != null && event.getAdvancement().getDisplay() != null && event.getAdvancement().getDisplay().getTitle() != null) {
            Objects.requireNonNull(event.getAdvancement().getDisplay()).getTitle();
            Minecord.LOGGER.info("Sending player advancement embed");
            //noinspection unchecked
            @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");
            if ((boolean) Objects.requireNonNull(webhookData).get("enabled")) {
                net.maxplayz.Webhook.Embeds.send_advancment_embed(event.getPlayer(), event.getAdvancement().getDisplay().getTitle());
            } else {
                Embeds.send_achievement_embed(event.getPlayer(), event.getAdvancement().getDisplay().getTitle());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
        Minecord.LOGGER.info("Sending player death embed");

        //noinspection unchecked
        @SuppressWarnings("unchecked") Map<String, Object> webhookData = (Map<String, Object>) Config.readConfig("webhook");
        if ((boolean) Objects.requireNonNull(webhookData).get("enabled")) {
            net.maxplayz.Webhook.Embeds.send_death_embed(Objects.requireNonNull(event.getEntity().getPlayer()), event.getDeathMessage());
        } else {
            Embeds.send_death_embed(event.getEntity().getPlayer(), event.getDeathMessage());
        }
    }
}
