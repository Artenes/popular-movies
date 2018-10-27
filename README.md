# Popular Movies app

![Cover image](images/cover.jpg)

<a href='https://play.google.com/store/apps/details?id=com.artenesnogueira.popularmovies&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="200px" /></a>

Android application to browse popular and top rated movie from [The Movie Database](https://www.themoviedb.org/).

# Features

- Browse popular movies
- Browse top rated movies
- See movie details (synopsis, trailers, reviews)
- Favorite movies

# Screenshots

<p float="left" align="center">
  <img src="images/Screenshot_1540598636.png" width="300px" />
  <img src="images/Screenshot_1540598685.png" width="300px" /> 
</p>

# Setup

Clone the repository

````
git clone git@github.com:Artenes/popular-movies.git
````

Add the following field to your global gradle.properties file (located at ~/.gradle), note the use of double quotes:

````
PopularMovies_TheMovieDbAPIKey="your api key"
````

Import the project in Android Studio and run the application