package cz.muni.fi.pb138.ODSVideo.gui;

import cz.muni.fi.pb138.ODSVideo.managers.CategoryManager;
import cz.muni.fi.pb138.ODSVideo.managers.MovieManager;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

public class FinderController {

    @FXML
    private ToggleGroup findParameter;

    @FXML
    private TextField tfFindQuery;

    @FXML
    private TableView<Movie> tbFindResult;

    @FXML
    private TableColumn<Movie, String> tcName;

    @FXML
    private TableColumn<Movie, String> tcCategory;


    private CategoryManager categoryManager;
    private MovieManager movieManager;


    @FXML
    private void initialize() {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCategory.setCellValueFactory(param -> new ReadOnlyStringWrapper(categoryManager.findCategoryOfMovie(param.getValue()).getName()));
    }

    @FXML
    private void findMovies() {
        RadioButton selected = (RadioButton) findParameter.getSelectedToggle();

        Set<Movie> movies = categoryManager.findAllMovies();
        Set<Movie> results = new HashSet<>();
        String query = tfFindQuery.getText().trim();

        switch (selected.getText().toLowerCase()) {
            case "name":
                results = movieManager.findByNamePartial(movies, query);
                break;
            case "length":
                results = movieManager.findByLength(movies, Integer.valueOf(query));
                break;
            case "actor":
                results = movieManager.findByActor(movies, query);
                break;
            case "release":
                results = movieManager.findByYear(movies, Year.of(Integer.valueOf(query)));
                break;
            case "status":
                results = movieManager.findByStatus(movies, Status.valueOf(query.toUpperCase()));
                break;
        }

        tbFindResult.getItems().setAll(results);
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    public void setMovieManager(MovieManager movieManager) {
        this.movieManager = movieManager;
    }
}
