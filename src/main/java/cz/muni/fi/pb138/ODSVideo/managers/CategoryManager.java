package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Collection;


public interface CategoryManager {
    void createCategory(Category category);
    void deleteCategory(String name);
    void updateCategory(Category category);
    Category findCategory(String name);
    void moveMovie(Category from, Category into, Movie movie);
    Collection<Category> findAllCategories();
    Collection<Movie> findAllMovies();
}
