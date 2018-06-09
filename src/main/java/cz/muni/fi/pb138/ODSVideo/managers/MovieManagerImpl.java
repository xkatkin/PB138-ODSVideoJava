package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieManagerImpl implements MovieManager {

    @Override
    public void createMovie(Category category, Movie movie) throws ValidationException {
        if (category == null || movie == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        if (!isValid(movie)) {
            throw new ValidationException("Movie is not valid");
        }

        if (movie.getStatus() == null) {
            movie.setStatus(Status.AVAILABLE);
        }

        category.getMovies().add(movie);
    }

    @Override
    public void deleteMovie(Category category, Movie movie) {
        if (category == null || movie == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        category.getMovies().remove(movie);
    }

    @Override
    public Movie findByName(Category category, String name) {
        if (category == null || name == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }
        return category.getMovies()
                .stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    @Override
    public Set<Movie> findByNamePartial(Set<Movie> movies, String query) {
        if (movies == null || query == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        return Collections.unmodifiableSet(
                movies
                        .stream()
                        .filter(movie -> movie.getName().toLowerCase().contains(query.trim().toLowerCase()))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByLength(Set<Movie> movies, int length) {
        if (movies == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        return Collections.unmodifiableSet(
                movies
                        .stream()
                        .filter(movie -> movie.getLength() == length)
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByActor(Set<Movie> movies, String actor) {
        if (movies == null || actor == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        return Collections.unmodifiableSet(
                movies
                        .stream()
                        .filter(movie -> movie.getActors().contains(actor))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByYear(Set<Movie> movies, Year year) {
        if (movies == null || year == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }

        return Collections.unmodifiableSet(
                movies
                        .stream()
                        .filter(movie -> movie.getReleaseYear().equals(year))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findByStatus(Set<Movie> movies, Status status) {
        if (movies == null || status == null) {
            throw new IllegalArgumentException("parameter cannot be null");
        }
        return Collections.unmodifiableSet(
                movies
                        .stream()
                        .filter(movie -> movie.getStatus().equals(status))
                        .collect(Collectors.toSet()));
    }

    @Override
    public Set<Movie> findAllMovies(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        return Collections.unmodifiableSet(category.getMovies());
    }

    private static boolean isValid(Movie movie) {
        return movie.getActors() != null && movie.getLength() >= 0 && movie.getName() != null
                && movie.getReleaseYear() != null;
    }
}
