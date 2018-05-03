package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Collection;

public class MovieManagerImpl implements MovieManager{
    @Override
    public void createMovie(Category category, Movie movie) {

    }

    @Override
    public void deleteMovie(Category category, String name) {

    }

    @Override
    public void updateMovie(Category category, Movie movie) {

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
}
