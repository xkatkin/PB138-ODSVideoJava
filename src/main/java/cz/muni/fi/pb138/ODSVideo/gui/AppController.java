package cz.muni.fi.pb138.ODSVideo.gui;

import cz.muni.fi.pb138.ODSVideo.exceptions.ValidationException;
import cz.muni.fi.pb138.ODSVideo.managers.*;
import cz.muni.fi.pb138.ODSVideo.models.Category;
import cz.muni.fi.pb138.ODSVideo.models.Movie;
import cz.muni.fi.pb138.ODSVideo.models.Status;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TextField tfMovieName;

    @FXML
    private TextField tfMovieLength;

    @FXML
    private TextField tfMovieActors;

    @FXML
    private TextField tfMovieRelease;

    @FXML
    private ComboBox<Status> cbMovieStatus;

    @FXML
    private Button btNewCategory;

    @FXML
    private Button btDeleteCategory;

    @FXML
    private Button btRenameCategory;

    @FXML
    private Button btNewMovie;

    @FXML
    private Button btDeleteMovie;

    @FXML
    private Button btSaveMovie;

    @FXML
    private Button btOpenFinder;

    @FXML
    private MenuItem miSave;



    private SpreadsheetDocument database;
    private File databaseFile;
    private ObjectProperty<Category> selectedCategory = new SimpleObjectProperty<>();
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    private IOUtility ioManager;
    private CategoryManager categoryManager;
    private MovieManager movieManager;


    @FXML
    private void initialize() {
        ioManager = new IOUtilityImpl();

        cbMovieStatus.setItems(FXCollections.observableArrayList(Status.values()));

        makeTextFieldNumeric(tfMovieLength);
        makeTextFieldNumeric(tfMovieRelease);

        selectedCategory.addListener((observable, oldValue, newValue) -> toggleCategoryButtons(newValue));
        selectedMovie.addListener((observable, oldValue, newValue) -> toggleMovieButtons(newValue));

        movieSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectMovie(newValue));
        globalCategorySelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectCategory(newValue));
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

    @FXML
    private void saveFile() throws IOException {
        if (database == null) return;
        SpreadsheetDocument saved = ioManager.transformToDocument(categoryManager.getCategories());
        ioManager.writeFile(databaseFile, saved);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText(null);
        alert.setContentText("Database saved successfully to " + databaseFile.getName());

        alert.showAndWait();
    }

    @FXML
    private void quitApplication() {
        Platform.exit();
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
            globalCategorySelector.setDisable(false);
            globalCategorySelector.getSelectionModel().select(category.getName());
        });
    }

    @FXML
    private void renameCategory() {
        TextInputDialog dialog = new TextInputDialog(selectedCategory.get().getName());
        dialog.setTitle("Rename category");
        dialog.setHeaderText("Rename category");
        dialog.setContentText("Please enter new category name:");

        dialog.showAndWait().ifPresent(name -> {
            selectedCategory.get().setName(name);
            updateCategoriesList();
            globalCategorySelector.getSelectionModel().select(name);
        });
    }

    @FXML
    private void deleteCategory() {
        if (!selectedCategory.get().getMovies().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error removing category");
            alert.setContentText("Can't remove non-empty category");

            alert.showAndWait();
            return;
        }

        categoryManager.deleteCategory(selectedCategory.get());
        updateCategoriesList();
        updateMoviesList();

        if (categoryManager.getCategories().isEmpty()) {
            globalCategorySelector.setDisable(true);
            movieSelector.setItems(null);
            selectedCategory.set(null);
            selectedMovie.set(null);
        } else {
            globalCategorySelector.getSelectionModel().selectFirst();
        }

    }

    @FXML
    private void createMovie() {
        selectedMovie.set(new Movie());

        localCategorySelector.getSelectionModel().select(selectedCategory.get().getName());
        tfMovieName.setText("");
        tfMovieLength.setText("");
        tfMovieActors.setText("");
        tfMovieRelease.setText("");
        cbMovieStatus.getSelectionModel().select(Status.AVAILABLE);
    }

    @FXML
    private void saveMovie() throws ValidationException {
        movieManager.deleteMovie(selectedCategory.get(), selectedMovie.get());

        Movie movie = selectedMovie.get();

        movie.setName(tfMovieName.getText());
        movie.setLength(Integer.parseInt(tfMovieLength.getText()));
        movie.setActors(new HashSet<>(Arrays.asList(tfMovieActors.getText().split(", "))));
        movie.setReleaseYear(Year.of(Integer.parseInt(tfMovieRelease.getText())));
        movie.setStatus(cbMovieStatus.getValue());

        movieManager.createMovie(selectedCategory.get(), movie);
        Category newCategory = categoryManager.findCategory(localCategorySelector.getValue());

        if (!newCategory.equals(selectedCategory.get())) {
            categoryManager.moveMovie(selectedCategory.get(), newCategory, movie);
            globalCategorySelector.getSelectionModel().select(newCategory.getName());
        }

        updateMoviesList();
    }

    @FXML
    private void deleteMovie() {
        movieManager.deleteMovie(selectedCategory.get(), selectedMovie.get());
        updateMoviesList();
        movieSelector.getSelectionModel().selectFirst();

    }

    @FXML
    private void openFinder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("finder.fxml"));
        Parent root = loader.load();
        FinderController controller = loader.getController();
        controller.setCategoryManager(categoryManager);
        controller.setMovieManager(movieManager);

        Stage stage = new Stage();
        stage.setTitle("Find movies");
        stage.setScene(new Scene(root, 800, 450));
        stage.setResizable(false);
        stage.initOwner(main.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }



    private void initDatabase(File databaseFile) throws IOException {
        database = ioManager.readFile(databaseFile);
        categoryManager = new CategoryManagerImpl(ioManager.transformToSet(database));
        movieManager = new MovieManagerImpl();

        globalCategorySelector.setDisable(false);
        miSave.setDisable(false);
        btNewCategory.setDisable(false);

        updateCategoriesList();

        globalCategorySelector.getSelectionModel().selectFirst();
    }

    private void selectMovie(String name) {
        if (name == null) return;
        selectedMovie.set(movieManager.findByName(selectedCategory.get(), name));

        localCategorySelector.getSelectionModel().select(selectedCategory.get().getName());

        Movie movie = selectedMovie.get();

        tfMovieName.setText(movie.getName());
        tfMovieLength.setText(String.valueOf(movie.getLength()));
        tfMovieActors.setText(String.join(", ", movie.getActors()));
        tfMovieRelease.setText(String.valueOf(movie.getReleaseYear()));
        cbMovieStatus.getSelectionModel().select(movie.getStatus());

    }

    private void selectCategory(String name) {
        if (name == null) return;
        selectedCategory.set(categoryManager.findCategory(name));

        updateMoviesList();
    }

    private void updateCategoriesList() {
        ObservableList<String> categoriesNames = categoryManager.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        globalCategorySelector.setItems(categoriesNames);
        localCategorySelector.setItems(categoriesNames);
    }

    private void updateMoviesList() {
        ObservableList<String> movieNames = selectedCategory.get().getMovies().stream()
                .map(Movie::getName)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        movieSelector.setItems(movieNames);
    }

    private void toggleCategoryButtons(Category category) {
        if (category == null) {
            btDeleteCategory.setDisable(true);
            btRenameCategory.setDisable(true);
            btNewMovie.setDisable(true);
            btOpenFinder.setDisable(true);
        } else {
            btDeleteCategory.setDisable(false);
            btRenameCategory.setDisable(false);
            btNewMovie.setDisable(false);
            btOpenFinder.setDisable(false);
        }
    }

    private void toggleMovieButtons(Movie movie) {
        if (movie == null) {
            btDeleteMovie.setDisable(true);
            btSaveMovie.setDisable(true);
            localCategorySelector.setDisable(true);
            tfMovieName.setDisable(true);
            tfMovieLength.setDisable(true);
            tfMovieRelease.setDisable(true);
            tfMovieActors.setDisable(true);
            cbMovieStatus.setDisable(true);
        } else {
            btDeleteMovie.setDisable(false);
            btSaveMovie.setDisable(false);
            localCategorySelector.setDisable(false);
            tfMovieName.setDisable(false);
            tfMovieLength.setDisable(false);
            tfMovieRelease.setDisable(false);
            tfMovieActors.setDisable(false);
            cbMovieStatus.setDisable(false);
        }
    }


    private void makeTextFieldNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
