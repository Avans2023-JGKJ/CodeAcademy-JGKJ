package Controllers;

import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import Objects.Cursus;
import Objects.Inschrijven;
import Objects.Niveau;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CursusScreenCursistFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Cursus> CursusTableView;

    @FXML
    private TableColumn<Cursus, Short> aantalContentItemsCursusColumn;

    @FXML
    private TableColumn<Inschrijven, LocalDate> DatumInschrijvenColumn;

    @FXML
    private TableColumn<Cursus, String> introductieTekstCursusColumn;

    @FXML
    private TableColumn<Cursus, String> naamCursusCursusColumn;

    @FXML
    private TableColumn<Niveau, Niveau> niveauCursusColumn;

    @FXML
    private ObservableList<Cursus> observableCursus;

    private void loadTableCursus() {
        try {
            initTable();
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT *\n"
                    + "FROM Inschrijven\n"
                    + "JOIN Cursus ON Cursus.naamCursus = Inschrijven.naamCursus\n"
                    + "WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'");
            while (rs.next()) {
                Cursus cursus = new Cursus();
                cursus.setNaamCursus(rs.getString("naamCursus"));
                cursus.setDatum(LocalDate.parse(rs.getString("datum")));
                cursus.setAantalContentItems(rs.getShort("aantalContentItems"));
                String niveauString = rs.getString("niveau");
                try {
                    Niveau niveau = Niveau.valueOf(niveauString.toUpperCase());
                    cursus.setNiveau(niveau);
                } catch (IllegalArgumentException e) {
                    Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE,
                            "Niveauwaarde uit de database komt niet overeen met enige enum-constanten " + niveauString, e);
                    continue; // toevoegen van deze cursus (aan de lijst) overslaan
                }
                cursus.setIntroductieTekst(rs.getString("introductieTekst"));

                observableCursus.add(cursus);
            }
            CursusTableView.setItems(observableCursus);
            CursusTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenCursist.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void rowClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/CursusInformatie.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadTableCursus();
    }

    private void initTable() {
        observableCursus = FXCollections.observableArrayList();
        naamCursusCursusColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        DatumInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        aantalContentItemsCursusColumn.setCellValueFactory(new PropertyValueFactory<>("aantalContentItems"));
        introductieTekstCursusColumn.setCellValueFactory(new PropertyValueFactory<>("introductieTekst"));
        niveauCursusColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
    }
}
