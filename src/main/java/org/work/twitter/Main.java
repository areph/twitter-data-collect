package org.work.twitter;

public class Main {
    public static void main(String[] args) {
        SettingProperties properties = new SettingProperties("setting.properties");
        TwitterSearcher twitter = new TwitterSearcher(properties);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            twitter.save();
            System.out.println("twitter data collect stoping...");
        }));

        twitter.run();

    }
}
