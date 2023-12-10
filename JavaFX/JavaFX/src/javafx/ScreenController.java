
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenController {

private static Stage stage;
private Scene scene;
private Parent root;

    public void switchToHomeScreen(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }
    
    

}
