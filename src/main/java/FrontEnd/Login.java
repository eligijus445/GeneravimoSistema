package FrontEnd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Login extends Application {
    private static Stage stg;
    @Override
    public void start(Stage primaryStage) throws IOException {

        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("SF Generavimo sistema");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //changeLoginsceneToMain("main.fxml");
    }
    public  void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public void changeLoginsceneToMain(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));



    }
    public void changeToMain(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane, 493,280));
    }


    public static void main(String[] args) {
        launch();
    }
}