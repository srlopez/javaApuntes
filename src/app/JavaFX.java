package app;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicios.IDAL;
import servicios.RepoMFile;
import servicios.RepoSQLite;
import sistema.Sistema;
import ui.javaFX.Controlador;

public class JavaFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Preparamos los objetos que van a interaccionar
            // IDAL repo = new RepoSQLite();
            IDAL repo = new RepoMFile();
            Sistema sistema = new Sistema(repo);
            // Controller
            Controlador controller = new Controlador(sistema);
            // Vista
            String path = "/ui/javaFX/Vista.fxml";
            URL location = getClass().getResource(path);
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            fxmlLoader.setController(controller);
            // RUN
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Control de Gastos");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}