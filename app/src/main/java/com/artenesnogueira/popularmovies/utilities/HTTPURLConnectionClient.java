package com.artenesnogueira.popularmovies.utilities;

import com.artenesnogueira.popularmovies.models.HTTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility class to make network requests
 */
public class HTTPURLConnectionClient implements HTTPClient {

    //timeout in 5 seconds
    private static final int TIMEOUT = 5000;

    @Override
    public String get(String url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) (new URL(url)).openConnection();
        urlConnection.setConnectTimeout(TIMEOUT);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
