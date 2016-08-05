package org.work.twitter;

public class Main {
    public static void main(String[] args) {
        SettingProperties properties = new SettingProperties("../../../setting.properties");
        new TwitterSearcher(properties).run();
    }
}
