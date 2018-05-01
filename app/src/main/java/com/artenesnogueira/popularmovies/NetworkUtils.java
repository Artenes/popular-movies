package com.artenesnogueira.popularmovies;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility class to make network requests
 */
class NetworkUtils {

    /**
     * Gets a response from a HTTP url while login the URL and the response
     *
     * @param TAG The TAG to print the log with
     * @param url The URL to make the request with
     * @return the raw response from the server
     * @throws IOException in case of connection failure or error while reading the response
     */
    public static String getResponseFromHttpUrlWithLog(String TAG, URL url) throws IOException {
        Log.v(TAG, "Requesting data from " + url.toString());
        String rawJSONResponse = NetworkUtils.getResponseFromHttpUrl(url);
        Log.v(TAG, "Response from server: " + rawJSONResponse);
        return rawJSONResponse;
    }

    /**
     * Gets a response from a HTTP url
     *
     * @param url The URL to make the request with
     * @return the raw response from the server
     * @throws IOException in case of connection failure or error while reading the response
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
