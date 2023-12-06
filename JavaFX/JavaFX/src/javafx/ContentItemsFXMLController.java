package javafx;

import Java2Database.DataBaseSQL;
import Objects.ContentItem;
import Objects.Status;
//import Objects.Module;
//import Objects.Webcast;
//import Objects.Inschrijven;
//import Objects.Status;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ContentItemsFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private TableView<ContentItem> ContentItemTableView;

    @FXML
    void ContentItemsAanmakenClicked(ActionEvent event) {

    }

    @FXML
    void ContentItemsAanpassenClicked(ActionEvent event) {
        loadTableContentItems();
    }

    @FXML
    void ContentItemsVerwijderenClicked(ActionEvent event) {
        loadTableContentItems();
    }

    @FXML
    TableColumn<ContentItem, Integer> contentItemsIdColumn;
    @FXML
    TableColumn<ContentItem, String> contentItemsTitelColumn;
    @FXML
    TableColumn<ContentItem, LocalDate> contentItemsDatumColumn;
    @FXML
    TableColumn<ContentItem, String> contentItemsStatusColumn;

    ObservableList<ContentItem> observableContentItem;

    private void initTable() {
        observableContentItem = FXCollections.observableArrayList(); // Initialize the list
        contentItemsIdColumn.setCellValueFactory(new PropertyValueFactory<>("contentItemId"));
        contentItemsTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        contentItemsDatumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        contentItemsStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    void loadTableContentItems() {
        try {
            System.out.println("test1");
            initTable();
            System.out.println("test2");
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM contentItems").executeQuery()) {
                while (rs.next()) {
                    ContentItem ContentItem = new ContentItem();
                    System.out.println("TESTING");
                    ContentItem.setContentItemId(rs.getInt("contentItemId"));
                    ContentItem.setTitel(rs.getString("titel"));
                    ContentItem.setDatum(LocalDate.parse(rs.getString("datum")));
                    ContentItem.setStatus(rs.getString("status"));

                    System.out.println("Printline");
                    observableContentItem.add(ContentItem);

                }
            }
            System.out.println("PUNT 4");
            ContentItemTableView.setItems(observableContentItem);
            System.out.println("PUNT 5");
            ContentItemTableView.refresh();
            System.out.println("PUNT 6");

        } catch (SQLException ex) {
            Logger.getLogger(InschrijvenFXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ContentItemsBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
