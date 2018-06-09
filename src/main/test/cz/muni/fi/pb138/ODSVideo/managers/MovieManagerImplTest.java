package cz.muni.fi.pb138.ODSVideo.managers;

/**
 * @author Slavomir Katkin
 */
class MovieManagerImplTest {
    /*
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

    @Test
    void createWithNullCategory() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.createMovie(null, testMovie1Builder().build());
        });
    }

    @Test
    void createWithNullMovie() throws Exception {
        Assertions.assertThrows(IllegalArgumentException.class,()-> {
            manager.createMovie(testCategory().build(), null);
        });
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
        assertTrue(manager.findByName(category, movie.getName()).equals(movie));
    }

    @Test
    void createMovieWithIncorrectLength() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .length(-12)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createMovie(category, movie);
        });
    }

    @Test
    void createMovieWithIncorrectActors() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .actors(null)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createMovie(category, movie);
        });
    }

    @Test
    void createMovieWithIncorrectName() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .name(null)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createMovie(category, movie);
        });
    }

    @Test
    void createMovieWithIncorrectYear() throws Exception {
        Category category = testCategory().build();
        Movie movie = testMovie1Builder()
                .realeaseYear(null)
                .build();
        Assertions.assertThrows(ValidationException.class,()-> {
            manager.createMovie(category, movie);
        });
    }

    @Test
    void deleteNonexistentMovie() throws Exception{
        Movie movie1 = testMovie1Builder().build();
        Movie movie2 = testMovie2Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie1);
        assertTrue(manager.findAllMovies(category).size() == 1);
        manager.deleteMovie(category, movie2);
        assertTrue(manager.findAllMovies(category).size() == 1);
    }

    @Test
    void deleteExistentMovie() throws Exception{
        Movie movie = testMovie1Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findAllMovies(category).size() == 1);
        manager.deleteMovie(category, movie);
        assertTrue(manager.findAllMovies(category).size() == 0);
    }

    @Test
    void findMovieNonexistent() {
        Category category = testCategory().build();
        assertTrue(manager.findByName(category, "Sharks") == null);
    }

    @Test
    void findMovieExistent() throws Exception{
        Movie movie = testMovie2Builder().build();
        Category category = testCategory().build();
        manager.createMovie(category, movie);
        assertTrue(manager.findByName(category, movie.getName()).equals(movie));
    }

    @Test
    void findMovieByLength() throws Exception{
        Movie movie = testMovie2Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findByLength(category, movie.getLength()).contains(movie));
    }

    @Test
    void findMovieByActor() throws Exception{
        Movie movie = testMovie2Builder().build();
        Category category = testCategory().build();

        String actor = movie.getActors().iterator().next();
        manager.createMovie(category, movie);
        assertTrue(manager.findByActor(category, actor).contains(movie));
    }

    @Test
    void findMovieByYear() throws Exception {
        Movie movie = testMovie2Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findByYear(category, movie.getReleaseYear()).contains(movie));
    }

    @Test
    void findMovieByStatus() throws Exception{
        Movie movie = testMovie2Builder().build();
        Category category = testCategory().build();

        manager.createMovie(category, movie);
        assertTrue(manager.findByStatus(category, movie.getStatus()).contains(movie));
    }

    @Test
    void findAllMovies() throws Exception{
        Movie movie1 = testMovie1Builder().build();
        Movie movie2 = testMovie2Builder().build();
        Category category = testCategory().build();

        assertTrue(manager.findAllMovies(category).size() == 0);
        manager.createMovie(category, movie1);
        manager.createMovie(category, movie2);
        assertTrue(manager.findAllMovies(category).size() == 2);
        assertTrue(manager.findAllMovies(category).contains(movie1));
        assertTrue(manager.findAllMovies(category).contains(movie2));
    }
*/
}