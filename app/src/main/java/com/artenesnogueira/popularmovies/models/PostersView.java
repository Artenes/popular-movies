package com.artenesnogueira.popularmovies.models;

import java.util.List;

public interface PostersView {

    String KEY_MOVIES_LIST = "movies_list";
    String KEY_CURRENT_LIST_POSITION = "current_list_position";
    String KEY_CURRENT_FILTER = "current_filter";

    void showLoading();

    void stopLoading();

    void showMovies(List<Movie> movies);

    void showError();

}
