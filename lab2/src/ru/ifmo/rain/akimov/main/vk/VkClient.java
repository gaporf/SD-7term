package ru.ifmo.rain.akimov.main.vk;

import java.io.IOException;
import java.util.Calendar;

import org.json.*;
import ru.ifmo.rain.akimov.main.http.UrlReader;

public class VkClient {
    private static final int SECONDS_IN_HOUR = 3600;
    private static final String ACCESS_TOKEN = "0b790dab0b790dab0b790dab780b0086b000b790b790dab6a2e670724379bf9c4cddb78";
    private final String host;
    private final int port;
    private final UrlReader urlReader = new UrlReader();
    private final NewsParser newsParser = new NewsParser();

    public VkClient(final String host, final int port) {
        if (port < 1 || port > 65535) {
            throw new VkClientException("port should be a valid int in [1, 65535]");
        }
        this.host = host;
        this.port = port;
    }

    public long getInfo(final String hashtag, final int hours) {
        final int curTime = (int) (Calendar.getInstance().getTime().getTime() / 1000);
        final String sourceUrl = getUrl(hashtag, curTime - (hours + 1) * SECONDS_IN_HOUR, curTime - hours * SECONDS_IN_HOUR);
        String responseString;
        try {
            responseString = urlReader.getAsText(sourceUrl);
        } catch (final IOException e) {
            throw new VkClientException("Couldn't connect to the host properly", e);
        }
        try {
            return newsParser.parseResponse(responseString);
        } catch (final JSONException e) {
            throw new VkClientException("Incorrect JSON in the response", e);
        }
    }

    private String getUrl(final String hashtag, final int startTime, final int endTime) {
        return "https://" + host + ":" + port + "/method/newsfeed.search?"
                + "q=%23" + hashtag
                + "&access_token=" + ACCESS_TOKEN
                + "&v=5.131"
                + "&start_time=" + startTime
                + "&end_time=" + endTime;
    }
}
