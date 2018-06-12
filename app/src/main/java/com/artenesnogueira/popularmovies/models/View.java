package com.artenesnogueira.popularmovies.models;

/**
 * A view that can be rendered base on a state
 */
@SuppressWarnings("unused")
public interface View {

    /**
     * Render the current view base on the given state
     * @param state the state to render
     */
    void render(State state);

}
