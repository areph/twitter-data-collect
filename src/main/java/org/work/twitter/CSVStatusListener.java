package org.work.twitter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.work.twitter.SettingProperties.*;

public class CSVStatusListener implements StatusListener {

    private final Pattern pt = Pattern.compile("[\\r\\n]");
    private final AWSS3 s3;

    private final int fileSaveTweetCount;
    private final List<String> csvList = new ArrayList<>();

    public CSVStatusListener(SettingProperties properties, int fileSaveTweetCount) {
        this.fileSaveTweetCount = fileSaveTweetCount;
        s3 = new AWSS3(properties.getProperty(PropKey.s3_bucketName),
                properties.getProperty(PropKey.s3_key),
                properties.getProperty(PropKey.s3_secret_key)
        );
    }

    @Override
    public void onStatus(Status status) {
        if (status.isRetweet()) return;

        String csv = String.join(",",
                ZonedDateTime.ofInstant(status.getCreatedAt().toInstant(), ZoneId.of("Asia/Tokyo")).toString(), // 日付
                "@" + status.getUser().getScreenName(),  // アカウント名
                pt.matcher(status.getText()).replaceAll(" ")  // つぶやき
        );
        System.out.println(csv);
        csvList.add(csv);

        if (csvList.size() >= fileSaveTweetCount) {
            List<String> copyCsvList = new ArrayList<>(csvList);
            csvList.clear();
            s3.upload(copyCsvList);
        }

    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
