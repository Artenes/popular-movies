package com.artenesnogueira.popularmovies.utilities;

import android.net.Uri;

import com.artenesnogueira.popularmovies.models.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory to create Image objects from relative paths from TheMovieDB API
 */
class TheMovieDBImageFactory {

    //the base path used to fetch the images
    private static final Uri BASE_IMAGE_PATH = Uri.parse("http://image.tmdb.org/t/p");

    //a map that holds the width and height of each kind of image size available in TheMovieDB API
    //since their response does not give the size of the image, let`s make this static to make it
    //easier if we need this information
    private static final Map<String, Image> sizeMap = new HashMap<>();

    /**
     * Make a new Image object from a relative URL path.
     *
     * @param relativeURL the relative url in the format /imagename.jpg
     * @param width       the kind of the width for the image. This is not a number, but a enumeration defined by the API
     * @return the new Image instance
     * @throws IllegalArgumentException in case the provided width is invalid
     */
    public static Image make(String relativeURL, String width) throws IllegalArgumentException {

        Image image = sizeMap.get(width);

        //if the provided size is not in the map, it does not exist
        //doing this check here instead of waiting until the actual HTTP request
        //is made to fetch the image make the error more obvious since we will
        //be failing earlier if we pass a wrong value to this method
        if (image == null) {
            throw new IllegalArgumentException("Invalid width provided: " + width);
        }

        //this removes the first slash that appears in the relative path
        //if we left this here we will not be able to properly build the
        //uri to the image, the uri class will encode the slash in the path
        String relativeURLWithoutSlashInTheBeginning = relativeURL.replaceFirst("/", "");

        String imagePath = BASE_IMAGE_PATH.buildUpon()
                .appendPath(image.getPath())
                .appendPath(relativeURLWithoutSlashInTheBeginning)
                .build().toString();

        return new Image(imagePath, image.getWidth(), image.getHeight());

    }

    static {
        //we are not actually storing "Images", but what matter here are
        //the default sizes for each kind of width available in the API
        //this date is not actually useful now, but we might need this
        //to properly resize or change the display of this image in the UI
        sizeMap.put("w92", new Image("w92", 92, 138));
        sizeMap.put("w154", new Image("w154", 154, 231));
        sizeMap.put("w185", new Image("w185", 185, 278));
        sizeMap.put("w342", new Image("w342", 342, 513));
        sizeMap.put("w500", new Image("w500", 500, 750));
        sizeMap.put("w780", new Image("w780", 780, 1170));
        sizeMap.put("original", new Image("original", 2000, 3000));
    }

}
