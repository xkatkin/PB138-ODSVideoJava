package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Set;

public interface MovieManager {

    /**
     * adds movie into category and sets status to available if needed
     * if movie already exists it is overwritten
     * @param category category to be movie added to
     * @param movie to be added
     * @throws IllegalArgumentException if any of the arguments are null
     * @throws ValidationException if any of movie's parameters are incorrect
     *         except status (null status means newly created movie)
     */
    void createMovie(Category category, Movie movie) throws ValidationException;

    /**
     * deletes movie from category
     * if movie does not exists, nothing happens
     * @param category to be deleted from
     * @param movie to be deleted
     * @throws IllegalArgumentException if any of the arguments are null
     */
    void deleteMovie(Category category, Movie movie);

    /**
     * finds movie by name
     * @param category to look for movie in
     * @param name of the movie
     * @return movie if found, null otherwise
     * @throws IllegalArgumentException if any of the arguments are null
     */
    Movie findByName(Category category, String name);

    /**
     * finds all movies with which name matches query
     *
     * @param movies to look in
     * @param query to filter by
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByNamePartial(Set<Movie> movies, String query);

    /**
     * finds all movies with corresponding length
     * @param movies to look in
     * @param length of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByLength(Set<Movie> movies, int length);

    /**
     * finds all movies where specific actor played
     * @param movies to look in
     * @param actor name of the actor
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByActor(Set<Movie> movies, String actor);

    /**
     * finds all movies that came out in corresponding year
     * @param movies to look in
     * @param year of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByYear(Set<Movie> movies, Year year);

    /**
     * finds all movies with corresponding status
     * @param movies to look in
     * @param status of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByStatus(Set<Movie> movies, Status status);

    /**
     * finds all movies in category
     * @param category to look for movies in
     * @return collection of all movies, empty collection if no movies were found
     */
    Set<Movie> findAllMovies(Category category);
}
