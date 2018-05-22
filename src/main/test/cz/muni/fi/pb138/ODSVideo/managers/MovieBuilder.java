package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;

import java.time.Year;
import java.util.Set;

/**
 * @author Slavomir Katkin
 */
public class MovieBuilder {
    private String name;
    private int length;
    private Set<String> actors;
    private Status status;
    private Year releaseYear;


    public MovieBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MovieBuilder length(int length) {
        this.length = length;
        return this;
    }

    public MovieBuilder actors(Set<String> actors) {
        this.actors = actors;
        return this;
    }

    public MovieBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public MovieBuilder realeaseYear(Year year) {
        this.releaseYear = year;
        return this;
    }

    public Movie build() {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setLength(length);
        movie.setActors(actors);
        movie.setStatus(status);
        movie.setReleaseYear(releaseYear);
        return movie;
    }


}
