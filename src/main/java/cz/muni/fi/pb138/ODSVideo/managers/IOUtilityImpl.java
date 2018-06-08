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
import java.util.stream.Collectors;

public class IOUtilityImpl implements IOUtility {

    @Override
    public SpreadsheetDocument readFile(String path) throws IOException {
        Objects.requireNonNull(path);

        return readFile(new File(path));
    }

    @Override
    public SpreadsheetDocument readFile(File file) throws IOException {
        Objects.requireNonNull(file);

        SpreadsheetDocument document;
        try {
            document = SpreadsheetDocument.loadDocument(file);
        } catch (Exception e) {
            throw new IOException("Failed to open document or the file is not in .ods format", e);
        }
        return document;
    }

    @Override
    public void writeFile(String path, SpreadsheetDocument document) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(document);

        writeFile(new File(path), document);
    }

    @Override
    public void writeFile(File file, SpreadsheetDocument document) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(document);

        try {
            document.save(file);
        } catch (Exception e) {
            throw new IOException("Failed to write to document", e);
        }
    }

    @Override
    public Set<Category> transformToSet(SpreadsheetDocument document) {
        Objects.requireNonNull(document);

        Set<Category> categories = new HashSet<>();
        document.getTableList()
                .forEach(table -> {
                    Category category = new Category();
                    category.setName(table.getTableName());
                    category.setMovies(table
                            .getRowList()
                            .stream()
                            .skip(1)
                            .map(IOUtilityImpl::parseRow)
                            .collect(Collectors.toSet())
                    );
                    categories.add(category);
                });

        return categories;
    }

    private static Movie parseRow(Row row) {
        String name = row.getCellByIndex(0).getDisplayText();

        int length = row.getCellByIndex(1).getDoubleValue().intValue();

        Set<String> actors = new HashSet<>(Arrays.asList(row.getCellByIndex(2).getDisplayText().split(";")));
        Status status = parseStatus(row.getCellByIndex(3).getDisplayText());
        Year releaseYear = Year.of(row.getCellByIndex(4).getDoubleValue().intValue());

        return movieConstructor(name, length, actors, status, releaseYear);
    }

    private static Status parseStatus(String input) {
        return Status.valueOf(input.trim().toUpperCase());
    }

    private static Movie movieConstructor(String name, int length, Set<String> actors, Status status, Year releaseYear) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setStatus(status);
        movie.setLength(length);
        movie.setActors(actors);
        movie.setReleaseYear(releaseYear);
        return movie;
    }

    @Override
    public SpreadsheetDocument transformToDocument(Set<Category> categoryList) {
        Objects.requireNonNull(categoryList);

        SpreadsheetDocument document = null;
        try {
            document = SpreadsheetDocument.newSpreadsheetDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }

        document.removeSheet(0);
        for (Category category : categoryList) {
            Table table = document.appendSheet(category.getName());
            table.removeRowsByIndex(0, 2);
            writeFirstRow(table.appendRow());
            category.getMovies().forEach(movie -> parseMovie(table.appendRow(), movie));
        }

        return document;
    }

    private static void writeFirstRow(Row row) {
        row.getCellByIndex(0).setDisplayText("name");
        row.getCellByIndex(1).setDisplayText("length");
        row.getCellByIndex(2).setDisplayText("actors");
        row.getCellByIndex(3).setDisplayText("status");
        row.getCellByIndex(4).setDisplayText("releaseYear");
    }

    private static void parseMovie(Row row, Movie movie) {
        row.getCellByIndex(0).setDisplayText(movie.getName());
        row.getCellByIndex(1).setFormatString("0");
        row.getCellByIndex(1).setDisplayText(Integer.toString(movie.getLength()));
        row.getCellByIndex(2).setDisplayText(String.join(";", movie.getActors()));
        row.getCellByIndex(3).setDisplayText(movie.getStatus().toString());
        row.getCellByIndex(4).setFormatString("0");
        row.getCellByIndex(4).setDisplayText(Integer.toString(movie.getReleaseYear().getValue()));

    }
}
