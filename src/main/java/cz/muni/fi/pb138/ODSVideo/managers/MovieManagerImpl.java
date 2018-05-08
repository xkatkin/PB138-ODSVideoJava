package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Collection;
import java.util.Set;

public class MovieManagerImpl implements MovieManager{
    @Override
    public void createMovie(Category category, Movie movie) throws ValidationException {
        if (category == null || movie == null) {
            throw new IllegalArgumentException("input arguments cannot be null");
        }
        if (!isValid(movie)) {
            throw new ValidationException("movie not valid");
        }
        if (movie.getStatus() == null) {
            movie.setStatus(Status.AVAILABLE);
        }
        Set<Movie> movieSet = category.getMovies();
        if(movieSet.contains(movie)) {
            movieSet.remove(movie);
        }
        movieSet.add(movie);
    }

    @Override
    public void deleteMovie(Category category, String name) {
        if (category == null || name == null) {
            throw new IllegalArgumentException("input arguments cannot be null");
        }

        category.getMovies().remove(findMovie(category,name));
    }

    @Override
    public void updateMovie(Category category, Movie movie) throws ValidationException, IllegalEntityException {

    }

    @Override
    public Movie findMovie(Category category, String name) {
        return null;
    }

    @Override
    public Collection<Movie> findByLength(Category category, int length) {
        return null;
    }

    @Override
    public Collection<Movie> findByActor(Category category, String actor) {
        return null;
    }

    @Override
    public Collection<Movie> findByYear(Category category, Year year) {
        return null;
    }

    @Override
    public Collection<Movie> findByStatus(Category category, Status status) {
        return null;
    }

    @Override
    public Collection<Movie> findAllMovies(Category category) {
        return null;
    }

    private boolean isValid(Movie movie) {
        return movie.getActors() != null && movie.getLength() >= 0 && movie.getName() != null
                && movie.getReleaseYear() != null;
    }
}
