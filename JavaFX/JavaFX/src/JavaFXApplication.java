import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.image.Image;

public class JavaFXApplication extends Application {

    //Main methode, zorgt voor de start van de applicatie
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
         

    @Override //Start methode zorgt voor de weergave van het eerste scherm: Login
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        // Pad relatief aan de project directory
        Path path = Paths.get("src/FXML_Bestanden/login.fxml");
        URL url = path.toUri().toURL();
        
        loader.setLocation(url);
        
        VBox vbox = loader.<VBox>load();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResource("/FXML_Bestanden/Logo.png").toString()));
        primaryStage.show();
    }
}
