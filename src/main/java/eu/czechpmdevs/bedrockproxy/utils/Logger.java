package eu.czechpmdevs.bedrockproxy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public void info(String text) {
        System.out.println(TextFormat.colorize("§b[" + this.getTime() + "] §3[Info] §f" + text));
    }

    public void error(String text) {
        System.out.println(TextFormat.colorize("§b[" + this.getTime() + "] §4[Error] §c" + text));
    }

    private String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}