package cz.muni.fi.pb138.ODSVideo.managers;

import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;
import cz.muni.fi.pb138.ODSVideo.exceptions.IllegalEntityException;
import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Slavomir Katkin
 */
class CategoryManagerImplTest {
    Set<Category> categories = new HashSet<>();
    private CategoryManagerImpl manager = new CategoryManagerImpl(categories);

    private CategoryBuilder testCategory1Builder() {
        return new CategoryBuilder()
                .name("Favourites")
                .movies(new HashSet<>());
    }

    private CategoryBuilder testCategory2Builder() {
        return new CategoryBuilder()
                .name("Thriller")
                .movies(new HashSet<>());
    }

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

    @Test
    void createCategoryWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
           manager.createCategory(null);
        });
    }

    @Test
    void createDuplicitCategory() throws Exception{
        Category category = testCategory1Builder().build();
        manager.createCategory(category);
        Assertions.assertThrows(IllegalEntityException.class,()-> {
            manager.createCategory(category);
        });
    }

    @Test
    void createCategoryWithNullMovies() throws Exception{
        Category category = testCategory1Builder()
                .movies(null)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createCategory(category);
        });
    }

    @Test
    void createCategoryWithNullName() throws Exception{
        Category category = testCategory1Builder()
                .name(null)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createCategory(category);
        });
    }

    @Test
    void deleteCategoryWithNullName() throws Exception{
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.deleteCategory(null);
        });
    }

    @Test
    void deleteCategory() throws Exception {
        Category category1 = testCategory1Builder().build();
        Category category2 = testCategory2Builder().build();
        manager.createCategory(category1);
        manager.createCategory(category2);
        manager.deleteCategory(category1);
        Assertions.assertTrue(manager.getCategories().size() == 1);
        Assertions.assertFalse(manager.getCategories().contains(category1));
    }

   /* @Test
    void updateCategory() throws Exception{
        Category category = testCategory1Builder().build();
        Movie movie = testMovie1Builder().build();
        Set<Movie> movies = new HashSet<>();
        movies.add(movie);
        manager.createCategory(category);
        category.setMovies(movies);
        manager.updateCategory(category);
        Assertions.assertTrue(manager.findCategory(category).equals(category));
    }

    @Test
    void updateNullCategory() {
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.updateCategory(null);
        });
    }

    @Test
    void updateInvalidCategory() throws Exception {
        Category category = testCategory1Builder().build();
        manager.createCategory(category);
        category.setName(null);
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.updateCategory(category);
        });
    }

    @Test
    void updateNonexistentCategory() {
        Category category = testCategory2Builder().build();
        Assertions.assertThrows(IllegalEntityException.class,()-> {
            manager.updateCategory(category);
        });
    }
*/
    @Test
    void findCategoryWithNullName() {
        Category category = testCategory1Builder()
                .name(null)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.findCategory(category);
        });
    }

    @Test
    void findCategory() throws Exception{
        Category category = testCategory1Builder().build();
        manager.createCategory(category);
        Assertions.assertTrue(manager.findCategory(category).equals(category));
    }

    @Test
    void moveMovie() throws Exception{
        Category category1 = testCategory1Builder().build();
        Category category2 = testCategory2Builder().build();
        Movie movie = testMovie1Builder().build();
        manager.createCategory(category1);
        manager.createCategory(category2);
        new MovieManagerImpl().createMovie(category1, movie);
        manager.moveMovie(category1, category2, movie);
        Assertions.assertTrue(category1.getMovies().size() == 0);
        Assertions.assertTrue(category2.getMovies().contains(movie));
    }

    @Test
    void moveMovieWithNull() throws Exception{
        Category category1 = testCategory1Builder().build();
        manager.createCategory(category1);
        Movie movie = testMovie1Builder().build();
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.moveMovie(category1, null, movie);
        });
    }

    @Test
    void moveMovieWithNonexistent() throws Exception {
        Category category1 = testCategory1Builder().build();

        Category category2 = testCategory2Builder().build();
        manager.createCategory(category1);
        Movie movie = testMovie1Builder().build();
        Assertions.assertThrows(IllegalEntityException.class, () -> {
            manager.moveMovie(category1, category2, movie);

        });
    }


    @Test
    void getCategories() throws Exception{
        Category category1 = testCategory1Builder().build();
        Category category2 = testCategory2Builder().build();
        manager.createCategory(category1);
        manager.createCategory(category2);
        Assertions.assertTrue(manager.getCategories().size() == 2);
        Assertions.assertTrue(manager.getCategories().contains(category1));
        Assertions.assertTrue(manager.getCategories().contains(category2));
    }

    @Test
    void findAllMovies() throws Exception {
        Category category1 = testCategory1Builder().build();
        Category category2 = testCategory2Builder().build();
        Movie movie = testMovie1Builder().build();
        manager.createCategory(category1);
        manager.createCategory(category2);
        new MovieManagerImpl().createMovie(category1, movie);
        new MovieManagerImpl().createMovie(category2, movie);
        Assertions.assertTrue(manager.findAllMovies().size() == 1);
    }

}