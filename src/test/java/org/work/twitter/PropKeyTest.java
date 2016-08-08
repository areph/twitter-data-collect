package org.work.twitter;

import org.junit.Test;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by areph on 2016/08/05.
 */
public class PropKeyTest {

    @Test
    public void loadtest() {
        SettingProperties sut = new SettingProperties("setting.properties");
        assertThat(sut.getProperty(SettingProperties.PropKey.twitter_oauth_consumerKey), is("testconsumerkey"));
        assertThat(sut.getProperty(SettingProperties.PropKey.twitter_oauth_consumerSecret), is("testconsumersecret"));
        assertThat(sut.getProperty(SettingProperties.PropKey.twitter_oauth_accessToken), is("testaccesstoken"));
        assertThat(sut.getProperty(SettingProperties.PropKey.twitter_oauth_accessTokenSecret), is("testaccesstokensecret"));
        assertThat(sut.getProperty(SettingProperties.PropKey.s3_bucketName), is("testbucket"));
        assertThat(sut.getProperty(SettingProperties.PropKey.s3_key), is("tests3key"));
        assertThat(sut.getProperty(SettingProperties.PropKey.s3_secret_key), is("tests3secret"));
    }
}