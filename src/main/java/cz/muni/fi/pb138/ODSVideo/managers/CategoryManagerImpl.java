package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryManagerImpl implements CategoryManager {

    private Map<String,Category> categories;

    public CategoryManagerImpl(Map<String, Category> categories) {
        if (categories == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }
        this.categories = categories;
    }

    @Override
    public void createCategory(Category category) throws ValidationException, IllegalEntityException {
        if (category == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }
        if (!isValid(category)) {
            throw new ValidationException("category is invalid");
        }
        if (categories.containsKey(category.getName())) {
            throw new IllegalEntityException("category is already exists in map");
        }

        categories.put(category.getName(),category);
    }

    @Override
    public void deleteCategory(String name) throws IllegalArgumentException{
        if (name == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }

        categories.remove(name);
    }

    @Override
    public void updateCategory(Category category) throws IllegalEntityException, ValidationException {
        if (category == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }
        if (!isValid(category)) {
            throw new ValidationException("category is invalid");
        }
        if (!categories.containsKey(category.getName())) {
            throw new IllegalEntityException("category doesn't exist in map");
        }

        categories.replace(category.getName(),category);
    }

    @Override
    public Category findCategory(String name) {
        if (name == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }

        return categories.get(name);
    }

    @Override
    public void moveMovie(String from, String into, Movie movie) throws IllegalEntityException {
        if (from == null || into == null || movie == null) {
            throw new IllegalArgumentException("null input argument is not allowed");
        }
        Category fromC = findCategory(from);
        Category intoC = findCategory(into);
        if (fromC == null || intoC == null || !(fromC.getMovies().contains(movie))) {
            //TODO write message
            throw new IllegalEntityException("idk");
        }

        try {
            MovieManager movieManager = new MovieManagerImpl();
            movieManager.deleteMovie(findCategory(from),movie.getName());
            movieManager.createMovie(findCategory(into),movie);
        } catch (ValidationException e) {
            //never happens tho
            e.printStackTrace();
        }


    }

    @Override
    public Collection<Category> findAllCategories() {
        return Collections.unmodifiableCollection(categories.values());
    }

    @Override
    public Collection<Movie> findAllMovies() {
        return Collections.unmodifiableCollection(
                categories
                .values()
                .stream()
                .map(category -> new MovieManagerImpl().findAllMovies(category))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                );

    }

    private boolean isValid(Category category) {
        return category.getName() != null && category.getMovies() != null;
    }
}
