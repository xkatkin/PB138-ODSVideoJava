package cz.muni.fi.pb138.ODSVideo.gui;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.managers.*;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class AppController {

    @FXML
    private VBox main;

    @FXML
    private ComboBox<String> globalCategorySelector;

    @FXML
    private ComboBox<String> localCategorySelector;

    @FXML
    private ListView<String> movieSelector;

    @FXML
    private TextField movieName;

    @FXML
    private TextField movieLength;

    @FXML
    private TextField movieActors;

    @FXML
    private TextField movieRelease;

    @FXML
    private ComboBox<Status> movieStatus;


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


        movieStatus.setItems(FXCollections.observableArrayList(Status.values()));

        makeTextFieldNumeric(movieLength);
        makeTextFieldNumeric(movieRelease);

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
        categoryManager = new CategoryManagerImpl(ioManager.transformToSet(database));
        movieManager = new MovieManagerImpl();

        globalCategorySelector.setDisable(false);

        updateCategoriesList();

        movieSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectMovie(newValue));

        globalCategorySelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectCategory(newValue));
    }

    private void selectCategory(String name) {
        if (name == null) return;
        selectedCategory = categoryManager.findCategory(name);

        updateMoviesList();
    }

    private void selectMovie(String name) {
        if (name == null) return;
        selectedMovie = movieManager.findByName(selectedCategory, name);

        if (selectedMovie != null) {
            localCategorySelector.setDisable(false);
            movieStatus.setDisable(false);
            localCategorySelector.getSelectionModel().select(selectedCategory.getName());

            movieName.setText(selectedMovie.getName());
            movieLength.setText(String.valueOf(selectedMovie.getLength()));
            movieActors.setText(String.join(", ", selectedMovie.getActors()));
            movieRelease.setText(String.valueOf(selectedMovie.getReleaseYear()));
            movieStatus.getSelectionModel().select(selectedMovie.getStatus());
        }
    }

    @FXML
    private void saveMovie() throws ValidationException {
        movieManager.deleteMovie(selectedCategory, selectedMovie);

        selectedMovie.setName(movieName.getText());
        selectedMovie.setLength(Integer.parseInt(movieLength.getText())); // TODO validate
        selectedMovie.setActors(new HashSet<>(Arrays.asList(movieActors.getText().split(", "))));
        selectedMovie.setReleaseYear(Year.parse(movieRelease.getText()));
        selectedMovie.setStatus(movieStatus.getValue());

        movieManager.createMovie(selectedCategory, selectedMovie);
        Category newCategory = categoryManager.findCategory(localCategorySelector.getValue());
        if (!newCategory.equals(selectedCategory)) {
            categoryManager.moveMovie(selectedCategory, newCategory, selectedMovie);
            globalCategorySelector.getSelectionModel().select(newCategory.getName());
        }

        updateMoviesList();
    }

    private void makeTextFieldNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void updateCategoriesList() {
        ObservableList<String> categoriesNames = categoryManager.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        globalCategorySelector.setItems(categoriesNames);
        localCategorySelector.setItems(categoriesNames);
    }

    private void updateMoviesList() {
        ObservableList<String> movieNames = selectedCategory.getMovies().stream()
                .map(Movie::getName)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        movieSelector.setItems(movieNames);
    }

    @FXML
    private void saveFile() throws IOException {
        SpreadsheetDocument saved = ioManager.transformToDocument(categoryManager.getCategories());
        ioManager.writeFile(databaseFile, saved);
        System.out.println("Saved!");
    }

    @FXML
    private void deleteMovie() {
        if (selectedMovie != null) {
            movieManager.deleteMovie(selectedCategory, selectedMovie);
            updateMoviesList();
            movieSelector.getSelectionModel().selectFirst();

        }
    }


    @FXML
    private void newMovie() {
        selectedMovie = new Movie();

        localCategorySelector.getSelectionModel().select(selectedCategory.getName());
        movieName.setText("");
        movieLength.setText("");
        movieActors.setText("");
        movieRelease.setText("");
        movieStatus.getSelectionModel().select(Status.AVAILABLE);
    }

    @FXML
    private void createCategory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create new category");
        dialog.setHeaderText("Create new category");
        dialog.setContentText("Please enter category name:");

        dialog.showAndWait().ifPresent(name -> {
            Category category = new Category();
            category.setMovies(new HashSet<>());
            category.setName(name);
            try {
                categoryManager.createCategory(category);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            updateCategoriesList();
            globalCategorySelector.getSelectionModel().select(category.getName());
        });
    }

    @FXML
    private void renameCategory() {
        TextInputDialog dialog = new TextInputDialog(selectedCategory.getName());
        dialog.setTitle("Rename category");
        dialog.setHeaderText("Rename category");
        dialog.setContentText("Please enter new category name:");

        dialog.showAndWait().ifPresent(name -> {
            selectedCategory.setName(name);
            updateCategoriesList();
            globalCategorySelector.getSelectionModel().select(name);
        });
    }

    @FXML
    private void deleteCategory() {
        if (selectedCategory != null) {
            categoryManager.deleteCategory(selectedCategory);
            updateCategoriesList();
            globalCategorySelector.getSelectionModel().selectFirst();
        }

        // TODO fix exception
    }

    @FXML
    private void quitApplication() {
        Platform.exit();
    }
}
