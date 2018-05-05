package com.artenesnogueira.popularmovies.models;

import java.io.IOException;

/**
 * A client to make http requests
 */
public interface HTTPClient {

    /**
     * Gets a response from a HTTP url
     *
     * @param url The URL to make the request with
     * @return the raw response from the server
     * @throws IOException in case of connection failure or error while reading the response
     */
    String get(String url) throws IOException;

}
