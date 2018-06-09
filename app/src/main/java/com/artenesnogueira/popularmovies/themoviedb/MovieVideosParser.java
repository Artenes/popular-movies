package com.artenesnogueira.popularmovies.themoviedb;

import com.artenesnogueira.popularmovies.models.YoutubeVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser that creates a list of Movie videos from a raw json response
 */
class MovieVideosParser {

    public static List<YoutubeVideo> parseList(String jsonString) throws JSONException {

        JSONObject rawResponse = new JSONObject(jsonString);
        JSONArray rawVideos = rawResponse.getJSONArray(TheMovieDBContract.RESULTS_FIELD);
        List<YoutubeVideo> videos = new ArrayList<>(0);

        for (int index = 0; index < rawVideos.length(); index++) {

            //If it is not from youtube, ignore it
            if (!TheMovieDBContract.YOUTUBE_SITE.equals(rawVideos.getJSONObject(index).getString(TheMovieDBContract.SITE_FIELD))) {
                continue;
            }

            videos.add(new YoutubeVideo(
                    rawVideos.getJSONObject(index).getString(TheMovieDBContract.KEY_FIELD),
                    rawVideos.getJSONObject(index).getString(TheMovieDBContract.TYPE_FIELD),
                    rawVideos.getJSONObject(index).getString(TheMovieDBContract.NAME_FIELD)
            ));

        }

        return videos;

    }

}
