package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;

import java.util.Collection;


public interface CategoryManager {
    /**
     * Adds category in parameter to Map<String,Category>
     * @throws IllegalArgumentException when category is null
     * @throws IllegalEntityException when category is already in Map
     * @throws ValidationException when any of the category's attributes are null
     * @param category category to be added
     */
    void createCategory(Category category) throws ValidationException, IllegalEntityException;

    /**
     * Deletes category from map, if category doesn't exist, nothing happens
     * @throws IllegalArgumentException when name is null
     * @param name name of category
     */
    void deleteCategory(String name);

    /**
     * updates existing category
     * @throws IllegalArgumentException when category is null
     * @throws IllegalEntityException when category is not present map
     * @throws ValidationException when category has null attributes
     * @param category category to be updated
     */
    void updateCategory(Category category) throws IllegalEntityException, ValidationException;

    /**
     * tries to find category in map
     * @throws IllegalArgumentException when name is null
     * @param name name of category
     * @return Category present in map or null if operation fails
     */
    Category findCategory(String name);

    /**
     * moves movie from one category to the other
     * @throws IllegalArgumentException if any of parameters are null
     * @throws IllegalEntityException if any category is not in Map or movie is not in first category
     * @param from category from which the movie is moved
     * @param into category into which the movie is inserted
     * @param movie movie to be moved
     */
    void moveMovie(String from, String into, Movie movie) throws IllegalEntityException;

    /**
     * returns all categories in map
     * @return immutable collection of categories in map
     */
    Collection<Category> findAllCategories();

    /**
     * returns all movies in all categories
     * @return immutable collection of movies in all categories
     */
    Collection<Movie> findAllMovies();
}
