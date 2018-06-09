package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryManagerImpl implements CategoryManager {

    private Set<Category> categories;

    public CategoryManagerImpl(Set<Category> categories) {
        if (categories == null) {
            throw new IllegalArgumentException("Categories cannot be null");
        }

        this.categories = categories;
    }

    @Override
    public void createCategory(Category category) throws ValidationException {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (!isValid(category)) {
            throw new ValidationException("Category is invalid");
        }

        if (categories.contains(category)) {
            throw new IllegalArgumentException("Category already exists in map");
        }

        category.setMovies(new HashSet<>());
        categories.add(category);
    }

    @Override
    public void deleteCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        categories.remove(category);
    }

    @Override
    public Category findCategory(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return categories.stream()
                .filter(c -> c.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Category does not exists."));
    }

    @Override
    public void moveMovie(Category from, Category into, Movie movie) {
        if (from == null || into == null || movie == null) {
            throw new IllegalArgumentException("parameters cannot be null");
        }
        from.getMovies().remove(movie);
        into.getMovies().add(movie);

    }

    @Override
    public Set<Category> getCategories() {
        return categories;
    }

    @Override
    public Set<Movie> findAllMovies() {
        return categories
                .stream()
                .map(Category::getMovies)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

    }

    @Override
    public Category findCategoryOfMovie(Movie movie) {
        return categories
                .stream()
                .filter(category -> category.getMovies().contains(movie))
                .findAny()
                .orElse(null);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    private static boolean isValid(Category category) {
        return category.getName() != null && category.getMovies() != null;
    }
}
