package com.artenesnogueira.popularmovies.models;

/**
 * A view that can be rendered base on a state
 */
public interface PostersView {

    /**
     * Render the current view base on the given state
     * @param state the state to render
     */
    void render(PosterViewState state);

}
