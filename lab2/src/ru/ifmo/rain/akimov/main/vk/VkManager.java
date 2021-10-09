package ru.ifmo.rain.akimov.main.vk;

import java.util.ArrayList;
import java.util.List;

public class VkManager {
    private final VkClient client;

    public VkManager(final VkClient client) {
        this.client = client;
    }

    public List<Long> getHistogram(final String hashtag, final int hours) {
        if (hours < 1 || hours > 24) {
            throw new VkClientException("Hours should be a valid int in [1, 24]");
        }
        if (hashtag.chars().anyMatch(character -> !(Character.isLetterOrDigit(character) || character == '-'))) {
            throw new VkClientException("Hashtag should consist of letters or digits or hyphens");
        }
        final List<Long> histogram = new ArrayList<>();
        for (int hour = 0; hour < hours; hour++) {
            histogram.add(client.getInfo(hashtag, hour));
        }
        return histogram;
    }
}
