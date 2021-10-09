package ru.ifmo.rain.akimov.test.url;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import ru.ifmo.rain.akimov.main.http.UrlReader;
import ru.ifmo.rain.akimov.main.rule.HostReachableRule;

import java.io.IOException;

@HostReachableRule.HostReachable("api.vk.com")
public class UrlReaderTest {

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void correctUrl() {
        try {
            final String url = "http://api.vk.com/method/newsfeed.search?q=%23Saint-Petersburg&access_token=0b790dab0b790dab0b790dab780b0086b000b790b790dab6a2e670724379bf9c4cddb78&v=5.131";
            final String result = new UrlReader().getAsText(url);
            Assert.assertTrue(result.length() > 0);
        } catch (final IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
