package org.work.twitter;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by areph on 2016/08/04.
 */
public class AWSS3Test {
    @Test
    public void fileNameTest() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'tweetdata_'yyyyMMdd'_'HHmmss'.csv'");
        ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println(time.format(formatter));
    }

}