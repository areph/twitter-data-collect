package org.work.twitter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SettingProperties properties = new SettingProperties("setting.properties");
        TwitterSearcher twitter = new TwitterSearcher(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitter.save();
            System.out.println("-> twitter data collect stoping...");
        }));

        try {
            twitter.run();
        } catch (IOException e) {
            System.out.println("-> Exceptions...");
            e.printStackTrace();
        }

    }
}
