package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Set;

/**
 * @author Slavomir Katkin
 */
public class CategoryBuilder {
    private String name;
    private Set<Movie> movies;

    public CategoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public Category build() {
        Category category = new Category();
        category.setName(name);
        category.setMovies(movies);
        return category;
    }
}
