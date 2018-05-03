package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Collection;

public interface MovieManager {

    void createMovie(Category category, Movie movie);
    void deleteMovie(Category category, String name);
    void updateMovie(Category category, Movie movie);
    Movie findMovie(Category category, String name);
    Collection<Movie> findByLength(Category category, int length);
    Collection<Movie> findByActor(Category category, String actor);
    Collection<Movie> findByYear(Category category, Year year);
    Collection<Movie> findByStatus(Category category, Status status);
    Collection<Movie> findAllMovies(Category category);
}
