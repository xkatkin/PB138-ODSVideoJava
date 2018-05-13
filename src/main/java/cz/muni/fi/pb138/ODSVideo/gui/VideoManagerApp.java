package cz.muni.fi.pb138.ODSVideo.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VideoManagerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        primaryStage.setTitle("Video Manager");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1288, 926));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
