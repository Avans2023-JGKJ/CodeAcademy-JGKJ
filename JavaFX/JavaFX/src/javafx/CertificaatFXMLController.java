package javafx;

import Objects.Certificaat;
import Java2Database.DataBaseSQL;
import Objects.Inschrijven;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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

public class CertificaatFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private TableView<Certificaat> CertificaatTableView;

    @FXML
    void CertificaatAanmakenClicked(ActionEvent event) {

    }

    @FXML
    void CertificaatVerwijderenClicked(ActionEvent event) {
    loadTableInschrijven();
    }

    @FXML
    void CertificaatBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    TableColumn<Certificaat, String> NaamCursistCertificaatColumn;
    @FXML
    TableColumn<Certificaat, Byte> BeoordelingCertificaatColumn;
    @FXML
    TableColumn<Certificaat, String> MedewerkerNummerCertificaatColumn;
    @FXML
    TableColumn<Certificaat, Integer> InschrijfIdCertificaatColumn;

    ObservableList<Certificaat> observableCertificaat;

    private void initTable() {
        observableCertificaat = FXCollections.observableArrayList();
        NaamCursistCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursist"));
        BeoordelingCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("beoordeling"));
        MedewerkerNummerCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("medeWerkerNaam"));
        InschrijfIdCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("inschrijfId"));

    }

    public void loadTableInschrijven() {
        try {
//            , totaalVoortgang
            System.out.println("test");
            initTable();
            System.out.println("test");
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT beoordeling, medewerkerNaam, inschrijfId FROM Certificaat").executeQuery()) {
                while (rs.next()) {
                    Certificaat Certificaat = new Certificaat();
                    Certificaat.setInschrijfId(rs.getInt("inschrijfId"));
                    Certificaat.setNaamCursist(Certificaat.getNaamCursist());
                    Certificaat.setBeoordeling(rs.getByte("beoordeling"));
                    Certificaat.setMedeWerkerNaam(rs.getString("medewerkerNaam"));
                    
                    System.out.println(Certificaat.getNaamCursist());
                    
        observableCertificaat.add(Certificaat); 
                }
            }
            System.out.println("PUNT 4");
            CertificaatTableView.setItems(observableCertificaat);
            CertificaatTableView.refresh();
            System.out.println("PUNT 5");

        } catch (SQLException ex) {
            Logger.getLogger(CertificaatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
