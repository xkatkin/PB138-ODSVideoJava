package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
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
     * updates movie in category
     * @param category to be updated in
     * @param movie to be updated
     * @throws IllegalArgumentException if any of the arguments are null
     * @throws ValidationException if any of movie's parameters are null or incorrect
     * @throws IllegalEntityException if movie is not present in category
     */
    void updateMovie(Category category, Movie movie) throws ValidationException, IllegalEntityException;

    /**
     * finds movie by name
     * @param category to look for movie in
     * @param name of the movie
     * @return movie if found, null otherwise
     * @throws IllegalArgumentException if any of the arguments are null
     */
    Movie findByName(Category category, String name);

    /**
     * finds all movies with corresponding length
     * @param category to look for movies in
     * @param length of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByLength(Category category, int length);

    /**
     * finds all movies where specific actor played
     * @param category to look for movies in
     * @param actor name of the actor
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByActor(Category category, String actor);

    /**
     * finds all movies that came out in corresponding year
     * @param category to look for movies in
     * @param year of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByYear(Category category, Year year);

    /**
     * finds all movies with corresponding status
     * @param category to look for movies in
     * @param status of the movies
     * @return collection of movies, empty collection if no corresponding movies were found
     */
    Set<Movie> findByStatus(Category category, Status status);

    /**
     * finds all movies in category
     * @param category to look for movies in
     * @return collection of all movies, empty collection if no movies were found
     */
    Set<Movie> findAllMovies(Category category);
}
