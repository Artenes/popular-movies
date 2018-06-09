package com.artenesnogueira.popularmovies.models;

/**
 * A trailer for a movie that comes from youtube
 */
@SuppressWarnings("unused")
public class YoutubeVideo {

    private final String id;
    private final String type;
    private final String name;

    public YoutubeVideo(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + getId();
    }

}
