package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;

public class IOUtilityImpl implements IOUtility{
    @Override
    public SpreadsheetDocument readFile(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        return readFile(new File(path));
    }

    @Override
    public SpreadsheetDocument readFile(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        SpreadsheetDocument document;
        try {
            document = SpreadsheetDocument.loadDocument(file);
        } catch (Exception e) {
            throw new IOException("failed to open document",e);
        }
        return document;
    }

    @Override
    public void writeFile(String path, SpreadsheetDocument document) throws IOException{
        if (path == null || document == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        writeFile(new File(path), document);
    }

    @Override
    public void writeFile(File file, SpreadsheetDocument document) throws IOException{
        if (file == null || document == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        try {
            document.save(file);
        } catch (Exception e) {
            throw new IOException("failed to write", e);
        }
    }

    @Override
    public Map<String, Category> transformToMap(SpreadsheetDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        Map<String, Category> map = new HashMap<>();
        for (Table table : document.getTableList()) {
            Category category = new Category();
            category.setName(table.getTableName());
            Set<Movie> movies = new HashSet<>();
            List<Row> rowList = ignoreFirstRow(table.getRowList());
            for (Row row : rowList) {
                movies.add(parseRow(row));
            }
            category.setMovies(movies);
            map.put(category.getName(),category);
        }
        return map;
    }

    @Override
    public SpreadsheetDocument transformToDocument(Map<String, Category> categoryMap) {
        return null;
    }

    private List<Row> ignoreFirstRow(List<Row> list) {
        if (list == null || list.size() == 0) {
            return list;
        }
        list.remove(0);
        return list;
    }

    private Status parseStatus(String input) {
        input = input.toUpperCase();
        switch (input) {
            case "AVAILABLE":
                return Status.AVAILABLE;
            case "RENTED":
                return Status.RENTED;
            case "LOST":
                return Status.LOST;
            default:
                throw new IllegalArgumentException("status cannot be parsed");
        }
    }

    private Movie parseRow(Row row) {
        String name = row.getCellByIndex(0).getDisplayText();
        int length;
        try {
            length = row.getCellByIndex(1).getDoubleValue().intValue();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("invalid length");
        }
        Set<String> actors= new HashSet<>(new ArrayList<>(
                Arrays.asList(
                        row.getCellByIndex(2).getDisplayText().split(";")
                )));
        Status status = parseStatus(row.getCellByIndex(3).getDisplayText());
        Year releaseYear;
        try {
            releaseYear = Year.of(row.getCellByIndex(4).getDoubleValue().intValue());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid year");
        }
        return movieQOLConstructor(name,length,actors,status,releaseYear);
    }

    private Movie movieQOLConstructor(String name, int length, Set<String> actors, Status status, Year releaseYear) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setStatus(status);
        movie.setLength(length);
        movie.setActors(actors);
        movie.setReleaseYear(releaseYear);
        return movie;
    }
}
