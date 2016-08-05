package org.work.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

import static org.work.twitter.SettingProperties.*;

public class TwitterSearcher {

    // 取得したいキーワードを指定
    private static final String[] TRACK = new String[]{ "オリンピック", "#オリンピック", "#リオ2016", "#リオオリンピック" };
    // 取得したいアカウントのTwetterIDを指定
    private static final long[] FOLLOW = new long[]{ 53929093 };
    // ファイルに保存するツイート数
    private static final int SAVE_FILE_TWEET_COUNT = 10;

    private final SettingProperties properties;

    public TwitterSearcher(SettingProperties properties) {
        this.properties = properties;
    }

    public void run() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(properties.getProperty(PropKey.twitter_oauth_consumerKey))
                .setOAuthConsumerSecret(properties.getProperty(PropKey.twitter_oauth_consumerSecret))
                .setOAuthAccessToken(properties.getProperty(PropKey.twitter_oauth_accessToken))
                .setOAuthAccessTokenSecret(properties.getProperty(PropKey.twitter_oauth_accessTokenSecret));
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(new CSVStatusListener(properties, SAVE_FILE_TWEET_COUNT));

        FilterQuery query = new FilterQuery();
        query.track(TRACK);
        query.follow(FOLLOW);
        query.language(new String[]{ "ja" });

        // 検索実行
        twitterStream.filter(query);
    }

    // TODO 外部から読み込む場合に使用する
    private long[] getFOLLOW() {
        ArrayList<String> follow = new ArrayList<>();

        follow.add("53929093");

        return follow.stream().mapToLong(s -> Long.valueOf(s)).toArray();
    }

    // TODO 外部から読み込む場合に使用する
    private String[] getTrack() {
        ArrayList<String> track = new ArrayList<>();

        track.add("オリンピック");
        track.add("#オリンピック");
        track.add("#リオ2016");
        track.add("#リオオリンピック");

        return track.toArray(new String[track.size()]);
    }

}