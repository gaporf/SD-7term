package ru.ifmo.rain.akimov.main.vk;

import org.json.JSONObject;

public class NewsParser {
    public long parseResponse(final String responseString) {
        final JSONObject json = new JSONObject(responseString);
        final JSONObject response = json.getJSONObject("response");
        return response.getLong("total_count");
    }
}
