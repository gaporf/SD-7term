package ru.ifmo.rain.akimov.main.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {
    public String getAsText(final String sourceUrl) throws IOException {
        final URL url = new URL(sourceUrl);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            final StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        }
    }
}
