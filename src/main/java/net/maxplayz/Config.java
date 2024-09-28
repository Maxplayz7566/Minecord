package net.maxplayz;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static net.maxplayz.Minecord.CONFIG_PATH;

@SuppressWarnings("ALL")
public class Config {
    private static final Map<String, Object> defaultConfigs = new HashMap<>();

    static {
        defaultConfigs.put("botToken", "INSERT TOKEN HERE");
        defaultConfigs.put("channelId", 0);
        defaultConfigs.put("playerHeadIcon", "https://minotar.net/armor/bust/%s/64.png");
        Map<String, Object> webhook = new HashMap<>();
        webhook.put("enabled", false);
        webhook.put("url", "INSERT URL HERE");
        defaultConfigs.put("webhook", webhook);
    }

    public static void writeDefaultConfigIfNotExists() {
        File configFile = new File(CONFIG_PATH);
        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Yaml yaml = new Yaml(options);
                yaml.dump(defaultConfigs, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object readConfig(String key) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(Files.newInputStream(Paths.get(CONFIG_PATH)));

            if (key != null) {
                return config.get(key);
            }
            return config;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}