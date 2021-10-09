package ru.ifmo.rain.akimov.test.vk;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import ru.ifmo.rain.akimov.main.rule.HostReachableRule;
import ru.ifmo.rain.akimov.main.vk.VkClient;

@HostReachableRule.HostReachable(VkClientIntegrationTest.HOST)
public class VkClientIntegrationTest {
    public static final String HOST = "api.vk.com";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void getInfoTest() {
        final VkClient client = new VkClient(HOST, 443);
        final long numOfPosts = client.getInfo("test", 5);
        Assert.assertTrue(numOfPosts >= 0);
    }

    @Test
    public void russianLettersTest() {
        final VkClient client = new VkClient(HOST, 443);
        final long numOfPosts = client.getInfo("Санкт-Петербург", 20);
        Assert.assertTrue(numOfPosts >= 0);
    }
}
