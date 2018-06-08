package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryManagerImpl implements CategoryManager {

    private Set<Category> categories;

    public CategoryManagerImpl(Set<Category> categories) {
        Objects.requireNonNull(categories, "Categories cannot be null");

        this.categories = categories;
    }

    @Override
    public void createCategory(Category category) throws ValidationException {
        Objects.requireNonNull(category, "Category cannot be null");

        if (!isValid(category)) {
            throw new ValidationException("Category is invalid");
        }

        if (categories.contains(category)) {
            throw new IllegalArgumentException("Category already exists in map");
        }

        categories.add(category);
    }

    @Override
    public void deleteCategory(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");

        categories.remove(category);
    }

    @Override
    public Category findCategory(Category category) {
        Objects.requireNonNull(category, "Category cannot be null");

        return categories.stream()
                .filter(c -> c.equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Category does not exists."));
    }

    @Override
    public void moveMovie(Category from, Category into, Movie movie) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(into);
        Objects.requireNonNull(movie);

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

    private static boolean isValid(Category category) {
        return category.getName() != null && category.getMovies() != null;
    }
}
