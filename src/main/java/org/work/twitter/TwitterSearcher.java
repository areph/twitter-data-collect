package org.work.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.work.twitter.SettingProperties.*;

public class TwitterSearcher {

    private final SettingProperties properties;
    private final AWSS3 s3;
    private final CSVStatusListener listener;
    private final ConfigurationBuilder cb = new ConfigurationBuilder();
    private static final String SEARCH_KEYWORD_FILE = "search-keyword-settings.csv";
    private static final String SEARCH_FOLLOW_FILE = "search-follow-settings.csv";

    public TwitterSearcher(SettingProperties properties) {
        this.properties = properties;
        s3 = new AWSS3(properties.getProperty(PropKey.s3_bucketName),
                properties.getProperty(PropKey.s3_key),
                properties.getProperty(PropKey.s3_secret_key)
        );
        listener = new CSVStatusListener(s3, Integer.valueOf(properties.getProperty(PropKey.save_file_tweet_count)));
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(properties.getProperty(PropKey.twitter_oauth_consumerKey))
                .setOAuthConsumerSecret(properties.getProperty(PropKey.twitter_oauth_consumerSecret))
                .setOAuthAccessToken(properties.getProperty(PropKey.twitter_oauth_accessToken))
                .setOAuthAccessTokenSecret(properties.getProperty(PropKey.twitter_oauth_accessTokenSecret));
    }

    public void run() {

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(listener);

        FilterQuery query = new FilterQuery();
        query.track(getTrack());
        query.follow(getFollow());
        query.language(new String[]{ "ja" });

        // 検索実行
        twitterStream.filter(query);
    }

    public long[] getFollow() {
        List<String> follow = readCSVFile(SEARCH_FOLLOW_FILE);

        return follow.stream().mapToLong(s -> Long.valueOf(s)).toArray();
    }

    public String[] getTrack() {
        List<String> track = readCSVFile(SEARCH_KEYWORD_FILE);

        return track.toArray(new String[track.size()]);
    }

    public List<String> readCSVFile(String fileName) {
        ArrayList<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Arrays.stream(line.split(",")).forEach(s -> data.add(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void save() {
        s3.upload(listener.getCsvList());
    }
}
