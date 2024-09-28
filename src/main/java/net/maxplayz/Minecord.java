package net.maxplayz;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.maxplayz.Bot.ReadyListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public final class Minecord extends JavaPlugin implements Listener {
    public static String CONFIG_PATH = "";
    public static String CONFIG_DIR = "";
    public static final Logger LOGGER = Bukkit.getLogger();

    @Nullable
    public static JDA BOT = null;

    @Nullable
    public static Guild GUILD = null;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        File newFolder = new File(this.getDataFolder().getAbsolutePath());

        if (!newFolder.exists()) {
            newFolder.mkdirs();
        }

        CONFIG_PATH = newFolder + "/config.yml";
        CONFIG_DIR = newFolder.toString();

        Config.writeDefaultConfigIfNotExists();
        String token = Objects.requireNonNull(Config.readConfig("botToken")).toString();
        getServer().getPluginManager().registerEvents(new Events(), this);

        BOT = ReadyListener.startBot(token);
    }

    @Override
    public void onDisable() {
        Bukkit.broadcast(ChatColor.RED + "Please dont use /reload for " + ChatColor.GREEN + "Minecord" + ChatColor.RED + " it can break the bot/webhook", "2");

        if (BOT != null) {
            try {
                BOT.shutdown();
                if (!BOT.awaitShutdown(Duration.ofSeconds(10))) {
                    BOT.shutdownNow();
                    BOT.awaitShutdown();
                }
            } catch (InterruptedException e) {}
        }
    }

    public static long rgbToLong(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return ((Long.valueOf(red) << 16) | (Long.valueOf(green) << 8) | blue);
    }
}
