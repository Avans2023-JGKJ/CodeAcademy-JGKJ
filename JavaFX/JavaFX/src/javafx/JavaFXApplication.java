
import Objects.Certificaat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import static javafx.application.Application.launch;
import javafx.application.Application;

public class JavaFXApplication extends Application {

    public static void main(String[] args) throws SQLException {
        
//        Certificaat c = new Certificaat(0, Byte.valueOf("5"), "Karel");
//        System.out.println(c.getNaamCursist());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(new URL("file:///C:\\Users\\Jochem\\Documents\\GitHub\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
//        loader.setLocation(new URL("file:///C:\\Users\\gijsv\\Documents\\GitHub\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
//        loader.setLocation(new URL("file:///C:\\Users\\joche\\Documents\\GitHub\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
        loader.setLocation(new URL("file:///C:\\School\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
        VBox vbox = loader.<VBox>load();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
