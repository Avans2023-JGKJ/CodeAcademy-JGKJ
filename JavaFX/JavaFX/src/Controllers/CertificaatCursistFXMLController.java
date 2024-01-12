package Controllers;

import Java2Database.DataShare;

import Objects.Certificaat;
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

public class CertificaatCursistFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Certificaat> CertificaatTableView;
    @FXML
    TableColumn<Certificaat, String> NaamCursusCertificaatColumn;
    @FXML
    TableColumn<Certificaat, Byte> BeoordelingCertificaatColumn;
    @FXML
    TableColumn<Certificaat, String> MedewerkerNummerCertificaatColumn;

    ObservableList<Certificaat> observableCertificaat;

    //Initialize wordt aangeroepen bij het inladen van de pagina
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableCertificaat();
    }

    //Deze methode bepaalt de kolommen van de tableview
    private void initTable() {
        observableCertificaat = FXCollections.observableArrayList();
        NaamCursusCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        BeoordelingCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("beoordeling"));
        MedewerkerNummerCertificaatColumn.setCellValueFactory(new PropertyValueFactory<>("medeWerkerNaam"));
    }

    //Deze methode laad de tableview met de gewenste data
    public void loadTableCertificaat() {
        try {
            initTable();
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT naamCursus, beoordeling, medewerkerNaam "
                    + "FROM Certificaat c JOIN Inschrijven i ON i.inschrijfId = c.inschrijfId "
                    + "WHERE i.email = '" + DataShare.getInstance().getCursistEmail() + "'").executeQuery()) {
                while (rs.next()) {
                    Certificaat Certificaat = new Certificaat();
                    Certificaat.setNaamCursus(rs.getString("naamCursus"));
                    Certificaat.setBeoordeling(rs.getByte("beoordeling"));
                    Certificaat.setMedeWerkerNaam(rs.getString("medewerkerNaam"));;
                    observableCertificaat.add(Certificaat);
                }
            }
            CertificaatTableView.setItems(observableCertificaat);
            CertificaatTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CertificaatCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        //De terugknop voor de pagina
    @FXML
    void CertificaatBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenCursist.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
