package cz.muni.fi.pb138.ODSVideo.models;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;
import java.util.Set;

public class Movie implements Serializable {

    enum Status {
        AVAILABLE, RENTED, LOST
    }

    private String name;

    private int length;

    private Set<String> actors;

    private Status status;

    private Year releaseYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Set<String> getActors() {
        return actors;
    }

    public void setActors(Set<String> actors) {
        this.actors = actors;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getName(), movie.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
