package ru.ifmo.rain.akimov.test.vk;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import ru.ifmo.rain.akimov.main.vk.NewsParser;

public class NewsParserTest {
    private final String NO_POSTS_RESPONSE = new JSONObject()
            .put("response", new JSONObject()
                    .put("total_count", 0)).toString();

    private final String ONE_POST_RESPONSE = new JSONObject()
            .put("response", new JSONObject()
                    .put("total_count", 1)).toString();

    private final String MANY_POSTS_RESPONSE = new JSONObject()
            .put("response", new JSONObject()
                    .put("total_count", 1337)).toString();

    private final String INCORRECT_RESPONSE_1 = "json";

    private final String INCORRECT_RESPONSE_2 = new JSONObject()
            .put("response", 5).toString();

    private final String INCORRECT_RESPONSE_3 = new JSONObject()
            .put("response", new JSONObject()
                    .put("total_count", "number")).toString();

    @Test
    public void noPostsResponse() {
        final NewsParser parser = new NewsParser();
        final long posts = parser.parseResponse(NO_POSTS_RESPONSE);
        Assert.assertEquals(0, posts);
    }

    @Test
    public void onePostResponse() {
        final NewsParser parser = new NewsParser();
        final long posts = parser.parseResponse(ONE_POST_RESPONSE);
        Assert.assertEquals(1, posts);
    }

    @Test
    public void manyPostsResponse() {
        final NewsParser parser = new NewsParser();
        final long posts = parser.parseResponse(MANY_POSTS_RESPONSE);
        Assert.assertEquals(1337, posts);
    }

    @Test
    public void incorrectResponse1() {
        final NewsParser parser = new NewsParser();
        Assert.assertThrows(JSONException.class, () -> parser.parseResponse(INCORRECT_RESPONSE_1));
    }

    @Test
    public void incorrectResponse2() {
        final NewsParser parser = new NewsParser();
        Assert.assertThrows(JSONException.class, () -> parser.parseResponse(INCORRECT_RESPONSE_2));
    }

    @Test
    public void incorrectResponse3() {
        final NewsParser parser = new NewsParser();
        Assert.assertThrows(JSONException.class, () -> parser.parseResponse(INCORRECT_RESPONSE_3));
    }
}
