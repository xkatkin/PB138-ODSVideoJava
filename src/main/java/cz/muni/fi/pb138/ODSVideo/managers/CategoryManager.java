package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Set;


public interface CategoryManager {
    /**
     * Adds category in parameter to set of categories
     * @throws IllegalArgumentException when category is null or is already contained in category
     * @throws ValidationException when any of the category's attributes are null
     * @param category category to be added
     */
    void createCategory(Category category) throws ValidationException;

    /**
     * Deletes category from map, if category doesn't exist, nothing happens
     * @throws IllegalArgumentException when category is null
     */
    void deleteCategory(Category category);

    /**
     * Finds category
     * @throws IllegalArgumentException if category does not exist in database
     * */
    Category findCategory(String name);


    /**
     * moves movie from one category to the other
     * @throws IllegalArgumentException if any of parameters are null
     * @throws IllegalEntityException if any category is not in Map or movie is not in first category
     * @param from category from which the movie is moved
     * @param into category into which the movie is inserted
     * @param movie movie to be moved
     */
    void moveMovie(Category from, Category into, Movie movie);

    /**
     * getter for categories
     * @return collection of categories
     */
    Set<Category> getCategories();

    /**
     * setter for categories
     * @param categories to be set
     */
    void setCategories(Set<Category> categories);

    /**
     * returns all movies in all categories
     * @return collection of movies in all categories
     */
    Set<Movie> findAllMovies();

    /**
     * Finds category of given movie
     *
     * @return category of movie
     * @parma movie
     */
    Category findCategoryOfMovie(Movie movie);
}
