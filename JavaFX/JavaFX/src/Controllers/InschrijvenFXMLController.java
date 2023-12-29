package Controllers;
import Java2Database.DataShare;

import Objects.Inschrijven;

import Java2Database.DataBaseSQL;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InschrijvenFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private TableView<Inschrijven> InschrijvenTableView;

    @FXML
    void InschrijvenAanmakenClicked(ActionEvent event) {

    }

    @FXML
    void InschrijvenAanpassenClicked(ActionEvent event) {

    }

    @FXML
    void InschrijvenVerwijderenClicked(ActionEvent event) {
        loadTableInschrijven();
        System.out.println("test");
    }

    @FXML
    void InschrijvenBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    TableColumn<Inschrijven, String> emailInschrijvenColumn;
    @FXML
    TableColumn<Inschrijven, String> naamCursusInschrijvenColumn;
    @FXML
    TableColumn<Inschrijven, Integer> inschrijfIdInschrijvenColumn;
//    @FXML
//    TableColumn<Inschrijven, String> totaalVoortgangInschrijvenColumn;

    ObservableList<Inschrijven> observableInschrijven;

    private void initTable() {
        observableInschrijven = FXCollections.observableArrayList();
        emailInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        naamCursusInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        inschrijfIdInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("inschrijfId"));
//        totaalVoortgangInschrijvenColumn
    }

    public void loadTableInschrijven() {
        try {
//            , totaalVoortgang
            System.out.println("test");
            initTable();
            System.out.println("test");
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT email, naamCursus, inschrijfId FROM Inschrijven").executeQuery()) {
                while (rs.next()) {
                    Inschrijven Inschrijven = new Inschrijven();
                    Inschrijven.setEmail(rs.getString("email"));
                    Inschrijven.setNaamCursus(rs.getString("naamCursus"));
                    Inschrijven.setInschrijfId(rs.getInt("inschrijfId"));
//                    Inschrijven.setEmail//totaalVoortgang maar deze is nog niet af
        //(rs.getString("email"));
        observableInschrijven.add(Inschrijven); 
                }
            }
            System.out.println("PUNT 4");
            InschrijvenTableView.setItems(observableInschrijven);
            InschrijvenTableView.refresh();
            System.out.println("PUNT 5");

        } catch (SQLException ex) {
            Logger.getLogger(InschrijvenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
