package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Slavomir Katkin
 */
class MovieManagerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private MovieManagerImpl manager = new MovieManagerImpl();

    private MovieBuilder testMovie1Builder() {
        Set<String> actors = new HashSet<>();
        actors.add("Karel Fiala");
        actors.add("Rudolf Deyl ml.");
        actors.add("Miloš Kopecký");

        return new MovieBuilder()
                .name("Limonadovy Joe")
                .length(95)
                .actors(actors)
                .status(Status.AVAILABLE)
                .realeaseYear(Year.of(1964));
    }

    private MovieBuilder testMovie2Builder() {
        Set<String> actors = new HashSet<>();
        actors.add("Frances McDormand");
        return new MovieBuilder()
                .name("Three Billboards Outside Ebbing, Missouri")
                .length(115)
                .actors(actors)
                .status(Status.LOST)
                .realeaseYear(Year.of(2017));
    }

    private CategoryBuilder testCategory() {
        return new CategoryBuilder()
                .name("Favourite")
                .movies(new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    void createWithNullCategory() throws Exception {
        manager.createMovie(null, testMovie1Builder().build());
    }

    @Test(expected = IllegalArgumentException.class)
    void createWithNullMovie() throws Exception {
        manager.createMovie(testCategory().build(), null);
    }

    @Test
    void createMovieWithNotNullStatus() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findAllMovies(category).size() == 1);
    }

    @Test
    void createMovieWithNullStatus() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .status(null)
                .build();

        manager.createMovie(category, movie);
        assertTrue(movie.getStatus().equals(Status.AVAILABLE));
        assertTrue(manager.findMovie(category, movie.getName()).equals(movie));
    }

    @Test(expected = ValidationException.class)
    void createMovieWithIncorrectLength() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .length(-12)
                .build();
        manager.createMovie(category, movie);
    }

    @Test(expected = ValidationException.class)
    void createMovieWithIncorrectActors() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .actors(null)
                .build();
        manager.createMovie(category, movie);
    }

    @Test(expected = ValidationException.class)
    void createMovieWithIncorrectName() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .name(null)
                .build();
        manager.createMovie(category, movie);
    }

    @Test(expected = ValidationException.class)
    void createMovieWithIncorrectYear() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .realeaseYear(null)
                .build();
        manager.createMovie(category, movie);
    }

    @Test
    void deleteNonexistentMovie() throws Exception{
        Movie movie1 = testMovie1Builder().build();
        Movie movie2 = testMovie2Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie1);
        assertTrue(manager.findAllMovies(category).size() == 1);
        manager.deleteMovie(category, movie2.getName());
        assertTrue(manager.findAllMovies(category).size() == 1);
    }

    @Test
    void deleteExistentMovie() throws Exception{
        Movie movie = testMovie1Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findAllMovies(category).size() == 1);
        manager.deleteMovie(category, movie.getName());
        assertTrue(manager.findAllMovies(category).size() == 0);
    }
}