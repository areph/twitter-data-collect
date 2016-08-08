package org.work.twitter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class SettingProperties {
    private Properties properties = new Properties();
    public enum PropKey {
        file_save_tweet_count,
        twitter_oauth_consumerKey,
        twitter_oauth_consumerSecret,
        twitter_oauth_accessToken,
        twitter_oauth_accessTokenSecret,
        s3_bucketName,
        s3_key,
        s3_secret_key,
        save_file_tweet_count,
    }

    public SettingProperties(String fileName) {
        load(fileName);
    }

    public void load(String fileName) {
        load(fileName, null);
    }
    public void load(String fileName, String encode) {
        properties = new Properties();

        try (InputStream in = ClassLoader.getSystemResourceAsStream(fileName)) {
            Reader reader = encode != null ? new InputStreamReader(in, encode) : new InputStreamReader(in);
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadToUTF(String fileName) {
        load(fileName, "UTF8");
    }
    public String getProperty(PropKey key) {
        return properties.getProperty(key.name());
    }
}
