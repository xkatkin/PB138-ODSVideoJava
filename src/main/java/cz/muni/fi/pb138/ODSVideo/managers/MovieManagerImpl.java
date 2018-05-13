package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.*;

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
        if (category == null || movie == null) {
            throw new IllegalArgumentException("input arguments cannot be null");
        }
        if (!isValid(movie) || movie.getStatus() == null) {
            throw new ValidationException("movie not valid");
        }
        if(!category.getMovies().contains(movie)) {
            throw new IllegalEntityException("movie not in category");
        }
        Set<Movie> movieSet = category.getMovies();
        movieSet.remove(movie);
        movieSet.add(movie);

    }

    @Override
    public Movie findMovie(Category category, String name) {
        for(Movie movie : category.getMovies()) {
            if(movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public Collection<Movie> findByLength(Category category, int length) {
        List<Movie> byLength = new ArrayList<>();
        for(Movie movie : category.getMovies()) {
            if(movie.getLength() == length) {
                byLength.add(movie);
            }
        }
        return Collections.unmodifiableCollection(byLength);
    }

    @Override
    public Collection<Movie> findByActor(Category category, String actor) {
        List<Movie> byActor = new ArrayList<>();
        for(Movie movie : category.getMovies()) {
            if(movie.getActors().contains(actor)) {
                byActor.add(movie);
            }
        }
        return Collections.unmodifiableCollection(byActor);
    }

    @Override
    public Collection<Movie> findByYear(Category category, Year year) {
        List<Movie> byYear = new ArrayList<>();
        for(Movie movie : category.getMovies()) {
            if(movie.getReleaseYear() == year) {
                byYear.add(movie);
            }
        }
        return Collections.unmodifiableCollection(byYear);
    }

    @Override
    public Collection<Movie> findByStatus(Category category, Status status) {
        List<Movie> byStatus = new ArrayList<>();
        for(Movie movie : category.getMovies()) {
            if(movie.getStatus().equals(status)) {
                byStatus.add(movie);
            }
        }
        return Collections.unmodifiableCollection(byStatus);
    }

    @Override
    public Collection<Movie> findAllMovies(Category category) {
        return Collections.unmodifiableCollection(category.getMovies());
    }

    private boolean isValid(Movie movie) {
        return movie.getActors() != null && movie.getLength() >= 0 && movie.getName() != null
                && movie.getReleaseYear() != null;
    }
}
