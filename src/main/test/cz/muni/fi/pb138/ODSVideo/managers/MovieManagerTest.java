package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Slavomir Katkin
 */
class MovieManagerTest {
    private MovieBuilder testMovie1Builder() {
        Set<String> actors = new HashSet<>();
        actors.add("Karel Fiala");
        actors.add("Rudolf Deyl ml.");
        actors.add("Miloš Kopecký");

        return new MovieBuilder()
                .name("Limonadovy Joe")
                .length(95)
                .actors(actors)
                .status(null)
                .realeaseYear(Year.of(1964));
    }

    private MovieBuilder testMovie2Builder() {
        Set<String> actors = new HashSet<>();
        actors.add("Frances McDormand");
        return new MovieBuilder()
                .name("Three Billboards Outside Ebbing, Missouri")
                .length(115)
                .actors(actors)
                .status(null)
                .realeaseYear(Year.of(2017));
    }
}