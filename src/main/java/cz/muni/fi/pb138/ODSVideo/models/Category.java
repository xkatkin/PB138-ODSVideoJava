package cz.muni.fi.pb138.ODSVideo.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Category implements Serializable {

    private String name;

    private Set<Movie> movies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getName(), category.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", movies=" + movies +
                '}';
    }
}
