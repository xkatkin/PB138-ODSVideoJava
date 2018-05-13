package cz.muni.fi.pb138.ODSVideo.gui;

import cz.muni.fi.pb138.ODSVideo.managers.*;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.File;
import java.io.IOException;

public class AppController {

    @FXML
    private VBox main;

    @FXML
    private ComboBox<Category> globalCategorySelector;

    @FXML
    private ComboBox<Category> localCategorySelector;

    @FXML
    private ListView<Movie> movieSelector;

    @FXML
    private TextField movieName;

    @FXML
    private TextField movieLength;

    @FXML
    private TextField movieActors;

    @FXML
    private TextField movieRelease;

    @FXML
    private TextField movieStatus;


    private SpreadsheetDocument database;
    private File databaseFile;
    private Category selectedCategory;
    private Movie selectedMovie;

    private IOUtility ioManager;
    private CategoryManager categoryManager;
    private MovieManager movieManager;


    @FXML
    private void initialize() {
        ioManager = new IOUtilityImpl();

        Callback<ListView<Category>, ListCell<Category>> categoryFactory = lv -> new ListCell<Category>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "Unnamed category" : item.getName());
            }
        };

        globalCategorySelector.setCellFactory(categoryFactory);
        globalCategorySelector.setButtonCell(categoryFactory.call(null));
        localCategorySelector.setCellFactory(categoryFactory);
        localCategorySelector.setButtonCell(categoryFactory.call(null));

        Callback<ListView<Movie>, ListCell<Movie>> movieFactory = lv -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(item.getName());
                }
            }
        };
        movieSelector.setCellFactory(movieFactory);
        movieSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectMovie(newValue);
            }
        });
    }

    @FXML
    private void selectFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a video database");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Video database", "*.ods")
        );

        databaseFile = fileChooser.showOpenDialog(main.getScene().getWindow());
        if (databaseFile != null) {
            initDatabase(databaseFile);
        }
    }

    private void initDatabase(File databaseFile) throws IOException {
        database = ioManager.readFile(databaseFile);
        categoryManager = new CategoryManagerImpl(ioManager.transformToMap(database));
        movieManager = new MovieManagerImpl();

        globalCategorySelector.setDisable(false);
        globalCategorySelector.setItems(FXCollections.observableArrayList(categoryManager.findAllCategories()));
        localCategorySelector.setItems(FXCollections.observableArrayList(categoryManager.findAllCategories()));
    }

    @FXML
    private void selectCategory() {
        selectedCategory = globalCategorySelector.getValue();
        movieSelector.setItems(FXCollections.observableArrayList(selectedCategory.getMovies()));
    }

    @FXML
    private void selectMovie(Movie movie) {
        selectedMovie = movie;
        localCategorySelector.setDisable(false);
        localCategorySelector.getSelectionModel().select(selectedCategory);

        movieName.setText(selectedMovie.getName());
        movieLength.setText(String.valueOf(selectedMovie.getLength()));
        movieActors.setText(String.join(", ", selectedMovie.getActors()));
        movieRelease.setText(String.valueOf(selectedMovie.getReleaseYear()));
        movieStatus.setText(selectedMovie.getStatus().toString());
    }
}
