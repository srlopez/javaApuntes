
import java.net.URL;

import dominio.Sistema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistencia.IDAL;
import persistencia.RepoMFile;
import persistencia.RepoSQLite;
import ui.ControladorJavaFX;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Preparamos los objetos que van a interaccionar
            // IDAL repo = new RepoSQLite();
            IDAL repo = new RepoMFile();
            Sistema sistema = new Sistema(repo);
            // Controller
            ControladorJavaFX controller = new ControladorJavaFX(sistema);
            // Vista
            URL location = getClass().getResource("ui/VistaJavaFX.fxml");
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