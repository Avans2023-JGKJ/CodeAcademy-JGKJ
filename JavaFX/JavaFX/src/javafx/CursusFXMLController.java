package javafx;

import Java2Database.DataBaseSQL;
import Objects.Cursus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursusFXMLController implements Initializable {

    @FXML
    private TableView<Cursus> CursusTableView;

    @FXML
    private TableColumn<Cursus, String> naamCursusCursusColumn;

    @FXML
    private TableColumn<Cursus, Short> aantalContentItemsCursusColumn;

    @FXML
    private TableColumn<Cursus, String> onderwerpCursusColumn;

    @FXML
    private TableColumn<Cursus, String> introductieTekstCursusColumn;

    @FXML
    private TableColumn<Cursus, String> niveauCursusColumn;

    private ObservableList<Cursus> observableCursus;

    private Stage stage;
    private Scene scene;
    private Parent root;

        @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableCursus = FXCollections.observableArrayList();
        naamCursusCursusColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        aantalContentItemsCursusColumn.setCellValueFactory(new PropertyValueFactory<>("aantalContentItems"));
        onderwerpCursusColumn.setCellValueFactory(new PropertyValueFactory<>("onderwerp"));
        introductieTekstCursusColumn.setCellValueFactory(new PropertyValueFactory<>("introductieTekst"));
        niveauCursusColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        loadTableCursus();
    }

    public void loadTableCursus() {
        observableCursus.clear();
        try (ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Cursus").executeQuery()) {
            while (rs.next()) {
                Cursus cursus = new Cursus();
                cursus.setNaamCursus(rs.getString("naamCursus"));
                cursus.setAantalContentItems((short) rs.getInt("aantalContentItems"));
                cursus.setOnderwerp(rs.getString("onderwerp"));
                cursus.setIntroductieTekst(rs.getString("introductieTekst"));
                cursus.setNiveau(rs.getString("niveau"));
                observableCursus.add(cursus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CursusTableView.setItems(observableCursus);
    }

    @FXML
    void CursusAanmakenClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createCursusDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursusFXMLController createCursusController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                createCursusController.FinishButtonCreateCursusClicked();
                loadTableCursus();
            }

            dialog.setTitle("Cursus aanmaken");

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusAanpassenClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCursusDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursusFXMLController updateCursusController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                updateCursusController.FinishButtonUpdateCursusClicked();
                loadTableCursus();
            }

            dialog.setTitle("Cursus aanpassen");

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusVerwijderenClicked(ActionEvent event) {
        Cursus selectedCursus = CursusTableView.getSelectionModel().getSelectedItem();
        if (selectedCursus != null) {
            if (confirmDelete(selectedCursus)) {
                try {
                    String deleteSQL = "DELETE FROM Cursus WHERE naamCursus = ?";
                    try (PreparedStatement stmt = DataBaseSQL.createConnection().prepareStatement(deleteSQL)) {
                        stmt.setString(1, selectedCursus.getNaamCursus());
                        stmt.executeUpdate();
                        loadTableCursus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            showAlert("Geen Cursus Geselecteerd", "Selecteer alstublieft een cursus uit de lijst om te verwijderen.");
        }
    }

    @FXML
    void CursusBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean confirmDelete(Cursus cursus) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bevestig Verwijdering");
        alert.setHeaderText("Cursus Verwijderen");
        alert.setContentText("Weet u zeker dat u de cursus: " + cursus.getNaamCursus() + " wilt verwijderen?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
