package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieManagerImpl implements MovieManager {

    @Override
    public void createMovie(Category category, Movie movie) throws ValidationException {
        Objects.requireNonNull(category);
        Objects.requireNonNull(movie);

        if (!isValid(movie)) {
            throw new ValidationException("Movie is not valid");
        }

        category.getMovies().add(movie);
    }

    @Override
    public void deleteMovie(Category category, Movie movie) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(movie);

        category.getMovies().remove(movie);
    }

    @Override
    public void updateMovie(Category category, Movie movie) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(movie);

        category.getMovies().remove(movie);
        category.getMovies().add(movie);

    }

    @Override
    public Movie findByName(Category category, String name) {
        return category.getMovies()
                .stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public Set<Movie> findByLength(Category category, int length) {
        return Collections.unmodifiableSet(
                category.getMovies()
                        .stream()
                        .filter(movie -> movie.getLength() == length)
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByActor(Category category, String actor) {
        return Collections.unmodifiableSet(
                category.getMovies()
                        .stream()
                        .filter(movie -> movie.getActors().contains(actor))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByYear(Category category, Year year) {
        return Collections.unmodifiableSet(
                category.getMovies()
                        .stream()
                        .filter(movie -> movie.getReleaseYear().equals(year))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByStatus(Category category, Status status) {
        return Collections.unmodifiableSet(
                category.getMovies()
                        .stream()
                        .filter(movie -> movie.getStatus().equals(status))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findAllMovies(Category category) {
        return Collections.unmodifiableSet(category.getMovies());
    }

    private static boolean isValid(Movie movie) {
        return movie.getActors() != null && movie.getLength() >= 0 && movie.getName() != null
                && movie.getReleaseYear() != null;
    }
}
