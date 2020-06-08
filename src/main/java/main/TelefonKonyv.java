package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import telefonkonyv.DB;

public class TelefonKonyv extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Nezet.fxml"));

        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("/pictures/appicon.png"));
        stage.setTitle("Telefonkönyv");
        stage.setResizable(false);
        stage.setWidth(605);
        stage.setHeight(680);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> DB.closeEmf());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
