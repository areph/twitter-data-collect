package org.work.twitter;

import org.junit.Test;

import org.junit.Test;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by areph on 2016/08/08.
 */
public class TwitterSearcherTest {
    @Test
    public void getTrackTest() throws Exception {
        TwitterSearcher sut = new TwitterSearcher(new SettingProperties("setting.properties"));
        String[] track = sut.getTrack();

        assertThat(track.length, is(5));
        assertThat(track[0], is("てすと"));
        assertThat(track[1], is("テスト"));
        assertThat(track[2], is("2015"));
        assertThat(track[3], is("2016"));
        assertThat(track[4], is("年月"));

    }

    @Test
    public void getFollowTest() throws Exception {
        TwitterSearcher sut = new TwitterSearcher(new SettingProperties("setting.properties"));
        long[] follow = sut.getFollow();

        assertThat(follow.length, is(4));
        assertThat(follow[0], is(11111L));
        assertThat(follow[1], is(22222L));
        assertThat(follow[2], is(33333L));
        assertThat(follow[3], is(44444L));
    }

}