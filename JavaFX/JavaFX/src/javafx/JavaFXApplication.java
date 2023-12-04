
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import static javafx.application.Application.launch;
import javafx.application.Application;

public class JavaFXApplication extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(new URL("file:///C:\\Users\\Jochem\\Documents\\GitHub\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
//        loader.setLocation(new URL("file:///C:\\Users\\gijsv\\Documents\\GitHub\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
//Gijs Pc
        loader.setLocation(new URL("file:///C:\\School\\CodeAcademy-JGKJ\\JavaFX\\JavaFX\\src\\javafx\\login.fxml"));
//Gijs laptop
        
        VBox vbox = loader.<VBox>load();

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
