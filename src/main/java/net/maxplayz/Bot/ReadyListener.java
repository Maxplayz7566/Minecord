package net.maxplayz.Bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.maxplayz.Config;
import net.maxplayz.Minecord;
import org.jetbrains.annotations.NotNull;

public class ReadyListener implements EventListener {
    public static JDA startBot(String token) {
        JDA jda = JDABuilder.createDefault(token)
                .addEventListeners(new ReadyListener())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .build();

        jda.addEventListener(new EventListeners());

        return jda;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) throws NullPointerException {
        if (event instanceof ReadyEvent) {
            Minecord.LOGGER.info("Discord bot is online, should be at least.");
            //noinspection DataFlowIssue
            TextChannel textChannel = event.getJDA().getTextChannelById((long) Config.readConfig("channelId"));
            assert textChannel != null;
            Minecord.GUILD = textChannel.getGuild();
            EventListeners.reloadCommands();
        }
    }
}