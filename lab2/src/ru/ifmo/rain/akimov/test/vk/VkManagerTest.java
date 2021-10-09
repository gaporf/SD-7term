package ru.ifmo.rain.akimov.test.vk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.rain.akimov.main.vk.VkClient;
import ru.ifmo.rain.akimov.main.vk.VkManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class VkManagerTest {
    private VkManager vkManager;

    @Mock
    private VkClient vkClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vkManager = new VkManager(vkClient);
    }

    @Test
    public void oneHourTest() {
        when(vkClient.getInfo("test", 0))
                .thenReturn(42L);
        final List<Long> histogram = vkManager.getHistogram("test", 1);
        Assert.assertEquals(List.of(42L), histogram);
    }

    @Test
    public void severalHoursTest() {
        for (int hour = 0; hour < 5; hour++) {
            when(vkClient.getInfo("Санкт-Петербург", hour)).thenReturn(5L);
        }
        final List<Long> histogram = vkManager.getHistogram("Санкт-Петербург", 5);
        Assert.assertEquals(List.of(5L, 5L, 5L, 5L, 5L), histogram);
    }

    @Test
    public void allDayTest() {
        for (int hour = 0; hour < 24; hour++) {
            when(vkClient.getInfo("ITMO", hour)).thenReturn((long) hour);
        }
        final List<Long> histogram = vkManager.getHistogram("ITMO", 24);
        final List<Long> rightHistogram = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            rightHistogram.add((long) i);
        }
        Assert.assertEquals(rightHistogram, histogram);
    }
}
