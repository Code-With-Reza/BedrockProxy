package eu.czechpmdevs.bedrockproxy;

import eu.czechpmdevs.bedrockproxy.console.Console;
import eu.czechpmdevs.bedrockproxy.utils.Config;
import eu.czechpmdevs.bedrockproxy.utils.Logger;
import eu.czechpmdevs.bedrockproxy.utils.Utils;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProxyServer {

    @Getter
    private static ProxyServer instance;

    @Getter
    private boolean isRunning = true;

    @Getter
    private Logger logger;
    @Getter
    private Console console;

    @Getter
    private String motd;
    @Getter
    private String ip;
    @Getter
    private int port;
    @Getter
    private boolean isXboxAuthEnabled;

    public ProxyServer(Logger logger) {
        ProxyServer.instance = this;
        this.logger = logger;
        this.loadProperties();
        this.loadConfiguration();
        this.start();

        this.getLogger().info(this.getName() + " started in " + ((double) (System.currentTimeMillis() - BedrockProxy.START_TIME) / 1000) + " seconds!");
        this.tickProcessor();
    }

    private void loadProperties() {
        this.getLogger().info("Loading proxy.properties...");

        File file = new File(this.getDataPath() + "proxy.properties");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                this.getLogger().logException(exception);
            }

            Map<Object, Object> defaultProperties = new HashMap<Object, Object>();
            defaultProperties.put("proxy-name", this.getName());
            defaultProperties.put("proxy-ip", "0.0.0.0");
            defaultProperties.put("proxy-port", 19132);
            defaultProperties.put("xbox-auth", true);

            Config config = new Config(file, Config.PROPERTIES);
            config.setAll(defaultProperties);
            config.save();
        }

        Config config = new Config(file, Config.PROPERTIES);
        this.motd = (String) config.getAll().get("proxy-name");
        this.ip = (String) config.getAll().get("proxy-ip");
        this.port = Integer.parseInt((String) config.getAll().get("proxy-port"));
        this.isXboxAuthEnabled = (boolean) config.getAll().get("xbox-auth");
    }

    private void loadConfiguration() {
        this.getLogger().info("Loading proxy.yml...");

        File file = new File(this.getDataPath() + "proxy.yml");
        if(!file.exists()) {
            Utils.saveResource("proxy.yml");
        }

        Config config = new Config(file, Config.YAML);
        System.out.println(config.getAll());
    }

    private void start() {
        this.console = new Console(this);
    }

    private void tickProcessor() {
        while (this.isRunning()) {
            this.tick();
        }
    }

    private void tick() {
        this.getConsole().tick();
    }

    public void shutdown() {
        this.getLogger().info("Shutting down proxy server.");
        this.isRunning = false;
    }

    public String getName() {
        return BedrockProxy.NAME;
    }

    public String getDataPath() {
        return BedrockProxy.DATA_PATH;
    }
}
